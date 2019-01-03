package cn.k12soft.servo.module.expense.service;

import cn.k12soft.servo.module.expense.domain.*;
import cn.k12soft.servo.module.expense.form.ExpenseEntryForm;
import cn.k12soft.servo.module.expense.repository.*;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.service.dto.ExpenseEntryDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2017/12/16.
 */
@Service
public class ExpenseEntryService extends AbstractRepositoryService<ExpenseEntry, Integer, ExpenseEntryRepository> {

  private final ApplicationContext context;
  private final ExpenseIdentDiscountRepository identDiscountRepository;
  private final ExpensePeriodDiscountRepository periodDiscountRepository;
  private final ExpressionParser expressionParser = new SpelExpressionParser();

  @Autowired
  protected ExpenseEntryService(ApplicationContext context,
                                ExpenseEntryRepository repository,
                                ExpenseIdentDiscountRepository identDiscountRepository,
                                ExpensePeriodDiscountRepository periodDiscountRepository) {
    super(repository);
    this.context = context;
    this.identDiscountRepository = identDiscountRepository;
    this.periodDiscountRepository = periodDiscountRepository;
  }

  @Transactional(readOnly = true)
  public List<ExpenseEntryDTO> findBySchoolId(Integer schoolId) {
    List<ExpenseEntry> expenseEntries = getRepository().findAllBySchoolId(schoolId);
    return expenseEntries.stream().map(expenseEntry -> {
      List<ExpenseIdentDiscount> identDiscounts = identDiscountRepository.findAllByEntry(expenseEntry);
      List<ExpensePeriodDiscount> periodDiscounts = periodDiscountRepository.findAllByEntry(expenseEntry);
      List<PaybackByDays> paybackByDays = getPaybackByDaysRepository().findAllByEntry(expenseEntry);
      List<PaybackBySemester> paybackBySemesters = getPaybackBySemesterRepository().findAllByEntry(expenseEntry);
      return new ExpenseEntryDTO(expenseEntry, identDiscounts, periodDiscounts, paybackByDays, paybackBySemesters);
    }).collect(Collectors.toList());
  }

