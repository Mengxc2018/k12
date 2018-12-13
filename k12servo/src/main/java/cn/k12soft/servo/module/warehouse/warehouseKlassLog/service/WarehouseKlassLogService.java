package cn.k12soft.servo.module.warehouse.warehouseKlassLog.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import cn.k12soft.servo.module.warehouse.warehouseKlassLog.domain.WarehouseKlassLog;
import cn.k12soft.servo.module.warehouse.warehouseKlassLog.repository.WarehouseKlassLogRepository;
import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.service.WarehouseSchoolLogService;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseKlassLogService extends AbstractRepositoryService<WarehouseKlassLog, Long, WarehouseKlassLogRepository>{

    private final KlassRepository klassRepository;
    private final WarehouseSchoolLogService warehouseSchoolLogService;

    @Autowired
    protected WarehouseKlassLogService(WarehouseKlassLogRepository repository, KlassRepository klassRepository, WarehouseSchoolLogService warehouseSchoolLogService) {
        super(repository);
        this.klassRepository = klassRepository;
        this.warehouseSchoolLogService = warehouseSchoolLogService;
    }


    public List<WarehouseKlassLog> findBy(Actor actor, Integer klassId, LocalDate localDate, String isbn, FeePeriodType type) {
        Map<String, Instant> map = new HashMap<>();
        Instant first = null;
        Instant second = null;
        Klass klass = null;
        if (klassId != null){
            klass = this.klassRepository.findOne(klassId);
        }

        // 日期、isbn、班级id
        if (localDate != null && !StringUtils.isEmpty(isbn) && klassId != null) {
            map = warehouseSchoolLogService.getInstantBetween(localDate, type);
            first = map.get("first");
            second = map.get("second");
            return this.getRepository().findAllBySchoolIdAndKlassAndIsbnAndCreatedAtBetween(actor.getSchoolId(), klass, isbn, first, second);
        }
        // 日期、!isbn、班级
        if (localDate != null && StringUtils.isEmpty(isbn) && klassId != null){
            map = warehouseSchoolLogService.getInstantBetween(localDate, type);
            first = map.get("first");
            second = map.get("second");
            return this.getRepository().findAllBySchoolIdAndKlassAndCreatedAtBetween(actor.getSchoolId(), klass, first, second);
        }
        // 日期、isbn、!班级
        if (localDate != null && !StringUtils.isEmpty(isbn) && klassId == null){
            map = warehouseSchoolLogService.getInstantBetween(localDate, type);
            first = map.get("first");
            second = map.get("second");
            return this.getRepository().findAllBySchoolIdAndIsbnAndCreatedAtBetween(actor.getSchoolId(), isbn, first, second);
        }
        // !日期、isbn、班级
        if (localDate == null && !StringUtils.isEmpty(isbn) && klassId != null){
            return this.getRepository().findAllBySchoolIdAndKlassAndIsbn(actor.getSchoolId(), klass, isbn);
        }
        // !日期、!isbn、班级
        if (localDate == null && StringUtils.isEmpty(isbn) && klassId != null){
            return this.getRepository().findAllBySchoolIdAndKlass(actor.getSchoolId(), klass);
        }
        // !日期、isbn、!班级
        if (localDate == null && !StringUtils.isEmpty(isbn) && klassId == null){
            return this.getRepository().findAllBySchoolIdAndIsbn(actor.getSchoolId(), isbn);
        }
        // 日期、!isbn、!班级
        if (localDate != null && StringUtils.isEmpty(isbn) && klassId == null){
            map = warehouseSchoolLogService.getInstantBetween(localDate, type);
            first = map.get("first");
            second = map.get("second");
            return this.getRepository().findAllBySchoolIdAndCreatedAtBetween(actor.getSchoolId(), first, second);
        }
        return getRepository().findAllBySchoolId(actor.getSchoolId());
    }


}
