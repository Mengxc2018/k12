package cn.k12soft.servo.module.expense.service;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.Vacation;
import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.charge.domain.VacationSummary;
import cn.k12soft.servo.module.charge.service.StudentChargePlanService;
import cn.k12soft.servo.module.expense.domain.*;
import cn.k12soft.servo.module.expense.repository.PaybackRuleRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.List;

import cn.k12soft.servo.repository.VacationRepository;
import cn.k12soft.servo.service.StudentService;
import cn.k12soft.servo.util.Times;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.Pair;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaybackService {

  private final ApplicationContext context;
  private final ExpressionParser expressionParser = new SpelExpressionParser();

  @Autowired
  PaybackService(ApplicationContext context) {
    this.context = context;
  }

  @Transactional
  public int calculate(Integer schoolId, Integer studentId, LocalDate fromDate, LocalDate toDate) {
//    List<PaybackRule> paybackRules = paybackRuleRepository.findAllBySchoolId(schoolId);
//    PaybackContext paybackContext = new PaybackContextImpl(studentId, Pair.of(fromDate, toDate), context);
//    return paybackRules.stream().mapToInt(paybackRule -> calculate(paybackRule, paybackContext)).sum();
    return 0;
  }

  @Transactional
  public int calculate(PaybackRule rule, Integer studentId, LocalDate fromDate, LocalDate toDate) {
    PaybackContext paybackContext = new PaybackContextImpl(studentId, Pair.of(fromDate, toDate), context);
    return calculate(rule, paybackContext);
  }

  @Transactional
  public int calculate(PaybackRule rule, PaybackContext context) {
    Expression expression = expressionParser.parseExpression(rule.getExpr());
    EvaluationContext evaluationContext = new StandardEvaluationContext(context);
    return expression.getValue(evaluationContext, int.class);
  }

  public int getLeftDaysOfMonth(Integer studentId, LocalDate date) {
    LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
    LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
    Collection<Vacation> vacationList = getVacation(studentId, firstDayOfMonth, lastDayOfMonth);
    int leftDays = 0;
    for (Vacation vacation : vacationList) {
      leftDays += Times.getIntervalDays(vacation.getToDate(), vacation.getFromDate());
    }
    return leftDays;
  }

  public Collection<Vacation> getAllByCreateAt(LocalDate fromDate, LocalDate toDate){
    Pair<LocalDate, LocalDate> dataRange = Pair.of(fromDate, toDate);
    return getVacationRepository().findAllByCreatedAtBetween(dataRange.getFirst().atStartOfDay(ZoneId.systemDefault()).toInstant(),
            dataRange.getSecond().atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  public Collection<Vacation> getVacation(Integer studentId, LocalDate fromDate, LocalDate toDate){
    Pair<LocalDate, LocalDate> dataRange = Pair.of(fromDate, toDate);
    return getVacationRepository()
            .findAllByStudentIdAndCreatedAtBetween(studentId,
                    dataRange.getFirst().atStartOfDay(ZoneId.systemDefault()).toInstant(),
                    dataRange.getSecond().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  private VacationRepository getVacationRepository() {
    return context.getBean(VacationRepository.class);
  }

  public VacationSummary getVacationSummary(Integer studentId, LocalDate fromDate, LocalDate toDate){
      LocalDate localDateNow = LocalDate.now();

      int leftDaysOfMonth = getLeftDaysOfMonth(studentId, fromDate);// 本月请假天数

      Pair<LocalDate, LocalDate> termDatePair = Times.getTermDatePair(localDateNow);
      LocalDate firstDayOfThisTerm = termDatePair.getFirst();
      LocalDate lastDayOfThisTerm = termDatePair.getSecond();
      Collection<Vacation> termVacationList = getVacation(studentId, firstDayOfThisTerm, lastDayOfThisTerm);
      int termVacationCount = 0;
      int termVacationDays = 0;
      for (Vacation vacation : termVacationList) {
          termVacationCount++;
          termVacationDays += Times.getIntervalDays(vacation.getToDate(), vacation.getFromDate());
      }
      int[] teramArr = new int[]{termVacationCount, termVacationDays};// 学期请假次数，请假天数

      int yearVacationCount = 0;
      int yearVacationDays = 0;
      LocalDate firstDayOfYear = localDateNow.with(TemporalAdjusters.firstDayOfYear());
      LocalDate lastDayOfYear = localDateNow.with(TemporalAdjusters.lastDayOfYear());
      Collection<Vacation> yearVacationList = getVacation(studentId, firstDayOfYear, lastDayOfYear);
      for (Vacation vacation : yearVacationList) {
          yearVacationCount++;
          yearVacationDays += Times.getIntervalDays(vacation.getToDate(), vacation.getFromDate());
      }
      int[] yearArr = new int[]{yearVacationCount, yearVacationDays}; // 年度请假次数，请假天数

      VacationSummary vacationSummary = new VacationSummary(leftDaysOfMonth, teramArr, yearArr);
      return vacationSummary;
  }

  public PaybackResult getPaybackResult(StudentService studentService, StudentChargePlanService studentChargePlanService, Integer studentId, LocalDate fromDate, LocalDate toDate) {
      Student student = studentService.get(studentId);
      Klass klass = student.getKlass();
//      LocalDate localDateNow = LocalDate.now();

//      Pair<LocalDate, LocalDate> termDatePair = Times.getTermDatePair(localDateNow);
//      LocalDate firstDayOfThisTerm = termDatePair.getFirst();
//      LocalDate lastDayOfThisTerm = termDatePair.getSecond();
      // 学期请假记录
//      Collection<Vacation> termVacationList = getVacation(studentId, firstDayOfThisTerm, lastDayOfThisTerm);
//      LocalDate firstDayOfYear = localDateNow.with(TemporalAdjusters.firstDayOfYear());
//      LocalDate lastDayOfYear = localDateNow.with(TemporalAdjusters.lastDayOfYear());
//      // 年度请假记录
//      Collection<Vacation> yearVacationList = getVacation(studentId, firstDayOfYear, lastDayOfYear);
      List<StudentCharge> studentChargeList = studentChargePlanService.findByStudentId(studentId);
      if(studentChargeList.size()<=0){
        return null;
      }

//      int leftDaysOfMonth = getLeftDaysOfMonth(studentId, fromDate);// 本月请假天数

//      int termVacationCount = 0;
//      int termVacationDays = 0;
//      for (Vacation vacation : termVacationList) {
//        termVacationCount++;
//        termVacationDays += Times.getIntervalDays(vacation.getToDate(), vacation.getFromDate());
//      }
//      int[] teramArr = new int[]{termVacationCount, termVacationDays};// 学期请假次数，请假天数

//      int yearVacationCount = 0;
//      int yearVacationDays = 0;
//      for (Vacation vacation : yearVacationList) {
//        yearVacationCount++;
//        yearVacationDays += Times.getIntervalDays(vacation.getToDate(), vacation.getFromDate());
//      }
//      int[] yearArr = new int[]{yearVacationCount, yearVacationDays}; // 年度请假次数，请假天数

      VacationSummary vacationSummary = getVacationSummary(studentId, fromDate, toDate);
      int leftDaysOfMonth = vacationSummary.getLeaveDaysOfMonth();
      int[] termArr = vacationSummary.getTermArr();
      int[] yearArr = vacationSummary.getYearArr();

      PaybackResult result = new PaybackResult();
      result.setStudent(student);
      result.setKlass(klass);
      for (StudentCharge studentCharge : studentChargeList) {
        calc(studentCharge, result, leftDaysOfMonth, termArr, yearArr);
      }
      return result;
  }

  public void calc(StudentCharge studentCharge, PaybackResult result, LocalDate fromDate, int[] termArr, int[] yearArr){
      int leftDaysOfMonth = getLeftDaysOfMonth(studentCharge.getStudentId(), fromDate);// 本月请假天数
      calc(studentCharge, result, leftDaysOfMonth, termArr, yearArr);
  }

  private void calc(StudentCharge studentCharge, PaybackResult result, int leftDays, int[] termArr, int[] yearArr) {
    List<PaybackBySemester> paybackBySemesterList = studentCharge.getExpenseEntry().getPaybackBySemesters();
    // 优先按 学期规则退费
    if(paybackBySemesterList.size()>0){
      for (PaybackBySemester paybackBySemester : paybackBySemesterList) {
        if(paybackBySemester.match(termArr, yearArr)){
          if(paybackBySemester.pTypeIsPerc()) {
            float tmpMoney = studentCharge.getMoney() * paybackBySemester.getpValue()/100f;
            result.addMoney(tmpMoney);
          }else{
            result.addMoney(paybackBySemester.getpValue());// 金额类型，直接加
          }
          // 找到合适的退费规则，退费成功 return
          ExpenseEntry tmpExpenseEntry = new ExpenseEntry(studentCharge.getExpenseEntry().getId());
          tmpExpenseEntry.setName(studentCharge.getExpenseEntry().getName());
          tmpExpenseEntry.addPaybackBySemester(paybackBySemester);
          result.addExpenseEntry(tmpExpenseEntry);
          return;
        }
      }
    }

    List<PaybackByDays> paybackByDaysList = studentCharge.getExpenseEntry().getPaybackByDays();
    if(paybackByDaysList.size() > 0){
      for (PaybackByDays paybackByDays : paybackByDaysList) {
        if(paybackByDays.match(leftDays)){
          if(paybackByDays.pTypeIsPerc()){
            float tmpMoney = studentCharge.getMoney()*paybackByDays.getpValue()/100f;
            result.addMoney(tmpMoney);
          }else{
            result.addMoney(paybackByDays.getpValue());
          }
          ExpenseEntry tmpExpenseEntry = new ExpenseEntry(studentCharge.getExpenseEntry().getId());
          tmpExpenseEntry.setName(studentCharge.getExpenseEntry().getName());
          tmpExpenseEntry.addPaybackByDays(paybackByDays);
          result.addExpenseEntry(tmpExpenseEntry);
          return;
        }
      }
    }
  }
}