  public ExpenseEntry create(Integer schoolId, ExpenseEntryForm form) {
    ExpenseEntry created = new ExpenseEntry(schoolId, form.getName(), form.getPeriodType(), form.getAmount(), form.isDelayCharge(), form.getIsFixation());
    ExpenseEntryRepository repository = getRepository();
    ExpenseEntry entry = repository.save(created);
    boolean updated = false;
    if (form.getPeriodDiscounts() != null && !form.getPeriodDiscounts().isEmpty()) {
      form.getPeriodDiscounts().forEach(discount ->
        entry.addPeriodDiscount(new ExpensePeriodDiscount(entry, discount.getPeriodType(), discount.getDiscountRate())));
      updated = true;
    }
    if (form.getIdentDiscounts() != null && !form.getIdentDiscounts().isEmpty()) {
      form.getIdentDiscounts().forEach(discount ->
        entry.addIdentDiscount(new ExpenseIdentDiscount(entry, discount.getIdentType(), discount.getDiscountRate())));
      updated = true;
    }
//    if (domain.getPaybacks() != null && !domain.getPaybacks().isEmpty()) {
//      domain.getPaybacks().forEach(payback -> {
//        String expr = payback.getExpr();
//        for (Entry<String, String> alias : PaybackAlias.getALIAS().entrySet()) {
//          expr = expr.replace(alias.getKey(), alias.getValue());
//        }
//        Expression expression = expressionParser.parseExpression(expr);
//        entry.addPaybackRule(payback.getDesc(), expression.getExpressionString());
//      });
//      updated = true;
//    }

    if(form.getPaybackByDays() != null && form.getPaybackByDays().size()>0){
      form.getPaybackByDays().forEach(payBackByDays->{
        int pDay = payBackByDays.getpDay() == null? 0:payBackByDays.getpDay();
        int compareType = payBackByDays.getCompareType() == null ? 0 : payBackByDays.getCompareType();
        int pType = payBackByDays.getpType() == null?0:payBackByDays.getpType();
        float pValue = payBackByDays.getpValue()==null?0:payBackByDays.getpValue();
        PaybackByDays tmpPaybackByDays = new PaybackByDays();
        tmpPaybackByDays.setpDay(pDay);
        tmpPaybackByDays.setCompareType(compareType);
        tmpPaybackByDays.setpType(pType);
        tmpPaybackByDays.setpValue(pValue);
        tmpPaybackByDays.setEntry(entry);
        entry.addPaybackByDays(tmpPaybackByDays);
      });
      updated = true;
    }

    if(form.getPaybackBySemesters() != null && form.getPaybackBySemesters().size()>0){
      form.getPaybackBySemesters().forEach(paybackBySemester->{
        int pType = paybackBySemester.getpType() == null ? 0:paybackBySemester.getpType();
        float pValue = paybackBySemester.getpValue() == null ? 0 : paybackBySemester.getpValue();
        int semesterType = paybackBySemester.getSemesterType() == null ? 0 : paybackBySemester.getSemesterType();
        int vCount = paybackBySemester.getVacationCount() == null ? 0 : paybackBySemester.getVacationCount();
        int countCompareType = paybackBySemester.getCountCompareType() == null ? 0 : paybackBySemester.getCountCompareType();
        int vDays = paybackBySemester.getVacationDays() == null ? 0:paybackBySemester.getVacationDays();
        int dayCompareType = paybackBySemester.getDaysCompareType() == null ? 0 : paybackBySemester.getDaysCompareType();
        PaybackBySemester tmpPaybackBySemester = new PaybackBySemester();
        tmpPaybackBySemester.setpType(pType);
        tmpPaybackBySemester.setpValue(pValue);
        tmpPaybackBySemester.setSemesterType(semesterType);
        tmpPaybackBySemester.setVacationCount(vCount);
        tmpPaybackBySemester.setCountCompareType(countCompareType);
        tmpPaybackBySemester.setVacationDays(vDays);
        tmpPaybackBySemester.setDaysCompareType(dayCompareType);
        tmpPaybackBySemester.setEntry(entry);
        entry.addPaybackBySemester(tmpPaybackBySemester);
      });
      updated = true;
    }

    if (updated) {
      return repository.save(entry);
    }
    return entry;
  }

  public List<ExpenseEntry> createMany(Integer schoolId, List<ExpenseEntryForm> forms) {
    List<ExpenseEntry> list = new ArrayList<>();
    for (ExpenseEntryForm form : forms){
      list.add(create(schoolId, form));
    }
    return list;
  }

