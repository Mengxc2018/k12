package cn.k12soft.servo.module.revenue.service;

import cn.k12soft.servo.module.revenue.domain.IncomeDetail;
import cn.k12soft.servo.module.revenue.repository.IncomeDetailRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IncomeDetailService extends AbstractEntityService<IncomeDetail, Integer>{

    @Autowired
    public IncomeDetailService(IncomeDetailRepository entityRepository) {
        super(entityRepository);
    }

    @Override
    protected IncomeDetailRepository getEntityRepository(){
        return (IncomeDetailRepository)super.getEntityRepository();
    }

    public List<IncomeDetail> findIncomeDetail(Integer id, Integer theYearMonth) {
        return getEntityRepository().findByExpenseIdAndTheYearMonth(id, theYearMonth);
    }
}
