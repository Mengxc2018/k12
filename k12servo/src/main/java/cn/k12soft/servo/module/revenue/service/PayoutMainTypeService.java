package cn.k12soft.servo.module.revenue.service;

import cn.k12soft.servo.module.revenue.domain.PayoutMainType;
import cn.k12soft.servo.module.revenue.repository.PayoutMainTypeRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PayoutMainTypeService extends AbstractEntityService<PayoutMainType, Integer>{

    @Autowired
    public PayoutMainTypeService(PayoutMainTypeRepository entityRepository) {
        super(entityRepository);
    }

    @Override
    protected PayoutMainTypeRepository getEntityRepository(){
        return (PayoutMainTypeRepository)super.getEntityRepository();
    }

    public PayoutMainType findByNameAndSchoolId(String name, Integer schoolId) {
        return getEntityRepository().findByNameAndSchoolId(name, schoolId);
    }

    public List<PayoutMainType> findByShoolId(Integer schoolId) {
        return getEntityRepository().findBySchoolId(schoolId);
    }
}
