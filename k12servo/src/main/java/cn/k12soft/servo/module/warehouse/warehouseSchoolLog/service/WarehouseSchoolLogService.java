package cn.k12soft.servo.module.warehouse.warehouseSchoolLog.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.domain.WarehouseSchoolLog;
import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.repository.WarehouseSchoolAllotLogRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class WarehouseSchoolLogService extends AbstractRepositoryService<WarehouseSchoolLog, Long, WarehouseSchoolAllotLogRepository>{

    @Autowired
    protected WarehouseSchoolLogService(WarehouseSchoolAllotLogRepository repository) {
        super(repository);
    }

    public List<WarehouseSchoolLog> findAllBySchoolId(Actor actor, LocalDate localDate, String isbn, FeePeriodType type) {
        Map<String, Instant> map = new HashMap<>();
        Instant first = null;
        Instant second = null;
        if (localDate != null && !StringUtils.isEmpty(isbn)) {
            map = getInstantBetween(localDate, type);
            first = map.get("first");
            second = map.get("second");
            return this.getRepository().findAllBySchoolIdAndIsbnAndCreatedAtBetween(actor.getSchoolId(), isbn, first, second);
        }
        if (localDate != null && StringUtils.isEmpty(isbn)){
            map = getInstantBetween(localDate, type);
            first = map.get("first");
            second = map.get("second");
            return this.getRepository().findAllBySchoolIdAndCreatedAtBetween(actor.getSchoolId(), first, second);
        }
        if (!StringUtils.isEmpty(isbn) && localDate == null){
            return this.getRepository().findAllBySchoolIdAndIsbn(actor.getSchoolId(), isbn);
        }
        return getRepository().findAllBySchoolId(actor.getSchoolId());
    }

    public  Map<String, Instant> getInstantBetween(LocalDate localDate, FeePeriodType type){
        Map<String, Instant> map = new HashMap<>();
        Instant first = null;
        Instant second = null;
        Integer year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        switch (type) {
            case YEAR:
                first = LocalDate.of(year, 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
                second = LocalDate.of(year, 12, 31).plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
                break;
            case SEMESTER:
                if (month >= 2 && month <= 8) {  // 上学期
                    first = LocalDate.of(year, 2, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
                    second = LocalDate.of(year, 8, 31).plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
                } else { // 下学期
                    first = LocalDate.of(year, 9, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
                    second = LocalDate.of(year, 12, 31).plusDays(1).plusMonths(1).atStartOfDay().toInstant(ZoneOffset.UTC);
                }
                break;
            case MONTH:
                first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
                second = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
                break;
            case WEEK:
                TemporalField temporalField = WeekFields.of(Locale.getDefault()).dayOfWeek();
                first = localDate.with(temporalField, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
                second = localDate.with(temporalField, 7).atStartOfDay().toInstant(ZoneOffset.UTC);
                break;
            case DAY:
                first = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
                second = localDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
                break;
        }
        map.put("first", first);
        map.put("second", second);
        return map;
    }
}
