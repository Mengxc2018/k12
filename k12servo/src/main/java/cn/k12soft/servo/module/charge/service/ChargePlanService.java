package cn.k12soft.servo.module.charge.service;

import cn.k12soft.servo.module.charge.domain.ChargePlan;
import cn.k12soft.servo.module.charge.repository.ChargePlanRepository;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.service.AbstractEntityService;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChargePlanService extends AbstractEntityService<ChargePlan, Integer> {

  @Autowired
  public ChargePlanService(ChargePlanRepository chargePlanRepository) {
    super(chargePlanRepository);
  }

  @Override
  protected ChargePlanRepository getEntityRepository() {
    return (ChargePlanRepository) super.getEntityRepository();
  }

  public Page<ChargePlan> findBySchoolIdAndCreateAt(int schoolId, Instant monthStartTimeInstant, Pageable pageable) {
    return getEntityRepository().findAllBySchoolIdAndCreateAtAfter(schoolId, monthStartTimeInstant, pageable);
  }

  @Override
  public ChargePlan get(Integer id) {
    return getEntityRepository().findOne(id);
  }

  public void update(ChargePlan chargePlan) {
    getEntityRepository().save(chargePlan);
  }

  public void deleteByExpenseEntry(ExpenseEntry entry) {
    getEntityRepository().deleteByExpenseEntry(entry);
  }

}
