package cn.k12soft.servo.module.feeDetails.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Guardian;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.commmontest.Commonmxc;
import cn.k12soft.servo.module.feeCollect.domain.FeeCollect;
import cn.k12soft.servo.module.feeCollect.service.FeeCollectService;
import cn.k12soft.servo.module.feeDetails.domain.FeeDetails;
import cn.k12soft.servo.module.feeDetails.domain.FeeDetailsLog;
import cn.k12soft.servo.module.feeDetails.domain.form.FeeDetailsForm;
import cn.k12soft.servo.module.feeDetails.repository.FeeDetailsLogRepository;
import cn.k12soft.servo.module.feeDetails.repository.FeeDetailsRepository;
import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import cn.k12soft.servo.module.vedioMonitor.domain.HKDevice;
import cn.k12soft.servo.module.vedioMonitor.domain.HKUser;
import cn.k12soft.servo.module.vedioMonitor.repository.HKDeviceRepository;
import cn.k12soft.servo.module.vedioMonitor.rest.HKUserManagement;
import cn.k12soft.servo.module.vedioMonitor.service.HKUserService;
import cn.k12soft.servo.module.weixin.pay.WeixinPayOrder;
import cn.k12soft.servo.module.weixin.pay.WeixinPayOrderRepository;
import cn.k12soft.servo.repository.GuardianRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.util.CommonUtils;
import cn.k12soft.servo.util.HttpUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static cn.k12soft.servo.module.feeDetails.service.FeeDetailsService.RENEW.PAY;

@Service
@Transactional
public class FeeDetailsService extends AbstractRepositoryService<FeeDetails, Long, FeeDetailsRepository>{

    public enum RENEW{
        PAY,    // 已缴费
        RENEW   // 续费
    }

    private final Logger log = LoggerFactory.getLogger(FeeDetailsService.class);
    private final UserRepository userRepository;
    private final FeeDetailsLogRepository feeDetailsLogRepository;
    private final HKUserManagement hkUserManagement;
    private final HKUserService hkUserService;
    private final HKDeviceRepository hkDeviceRepository;
    private final GuardianRepository guardianRepository;
    private final WeixinPayOrderRepository weixinPayOrderRepository;
    private final FeeCollectService feeCollectService;

    protected FeeDetailsService(FeeDetailsRepository repository, UserRepository userRepository, FeeDetailsLogRepository feeDetailsLogRepository, HKUserManagement hkUserManagement, HKUserService hkUserService, HKDeviceRepository hkDeviceRepository, GuardianRepository guardianRepository, WeixinPayOrderRepository weixinPayOrderRepository, FeeCollectService feeCollectService) {
        super(repository);
        this.userRepository = userRepository;
        this.feeDetailsLogRepository = feeDetailsLogRepository;
        this.hkUserManagement = hkUserManagement;
        this.hkUserService = hkUserService;
        this.hkDeviceRepository = hkDeviceRepository;
        this.guardianRepository = guardianRepository;
        this.weixinPayOrderRepository = weixinPayOrderRepository;
        this.feeCollectService = feeCollectService;
    }

    private final static String ADD_PERMISSION_URL = "https://open.ys7.com/api/lapp/ram/statement/add";

