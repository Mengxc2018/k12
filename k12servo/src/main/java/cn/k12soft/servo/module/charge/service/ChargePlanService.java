package cn.k12soft.servo.module.charge.service;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.enumeration.KlassTypeCharge;
import cn.k12soft.servo.module.charge.domain.ChargePlan;
import cn.k12soft.servo.module.charge.repository.ChargePlanRepository;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.repository.ExpenseEntryRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

  @Autowired
  private KlassRepository klassRepository;
  @Autowired
  private ExpenseEntryRepository expenseEntryRepository;

  @Override
  protected ChargePlanRepository getEntityRepository() {
    return (ChargePlanRepository) super.getEntityRepository();
  }

  public Page<ChargePlan> findBySchoolIdAndCreateAt(int schoolId, Pageable pageable,
          Integer klassId, Integer expenseId, Instant fromDate, Instant toDate, KlassTypeCharge type) {

    Page<ChargePlan> list = null;
    Klass klass = this.klassRepository.findOne(klassId);
    ExpenseEntry expenseEntry = null;
    if (expenseId != null) {
      expenseEntry = expenseEntryRepository.findOne(expenseId);
    }

    /**
     * 除了时间外，其他查询条件都可以用匹配器查询
     */
    if (fromDate == null) {
      if (klassId != null) {
        klass = this.klassRepository.findOne(klassId);
        expenseEntry = expenseEntryRepository.findOne(expenseId);
      }
      ChargePlan chargePlan = new ChargePlan();

      chargePlan.setKlass(klass);
      chargePlan.setExpenseEntry(expenseEntry);
      chargePlan.setKlassType(type);
      chargePlan.setSchoolId(schoolId);
      Example<ChargePlan> example = Example.of(chargePlan);
      list = this.getEntityRepository().findAll(example, pageable);
    } else {
      /**
       * 班级     不空
       * 费种     空
       * 班级类型 空
       * 时间     空
       */
      if (klassId != null && expenseId == null && type == null) {
        list = this.getEntityRepository().findAllBySchoolIdAndKlass(schoolId, klass, pageable);
      }

      /**
       * 班级     空
       * 费种     不空
       * 班级类型 空
       * 时间     空
       */
      if (klassId == null && expenseId != null && type == null) {
        list = this.getEntityRepository().findAllBySchoolIdAndExpenseEntry(schoolId, expenseEntry, pageable);
      }

      /**
       * 班级     空
       * 费种     空
       * 班级类型 不空
       * 时间     空
       */
      if (klassId == null && expenseId == null && type != null) {
        list = this.getEntityRepository().findAllBySchoolIdAndKlassType(schoolId, type, pageable);
      }
    }
    return list;
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