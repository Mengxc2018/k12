package cn.k12soft.servo.module.revenue.service;

import cn.k12soft.servo.module.revenue.domain.Payout;
import cn.k12soft.servo.module.revenue.repository.PayoutRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class PayoutService extends AbstractEntityService<Payout, Integer>{

    @Autowired
    public PayoutService(PayoutRepository entityRepository) {
        super(entityRepository);
    }

    @Override
    protected PayoutRepository getEntityRepository(){
        return (PayoutRepository)super.getEntityRepository();
    }

    public List<Payout> findBySchoolId(Integer schoolId) {
        return getEntityRepository().findBySchoolId(schoolId);
    }

    public List<Payout> findAllBySchoolIdAndCreateAtBetween(Integer schoolId, long fromTime, long toTime){
        return getEntityRepository().findAllBySchoolIdAndCreateAtBetween(schoolId, Instant.ofEpochMilli(fromTime), Instant.ofEpochMilli(toTime));
    }
}
