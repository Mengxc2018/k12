package cn.k12soft.servo.module.revenue.service;

import cn.k12soft.servo.module.revenue.domain.PayoutSubType;
import cn.k12soft.servo.module.revenue.domain.PayoutMainType;
import cn.k12soft.servo.module.revenue.repository.PayoutSubTypeRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PayoutSubTypeService extends AbstractEntityService<PayoutSubType, Integer> {

    @Autowired
    public PayoutSubTypeService(PayoutSubTypeRepository entityRepository) {
        super(entityRepository);
    }

    @Override
    protected PayoutSubTypeRepository getEntityRepository(){
        return (PayoutSubTypeRepository)super.getEntityRepository();
    }


    public PayoutSubType findByNameAndPayoutMainTypeAndSchoolId(String name, PayoutMainType payoutMainType, Integer schoolId) {
        return getEntityRepository().findByNameAndPayoutMainTypeAndSchoolId(name, payoutMainType, schoolId);
    }

    public List<PayoutSubType> findBySchoolId(Integer schoolId) {
        return getEntityRepository().findBySchoolId(schoolId);
    }

    public void deleteByPayoutMainType(PayoutMainType payoutMainType) {
        getEntityRepository().deleteByPayoutMainType(payoutMainType);
    }

    public List<PayoutSubType> findByPayoutMainTypeAndSchoolId(PayoutMainType payoutMainType, Integer schoolId) {
        return getEntityRepository().findByPayoutMainTypeAndSchoolId(payoutMainType, schoolId);
    }

}