    public FeeDetails create(Actor actor, FeeDetailsForm form) {

        boolean isPay = true;
    // 通过微信支付账单支付是否成功判断是否继续
        WeixinPayOrder weixinPayOrder = weixinPayOrderRepository.findByOrderId(form.getWxOrder());
        if (weixinPayOrder.getState() != 2 ){
            isPay = false;
            throw new IllegalArgumentException("Payment not completed or failed");  // 支付未完成或者支付失败
        }

        Integer schoolId = actor.getSchoolId();
        String mobile = form.getMobile();
        Optional<User> userOpt = userRepository.findByMobile(mobile);
        User user = userOpt.get();

        // 查询本地有没有海康视频账户
        HKUser hkUser = hkUserService.findOneUser(schoolId, user.getId());

        Map<String, Object> mapToken = this.hkUserManagement.getToken(actor);
        Integer userId = user.getId();
        Integer stuId = form.getStudentId();
        String hkToken = mapToken.get("accessToken").toString();
        String stuName = form.getStuName();
        String accountName = "hk_bdqa_" + user.getId();
        String pwd = "123456";
        String MD5PWD = Commonmxc.StringToMd5(pwd);

        // 没有就创建一个
        if (hkUser == null){
            try{
                // 创建子账户(家长), 线上线下一块
                hkUser = this.hkUserManagement.createhkAccount(actor, userId, stuId, hkToken, stuName, accountName, MD5PWD);
            }catch (Exception e){
                System.out.println(e);
            }
        }

        if (isPay) {
            // 有就添加权限，本地更新
            hkUser.setState(1);
            hkUser = hkUserManagement.getSave(hkUser);
            CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
                // 添加海康线上账户权限
                addOnLinePolicy(actor, form.getStudentId(), hkToken);
            });
        }
        Instant startTime = Instant.ofEpochMilli(hkUser.getStartTime());
        Instant endTime = Instant.ofEpochMilli(getEndTime(hkUser, form.getFeePeriodtype()));

        FeeDetails feeDetails = new FeeDetails(
                "视频监控费用",
                user,
                form.getStudentId(),
                form.getStuName(),
                form.getMoney(),
                form.getScopt(),
                form.getFeeType(),
                form.getFeePeriodtype(),
                PAY,
                startTime,
                endTime,
                true,
                actor.getSchoolId(),
                Instant.now(),
                actor.getId()
        );

        FeeDetails feeDetails1 = this.getRepository().save(feeDetails);
        CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
            feeCollectService.create(feeDetails.getName(),feeDetails.getMoney(), feeDetails.getScopt(), feeDetails.getFeeType(), feeDetails.getId().intValue());
        });

        return feeDetails1;
    }

    // 有效期时间处理
    public Long getEndTime(HKUser hkUser, FeePeriodType type){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(hkUser.getStartTime());
        Long startTime = hkUser.getStartTime();
        Long endTime = new Long(0);

        Integer yearDay = null;
        Long yearDayLong = null;
        switch (type){
            case YEAR:
                yearDay = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
                yearDayLong = new Long(yearDay * 3600 * 24 * 1000);    // 毫秒
                endTime = startTime + yearDayLong;
                break;
            case SEMESTER:
                yearDay = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
                yearDayLong = new Long(yearDay * 3600 * 24 * 1000);    // 毫秒
                endTime = startTime + yearDayLong/2;
                break;
            case MONTH:
                calendar.add(Calendar.MONTH ,1);
                endTime = calendar.getTimeInMillis();
                break;
            case WEEK:
                calendar.add(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
                endTime = calendar.getTimeInMillis();
                break;
            case DAY:
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                endTime = calendar.getTimeInMillis();
                break;
        }
        return endTime;
    }

    public String addPolicy(HKUser hkUser, String token, String deviceId){
        if(hkUser == null){
            System.out.println("addhkpermission hkUser is null");
            return null;
        }

        int klassId = hkUser.getKlassId();
        HKDevice hkDevice = this.hkDeviceRepository.findByDeviceIdAndKlassId(deviceId, klassId);
        if(hkDevice == null){
            System.out.println("addhkpermission hkdevice is null");
            return null;
        }
        Set<Integer> hkDeviceIdSet = CommonUtils.convertContentToSet(Integer.class, hkUser.getHkDeviceIds());
        if(hkDeviceIdSet.contains(hkDevice.getHkdevId())){
            // 有权限了，不用再赋了
            return null;
        }

        String statement = "{\"Permission\": \"GET,Real\", \"Resource\": [\"dev:"+deviceId+"\"]}";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accessToken", token);
        paramMap.put("accountId", hkUser.getAccountId());
        paramMap.put("statement", statement);

        JSONObject jsonObject =  HttpUtil.doPost(ADD_PERMISSION_URL, paramMap);
        String code = jsonObject.getString("code");
        if(code.equals("200")){
            hkDeviceIdSet.add(hkDevice.getHkdevId());
            String hkDeviceIdStr = CommonUtils.convertSetToContent(hkDeviceIdSet);
            hkUser.setHkDeviceIds(hkDeviceIdStr);
            hkUser.setState(1); // 改为已授权
            hkUser.setStartTime(System.currentTimeMillis());// 授权开始时间
            this.hkUserService.save(hkUser);
            return code;
        }
        log.error(jsonObject.toString());
        return null;
    }

    public void addOnLinePolicy(Actor actor, Integer stuId, String hkToken){
        // 添加海康线上账户权限
        Guardian guardian = this.guardianRepository.findByPatriarchIdAndStudent_Id(actor.getId(), stuId);
        HKUser hkUser = this.hkUserService.findOneUser(actor.getSchoolId(), actor.getUserId());
        Integer klassId = guardian.getStudent().getKlass().getId();
        Integer[] ids = hkDeviceRepository.queryIdByKlassId(klassId);
        for (Integer derviceId : ids){
            CompletableFuture completableFuture1 = CompletableFuture.runAsync(()-> {
                try{
                    addPolicy(hkUser, hkToken, String.valueOf(derviceId));
                }catch(Exception e){
                    log.error(e.toString());
                }
            });
        }
    }

    public void deleteBy(Actor actor, String ids) {
        String[] ida = ids.split(",");
        for(String id : ida){
            FeeDetails feeDetails = this.getRepository().findOne(Long.parseLong(id));
            this.getRepository().delete(feeDetails);
        }
    }

    public FeeDetails putBy(Actor actor, Integer id, FeeDetailsForm form) {
        FeeDetails feeDetails = this.getRepository().findOne(Long.parseLong(id.toString()));
        HKUser hkUser = hkUserService.findByUserId(feeDetails.getUserKlient().getId());
        Instant endTime = Instant.ofEpochMilli(getEndTime(hkUser, form.getFeePeriodtype()));
        feeDetails.setEndTime(endTime);
        feeDetails.setMoney(feeDetails.getMoney() + form.getMoney());
        feeDetails.setRenew(RENEW.RENEW);
        feeDetails.setState(true);
        if (form.getFeePeriodtype() != null){
            feeDetails.setFeePeriodtype(form.getFeePeriodtype());
        }
        if (form.getFeeType() != null){
            feeDetails.setFeeType(form.getFeeType());
        }
        FeeDetails feeDetailsOne = this.getRepository().save(feeDetails);


        Map<String, Object> mapToken = this.hkUserManagement.getToken(actor);
        String hkToken = mapToken.get("accessToken").toString();
        CompletableFuture completableFuture = CompletableFuture.runAsync(()->{
            // 添加海康线上账户权限
            addOnLinePolicy(actor, form.getStudentId(), hkToken);
        });

        FeeDetailsLog feeDetailsLog = new FeeDetailsLog(
                feeDetails.getUserKlient(),
                form.getStudentId(),
                form.getStuName(),
                form.getMoney(),
                feeDetails.getScopt(),
                feeDetails.getFeeType(),
                feeDetails.getFeePeriodtype(),
                feeDetails.getStartTime(),
                feeDetails.getEndTime(),
                feeDetailsOne.getEndTime(),
                actor.getSchoolId(),
                Instant.now(),
                actor.getId()
        );
        this.feeDetailsLogRepository.save(feeDetailsLog);
        return feeDetailsOne;
    }

    public List<FeeDetails> findBy(Actor actor, Integer actorId, LocalDate localDate) {
        Integer schoolId = actor.getSchoolId();
        Instant first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
        List<FeeDetails> list = actorId == null
                ? this.getRepository().findAllBySchoolIdAndCreatedAtBetween(schoolId, first, second)
                : this.getRepository().findAllBySchoolIdAndCreatedByAndCreatedAtBetween(schoolId, actorId, first, second);
        return list;
    }

    public List<FeeDetailsLog> findByLog(Actor actor, Integer actorId, LocalDate localDate) {
        Integer schoolId = actor.getSchoolId();
        Instant first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
        List<FeeDetailsLog> list = actorId == null
                ? this.feeDetailsLogRepository.findAllBySchoolIdAndCreatedAtBetween(schoolId, first, second)
                : this.feeDetailsLogRepository.findAllBySchoolIdAndCreatedByAndCreatedAtBetween(schoolId, actorId, first, second);
        return list;
    }

    public FeeDetails findByOne(Actor actor) {
        return this.getRepository().findOneByCreatedByAndState(actor.getId(), true);
    }


    public boolean isLimit(Actor actor) {
        boolean is = false;
        User user = userRepository.findOne(actor.getUserId());
        HKUser hkUser = hkUserService.findOneUser(actor.getSchoolId(), user.getId());
        // 1、授权 0、未授权
        if (hkUser.getState() == 1){
            is = true;
        }
        return is;
    }

}
