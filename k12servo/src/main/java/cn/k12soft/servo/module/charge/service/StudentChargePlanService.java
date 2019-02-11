package cn.k12soft.servo.module.charge.service;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.domain.enumeration.StudentAccountOpType;
import cn.k12soft.servo.module.account.domain.StudentAccount;
import cn.k12soft.servo.module.account.service.StudentAccountChangeRecordService;
import cn.k12soft.servo.module.account.service.StudentAccountService;
import cn.k12soft.servo.module.charge.domain.ChargePlan;
import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.charge.domain.VacationSummary;
import cn.k12soft.servo.module.charge.repository.StudentChargePlanRepository;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodType;
import cn.k12soft.servo.module.expense.domain.PaybackResult;
import cn.k12soft.servo.module.expense.service.PaybackService;
import cn.k12soft.servo.module.revenue.domain.Income;
import cn.k12soft.servo.module.revenue.domain.IncomeDetail;
import cn.k12soft.servo.module.revenue.domain.IncomeSrc;
import cn.k12soft.servo.module.revenue.service.IncomeDetailService;
import cn.k12soft.servo.module.revenue.service.IncomeService;
import cn.k12soft.servo.module.studentChargeRecord.service.StudentChargeRecordService;
import cn.k12soft.servo.module.wxLogin.service.WxService;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import cn.k12soft.servo.service.InterestKlassService;
import cn.k12soft.servo.service.KlassService;
import cn.k12soft.servo.service.StudentService;
import cn.k12soft.servo.util.Times;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.k12soft.servo.domain.enumeration.StudentChargeStatus.END;
import static cn.k12soft.servo.domain.enumeration.StudentChargeStatus.EXCUTE;

