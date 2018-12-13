package cn.k12soft.servo.module.vedioMonitor.rest;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.module.vedioMonitor.domain.HKDevice;
import cn.k12soft.servo.module.vedioMonitor.domain.HKUser;
import cn.k12soft.servo.module.vedioMonitor.domain.HKUserDTD;
import cn.k12soft.servo.module.vedioMonitor.domain.HKUserDTO;
import cn.k12soft.servo.module.vedioMonitor.service.HKDeviceService;
import cn.k12soft.servo.module.vedioMonitor.service.HKUserMapper;
import cn.k12soft.servo.module.vedioMonitor.service.HKUserService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.StudentService;
import cn.k12soft.servo.util.CommonUtils;
import cn.k12soft.servo.util.HttpUtil;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.*;

import static cn.k12soft.servo.domain.enumeration.Permission.HKDEVICE_CREATE;
import static cn.k12soft.servo.domain.enumeration.Permission.HKDEVICE_PUT;
import static cn.k12soft.servo.domain.enumeration.Permission.HKDEVICE_VIEW;

/**
 * Created by liubing on 2018/3/31
 */
@RestController
public class HKUserManagement {
    private final static String APPKEY = "9e204f14898048c2940e4a112caebcb9";
    private final static String APPSECERT = "a6425f61f46c5e0ca209bd344f1fa250";
    private final static String GET_TOKEN_URL = "https://open.ys7.com/api/lapp/token/get";
    private final static String CREATE_ACCOUNT_URL = "https://open.ys7.com/api/lapp/ram/account/create";
    private final static String ADD_PERMISSION_URL = "https://open.ys7.com/api/lapp/ram/statement/add";
    private final static String ADD_PERMISSION_STATEMENT = "{\n" +
            "    \"Statement\": [\n" +
            "        {\n" +
            "            \"Permission\": \"Get,Real\",\n" +
            "            \"Resource\": [\n" +
            "                \"dev:469631729\",\n" +
            "                \"cam:544229080:1\"\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    private final static String DEL_PERMISSION_URL = "https://open.ys7.com/api/lapp/ram/statement/delete";
    private final static String DEL_ACCOUNT_URL = "https://open.ys7.com/api/lapp/ram/statement/delete";
    private final static String GET_ACCOUNT_TOKEN = "https://open.ys7.com/api/lapp/ram/token/get";
    private final static String Get_ACCOUNT_INFO = "https://open.ys7.com/api/lapp/ram/account/get";

    private HKUserService hkUserService;
    private HKDeviceService hkDeviceService;
    private StudentService studentService;
    private final HKUserMapper hkUserMapper;

    @Autowired
    public HKUserManagement(HKUserService phkUserService,
                            HKDeviceService phkDeviceService,
                            StudentService studentService, HKUserMapper hkUserMapper) {
        this.hkUserService = phkUserService;
        this.hkDeviceService = phkDeviceService;
        this.studentService = studentService;
        this.hkUserMapper = hkUserMapper;
    }

    private final Logger log = LoggerFactory.getLogger(HKUserManagement.class);

    public JSONObject findOnLineOne(String accessToken, String accoundId, String accoundName){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accessToken", accessToken);
        paramMap.put("accoundId", accoundId);
        paramMap.put("accoundName", accoundName);
        return HttpUtil.doPost(Get_ACCOUNT_INFO, paramMap);
    }

    @ApiOperation("获取AccountId和DeviceIds")
    @GetMapping(value="/hkuser/findByUserId")
    @PermissionRequired(HKDEVICE_VIEW)
    @Timed
    public HKUserDTD find(@Active Actor actor, @RequestParam(value = "userId", required = true) Integer userId){
        HKUser hkUser = this.hkUserService.findByUserId(userId);
        if(hkUser == null){
            return null;
        }
        HKUserDTD hkUserDtd = HKUserDTD.create(hkUser);
        _fillDeviceIdList(hkUserDtd);
        return hkUserDtd;
    }

    private void _fillDeviceIdList(HKUserDTD hkUserDtd){
        if(hkUserDtd == null){
            return;
        }
        if(StringUtils.isBlank(hkUserDtd.getHkDeviceIds())){
            return;
        }
        Set<Integer> hkDeviceIdSet = CommonUtils.convertContentToSet(Integer.class, hkUserDtd.getHkDeviceIds());
        Set<HKDevice> hkDeviceSet = new HashSet<>();
        for (Integer hkDeviceId : hkDeviceIdSet) {
            HKDevice hkDevice = this.hkDeviceService.get(hkDeviceId);
            if(hkDevice != null){
                hkDeviceSet.add(hkDevice);
            }
        }
        hkUserDtd.setHkDeviceSet(hkDeviceSet);
    }