  public ExpenseEntry update(ExpenseEntry expenseEntry, ExpenseEntryForm form) {
    boolean update = false;
    if(StringUtils.isNotBlank(form.getName())){
      expenseEntry.setName(form.getName());
    }
    if(form.getPeriodType() != null){
      expenseEntry.setPeriodType(form.getPeriodType());
      update = true;
    }
    if(form.getAmount() != null){
      expenseEntry.setAmount(form.getAmount());
      update = true;
    }
    if(form.getIdentDiscounts() != null && form.getIdentDiscounts().size()>0){
      identDiscountRepository.deleteByEntry(expenseEntry);
      form.getIdentDiscounts().forEach(identDiscount -> {
        expenseEntry.addIdentDiscount(new ExpenseIdentDiscount(expenseEntry, identDiscount.getIdentType(), identDiscount.getDiscountRate()));
      });
      update = true;
    }
    if(form.getPeriodDiscounts() != null && form.getPeriodDiscounts().size()>0){
      periodDiscountRepository.deleteByEntry(expenseEntry);
      form.getPeriodDiscounts().forEach(periodDiscount -> {
        expenseEntry.addPeriodDiscount(new ExpensePeriodDiscount(expenseEntry, periodDiscount.getPeriodType(), periodDiscount.getDiscountRate()));
      });
      update = true;
    }

    if(form.getPaybackByDays() != null && form.getPaybackByDays().size()>0){
      getPaybackByDaysRepository().deleteByEntry(expenseEntry);
      form.getPaybackByDays().forEach(payBackByDays->{
        int pDay = payBackByDays.getpDay() == null? 0:payBackByDays.getpDay();
        int compareType = payBackByDays.getCompareType() == null ? 0 : payBackByDays.getCompareType();
        int pType = payBackByDays.getpType() == null?0:payBackByDays.getpType();
        float pValue = payBackByDays.getpValue()==null?0:payBackByDays.getpValue();
        PaybackByDays tmpPaybackByDays = new PaybackByDays();
        tmpPaybackByDays.setpDay(pDay);
        tmpPaybackByDays.setCompareType(compareType);
        tmpPaybackByDays.setpType(pType);
        tmpPaybackByDays.setpValue(pValue);
        tmpPaybackByDays.setEntry(expenseEntry);
        expenseEntry.addPaybackByDays(tmpPaybackByDays);
      });
      update = true;
    }

    if(form.getPaybackBySemesters() != null && form.getPaybackBySemesters().size()>0){
      getPaybackBySemesterRepository().deleteByEntry(expenseEntry);
      form.getPaybackBySemesters().forEach(paybackBySemester->{
        int pType = paybackBySemester.getpType() == null ? 0:paybackBySemester.getpType();
        float pValue = paybackBySemester.getpValue() == null ? 0 : paybackBySemester.getpValue();
        int semesterType = paybackBySemester.getSemesterType() == null ? 0 : paybackBySemester.getSemesterType();
        int vCount = paybackBySemester.getVacationCount() == null ? 0 : paybackBySemester.getVacationCount();
        int countCompareType = paybackBySemester.getCountCompareType() == null ? 0 : paybackBySemester.getCountCompareType();
        int vDays = paybackBySemester.getVacationDays() == null ? 0:paybackBySemester.getVacationDays();
        int dayCompareType = paybackBySemester.getDaysCompareType() == null ? 0 : paybackBySemester.getDaysCompareType();
        PaybackBySemester tmpPaybackBySemester = new PaybackBySemester();
        tmpPaybackBySemester.setpType(pType);
        tmpPaybackBySemester.setpValue(pValue);
        tmpPaybackBySemester.setSemesterType(semesterType);
        tmpPaybackBySemester.setVacationCount(vCount);
        tmpPaybackBySemester.setCountCompareType(countCompareType);
        tmpPaybackBySemester.setVacationDays(vDays);
        tmpPaybackBySemester.setDaysCompareType(dayCompareType);
        tmpPaybackBySemester.setEntry(expenseEntry);
        expenseEntry.addPaybackBySemester(tmpPaybackBySemester);
      });
      update = true;
    }

    if(update) {
      save(expenseEntry);
    }
    return get(expenseEntry.getId());
  }

  public void delete(Integer id) {
    ExpenseEntryRepository repository = getRepository();
    ExpenseEntry entry = repository.getOne(id);
    entry.getPeriodDiscounts().clear();
    entry.getIdentDiscounts().clear();
    repository.save(entry);
    periodDiscountRepository.deleteByEntry(entry);
    identDiscountRepository.deleteByEntry(entry);
    repository.delete(entry);
  }

  public void deletePaybackByDasy(Integer id){
    getPaybackByDaysRepository().delete(id);
  }

  public void deletePaybackBySemester(Integer id){
    getPaybackBySemesterRepository().delete(id);
  }

  public void deleteIdentDiscounts(Integer id) {
    this.identDiscountRepository.delete(id);
  }

  public void deletePeriodDiscounts(Integer id) {
    this.periodDiscountRepository.delete(id);
  }

  private PaybackByDaysRepository getPaybackByDaysRepository() {
    return context.getBean(PaybackByDaysRepository.class);
  }

  private PaybackBySemesterRepository getPaybackBySemesterRepository(){
    return context.getBean(PaybackBySemesterRepository.class);
  }


  public ExpenseEntry findByNameAndSchoolId(String expenseName, Integer schoolId) {
    return this.getRepository().findAllByNameAndSchoolId(expenseName, schoolId);
  }
}
