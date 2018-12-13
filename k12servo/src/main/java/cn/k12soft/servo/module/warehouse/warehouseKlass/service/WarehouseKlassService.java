package cn.k12soft.servo.module.warehouse.warehouseKlass.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.WarehouseOperationtype;
import cn.k12soft.servo.module.warehouse.warehouseKlass.domian.WarehouseKlass;
import cn.k12soft.servo.module.warehouse.warehouseKlass.domian.form.WarehouseKlassForm;
import cn.k12soft.servo.module.warehouse.warehouseKlass.repository.WarehouseKlassRepository;
import cn.k12soft.servo.module.warehouse.warehouseKlassLog.domain.WarehouseKlassLog;
import cn.k12soft.servo.module.warehouse.warehouseKlassLog.repository.WarehouseKlassLogRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WarehouseKlassService extends AbstractRepositoryService<WarehouseKlass, Long, WarehouseKlassRepository>{

    private final KlassRepository klassRepository;
    private final UserRepository userRepository;
    private final WarehouseKlassLogRepository warehouseKlassLogRepository;


    @Autowired
    protected WarehouseKlassService(WarehouseKlassRepository repository, KlassRepository klassRepository, UserRepository userRepository, WarehouseKlassLogRepository warehouseKlassLogRepository) {
        super(repository);
        this.klassRepository = klassRepository;
        this.userRepository = userRepository;
        this.warehouseKlassLogRepository = warehouseKlassLogRepository;
    }

    public List<WarehouseKlass> created(Actor actor, Integer klassId, List<WarehouseKlassForm> formList) {
        Integer schoolId = actor.getSchoolId();
        List<WarehouseKlass> list = new ArrayList<>();
        for (WarehouseKlassForm form : formList){
            WarehouseKlass warehouseKlass = new WarehouseKlass(
                    form.getName(),
                    form.getIsbn(),
                    form.getSpec(),
                    Instant.now(),
                    form.getNum(),
                    form.getSubclass(),
                    form.getSuperclass(),
                    klassRepository.findOne(form.getKlass()),
                    form.getPrice(),
                    schoolId,
                    Instant.now(),
                    Instant.now()
            );
            list.add(getRepository().save(warehouseKlass));
        }
        return list;
    }

    public List<WarehouseKlass> findByKlass(Actor actor, Integer klassId, String name, String isbn) {
        Integer schoolId = actor.getSchoolId();
        List<WarehouseKlass> list = new ArrayList<>();
        if (!StringUtils.isEmpty(isbn)){
            list = this.getRepository().findBySchoolIdAndKlassIdAndIsbn(schoolId, klassId, isbn);
        }else if(!StringUtils.isEmpty(name)){
            list = this.getRepository().findBySchoolIdAndKlassIdAndName(schoolId, klassId, name);
        }else {
            list = this.getRepository().findAllBySchoolIdAndKlassId(schoolId, klassId);
        }
        return list;
    }

    public void deleteBy(Actor actor, Integer klassId, String ids) {
        String[] idx = ids.split(",");
        Klass klass = klassRepository.findOne(klassId);
        for (String id : idx){
            WarehouseKlass warehouseKlass = this.get(Long.parseLong(id));
            this.getRepository().delete(warehouseKlass);

            User user = userRepository.findOne(actor.getUserId());
            WarehouseKlassLog warehouseKlassLog = new WarehouseKlassLog(
                    warehouseKlass.getName(),
                    warehouseKlass.getIsbn(),
                    warehouseKlass.getSpec(),
                    warehouseKlass.getPrice(),
                    warehouseKlass.getNum(),
                    warehouseKlass.getNum(),
                    0,
                    WarehouseOperationtype.DELETEBY,
                    user,
                    Instant.now(),
                    actor.getSchoolId(),
                    klass
            );
            warehouseKlassLogRepository.save(warehouseKlassLog);
        }
    }


}