    @ApiOperation("请求token")
    @GetMapping(value="/hkuser/gethkToken")
    @PermissionRequired(HKDEVICE_VIEW)
    @Timed
    public Map<String, Object> getToken(@Active Actor actor){
//        if(!actor.getTypes().contains(ActorType.MANAGER) && !actor.getTypes().contains(ActorType.TEACHER)){
//            return null;
//        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appKey", APPKEY);
        paramMap.put("appSecret", APPSECERT);
        JSONObject jsonObject = HttpUtil.doPost(GET_TOKEN_URL, paramMap);
        try {
            JSONObject dataObj = jsonObject.getJSONObject("data");
            String accessToken = dataObj.getString("accessToken");
            long expireTime = dataObj.getLong("expireTime");
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("accessToken", accessToken);
            retMap.put("expireTime", expireTime);
            return retMap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("获取子账户token")
    @GetMapping(value="/hkuser/getAccessToken")
    @PermissionRequired(HKDEVICE_VIEW)
    @Timed
    public Map<String, Object> getAccessToken(@Active Actor actor,
                                              @RequestParam(value = "token", required = true) String token,
                                              @RequestParam(value = "userId", required = true) Integer userId){
        HKUser hkUser = this.hkUserService.findByUserId(userId);
        if(hkUser == null){
            System.out.println("delhkaccount hkUser is null");
            return null;
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accessToken", token);
        paramMap.put("accountId", hkUser.getAccountId());
        JSONObject jsonObject = HttpUtil.doPost(GET_ACCOUNT_TOKEN, paramMap);
        JSONObject dataObject =  jsonObject.getJSONObject("data");
        String retToken = dataObject.getString("accessToken");
        long expireTime = dataObject.getLong("expireTime");
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("expireTime", expireTime);
        retMap.put("accessToken", retToken);
        this.hkUserService.save(hkUser.setHkToken(retToken).setExpireTime(Instant.ofEpochMilli(expireTime)));
        return retMap;
    }

    @ApiOperation("获取子账户关联token")
    @GetMapping(value="/hkuser/getSubToken")
    @PermissionRequired(HKDEVICE_VIEW)
    @Timed
    public Map<String, Object> getSubToken(@Active Actor actor){

        String mainToken = null;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appKey", APPKEY);
        paramMap.put("appSecret", APPSECERT);
        JSONObject jsonObject = HttpUtil.doPost(GET_TOKEN_URL, paramMap);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        mainToken = dataObj.getString("accessToken");

        HKUser hkUser = this.hkUserService.findByUserId(actor.getUserId());
        if(hkUser == null){
            System.out.println("delhkaccount hkUser is null");
            return null;
        }

        if(mainToken != null){
            paramMap = new HashMap<>();
            paramMap.put("accessToken", mainToken);
            paramMap.put("accountId", hkUser.getAccountId());
            jsonObject = HttpUtil.doPost(GET_ACCOUNT_TOKEN, paramMap);
            JSONObject dataObject =  jsonObject.getJSONObject("data");
            String retToken = dataObject.getString("accessToken");
            long expireTime = dataObject.getLong("expireTime");
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("accessToken", retToken);
            retMap.put("expireTime", expireTime);
            return retMap;
        }

        return null;
    }



    @ApiOperation("创建子账户(家长)")
    @PostMapping(value = "/hkuser/createhkAccount")
    @PermissionRequired(HKDEVICE_CREATE)
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public HKUser createhkAccount(@Active Actor actor, @RequestParam(value = "userId", required = true) Integer userId,
                                @RequestParam(value = "stuId", required = true) Integer stuId,
                                @RequestParam(value = "token", required = true) String token,
                                @RequestParam(value = "stuName", required = true) String stuName,
                                @RequestParam(value = "accountName", required = true) String accountName,
                                @RequestParam(value = "password", required = true) String password){
    if(userId == null || userId <= 0){
            return null;
        }

        HKUser hkUser = this.hkUserService.findByUserId(userId);
        if(hkUser == null){

            String accountId = "";
            Student student = this.studentService.get(stuId);
            // 创建 HKUser
            hkUser = new HKUser();
            hkUser.setUserId(userId);
            hkUser.setAccountName(accountName);
            hkUser.setPwd(password);
            hkUser.setKlassId(student.getKlass().getId());
            hkUser.setStuId(stuId);
            hkUser.setStuName(stuName);
            hkUser.setState(0);
            hkUser.setHkDeviceIds("");
            hkUser.setStartTime(0l);
            hkUser.setSchoolId(actor.getSchoolId());
            hkUser.setCreateAt(Instant.now());

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("accessToken", token);
            paramMap.put("accountName", accountName);
            paramMap.put("password", password);
            JSONObject jsonObject = HttpUtil.doPost(CREATE_ACCOUNT_URL, paramMap);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            hkUser.setAccountId(dataObject.getString("accountId"));
            hkUser = this.hkUserService.save(hkUser);
        }
        return hkUser;

    }

    @ApiOperation("增加子账户权限")
    @PutMapping(value = "/hkuser/addhkPermission")
    @PermissionRequired(HKDEVICE_PUT)
    @Timed
    public HKUser addhkPermission(@Active Actor actor,
                                @RequestParam(value = "token", required = true) String token,
                                @RequestParam(value = "userId", required = true) Integer userId,
                                @RequestParam(value = "deviceId", required = true) String deviceId){
        HKUser hkUser = this.hkUserService.findByUserId(userId);
        if(hkUser == null){
            System.out.println("addhkpermission hkUser is null");
            return null;
        }

        int klassId = hkUser.getKlassId();
        HKDevice hkDevice = this.hkDeviceService.findByDeviceIdAndKlassId(deviceId, klassId);
        if(hkDevice == null){
            System.out.println("addhkpermission hkdevice is null");
            return hkUser;
        }
        Set<Integer> hkDeviceIdSet = CommonUtils.convertContentToSet(Integer.class, hkUser.getHkDeviceIds());
        if(hkDeviceIdSet.contains(hkDevice.getHkdevId())){
            // 有权限了，不用再赋了
            return hkUser;
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
            return hkUser;
        }
        System.out.println(jsonObject.toString());

        return null;
    }

    @ApiOperation("删除子账户权限")
    @DeleteMapping(value = "/hkuser/delhkPermission")
    @PermissionRequired(HKDEVICE_PUT)
    @Timed
    public void delhkPermission(@Active Actor actor,
                                @RequestParam(value = "token", required = true) String token,
                                @RequestParam(value = "userId", required = true) Integer userId,
                                @RequestParam(value = "deviceId", required = true) String deviceId){
        HKUser hkUser = this.hkUserService.findByUserId(userId);
        if(hkUser == null){
            System.out.println("delhkpermission hkUser is null");
            return;
        }

        HKDevice hkDevice = this.hkDeviceService.findByDeviceIdAndKlassId(deviceId, hkUser.getKlassId());
        if(hkDevice == null){
            System.out.println("delhkpermission hkdevice is null");
            return;
        }

        Set<Integer> hkDeviceIdSet = CommonUtils.convertContentToSet(Integer.class, hkUser.getHkDeviceIds());
        if(!hkDeviceIdSet.contains(hkDevice.getHkdevId())){
            System.out.println("delhkpermission deviceId is not permissioned");
            return;
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accessToken", token);
        paramMap.put("accountId", hkUser.getAccountId());
        paramMap.put("deviceSerial", deviceId);
        JSONObject jsonObject = HttpUtil.doPost(DEL_PERMISSION_URL, paramMap);
        String code = jsonObject.getString("code");
        if(code.equals("200")) {
            hkDeviceIdSet.remove(hkDevice.getHkdevId());
            hkUser.setHkDeviceIds(CommonUtils.convertSetToContent(hkDeviceIdSet));
            hkUser.setState(0);
            hkUser.setStartTime(0l);
            this.hkUserService.save(hkUser);
        }
    }

    @ApiOperation("删除子账户")
    @DeleteMapping(value = "/hkuser/delhkAccount")
    @PermissionRequired(HKDEVICE_PUT)
    @Timed
    public void delhkAccount(@Active Actor actor,
                             @RequestParam(value = "token", required = true) String token,
                             @RequestParam(value = "userId", required = true) Integer userId){
        HKUser hkUser = this.hkUserService.findByUserId(userId);
        if(hkUser == null){
            System.out.println("delhkaccount hkUser is null");
            return;
        }

        if(StringUtils.isBlank(hkUser.getAccountId())){
            System.out.println("delhkaccount accountid is null");
            return;
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accessToken", token);
        paramMap.put("accountId", hkUser.getAccountId());
        JSONObject jsonObject = HttpUtil.doPost(DEL_ACCOUNT_URL, paramMap);
        String code = jsonObject.getString("code");
        if(code.equals("200")) {
            this.hkUserService.delete(hkUser.getId());
        }
    }

    @ApiOperation("获取子账户Info")
    @GetMapping(value="/hkuser/getAccessAccount")
    @PermissionRequired(HKDEVICE_VIEW)
    @Timed
    public Map<String, Object> getAccessAccount(@Active Actor actor,
                                              @RequestParam(value = "token", required = true) String token,
                                                @RequestParam(value = "accountId", required = true) String accountId){

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accessToken", token);
        paramMap.put("accountId", accountId);
        JSONObject jsonObject = HttpUtil.doPost(GET_ACCOUNT_TOKEN, paramMap);
        JSONObject dataObject =  jsonObject.getJSONObject("data");
        String retToken = dataObject.getString("accessToken");
        long expireTime = dataObject.getLong("expireTime");
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("accessToken", retToken);
        retMap.put("expireTime", expireTime);
        return retMap;
    }


    @ApiOperation("查询用户列表")
    @GetMapping("/hkuser/findUsers")
    public Collection<HKUserDTO> findUsers(@Active Actor actor,
                                           @RequestParam @Valid Integer klassId){
        return hkUserMapper.toDTOs(this.hkUserService.findByAll(actor, klassId));
    }

    @ApiOperation("查询一位用户")
    @GetMapping("/findOneUser")
    public HKUserDTO findOneUser(@RequestParam @Valid Integer schoolId,
                              @RequestParam @Valid Integer userId){
        return hkUserMapper.toDTO(this.hkUserService.findOneUser(schoolId, userId));
    }


    public HKUser getSave(HKUser hkUser) {
        return this.hkUserService.save(hkUser);
    }
}