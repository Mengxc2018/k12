package cn.k12soft.servo.module.expense.rest;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.Vacation;
import cn.k12soft.servo.domain.enumeration.StudentState;
import cn.k12soft.servo.module.account.domain.StudentAccount;
import cn.k12soft.servo.module.account.service.StudentAccountService;
import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.charge.service.ChargePlanService;
import cn.k12soft.servo.module.charge.service.StudentChargePlanService;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.PaybackByDays;
import cn.k12soft.servo.module.expense.domain.PaybackBySemester;
import cn.k12soft.servo.module.expense.domain.PaybackResult;
import cn.k12soft.servo.module.expense.service.ExpenseEntryService;
import cn.k12soft.servo.module.expense.service.PaybackService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.StudentService;
import cn.k12soft.servo.util.Times;
import cn.k12soft.servo.web.form.PaybackForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.List;

import static cn.k12soft.servo.domain.enumeration.Permission.CHARGE_PLAN_GET;
import static cn.k12soft.servo.domain.enumeration.Permission.CHARGE_PLAN_PUT;
import static cn.k12soft.servo.domain.enumeration.Permission.STUDENT_VIEW;

@RestController
@RequestMapping("/payback")
public class PaybackController {

  private final PaybackService service;
  private final StudentAccountService studentAccountService;
  private StudentService studentService;
  private ChargePlanService chargePlanService;
  private StudentChargePlanService studentChargePlanService;

  @Autowired
  public PaybackController(PaybackService service, StudentAccountService studentAccountService,
                           StudentService studentService,
                           ChargePlanService chargePlanService,
                           StudentChargePlanService studentChargePlanService) {
    this.service = service;
    this.studentAccountService = studentAccountService;
    this.studentService = studentService;
    this.chargePlanService = chargePlanService;
    this.studentChargePlanService = studentChargePlanService;
  }

  @ApiOperation("获取退费结算信息")
  @GetMapping
  @Timed
  @Transactional
  @PermissionRequired(CHARGE_PLAN_GET)
  public PaybackResult getPaybackInfo(@Active Actor operator,
                                      @RequestParam(value = "studentId", required = true) Integer studentId,
                                      @RequestParam(value = "fromDate", required = true) LocalDate fromDate,
                                      @RequestParam(value = "toDate", required = true) LocalDate toDate) {
    return this.service.getPaybackResult(this.studentService, this.studentChargePlanService, studentId,fromDate,toDate);
  }




  @ApiOperation("退费结算")
  @PutMapping
  @Timed
  @Transactional
  @PermissionRequired(CHARGE_PLAN_PUT)
  public void payback(@Active Actor operator, @RequestBody PaybackForm form){
    PaybackResult paybackResult = this.service.getPaybackResult(this.studentService, this.studentChargePlanService, form.getStudentId(),form.getFromDate(),form.getToDate());
    //tuifei
    Student student = this.studentService.get(form.getStudentId());
    student.setState(StudentState.LEAVE_SCHOOL);
    this.studentService.save(student);
  }

}
