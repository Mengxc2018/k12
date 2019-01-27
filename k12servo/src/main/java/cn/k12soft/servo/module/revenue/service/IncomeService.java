package cn.k12soft.servo.module.revenue.service;

import cn.k12soft.servo.module.revenue.domain.Income;
import cn.k12soft.servo.module.revenue.repository.IncomeRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional
public class IncomeService extends AbstractEntityService<Income, Integer>{

    @Autowired
    public IncomeService(IncomeRepository entityRepository) {
        super(entityRepository);
    }

    @Override
    protected IncomeRepository getEntityRepository(){
        return (IncomeRepository)super.getEntityRepository();
    }

    public Page<Income> findAll(Integer schoolId, long fromTime, long endTime,  Pageable pageable) {
        Instant from = Instant.ofEpochMilli(fromTime);
        Instant to = Instant.ofEpochMilli(endTime);
        return getEntityRepository().findBySchoolIdAndCreateAtBetween(schoolId, from, to,  pageable);
    }

    public List<Income> findAllBySchoolIdAndCreateAtBetween(Integer schoolId, long startTimeOfYear, long endTimeOfYear) {
        return getEntityRepository().findAllBySchoolIdAndCreateAtBetween(schoolId, Instant.ofEpochMilli(startTimeOfYear), Instant.ofEpochMilli(endTimeOfYear));
    }

    public List<Income> findAllBySchoolIdAndKlassIdAndCreateAtBetween(int schoolId, int klassType, int klassId, Pair<LocalDate, LocalDate> pairFirst) {
        Instant begin = pairFirst.getFirst().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = pairFirst.getSecond().atStartOfDay(ZoneId.systemDefault()).toInstant();
        return getEntityRepository().findAllBySchoolIdAndKlassTypeAndKlassIdAndCreateAtBetween(schoolId, klassType, klassId, begin, end);
    }

}
