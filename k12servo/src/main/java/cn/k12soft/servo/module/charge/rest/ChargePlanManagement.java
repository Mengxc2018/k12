package cn.k12soft.servo.module.charge.rest;

import static cn.k12soft.servo.domain.enumeration.Permission.CHARGE_PLAN_CREATE;
import static cn.k12soft.servo.domain.enumeration.Permission.CHARGE_PLAN_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.CHARGE_PLAN_GET;
import static cn.k12soft.servo.domain.enumeration.Permission.CHARGE_PLAN_PUT;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.InterestKlass;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.ChargePlanTargetType;
import cn.k12soft.servo.domain.enumeration.KlassType;
import cn.k12soft.servo.domain.enumeration.StudentAccountOpType;
import cn.k12soft.servo.module.account.domain.StudentAccount;
import cn.k12soft.servo.module.account.domain.StudentAccountChangeRecord;
import cn.k12soft.servo.module.account.service.StudentAccountChangeRecordService;
import cn.k12soft.servo.module.account.service.StudentAccountService;
import cn.k12soft.servo.module.charge.domain.ChargeInfo;
import cn.k12soft.servo.module.charge.domain.ChargePlan;
import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.charge.domain.StudentChargeRemain;
import cn.k12soft.servo.module.charge.form.ChargePlanForm;
import cn.k12soft.servo.module.charge.form.ChargePlanManyForm;
import cn.k12soft.servo.module.charge.form.RefundForm;
import cn.k12soft.servo.module.charge.service.ChargePlanService;
import cn.k12soft.servo.module.charge.service.StudentChargePlanService;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.ExpenseIdentDiscount;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodDiscount;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodType;
import cn.k12soft.servo.module.expense.repository.ExpenseEntryRepository;
import cn.k12soft.servo.module.expense.service.ExpenseEntryService;
import cn.k12soft.servo.module.expense.service.PaybackService;
import cn.k12soft.servo.module.revenue.domain.Income;
import cn.k12soft.servo.module.revenue.domain.IncomeDetail;
import cn.k12soft.servo.module.revenue.domain.IncomeSrc;
import cn.k12soft.servo.module.revenue.service.IncomeDetailService;
import cn.k12soft.servo.module.revenue.service.IncomeService;
import cn.k12soft.servo.module.wxLogin.service.WxService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.InterestKlassService;
import cn.k12soft.servo.service.KlassService;
import cn.k12soft.servo.service.StudentService;
import cn.k12soft.servo.util.Times;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChargePlanManagement {

  private ChargePlanService chargePlanService;
  private StudentChargePlanService studentChargePlanService;
  private InterestKlassService interestKlassService;
  private StudentService studentService;
  private ExpenseEntryService expenseEntryService;
  private StudentAccountService studentAccountService;
  private StudentAccountChangeRecordService studentAccountChangeRecordService;
  private IncomeService incomeService;
  private IncomeDetailService incomeDetailService;
  private KlassService klassService;
  private PaybackService paybackService;
  private final ExpenseEntryRepository expenseEntryRepository;
  private final WxService wxService;

  @Autowired
  public ChargePlanManagement(ChargePlanService chargePlanService,
                              StudentChargePlanService studentChargePlanService,
                              InterestKlassService interestKlassService,
                              StudentService studentService,
                              ExpenseEntryService expenseEntryService,
                              StudentAccountService studentAccountService,
                              StudentAccountChangeRecordService studentAccountChangeRecordService,
                              IncomeService incomeService,
                              IncomeDetailService incomeDetailService,
                              KlassService klassService,
                              PaybackService paybackService,
                              ExpenseEntryRepository expenseEntryRepository, WxService wxService) {
    this.chargePlanService = chargePlanService;
    this.studentChargePlanService = studentChargePlanService;
    this.interestKlassService = interestKlassService;
    this.studentService = studentService;
    this.expenseEntryService = expenseEntryService;
    this.studentAccountService = studentAccountService;
    this.studentAccountChangeRecordService = studentAccountChangeRecordService;
    this.incomeService = incomeService;
    this.incomeDetailService = incomeDetailService;
    this.klassService = klassService;
    this.paybackService = paybackService;
    this.expenseEntryRepository = expenseEntryRepository;
    this.wxService = wxService;
  }

  @ApiOperation("批量：发起收费计划")
  @PostMapping(value = "/charge/createMany")
  @PermissionRequired(CHARGE_PLAN_CREATE)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public void createMany(@Active Actor actor, @RequestBody List<ChargePlanManyForm> forms){
    Integer schoolId = actor.getSchoolId();
    for (ChargePlanManyForm form : forms){

      String expenseName = form.getExpenseName();// 费种名称

      ExpenseEntry expenseEntry = this.expenseEntryService.findByNameAndSchoolId(expenseName, schoolId);

      int cycleId = expenseEntry.getPeriodDiscounts().get(0).getId();
      int identityId = expenseEntry.getIdentDiscounts().get(0).getId();
      int targetType = form.getTargetType();
      String target = form.getTarget();
      Instant endAt = form.getEndAt();
      float money = form.getMoney();

      ExpensePeriodDiscount periodDiscount = null;
      List<ExpensePeriodDiscount> periodDiscountList = expenseEntry.getPeriodDiscounts();
      for (ExpensePeriodDiscount epd : periodDiscountList) {
        if (cycleId == epd.getId()) {
          periodDiscount = epd;
          break;
        }
      }
      if (periodDiscount == null) {
        return;
      }

      ExpenseIdentDiscount identDiscount = null;
      List<ExpenseIdentDiscount> identDiscountList = expenseEntry.getIdentDiscounts();
      for (ExpenseIdentDiscount eid : identDiscountList) {
        if (identityId == eid.getId()) {
          identDiscount = eid;
          break;
        }
      }
      if (identDiscount == null) {
        return;
      }

      List<StudentCharge> list = new LinkedList<>();

      String[] ids = StringUtils.splitByWholeSeparator(target, ",");
      KlassType klassType = null;
      int periodDate = Times.time2yyyyMM(System.currentTimeMillis());
      // 已经创建了的收费计划(退费转入费种的时候，会提前生成下个周期的收费计划, 所以现在发起收费计划，要过滤掉已有的)
      List<StudentCharge> alreadyCreatedList = this.studentChargePlanService
              .findAllBySchoolAndExpenseEntry(actor.getSchoolId(), expenseEntry);
      if (targetType == ChargePlanTargetType.COMMON_KLASS.getId()) {
        klassType = KlassType.COMMON;
        // 避免重名
        for (String klassId : ids) {
          Map<String, String> queryMap = new HashMap<>();
          queryMap.put("klassId", klassId);
          List<Student> studentList = this.studentService.query(queryMap);
          _createStudentCharge(actor.getSchoolId(), list, studentList, alreadyCreatedList, expenseEntry, identDiscount, periodDiscount, money,
                  endAt, Integer.valueOf(klassId), klassType, periodDate);
        }
      } else if (targetType == ChargePlanTargetType.INTEREST_KLASS.getId()) {
        for (String interestKlassId : ids) {
          InterestKlass interestKlass = this.interestKlassService.get(Integer.valueOf(interestKlassId));
          klassType = interestKlass.getType();
          _createStudentCharge(actor.getSchoolId(), list, interestKlass.getStudents(), alreadyCreatedList, expenseEntry, identDiscount,
                  periodDiscount, money, endAt, interestKlass.getId(), klassType, periodDate);
        }

      } else if (targetType == ChargePlanTargetType.STUDENT.getId()) {
        List<Student> studentList = new LinkedList<>();
        for (String studentId : ids) {
//            Student student = studentService.findByName(studentId);
            List<Student> students = studentService.getByName(studentId);
            for (Student student : students){
                Optional<Student> studentOptinal = this.studentService.find(student.getId());
                if (studentOptinal.isPresent()) {
                    studentList.add(studentOptinal.get());
                }
            }
        }
        _createStudentCharge(actor.getSchoolId(), list, studentList, alreadyCreatedList, expenseEntry, identDiscount, periodDiscount, money,
                endAt, 0, klassType, periodDate);
      }
      if (list.size() > 0) {
        ChargePlan chargePlan = new ChargePlan(actor.getSchoolId());
        chargePlan.setExpenseEntry(expenseEntry);
        chargePlan.setPeriodDiscount(periodDiscount);
        chargePlan.setIdentDiscount(identDiscount);
        chargePlan.setTargetType(targetType);
        chargePlan.setTarget(target);
        chargePlan.setEndAt(endAt);
        chargePlan.setMoney(money);
        chargePlan.createReceiptId();
        chargePlan.setCreateAt(Instant.now());
        this.chargePlanService.save(chargePlan);
        for (StudentCharge stuChargePlan : list) {
          stuChargePlan.setPlanId(chargePlan.getId());
        }
        this.studentChargePlanService.save(list);
      }
    }
  }

  @ApiOperation("发起收费计划")
  @PostMapping(value = "/charge/create")
  @PermissionRequired(CHARGE_PLAN_CREATE)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public void create(@Active Actor actor, @RequestBody ChargePlanForm form) {
    int expenseId = form.getExpenseId();// 费种id
    int cycleId = form.getCycleId();
    int identityId = form.getIdentityId();
    int targetType = form.getTargetType();
    String target = form.getTarget();   // 应用对象可能多个，多种类型：普通班级id、兴趣班id、学生id
    Instant endAt = form.getEndAt();
    float money = form.getMoney();

    // 选出周期性折扣、身份类型折扣------->开始
    ExpenseEntry expenseEntry = this.expenseEntryService.get(expenseId);
    ExpensePeriodDiscount periodDiscount = null;
    List<ExpensePeriodDiscount> periodDiscountList = expenseEntry.getPeriodDiscounts();
    for (ExpensePeriodDiscount epd : periodDiscountList) {
      if (cycleId == epd.getId()) {
        periodDiscount = epd;
        break;
      }
    }
    if (periodDiscount == null) {
      return;
    }

    ExpenseIdentDiscount identDiscount = null;
    List<ExpenseIdentDiscount> identDiscountList = expenseEntry.getIdentDiscounts();
    for (ExpenseIdentDiscount eid : identDiscountList) {
      if (identityId == eid.getId()) {
        identDiscount = eid;
        break;
      }
    }
    if (identDiscount == null) {
      return;
    }
    // 选出周期性折扣、身份类型折扣------->结束

    List<StudentCharge> list = new LinkedList<>();

    String[] ids = StringUtils.splitByWholeSeparator(target, ",");
    KlassType klassType = null;
    int periodDate = Times.time2yyyyMM(System.currentTimeMillis());
    // 已经创建了的收费计划(退费转入费种的时候，会 提前生成 下个周期的收费计划, 所以现在发起收费计划，要过滤掉已有的)
    List<StudentCharge> alreadyCreatedList = this.studentChargePlanService
      .findAllBySchoolAndExpenseEntry(actor.getSchoolId(), expenseEntry);
    if (targetType == ChargePlanTargetType.COMMON_KLASS.getId()) {
      klassType = KlassType.COMMON;
      for (String klassId : ids) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("klassId", klassId);
        List<Student> studentList = this.studentService.query(queryMap);
        _createStudentCharge(actor.getSchoolId(), list, studentList, alreadyCreatedList, expenseEntry, identDiscount, periodDiscount, money,
          endAt, Integer.valueOf(klassId), klassType, periodDate);
      }
    } else if (targetType == ChargePlanTargetType.INTEREST_KLASS.getId()) {
      for (String interestKlassId : ids) {
        InterestKlass interestKlass = this.interestKlassService.get(Integer.valueOf(interestKlassId));
        klassType = interestKlass.getType();
        _createStudentCharge(actor.getSchoolId(), list, interestKlass.getStudents(), alreadyCreatedList, expenseEntry, identDiscount,
          periodDiscount, money, endAt, interestKlass.getId(), klassType, periodDate);
      }

    } else if (targetType == ChargePlanTargetType.STUDENT.getId()) {
      List<Student> studentList = new LinkedList<>();
      for (String studentId : ids) {
        Optional<Student> studentOptinal = this.studentService.find(Integer.valueOf(studentId));
        if (studentOptinal.isPresent()) {
          studentList.add(studentOptinal.get());
        }
      }
      _createStudentCharge(actor.getSchoolId(), list, studentList, alreadyCreatedList, expenseEntry, identDiscount, periodDiscount, money,
        endAt, 0, klassType, periodDate);
    }
    if (list.size() > 0) {
      ChargePlan chargePlan = new ChargePlan(actor.getSchoolId());
      chargePlan.setExpenseEntry(expenseEntry);
      chargePlan.setPeriodDiscount(periodDiscount);
      chargePlan.setIdentDiscount(identDiscount);
      chargePlan.setTargetType(targetType);
      chargePlan.setTarget(target);
      chargePlan.setEndAt(endAt);
      chargePlan.setMoney(money);
      chargePlan.createReceiptId();
      chargePlan.setCreateAt(Instant.now());
      this.chargePlanService.save(chargePlan);
      for (StudentCharge stuChargePlan : list) {
        stuChargePlan.setPlanId(chargePlan.getId());
      }
      this.studentChargePlanService.save(list);
    }
  }

  @ApiOperation("获取本月发起的收费计划")
  @GetMapping(value = "/charge/findPlan")
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
  Page getByCreateAt(@Active Actor actor,
                     @RequestParam(value = "startTime", required = true) long startTime,
                     @RequestParam(value = "page", required = true) int page,
                     @RequestParam(value = "size") Integer size) {
    int pageSize = 10;
    if (size != null) {
      pageSize = size;
    }
    if (startTime == 0) {
      startTime = System.currentTimeMillis();
    }
    page = Math.max(0, page - 1);
    long firstDayOfMonthTime = Times.monthStartTime(startTime);
    Times.getBeginAndEndOfCurrentMonth();
    Date date = new Date();
    date.setTime(firstDayOfMonthTime);
    Instant startTimeInstant = date.toInstant();

    Sort sort = new Sort(Sort.Direction.DESC, "createAt");
    Pageable pageable = new PageRequest(page, pageSize, sort);
    return this.chargePlanService.findBySchoolIdAndCreateAt(actor.getSchoolId(), startTimeInstant, pageable);
  }

  @ApiOperation("修改收费计划")
  @PutMapping("/charge/update/{chargePlanId:\\d+}")
  @PermissionRequired(CHARGE_PLAN_PUT)
  @Timed
  public void editChargePlan(@PathVariable Integer chargePlanId,
                             @RequestBody ChargePlanForm form) {
    int cycleId = form.getCycleId();
    int identityId = form.getIdentityId();
    Instant endAt = form.getEndAt();
    float money = form.getMoney();

    ChargePlan chargePlan = this.chargePlanService.get(chargePlanId);
    if (chargePlan == null) {
      return;
    }

    ExpenseEntry expenseEntry = this.expenseEntryService.get(chargePlan.getExpenseEntry().getId()); //TODO 费种不能修改??
    ExpensePeriodDiscount periodDiscount = null;
    List<ExpensePeriodDiscount> periodDiscountList = expenseEntry.getPeriodDiscounts();
    for (ExpensePeriodDiscount epd : periodDiscountList) {
      if (cycleId == epd.getId()) {
        periodDiscount = epd;
        break;
      }
    }
    if (periodDiscount == null) {
      return;
    }

    ExpenseIdentDiscount identDiscount = chargePlan.getIdentDiscount(); // 应用类型
    List<ExpenseIdentDiscount> identDiscountList = expenseEntry.getIdentDiscounts();
    for (ExpenseIdentDiscount eid : identDiscountList) {
      if (identityId == eid.getId()) {
        identDiscount = eid;
        break;
      }
    }
    if (identDiscount == null) {
      return;
    }

    chargePlan.setPeriodDiscount(periodDiscount);
    chargePlan.setIdentDiscount(identDiscount);
    chargePlan.setEndAt(endAt);
    chargePlan.setMoney(money);
    this.chargePlanService.save(chargePlan);
    this.studentChargePlanService.updateByChargePlan(chargePlan);
  }

  @ApiOperation("删除收费计划")
  @DeleteMapping("/charge/delete/{chargePlanId:\\d+}")
  @PermissionRequired(CHARGE_PLAN_DELETE)
  @Timed
  public void delChargePlan(@PathVariable Integer chargePlanId) {
    ChargePlan chargePlan = this.chargePlanService.get(chargePlanId);
    if (chargePlan == null) {
      return;
    }
    this.chargePlanService.delete(chargePlanId);
    this.studentChargePlanService.deleteByChargePlan(chargePlanId);
  }

  // 按学生id查询
  @ApiOperation("获取单个学生的收费详细")
  @GetMapping(value = "/charge/findStuPlan", params = {"studentId"})
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
  List<StudentCharge> getStudentChargePlanList(@RequestParam("studentId") int studentId) {
    return this.studentChargePlanService.findByStudentId(studentId);
  }

  @ApiOperation("获取班级所有学生的收费详细")
  @GetMapping(value = "/charge/findStuPlanByKlass")
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
  List<StudentCharge> getStudentPlanByKlass(@Active Actor actor, @RequestParam(value = "startTime", required = true) long startTime,
                                            @RequestParam("klassId") int klassId, @RequestParam("expenseId") int expenseId){
    if (startTime == 0) {
      startTime = System.currentTimeMillis();
    }
    startTime = Times.monthStartTime(startTime); // 取月初
    long endTime = Times.monthEndTime(startTime);
    Instant startInstant = Instant.ofEpochMilli(startTime);
    Instant endInstant = Instant.ofEpochMilli(endTime);
    if(expenseId<=0) {
      return this.studentChargePlanService.findByKlassIdAndCreateAtBetween(klassId, startInstant, endInstant);
    }else{
      ExpenseEntry expenseEntry = this.expenseEntryService.get(expenseId);
      return this.studentChargePlanService.findByKlassIdAndExpenseEntryAndCreateAtBetween(klassId, expenseEntry, startInstant, endInstant);
    }
  }

  /**
   * 半年：9-2，3-8
   * @param startTime
   * @return
   */
  // 按创建时间查询学生的收费计划
  @ApiOperation("获取所有学生的收费详细")
  @GetMapping(value = "/charge/findAllStuPlan", params = {"startTime"})
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
  List<StudentCharge> getStudentPlanByCreateAt(@RequestParam("startTime") long startTime) {
    if (startTime == 0) {
      startTime = System.currentTimeMillis();
    }

    Calendar startCal = Calendar.getInstance();
    Calendar endCal = Calendar.getInstance();
    startCal.setTimeInMillis(startTime);

    int yearInt = startCal.get(Calendar.YEAR);
    int monthInt = startCal.get(Calendar.MONTH)+1;
    int month10 = 10 - 1;
    int month2 = 2 - 1;
    int day = 1;
    int time = 0;

    startCal.set(Calendar.DATE, day);
    startCal.set(Calendar.HOUR_OF_DAY, time);
    startCal.set(Calendar.MINUTE, time);
    startCal.set(Calendar.SECOND, time);

    endCal.set(Calendar.DATE, day);
    endCal.set(Calendar.HOUR_OF_DAY, time);
    endCal.set(Calendar.MINUTE, time);
    endCal.set(Calendar.SECOND, time);

    // 当年的2月-9月
    if (monthInt < 9 && monthInt >2){

      startCal.set(Calendar.YEAR, yearInt);
      startCal.set(Calendar.MONTH, month2);

      endCal.set(Calendar.YEAR, yearInt);
      endCal.set(Calendar.MONTH, month10);

    }else if (monthInt > 10 && monthInt < 12){

      // 如果比10月大比12月小
      startCal.set(Calendar.YEAR, yearInt);
      startCal.set(Calendar.MONTH, month10);

      endCal.set(Calendar.YEAR, yearInt +1 );
      endCal.set(Calendar.MONTH, month2);

    }else if (monthInt > 0 && monthInt < 2){

      // 如果当前月份在1月份
      startCal.set(Calendar.YEAR, yearInt - 1);
      startCal.set(Calendar.MONTH, month10);

      endCal.set(Calendar.YEAR, yearInt);
      endCal.set(Calendar.MONTH, month2);
    }

    Instant startInstant = Instant.ofEpochMilli(startCal.getTimeInMillis()).plusSeconds(8*3600);
    Instant endInstant = Instant.ofEpochMilli(endCal.getTimeInMillis()).plusSeconds(8*3600);

    return this.studentChargePlanService.findByCreateAtBetween(startInstant, endInstant);
  }

  // 标记为已收费
  @ApiOperation("标记已收费")
  @PutMapping(value = "/charge/payed")
  @PermissionRequired(CHARGE_PLAN_PUT)
  @Timed
  public StudentCharge pay(@RequestParam("id") int id, @RequestParam("money") int money, @RequestParam("userAccount") int useAccount) {
    StudentCharge studentCharge = this.studentChargePlanService.get(id);
    if (studentCharge != null && studentCharge.getPaymentAt() == null) {
      studentCharge.setPaymentAt(Instant.now());
      // 检查欠费
      checkArrears(studentCharge, useAccount);
    }
    return studentCharge;
  }

  // 标记为已收费
  @ApiOperation("核对收费")
  @PutMapping(value = "/charge/check", params = {"id"})
  @PermissionRequired(CHARGE_PLAN_PUT)
  @Timed
  public StudentCharge onCheck(@Active Actor actor, @RequestParam("id") int id) {
    StudentCharge studentCharge = this.studentChargePlanService.get(id);
//    if (studentCharge != null && studentCharge.getCheckAt() == null) {
    if (studentCharge != null) {
      studentCharge.setChecker(actor);
      studentCharge.setCheckAt(Instant.now());
      this.studentChargePlanService.save(studentCharge);
    }
    return studentCharge;
  }

  // 已缴费查询
  @ApiOperation("已缴费查询")
  @GetMapping(value = "/charge/findPaid")
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
  List<StudentCharge> getPayList(@RequestParam(value = "startTime", required = true) long startTime,
                                 @RequestParam("klassId") Integer klassIdInteger, @RequestParam("studentId") Integer studentIdInteger) {
    int studentId = 0;
    int klassId = 0;
    if (klassIdInteger != null) {
      klassId = klassIdInteger.intValue();
    }
    if (studentIdInteger != null) {
      studentId = studentIdInteger.intValue();
    }

    if (studentId == 0 && klassId == 0) {
      return Collections.emptyList();
    }
    if (startTime == 0) {
      startTime = System.currentTimeMillis();
    }
    long monthStartTime = Times.monthStartTime(startTime);
    long monthEndTime = Times.monthEndTime(monthStartTime);
    return this.studentChargePlanService.findPayList(studentId, klassId, monthStartTime, monthEndTime);
  }

  // 未缴费查询
  @ApiOperation("未缴费查询")
  @GetMapping(value = "/charge/findNotPaid")
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
  Page<StudentCharge> getNoPayList(@Active Actor actor, @RequestParam(value = "startTime", required = true) long startTime,
                                   @RequestParam("klassId") Integer klassIdInteger,
                                   @RequestParam("studentId") Integer studentIdInteger,
                                   @RequestParam(value = "page", required = true) int page,
                                   @RequestParam(value = "size", required = false) Integer size) {
    int pageSize = 10;
    if (size != null) {
      pageSize = size;
    }
    int studentId = 0;
    int klassId = 0;
    if (studentIdInteger != null) {
      studentId = studentIdInteger.intValue();
    }
    if (klassIdInteger != null) {
      klassId = klassIdInteger.intValue();
    }

    page = Math.max(0, page - 1);

    long monthStartTime = Times.monthStartTime(startTime);
    long monthEndTime = Times.monthEndTime(monthStartTime);
    Sort sort = new Sort(Sort.Direction.DESC, "createAt");
    Pageable pageable = new PageRequest(page, pageSize, sort);
    return this.studentChargePlanService.findNotPayList(actor.getSchoolId(), studentId, klassId, startTime, monthEndTime, pageable);
  }

  // 欠费查询
  @ApiOperation("欠费查询")
  @GetMapping(value = "/charge/findArrears")
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
  Page<StudentCharge> getArrearsList(@Active Actor actor, @RequestParam(value = "startTime", required = true) long startTime,
                                     @RequestParam("klassId") Integer klassIdInteger, @RequestParam("studentId") Integer studentIdInteger,
                                     @RequestParam(value = "page", required = true) int page,
                                     @RequestParam(value = "size", required = false) Integer size) {
    int pageSize = 10;
    if (size != null) {
      pageSize = size;
    }
    int studentId = 0;
    int klassId = 0;
    if (studentIdInteger != null) {
      studentId = studentIdInteger.intValue();
    }
    if (klassIdInteger != null) {
      klassId = klassIdInteger.intValue();
    }
    page = Math.max(0, page - 1);
    long monthStartTime = Times.monthStartTime(startTime);
    long monthEndTime = Times.monthEndTime(monthStartTime);
    Sort sort = new Sort(Sort.Direction.DESC, "createAt");
    Pageable pageable = new PageRequest(page, pageSize, sort);
      // remainMoney<0 就算欠费
      return this.studentChargePlanService.findArrearsListByRemainMoney(actor.getSchoolId(), studentId, klassId, monthStartTime, monthEndTime, pageable);
  }

  // 当月余额查询
  @ApiOperation("当月余额查询")
  @GetMapping(value = "/charge/findRemains")
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
  List<StudentChargeRemain> getRemainMoneyList(@RequestParam(value = "startTime", required = true) long startTime,
                                               @RequestParam("klassId") Integer klassIdInteger,
                                               @RequestParam("studentId") Integer studentIdInteger) {
    int klassId = 0;
    int studentId = 0;
    if (studentIdInteger != null) {
      studentId = studentIdInteger.intValue();
    }
    if (klassIdInteger != null) {
      klassId = klassIdInteger.intValue();
    }

    if (studentId == 0 && klassId == 0) {
      return Collections.emptyList();
    }

    long monthStartTime = Times.monthStartTime(startTime);
    long monthEndTime = Times.monthEndTime(monthStartTime);

    List<StudentCharge> studentChargeList = this.studentChargePlanService
      .findRemainMoneyList(studentId, klassId, monthStartTime, monthEndTime);
    Map<Integer, StudentChargeRemain> remainMap = new HashMap<>();
    for (StudentCharge studentCharge : studentChargeList) {
      // 不是按月扣除的周期类型， 没有余额
      if (!studentCharge.isPeriodDeduct()) {
        continue;
      }
      StudentChargeRemain remain = remainMap.get(studentCharge.getStudentId());
      if (remain == null) {
        Map<Integer, Float> cycleInfoMap = new HashMap<>();
        remain = new StudentChargeRemain(studentCharge.getStudentId(), studentCharge.getKlassId(), studentCharge.getStudentName(),
          cycleInfoMap);
        remainMap.put(remain.getStudentId(), remain);
      }
      remain.getExpenseEntryMap().put(studentCharge.getExpenseEntry().getId(), studentCharge.getRemainMoney());
    }
    List<StudentChargeRemain> remainList = new LinkedList<StudentChargeRemain>();
    remainList.addAll(remainMap.values());
    return remainList;
  }

  private void _createStudentCharge(Integer schoolId, List<StudentCharge> list, List<Student> studentList,
                                    List<StudentCharge> alreadyCreatedList, ExpenseEntry expenseEntry, ExpenseIdentDiscount identDiscount,
                                    ExpensePeriodDiscount periodDiscount, float money, Instant endAt, int klassId, KlassType klassType,
                                    int periodDate) {
    for (Student student : studentList) {
      klassId = student.getKlass().getId();
      StudentCharge studentCharge = null;
      StudentCharge originalStudentCharge = _alreadyCreated(alreadyCreatedList, student, expenseEntry);
      boolean is = true;  // 是否推送微信服务消息
      if (originalStudentCharge != null) {
        boolean isNext = originalStudentCharge.checkAndCreateNext(System.currentTimeMillis());
        if (isNext) {
          studentCharge = originalStudentCharge;
          list.add(originalStudentCharge);
        }else{
          is = false;
        }
      } else {
        StudentCharge stuChargePlan = new StudentCharge(schoolId);
        stuChargePlan.setPeriodDate(periodDate);
        stuChargePlan.setStudentId(student.getId());
        stuChargePlan.setStudentName(student.getName());
        stuChargePlan.setExpenseEntry(expenseEntry);
        stuChargePlan.setPeriodDiscount(periodDiscount);
        stuChargePlan.setKlassId(klassId);
        stuChargePlan.setKlassType(klassType);
        stuChargePlan.setIdentDiscount(identDiscount);
        stuChargePlan.setMoney(money);
        stuChargePlan.setCreateAt(Instant.now());
        stuChargePlan.setEndAt(endAt);
        stuChargePlan.setEndMills(endAt.toEpochMilli() - System.currentTimeMillis());
        stuChargePlan.setRemainMoney(0f); // 剩余的钱等于money
        studentCharge = stuChargePlan;
        list.add(stuChargePlan);
      }

      // 微信推送
//      if(is) {
//        StudentCharge finalStudentCharge = studentCharge;
//        CompletableFuture completableFuture = CompletableFuture.supplyAsync(()->{
//        String msg = "您的幼儿的缴费项目已经生成啦，快去看看吧！";
//        wxService.sendStudentPlan(finalStudentCharge, msg);
//        return null;
//      });
//      }

    }
  }

  private StudentCharge _alreadyCreated(List<StudentCharge> alreadyCreatedList, Student student, ExpenseEntry expenseEntry) {
    for (StudentCharge studentCharge : alreadyCreatedList) {
      if (student.getId().equals(studentCharge.getStudentId())
        && studentCharge.getExpenseEntry().getId().equals(expenseEntry.getId())) {
        return studentCharge;
      }
    }
    return null;
  }

  @ApiOperation("费种转入")
  @PostMapping(value = "/charge/refund")
  @PermissionRequired(CHARGE_PLAN_CREATE)
  @Timed
  @Transactional
  public void refund(@Active Actor actor, @RequestBody RefundForm refundForm) {
    Student student = this.studentService.get(refundForm.getStudentId());
    StudentCharge studentCharge = this.studentChargePlanService.get(refundForm.getStudentChargeId());// 要转出的收费计划
    if (studentCharge == null) {
      return; // 没必要转
    }
    ExpenseEntry toExpenseEntry = this.expenseEntryService.get(refundForm.getToExpenseId()); // 转入费种
    ExpensePeriodDiscount periodDiscount = null;
    ExpenseIdentDiscount identDiscount = null;

    for (ExpensePeriodDiscount epd : toExpenseEntry.getPeriodDiscounts()) {
      if (epd.getId().equals(refundForm.getCycleId())) {
        periodDiscount = epd;
        break;
      }
    }
    for (ExpenseIdentDiscount eid : toExpenseEntry.getIdentDiscounts()) {
      if (eid.getId().equals(refundForm.getIdentityId())) {
        identDiscount = eid;
        break;
      }
    }

    // 注释原因，：如果没有折扣的情况下，就直接return了，下面的费用转入转出逻辑也不走了
//    if (periodDiscount == null || identDiscount == null) {
//      return;
//    }

    StudentCharge toStudentCharge = this.studentChargePlanService.getByStudentIdAndExpenseId(refundForm.getStudentId(), toExpenseEntry);

    // 先把余额存入账号
    StudentAccount studentAccount = this.studentAccountService.findByStudentId(studentCharge.getStudentId());
    float needMoney = refundForm.getTotalMoney() - refundForm.getDescMoney();
    if(needMoney>0){
      // 钱不够，需要额外交费, 先从账户里扣
      if(studentAccount != null){
        float studentAccountMoney = studentAccount.getMoney();
        studentAccount.setMoney(Math.max(0, studentAccount.getMoney() - needMoney));
        this.studentAccountService.save(studentAccount);
        this.studentAccountChangeRecordService.create(student, studentAccount,student.getKlass().getId(), studentAccountMoney-studentAccount.getMoney(), actor, StudentAccountOpType.REFUND);
        needMoney = needMoney - studentAccountMoney;
      }
    }

    this.studentChargePlanService.delete(studentCharge.getId()); // 删掉转出费种(转出的学生收费记录)

    // 生成下一个周期的收费计划
    List<StudentCharge> newStudentChargeList = new LinkedList<>();
    List<Student> studentList = new LinkedList<>();
    studentList.add(student);
    Instant endAt = refundForm.getEndAt();
    if (toStudentCharge != null) {
      if(needMoney>0){
        toStudentCharge.setRemainMoney(toStudentCharge.getRemainMoney() - needMoney);
      }else{
        toStudentCharge.setRemainMoney(toStudentCharge.getRemainMoney() + studentCharge.getRemainMoney());
      }
    } else {
        // 改了需求，只能转入已经存在的费种
//      int nextPeriodDate = Times.time2yyyyMM(System.currentTimeMillis());
//      List<StudentCharge> emptyList = new LinkedList<>();
//      float newMoney = studentCharge.getRemainMoney();
//      if(needMoney>0){
//        newMoney = -newMoney; // 账户余额不足，置为负的，表示欠费
//      }
//      _createStudentCharge(actor.getSchoolId(), newStudentChargeList, studentList, emptyList, toExpenseEntry, identDiscount, periodDiscount,
//              newMoney, endAt, student.getKlass().getId(), KlassType.COMMON, nextPeriodDate);
    }
  }

    @ApiOperation("预缴费")
    @PutMapping(value ="/charge/prePay")
    @PermissionRequired(CHARGE_PLAN_CREATE)
    @Timed
    public void prePayment(@Active Actor actor,@RequestParam(value = "studentId", required = true) Integer studentId,
                           @RequestParam("klassId") Integer klassId,
                           @RequestParam("money") Float money){
      Student student = this.studentService.get(studentId);
      StudentAccount studentAccount = this.studentAccountService.findByStudentId(studentId);
      if(studentAccount == null){
          studentAccount = new StudentAccount(student, 0f);
      }
      studentAccount.setMoney(studentAccount.getMoney() + money);
      this.studentAccountService.save(studentAccount);
      this.studentAccountChangeRecordService.create(student, studentAccount,klassId, money, actor, StudentAccountOpType.PRE_PAY);
    }

  @ApiOperation("获取学生账户")
      @GetMapping(value = "/charge/findAccount")
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
    public StudentAccount getStudentAccount(@Active Actor actor,@RequestParam(value = "studentId", required = true) Integer studentId){
      return this.studentAccountService.findByStudentId(studentId);
    }

  @ApiOperation("修改预缴费")
  @PutMapping(value ="/charge/updatePrePay")
  @PermissionRequired(CHARGE_PLAN_CREATE)
  @Timed
    public StudentAccount updatePrePayment(@Active Actor actor,@RequestParam(value = "studentId", required = true) Integer studentId,
                                           @RequestParam(value="money",required = true) Float money){
      Student student = this.studentService.get(studentId);
      StudentAccount studentAccount = this.studentAccountService.findByStudentId(studentId);
      if(studentAccount == null){
        return null;
      }

      studentAccount.setMoney(studentAccount.getMoney() + money);
      this.studentAccountService.save(studentAccount);
      this.studentAccountChangeRecordService.create(student, studentAccount,student.getKlass().getId(), money, actor, StudentAccountOpType.UPDATE_PRE_PAY);
      return this.studentAccountService.get(studentAccount.getId());
    }

    @ApiOperation("账户明细")
    @GetMapping(value = "/charge/accountRecord")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    public Page<StudentAccountChangeRecord> getStudentAccountChangeRecord(@RequestParam(value = "studentId", required = true) int studentId, @RequestParam(value = "page", required = true) int page){
        int pageSize = 10;
        page = Math.max(0, page - 1);
        Sort sort = new Sort(Sort.Direction.DESC, "createAt");
        Pageable pageable = new PageRequest(page, pageSize, sort);
        return this.studentAccountChangeRecordService.findByStudentId(studentId, pageable);
    }

  @ApiOperation("手动执行扣费任务")
  @GetMapping(value = "/charge/execTask")
  @PermissionRequired(CHARGE_PLAN_CREATE)
  @Timed
    public void monthlyTask(){
      this.studentChargePlanService.deductExpenses(this.studentService, this.incomeService, this.klassService, this.interestKlassService, this.paybackService, this.studentAccountService, this.incomeDetailService, this.studentAccountChangeRecordService);
    }

  @ApiOperation("学生费用信息")
  @GetMapping(value = "/charge/chargeInfo")
  @PermissionRequired(CHARGE_PLAN_GET)
  @Timed
    public ChargeInfo getChargeInfo(@RequestParam(value = "studentId", required = true) int studentId){
      Student student = this.studentService.get(studentId);
      if(student == null){
        return null;
      }
      ChargeInfo chargeInfo = new ChargeInfo();
      chargeInfo.setStudentId(studentId);
      chargeInfo.setName(student.getName());
      chargeInfo.setSchoolId(student.getSchoolId());
      chargeInfo.setKlassId(student.getKlass().getId());
      StudentAccount studentAccount = this.studentAccountService.findByStudentId(studentId);
      if(studentAccount != null){
        chargeInfo.setLeaveDays(studentAccount.getLeaveDays());
        chargeInfo.setPaybackMoney(studentAccount.getPaybackMoney());
      }
      List<StudentCharge>  studentChargeList = this.studentChargePlanService.findByStudentId(studentId);
      chargeInfo.getStudentChargeList().addAll(studentChargeList);
      return chargeInfo;
    }

  @ApiOperation("补充学生账户")
  @GetMapping(value = "/charge/suppStudentAccount")
  @PermissionRequired(CHARGE_PLAN_CREATE)
  @Timed
    public int checkAndCreateStudentAccount(){
      List<StudentAccount> studentAccountList = this.studentAccountService.getAll();
      List<Student> studentList = this.studentService.getAll();
      int insertCount = 0;
      for (Student stu : studentList) {
          if(_noAccount(stu, studentAccountList)){
            StudentAccount studentAccount = new StudentAccount(stu, 0f);
            this.studentAccountService.save(studentAccount);
            insertCount++;
          }
      }
      return insertCount;
    }

  private boolean _noAccount(Student stu, List<StudentAccount> studentAccountList) {
    for (StudentAccount studentAccount : studentAccountList) {
      if(studentAccount.getStudent().getId() == stu.getId()){
        return false;
      }
    }
    return true;
  }

  private void checkArrears(StudentCharge studentCharge, int useAccount){
      float remainMoney = studentCharge.getRemainMoney(); // 欠费金额
      if(remainMoney>=0){
          return; // remainMoney >=0 不欠费
      }

      float incomeMoney = remainMoney;
      StudentAccount studentAccount = null;
      if(useAccount == 1){
          studentAccount = this.studentAccountService.findByStudentId(studentCharge.getStudentId());
          if(studentAccount == null || studentAccount.getMoney()<=0){
              return;
          }
          if(studentAccount.getMoney() >= Math.abs(remainMoney)){
              studentAccount.setMoney(studentAccount.getMoney() - Math.abs(remainMoney));
              studentCharge.setRemainMoney(0f);
          }else{
              incomeMoney = studentAccount.getMoney();
              studentCharge.setRemainMoney(remainMoney + studentAccount.getMoney());
              studentAccount.setMoney(0f);
          }
      }else{
          studentCharge.setRemainMoney(0f);//TODO 要对一下   缴费的时候，如果不使用账户余额，则清除欠费，表示 系统外已经交完了欠费???
      }

      Income income = new Income(studentCharge.getSchoolId());
      income.setStudentChargeId(studentCharge.getId());
      income.setKlassType(studentCharge.getKlassType().getId());
      income.setKlassId(studentCharge.getKlassId());
      income.setExpenseId(studentCharge.getExpenseEntry().getId());
      income.setNames(studentCharge.getExpenseEntry().getName());
      income.setMoney(incomeMoney);
      Klass klass = klassService.get(studentCharge.getKlassId());
      if (klass != null) {
          income.setKlassName(klass.getName());
      } else {
          InterestKlass interestKlass = interestKlassService.get(studentCharge.getKlassId());
          income.setKlassName(interestKlass.getName());
      }
      income.setCreateAt(Instant.now());
      income.setTheYearMonth(studentCharge.getPeriodDate());
      income.setSrc(IncomeSrc.PAY_DEDUCT.getId());


      IncomeDetail incomeDetail = new IncomeDetail();
      incomeDetail.setMoney(incomeMoney); // 扣的费用
      incomeDetail.setStudentId(studentCharge.getStudentId());
      incomeDetail.setStudentName(studentCharge.getStudentName());
      incomeDetail.setExpenseId(studentCharge.getExpenseEntry().getId());
      incomeDetail.setTheYearMonth(studentCharge.getPeriodDate());
      incomeDetail.setRefundMoney(0f);
      incomeDetail.setCreateAt(Instant.now());

      if(studentAccount != null){
          this.studentAccountService.save(studentAccount);
      }
      incomeService.save(income);
      incomeDetailService.save(incomeDetail);
      this.studentChargePlanService.save(studentCharge);
  }

}
