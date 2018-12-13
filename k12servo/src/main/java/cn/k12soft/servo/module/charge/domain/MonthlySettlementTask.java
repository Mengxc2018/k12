package cn.k12soft.servo.module.charge.domain;

import cn.k12soft.servo.module.account.service.StudentAccountService;
import cn.k12soft.servo.module.charge.service.StudentChargePlanService;
import javax.transaction.Transactional;

import cn.k12soft.servo.module.expense.service.PaybackService;

import cn.k12soft.servo.module.revenue.service.IncomeDetailService;
import cn.k12soft.servo.module.revenue.service.IncomeService;
import cn.k12soft.servo.service.InterestKlassService;
import cn.k12soft.servo.service.KlassService;
import cn.k12soft.servo.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 结算学生的收费计划，扣除上一个月的费用
 */
@Component
public class MonthlySettlementTask {

  private static final Logger logger = LoggerFactory.getLogger(MonthlySettlementTask.class);
  private StudentService studentService;
  private StudentChargePlanService studentChargePlanService;
  private IncomeService incomeService;
  private IncomeDetailService incomeDetailService;
  private KlassService klassService;
  private InterestKlassService interestKlassService;
  private PaybackService paybackService;
  private StudentAccountService studentAccountService;

  public MonthlySettlementTask(StudentService studentService,
                               StudentChargePlanService studentChargePlanService,
                               IncomeService incomeService,
                               KlassService klassService,
                               InterestKlassService interestKlassService,
                               IncomeDetailService incomeDetailService,
                               PaybackService paybackService,
                               StudentAccountService studentAccountService) {
    this.studentService = studentService;
    this.studentChargePlanService = studentChargePlanService;
    this.incomeService = incomeService;
    this.incomeDetailService = incomeDetailService;
    this.klassService = klassService;
    this.interestKlassService = interestKlassService;
    this.paybackService = paybackService;
    this.studentAccountService = studentAccountService;
  }

  @Scheduled(cron = "0 3 2 1 * ?") // 每个月1号 2点03分执行
//    @Scheduled(cron = "0/5 * * * * *") // 测试
  public void execute() {
    try {
      _innerExecute();
    } catch (Exception e) {
      logger.error("[MonthlySettlementTask] ", e);
    }
  }

  @Transactional
  void _innerExecute() {
    this.studentChargePlanService.deductExpenses(this.studentService, incomeService,klassService,interestKlassService, paybackService, studentAccountService, incomeDetailService);
  }

}