@Service
@Transactional
public class StudentChargePlanService extends AbstractEntityService<StudentCharge, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(StudentChargePlanService.class);
    private final StudentChargeRecordService studentChargeRecordService;
    private final WxService wxService;
    private final SchoolRepository schoolRepository;

    @Autowired
    public StudentChargePlanService(StudentChargePlanRepository entityRepository, StudentChargeRecordService studentChargeRecordService, WxService wxService, SchoolRepository schoolRepository) {
        super(entityRepository);
        this.studentChargeRecordService = studentChargeRecordService;
        this.wxService = wxService;
        this.schoolRepository = schoolRepository;
    }

    @Override
    protected StudentChargePlanRepository getEntityRepository() {
        return (StudentChargePlanRepository) super.getEntityRepository();
    }

    public void save(List<StudentCharge> list) {
        getEntityRepository().save(list);
    }

    public List<StudentCharge> findByStudentId(int studentId) {
        return getEntityRepository().findByStudentId(studentId);
    }

    public List<StudentCharge> findByCreateAtBetween(Instant startInstant, Instant endInstant) {
        return getEntityRepository().findByCreateAtBetween(startInstant, endInstant);
    }

    public List<StudentCharge> findPayList(int studentId, int klassId, long monthStartTime, long monthEndTime) {
        if (studentId > 0) {
            return getEntityRepository()
                    .findAllByStudentIdAndPaymentAtBetween(studentId, Instant.ofEpochMilli(monthStartTime), Instant.ofEpochMilli(monthEndTime));
        } else {
            return getEntityRepository()
                    .findAllByKlassIdAndPaymentAtBetween(klassId, Instant.ofEpochMilli(monthStartTime), Instant.ofEpochMilli(monthEndTime));
        }
    }

    public Page<StudentCharge> findNotPayList(int schoolId, int studentId, int klassId, long monthStartTime, long monthEndTime,
                                              Pageable pageable) {
        if (studentId == 0 && klassId == 0) {
            School school = new School(schoolId);
            return getEntityRepository().findBySchoolIdAndPaymentAtIsNullAndCreateAtAfter(schoolId, Instant.ofEpochMilli(monthEndTime), pageable);
        } else {
            if (studentId > 0) {
                return getEntityRepository()
                        .findAllByStudentIdAndPaymentAtIsNullAndCreateAtAfter(studentId, Instant.ofEpochMilli(monthStartTime), pageable);
            } else {
                return getEntityRepository()
                        .findAllByKlassIdAndPaymentAtIsNullAndCreateAtAfter(klassId, Instant.ofEpochMilli(monthStartTime), pageable);
            }
        }
    }

    public Page<StudentCharge> findArrearsList(Integer schoolId, int studentId, int klassId, long monthStartTime, long monthEndTime,
                                               Pageable pageable) {
        long zeroHourTime = Times.getZeroTimeOfToday();
        if (studentId == 0 && klassId == 0) {
            return getEntityRepository()
                    .findAllBySchoolIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtAfter(schoolId, Instant.ofEpochMilli(zeroHourTime), pageable);
        } else {
            if (studentId > 0) {
                return getEntityRepository()
                        .findAllByStudentIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtAfter(studentId, Instant.ofEpochMilli(zeroHourTime),
                                Instant.ofEpochMilli(monthStartTime), pageable);
            } else {
                return getEntityRepository()
                        .findAllByKlassIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtAfter(klassId, Instant.ofEpochMilli(zeroHourTime),
                                Instant.ofEpochMilli(monthStartTime), pageable);
            }
        }
    }

    public Page<StudentCharge> findArrearsListByRemainMoney(Integer schoolId, int studentId, int klassId, long monthStartTime, long monthEndTime,
                                                            Pageable pageable){
//        long zeroHourTime = Times.getZeroTimeOfToday();
        if (studentId == 0 && klassId == 0) {
            return getEntityRepository().findAllBySchoolIdAndRemainMoneyLessThan(schoolId, 0f, pageable);
//                    .findAllBySchoolIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtAfter(schoolId, Instant.ofEpochMilli(zeroHourTime), pageable);
        } else {
            if (studentId > 0) {
                return getEntityRepository().findAllByStudentIdAndRemainMoneyLessThan(studentId, 0f, pageable);
//                        .findAllByStudentIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtAfter(studentId, Instant.ofEpochMilli(zeroHourTime),
//                                Instant.ofEpochMilli(monthStartTime), pageable);
            } else {
                return getEntityRepository().findAllByKlassIdAndRemainMoneyLessThan(klassId, 0f, pageable);
//                        .findAllByKlassIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtAfter(klassId, Instant.ofEpochMilli(zeroHourTime),
//                                Instant.ofEpochMilli(monthStartTime), pageable);
            }
        }
    }

    public List<StudentCharge> findRemainMoneyList(int studentId, int klassId, long monthStartTime, long monthEndTime) {
        if (studentId > 0) {
            List<StudentCharge> studentChargesList = getEntityRepository()
                    .findByStudentIdAndCreateAtBetween(studentId, Instant.ofEpochMilli(monthStartTime), Instant.ofEpochMilli(monthEndTime));
            return studentChargesList;
        } else {
            List<StudentCharge> studentChargeList = getEntityRepository()
                    .findByKlassIdAndCreateAtBetween(klassId, Instant.ofEpochMilli(monthStartTime), Instant.ofEpochMilli(monthEndTime));
            return studentChargeList;
        }
    }

    public void updateByChargePlan(ChargePlan chargePlan) {
        getEntityRepository().updateByChargePlan(chargePlan.getExpenseEntry(), chargePlan.getPeriodDiscount(), chargePlan.getIdentDiscount(),
                chargePlan.getEndAt(), chargePlan.getMoney());
    }

    public void deleteByChargePlan(Integer changePlanId) {
        getEntityRepository().deleteByChargePlan(changePlanId);
    }

    public StudentCharge getByStudentIdAndExpenseId(int studentId, ExpenseEntry expenseEntry) {
        return getEntityRepository().findByStudentIdAndExpenseEntry(studentId, expenseEntry);
    }

    public List<StudentCharge> findAllBySchoolAndExpenseEntry(Integer schoolId, ExpenseEntry expenseEntry) {
        return getEntityRepository().findAllBySchoolIdAndExpenseEntry(schoolId, expenseEntry.getId());
    }

    public void deleteByExpenseEntry(ExpenseEntry entry) {
        getEntityRepository().deleteByExpenseEntry(entry);
    }

    public void deductExpenses(StudentService studentService, IncomeService incomeService, KlassService klassService, InterestKlassService interestKlassService, PaybackService paybackService,
                               StudentAccountService studentAccountService, IncomeDetailService incomeDetailService, StudentAccountChangeRecordService studentAccountChangeRecordService) {
        long currentTime = System.currentTimeMillis();
        List<School> schoolList = this.schoolRepository.findAll();
        for (School school : schoolList) {
            Integer schoolId = school.getId();
            LocalDate lastMonthDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).minusDays(2);
            LocalDate firstDayOfMonth = lastMonthDate.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate lastDayOfMonth = lastMonthDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
            logger.info("[deductExpenses] begin ...");
//            List<StudentCharge> list = this.getAll();
            // 需要查询周期为非一次性、没有结束周期的、教师经过确认的
            List<StudentCharge> list = this.getEntityRepository().findAllByStatusAndCreateAt(schoolId, firstDayOfMonth, lastDayOfMonth);
            Map<String, Income> klassIncomeMap = new HashMap<>(); // 班级收入
            Map<Integer, PaybackResult> paybackResultMap = new HashMap<>();
            Map<Integer, VacationSummary> vacationSummaryMap = new HashMap<>();
            int yyyyMM = Times.time2yyyyMM(System.currentTimeMillis());
            for (StudentCharge studentCharge : list) {
                ExpensePeriodType periodType = studentCharge.getExpenseEntry().getPeriodType();
                // 年，半年，季度，月 这4中周期类型，才需要按月扣钱
                if (!studentCharge.isPeriodDeduct()) {
                    continue;
                }

                float monthlyMoney = 0f;
                if (periodType == ExpensePeriodType.YEAR) {
                    monthlyMoney = studentCharge.calcPayMoney() / 12;
                } else if (periodType == ExpensePeriodType.HALF_YEAR) {
                    monthlyMoney = studentCharge.calcPayMoney() / 6;
                } else if (periodType == ExpensePeriodType.QUARTER) {
                    monthlyMoney = studentCharge.calcPayMoney() / 3;
                } else if (periodType == ExpensePeriodType.MONTH) {
                    monthlyMoney = studentCharge.calcPayMoney();
                }
                float inComeMoney = monthlyMoney;

                // 从账户表里扣除费用，如果不够，记录到studentCharge的remainMoney, 表示欠费, 欠费的，缴费时补扣
                boolean isInCome = true; // true: 算入园所收入表
                StudentAccount studentAccount = studentAccountService.findByStudentId(studentCharge.getStudentId());
                if (studentAccount == null || studentAccount.getMoney() <= 0) {
                    isInCome = false;
                } else {
                    if (studentAccount.getMoney() > inComeMoney) {
                        studentAccount.setMoney(studentAccount.getMoney() - inComeMoney); // 账户扣款
                    } else {
                        studentCharge.setRemainMoney(studentCharge.getRemainMoney() - (inComeMoney - studentAccount.getMoney())); // 账户不够，还要补交(inComeMoney-studentAccount.getMoney())
                        inComeMoney = studentAccount.getMoney(); // 实际扣除的钱为账户剩余额度
                        studentAccount.setMoney(0f); // 账户不够，直接设置为0
                    }
                    studentAccountChangeRecordService.create(studentAccount.getStudent().getId(), studentAccount, studentCharge.getKlassId(), inComeMoney, null, StudentAccountOpType.MONTHLY_TASK); // 账户变化明细
                }

//            // 当前周期结束了，生成下一个周期的收费计划
//            boolean success = studentCharge.checkAndCreateNext(currentTime);
//            if(!success){
//                continue;
//            }


                if (isInCome) {
                    String key = studentCharge.getSchoolId() + "_" + studentCharge.getKlassType().getId() + "_" + studentCharge.getKlassId() + "_" + studentCharge.getExpenseEntry().getId();
                    Income income = klassIncomeMap.get(key);
                    if (income == null) {
                        income = new Income(studentCharge.getSchoolId());
                        income.setStudentChargeId(studentCharge.getId());
                        income.setKlassType(studentCharge.getKlassType().getId());
                        income.setKlassId(studentCharge.getKlassId());
                        income.setExpenseId(studentCharge.getExpenseEntry().getId());
                        income.setNames(studentCharge.getExpenseEntry().getName());
                        income.setMoney(0f);
                        Klass klass = klassService.get(studentCharge.getKlassId());// 有可能是兴趣班的ID、、指定学生时分成两个，学生普通班，学生兴趣班
                        if (klass != null) {
                            income.setKlassName(klass.getName());
                        } else {
                            InterestKlass interestKlass = interestKlassService.get(studentCharge.getKlassId());
                            income.setKlassName(interestKlass.getName());
                        }
                        klassIncomeMap.put(key, income);
                    }
                    income.setMoney(income.getMoney() + inComeMoney);
                }

                VacationSummary vacationSummary = vacationSummaryMap.get(studentCharge.getStudentId());
                if (vacationSummary == null) {
                    vacationSummary = paybackService.getVacationSummary(studentCharge.getStudentId(), lastMonthDate, lastMonthDate);
                    vacationSummaryMap.put(studentCharge.getStudentId(), vacationSummary);
                }
                PaybackResult paybackResult = new PaybackResult();
                paybackService.calc(studentCharge, paybackResult, lastMonthDate, vacationSummary.getTermArr(), vacationSummary.getYearArr());
                paybackResultMap.put(studentCharge.getStudentId(), paybackResult);
                studentCharge.setPaybackMoney(paybackResult.getMoney());
                //检查是否要生成下一个周期的收费计划
                if (isInCome) {
                    boolean isNext = studentCharge.checkAndCreateNext(currentTime);
                }
                studentCharge.setStatus(EXCUTE);
                studentCharge.settCheck(false);
                this.save(studentCharge);

                // 统计班级收入
                CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
                    this.studentChargeRecordService.countStudentChargeKlass(studentCharge);
                });

                // 微信服务推送
