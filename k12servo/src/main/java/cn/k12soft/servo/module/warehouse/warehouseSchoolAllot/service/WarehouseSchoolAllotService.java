package cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.AllotType;
import cn.k12soft.servo.domain.enumeration.WareHouseSuper;
import cn.k12soft.servo.domain.enumeration.WarehouseOperationtype;
import cn.k12soft.servo.module.warehouse.warehouseKlass.domian.WarehouseKlass;
import cn.k12soft.servo.module.warehouse.warehouseKlass.repository.WarehouseKlassRepository;
import cn.k12soft.servo.module.warehouse.warehouseKlassLog.domain.WarehouseKlassLog;
import cn.k12soft.servo.module.warehouse.warehouseKlassLog.repository.WarehouseKlassLogRepository;
import cn.k12soft.servo.module.warehouse.warehouseSchool.domain.WarehouseSchool;
import cn.k12soft.servo.module.warehouse.warehouseSchool.repository.WarehouseSchoolRepository;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.WarehouseSchoolAllot;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.dto.WarehouseSchoolAllotDTO;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.form.WarehouseSchoolAllotForm;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.repository.WarehouseSchoolAllotRepository;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.service.mapper.WarehouseSchoolAllotMapper;
import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.domain.WarehouseSchoolLog;
import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.repository.WarehouseSchoolAllotLogRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class WarehouseSchoolAllotService extends AbstractRepositoryService<WarehouseSchoolAllot, Long, WarehouseSchoolAllotRepository>{

    private final UserRepository userRepository;
    private final KlassRepository klassRepository;
    private final WarehouseKlassRepository warehouseKlassRepository;
    private final WarehouseSchoolRepository warehouseSchoolRepository;
    private final WarehouseSchoolAllotMapper warehouseSchoolAllotMapper;
    private final WarehouseKlassLogRepository warehouseKlassLogRepository;
    private final WarehouseSchoolAllotLogRepository warehouseSchoolLogRepository;

    @Autowired
    protected WarehouseSchoolAllotService(WarehouseSchoolAllotRepository repository, UserRepository userRepository, KlassRepository klassRepository, WarehouseKlassRepository warehouseKlassRepository, WarehouseSchoolRepository warehouseSchoolRepository, WarehouseSchoolAllotMapper warehouseSchoolAllotMapper, WarehouseKlassLogRepository warehouseKlassLogRepository, WarehouseSchoolAllotLogRepository warehouseSchoolLogRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.klassRepository = klassRepository;
        this.warehouseKlassRepository = warehouseKlassRepository;
        this.warehouseSchoolRepository = warehouseSchoolRepository;
        this.warehouseSchoolAllotMapper = warehouseSchoolAllotMapper;
        this.warehouseKlassLogRepository = warehouseKlassLogRepository;
        this.warehouseSchoolLogRepository = warehouseSchoolLogRepository;
    }

    public List<WarehouseSchoolAllot> create(Actor actor, List<WarehouseSchoolAllotForm> formList) {
        Integer schoolId = actor.getSchoolId();

        List<WarehouseSchoolAllot> list = new ArrayList<>();
        User user = userRepository.findOne(actor.getUserId());
        for (WarehouseSchoolAllotForm form : formList){
            String isbn = form.getIsbn();
            Klass klass = null;
            WarehouseSchool warehouseSchool = this.warehouseSchoolRepository.findAllByIsbnAndSchoolId(isbn, schoolId);
            Integer allot = form.getAllotNum();
            AllotType type = form.getType();

            Integer oldNum = warehouseSchool.getNum();
            Integer newNum = oldNum - allot;
            warehouseSchool.setNum(newNum);

            if (newNum < 0){
                throw new IllegalArgumentException("库存不足，不能分配");
            }

            switch (type) {
                case DEPT:
                    break;
                case KLASS:
                    klass = this.klassRepository.findOne(Integer.valueOf(form.getApplyBy()));
                    WarehouseKlass warehouseKlass = this.warehouseKlassRepository.findAllBySchoolIdAndKlass(schoolId, klass);
//                    if (klass == null){
//                        throw new IllegalArgumentException("班级没有找到，请核对");
//                    }
                    if (warehouseKlass == null){
                        warehouseKlass = new WarehouseKlass(
                                form.getName(),
                                form.getIsbn(),
                                form.getSpec(),
                                Instant.now(),
                                0,
                                warehouseSchool.getSubclass(),
                                WareHouseSuper.KLASS,
                                klass,
                                form.getPrice(),
                                schoolId,
                                null,
                                Instant.now()
                                );
                        this.warehouseKlassRepository.save(warehouseKlass);
                    }
                    break;
            }

            WarehouseSchoolAllot warehouseSchoolAllot = new WarehouseSchoolAllot(
                    form.getName(),
                    form.getIsbn(),
                    form.getSpec(),
                    Instant.now(),
                    newNum,      // 剩余库存
                    form.getAllotNum(),
                    type,
                    form.getApplyBy(),
                    form.getPrice(),
                    false,
                    user,
                    null,
                    Instant.now(),
                    schoolId
            );
            list.add(this.getRepository().save(warehouseSchoolAllot));
            warehouseSchoolRepository.save(warehouseSchool);

            WarehouseSchoolLog log = new WarehouseSchoolLog(
                    form.getName(),
                    form.getIsbn(),
                    form.getSpec(),
                    form.getPrice(),
                    oldNum,
                    newNum,
                    allot,
                    WarehouseOperationtype.UPDATEBY,
                    user,
                    Instant.now(),
                    schoolId,
                    klass,
                    null
            );
            warehouseSchoolLogRepository.save(log);
        }
        return list;
    }

    public void getIt(Actor actor, String ids) {
        Integer schoolId = actor.getSchoolId();
        String[] idx = ids.split(",");
        User user = this.userRepository.findOne(actor.getUserId());
        for (String id : idx){
            WarehouseSchoolAllot allot = this.getRepository().findOne(Long.parseLong(id));
            if (allot == null){
                continue;
            }
            AllotType type = allot.getType();
            String idz = allot.getApplyBy();
            allot.setAllot(true);
            allot.setAllotBy(user);
            this.getRepository().save(allot);

            int num = allot.getAllotNum();

            switch (type){
                case DEPT:
                    break;
                case KLASS:
                    Klass klass = this.klassRepository.findOne(Integer.valueOf(idz));
                    // 班级库存调整
                    WarehouseKlass ware = this.warehouseKlassRepository.findAllBySchoolIdAndKlass(schoolId, klass);
                    Integer oldNum = ware.getNum();
                    Integer newNum = oldNum + num;
                    ware.setSuperclass(WareHouseSuper.KLASS);
                    ware.setNum(newNum);
                    warehouseKlassRepository.save(ware);

                    // 记录调整
                    WarehouseKlassLog log = new WarehouseKlassLog(
                            ware.getName(),
                            ware.getIsbn(),
                            ware.getSpec(),
                            ware.getPrice(),
                            oldNum,
                            newNum,
                            num,
                            WarehouseOperationtype.UPDATEBY,
                            user,
                            Instant.now(),
                            schoolId,
                            klass
                    );
                    warehouseKlassLogRepository.save(log);

                    break;
            }
        }
    }

    public Collection<WarehouseSchoolAllotDTO> findBy(Actor actor, LocalDate localDate) {
        Instant first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
        return warehouseSchoolAllotMapper.toDTOs(this.getRepository().findAllBySchoolIdAndCreatedAtBetween(actor.getSchoolId(), first, second, new Sort(Sort.Direction.ASC, "isAllot")));
    }

    public void deleteBy(Actor actor, String ids) {
        String[] idx = ids.split(",");
        for (String id : idx){
            WarehouseSchoolAllot allot = this.get(Long.parseLong(id));
            this.getRepository().delete(allot);
        }
    }
}
