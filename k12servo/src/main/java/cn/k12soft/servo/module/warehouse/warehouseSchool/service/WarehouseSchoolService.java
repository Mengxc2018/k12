package cn.k12soft.servo.module.warehouse.warehouseSchool.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.WareHouseSuper;
import cn.k12soft.servo.domain.enumeration.WarehouseOperationtype;
import cn.k12soft.servo.domain.enumeration.WarehouseSubclass;
import cn.k12soft.servo.module.revenue.domain.Payout;
import cn.k12soft.servo.module.revenue.domain.PayoutMainType;
import cn.k12soft.servo.module.revenue.domain.PayoutSubType;
import cn.k12soft.servo.module.revenue.repository.PayoutMainTypeRepository;
import cn.k12soft.servo.module.revenue.repository.PayoutRepository;
import cn.k12soft.servo.module.revenue.repository.PayoutSubTypeRepository;
import cn.k12soft.servo.module.revenue.rest.RevenueManagement;
import cn.k12soft.servo.module.warehouse.warehouse.domain.Warehouse;
import cn.k12soft.servo.module.warehouse.warehouse.repositopry.WarehouseRepository;
import cn.k12soft.servo.module.warehouse.warehouseSchool.domain.WarehouseSchool;
import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.domain.WarehouseSchoolLog;
import cn.k12soft.servo.module.warehouse.warehouseSchool.domain.form.WarehouseSchoolForm;
import cn.k12soft.servo.module.warehouse.warehouseSchool.domain.form.WarehouseSchoolUpdateForm;
import cn.k12soft.servo.module.warehouse.warehouseSchool.repository.WarehouseSchoolRepository;
import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.repository.WarehouseSchoolAllotLogRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.util.Times;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class WarehouseSchoolService extends AbstractRepositoryService<WarehouseSchool, Long, WarehouseSchoolRepository>{

    private final WarehouseRepository wareHouseRepository;
    private final UserRepository userRepository;
    private final WarehouseSchoolAllotLogRepository warehouseSchoolLogRepository;
    private final RevenueManagement revenueManagement;
    private final PayoutRepository payoutRepository;
    private final PayoutMainTypeRepository payoutMainTypeRepository;
    private final PayoutSubTypeRepository payoutSubTypeRepository;

    @Autowired
    protected WarehouseSchoolService(WarehouseSchoolRepository repository, WarehouseRepository wareHouseRepository, UserRepository userRepository, WarehouseSchoolAllotLogRepository warehouseSchoolLogRepository, RevenueManagement revenueManagement, PayoutRepository payoutRepository, PayoutMainTypeRepository payoutMainTypeRepository, PayoutSubTypeRepository payoutSubTypeRepository) {
        super(repository);
        this.wareHouseRepository = wareHouseRepository;
        this.userRepository = userRepository;
        this.warehouseSchoolLogRepository = warehouseSchoolLogRepository;
        this.revenueManagement = revenueManagement;
        this.payoutRepository = payoutRepository;
        this.payoutMainTypeRepository = payoutMainTypeRepository;
        this.payoutSubTypeRepository = payoutSubTypeRepository;
    }

    private final WarehouseOperationtype ADDBY = WarehouseOperationtype.ADDBY;
    private final WarehouseOperationtype DELETEBY = WarehouseOperationtype.DELETEBY;


    public void created(Actor actor, List<WarehouseSchoolForm> formList) {
        Integer schoolId = actor.getSchoolId();
        User user = userRepository.findOne(actor.getUserId());
        for (WarehouseSchoolForm form : formList){
            String isbn = form.getIsbn();
            String name = form.getName();
            Integer count = isbn == null
                    ? getRepository().countByNameAndSchoolId(name, schoolId)
                    : getRepository().countAllByIsbnAndSchoolId(isbn, schoolId);

            if (count == 0) {
                WarehouseSchool warehouseSchool = new WarehouseSchool(
                        name,
                        isbn,
                        form.getSpec(),
                        Instant.now(),
                        form.getPrice(),
                        form.getNum(),
                        form.getStandar(),
                        form.getSubclass(),
                        form.getSuperclass(),
                        schoolId,
                        user,
                        Instant.now(),
                        Instant.now()
                );
                getRepository().save(warehouseSchool);
            }else {
                throw new IllegalArgumentException("重复的物品名字或者isbn码");
            }

            // 如果匹配的库没有，就添加
            Integer count1 = isbn != null
                    ? wareHouseRepository.countByIsbn(isbn)
                    : wareHouseRepository.countByName(name);
            if (count1 == 0) {
                Warehouse warehouse = new Warehouse(
                        name,
                        isbn,
                        form.getSpec(),
                        form.getPrice(),
                        schoolId,
                        Instant.now()
                );
                wareHouseRepository.save(warehouse);
            }

            // 支出
            CompletableFuture completableFuture  = CompletableFuture.supplyAsync(()->{
                addPayOut(actor, JSONObject.fromObject(form));
                return null;
            });
        }

    }

    public List<WarehouseSchool> findAllBySchool(Actor actor, String name, String isbn) {
        Integer schoolId = actor.getSchoolId();
        List<WarehouseSchool> list = new ArrayList<>();
        if (!StringUtils.isEmpty(isbn)){
            list = this.getRepository().queryAllByIsbnAndSchoolId(isbn, schoolId);
        }else if(!StringUtils.isEmpty(name)){
            list = this.getRepository().queryAllByNameAndSchoolId(name, schoolId);
        }else{
            list = this.getRepository().findAllBySchoolId(actor.getSchoolId());
        }
        return list;
    }

    public List<WarehouseSchool> findAllBySchoolIdAndType(Actor actor, WarehouseSubclass subclass, WareHouseSuper superClass) {
        return getRepository().findAllBySchoolIdAndSubclassAndSuperclass(actor.getSchoolId(), subclass, superClass);
    }

    /**
     * 添加库存
     * @param actor
     * @param formList
     */
    public void addWarehouseSchool(Actor actor, List<WarehouseSchoolUpdateForm> formList) {
        Integer schoolId = actor.getSchoolId();
        User user = userRepository.findOne(actor.getUserId());
        for (WarehouseSchoolUpdateForm form : formList){
            String isbn = form.getIsbn();
            String name = form.getName();
            WarehouseSchool warehouseSchool = isbn == null
                    ? getRepository().findAllByNameAndSchoolId(name, schoolId)
                    : getRepository().findAllByIsbnAndSchoolId(isbn, schoolId);

            Integer num = form.getNum();
            Integer oldNum = warehouseSchool.getNum();
            Integer newNum = num + oldNum;

            // 处理库存
            warehouseSchool.setCreatedBy(user);
            warehouseSchool.setNum(newNum);
            warehouseSchool.setPrice(form.getPrice());
            getRepository().save(warehouseSchool);

            // 更新操作记录
            WarehouseSchoolLog warehouseSchoolLog = new WarehouseSchoolLog(
                    name,
                    isbn,
                    form.getSpec(),
                    form.getPrice(),
                    oldNum,
                    newNum,
                    num,
                    ADDBY,
                    user,
                    Instant.now(),
                    schoolId,
                    null,
                    form.getBuyAt()
            );
            warehouseSchoolLogRepository.save(warehouseSchoolLog);

            CompletableFuture completableFuture = CompletableFuture.supplyAsync( () -> {
                        this.addPayOut(actor, JSONObject.fromObject(form));
                        return null;
            });

        }

    }


    public void deleteByIds(Actor actor, String ids) {
        String[] idx = ids.split(",");
        for (String id : idx){
            WarehouseSchool warehouseSchool = getRepository().findOne(Long.parseLong(id));
            getRepository().delete(warehouseSchool);

            // 更新操作记录,删除操作
            User user = userRepository.findOne(actor.getUserId());
            WarehouseSchoolLog warehouseSchoolLog = new WarehouseSchoolLog(
                    warehouseSchool.getName(),
                    warehouseSchool.getIsbn(),
                    warehouseSchool.getSpec(),
                    warehouseSchool.getPrice(),
                    warehouseSchool.getNum(),
                    warehouseSchool.getNum(),
                    0,
                    DELETEBY,
                    user,
                    Instant.now(),
                    warehouseSchool.getSchoolId(),
                    null,
                    null
                    );
            warehouseSchoolLogRepository.save(warehouseSchoolLog);
        }
    }

    public void addPayOut(Actor actor, JSONObject jsonObject){
        Integer theYearMonth = Times.time2yyyyMM(System.currentTimeMillis());
//        JSONObject jsonObject = JSONObject.fromObject(form);
        Integer schoolId = actor.getSchoolId();
            String mainTypeName = "采购";
            PayoutMainType mainType = this.payoutMainTypeRepository.findByNameAndSchoolId("采购", schoolId);
            if (mainType == null){
                mainType = new PayoutMainType();
                mainType.setName(mainTypeName);
                mainType.setSchoolId(schoolId);
            }
            this.payoutMainTypeRepository.save(mainType);

            PayoutSubType subType = new PayoutSubType();
            subType.setName(jsonObject.get("name").toString());
            subType.setSchoolId(schoolId);
            subType.setPayoutMainType(mainType);
            this.payoutSubTypeRepository.save(subType);

            Float money = new BigDecimal(jsonObject.get("num").toString()).
                    multiply(new BigDecimal(jsonObject.get("price").toString())).floatValue();

            Payout payout = new Payout();
            payout.setMoney(money);
            payout.setPayoutSubType(subType);
            payout.setCreatedBy(actor);
            payout.setCreateAt(Instant.now());
            payout.setTheYearMonth(theYearMonth);
            payout.setSchoolId(schoolId);
            this.payoutRepository.save(payout);
    }
}