//            CompletableFuture completableFuture = CompletableFuture.supplyAsync(()->{
//                String msg = "您的幼儿有新的缴费项目，请及时查收！";
//                wxService.sendStudentPlan(studentCharge, msg);
//                return null;
//            });

                IncomeDetail incomeDetail = new IncomeDetail();
                if (isInCome) {
                    incomeDetail.setMoney(inComeMoney); // 扣的费用
                }
                incomeDetail.setStudentId(studentCharge.getStudentId());
                incomeDetail.setStudentName(studentCharge.getStudentName());
                incomeDetail.setExpenseId(studentCharge.getExpenseEntry().getId());
                incomeDetail.setTheYearMonth(yyyyMM);
                incomeDetail.setRefundMoney(paybackResult.getMoney());
                incomeDetail.setCreateAt(Instant.now());
                incomeDetailService.save(incomeDetail);

                logger.info(
                        "[deductExpenses] dec money studentId=" + studentCharge.getStudentId() + " money=" + studentCharge.getMoney() + " decMoney="
                                + inComeMoney + " remainMoney=" + studentCharge.getRemainMoney());
            }

            for (Income income : klassIncomeMap.values()) {
                income.setCreateAt(Instant.now());
                income.setTheYearMonth(yyyyMM);
                income.setSrc(IncomeSrc.MONTHLY_DEDUCT.getId());
                incomeService.save(income);
            }

            // 上个月请假天数

            LocalDate lastMonthBeginLocalDate = lastMonthDate.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate lastMonthEndLocalDate = lastMonthDate.with(TemporalAdjusters.lastDayOfMonth());
            Collection<Vacation> vacationList = paybackService.getAllByCreateAt(lastMonthBeginLocalDate, lastMonthEndLocalDate);
            Map<Integer, StudentAccount> tmpStudentAccountMap = new HashMap<>();
            for (Vacation vacation : vacationList) {
                StudentAccount studentAccount = tmpStudentAccountMap.get(vacation.getStudentId());
                if (studentAccount == null) {
                    studentAccount = studentAccountService.findByStudentId(vacation.getStudentId());
                    if (studentAccount != null) {
                        studentAccount.setLeaveDays(0);// 清除上上个月的
                        tmpStudentAccountMap.put(vacation.getStudentId(), studentAccount);
                    }
                }
                if (studentAccount != null) {
                    // 累加这上个月的请假天数
                    studentAccount.setLeaveDays(studentAccount.getLeaveDays() + Times.getIntervalDays(vacation.getToDate(), vacation.getFromDate()));
                }
            }
            for (StudentAccount studentAccount : tmpStudentAccountMap.values()) {
                PaybackResult paybackResult = paybackResultMap.get(studentAccount.getStudent().getId());
                if (paybackResult != null) {
                    studentAccount.setPaybackMoney(paybackResult.getMoney());
                }
                studentAccountService.save(studentAccount);
            }
        }
    }

    public List<StudentCharge> findByKlassIdAndCreateAtBetween(Integer klassId, Instant startInstant, Instant endInstant) {
        return getEntityRepository().findByKlassIdAndCreateAtBetween(klassId, startInstant, endInstant);
    }

    public List<StudentCharge> findByKlassIdAndExpenseEntryAndCreateAtBetween(int klassId, ExpenseEntry expenseEntry, Instant startInstant, Instant endInstant) {
        return getEntityRepository().findByKlassIdAndExpenseEntryAndCreateAtBetween(klassId, expenseEntry, startInstant, endInstant);
    }

    public List<StudentCharge> findUnCheck(Actor actor, Integer klassId) {
        Integer schoolId = actor.getSchoolId();
        LocalDate localDate = LocalDate.now();

        Instant first = localDate.atStartOfDay().with(TemporalAdjusters.firstDayOfMonth()).toInstant(ZoneOffset.UTC);
        Instant second = localDate.plusMonths(1).atStartOfDay().with(TemporalAdjusters.firstDayOfMonth()).toInstant(ZoneOffset.UTC);

        return this.getEntityRepository().findAllBySchoolIdAndKlassIdAndStatusAndCreateAtBetween(schoolId, klassId, EXCUTE, first, second);
    }

    public void tCheck(Actor actor, Integer id) {
        StudentCharge studentCharge = this.getEntityRepository().findOne(id);
        studentCharge.settCheck(true);
        this.getEntityRepository().save(studentCharge);
    }

    public void endStuCharge(Actor actor, Integer id) {
        StudentCharge studentCharge = this.getEntityRepository().findOne(id);
        studentCharge.setStatus(END);
        this.getEntityRepository().save(studentCharge);
    }
}
