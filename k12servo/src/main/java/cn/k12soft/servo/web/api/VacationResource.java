package cn.k12soft.servo.web.api;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.AttendPeriodStat;
import cn.k12soft.servo.domain.Vacation;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.VacationService;
import cn.k12soft.servo.service.dto.VacationDTO;
import cn.k12soft.servo.web.form.VacationForm;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@RestController
@RequestMapping("/api/vacations")
public class VacationResource {

  private final VacationService vacationService;

  @Autowired
  public VacationResource(VacationService vacationService) {
    this.vacationService = vacationService;
  }

  @ApiOperation("请假")
  @PostMapping
  public VacationDTO create(@RequestBody @Valid VacationForm form) {
    if (form.getFromDate().compareTo(form.getToDate()) >= 0) {
      throw new IllegalArgumentException("From must be less than to");
    }
    return vacationService.create(form);
  }

  @ApiOperation("查询指定学生的请假记录")
  @GetMapping("/students")
  public Collection<VacationDTO> query(@RequestParam(name = "klassId") Integer klassId,
                                       @RequestParam(name = "studentId") Integer studentId,
                                       @RequestParam(name = "date", required = false) LocalDate date) {
    if (date == null) {
      date = LocalDate.now();
    }
    return vacationService.query(klassId, studentId, date);
  }

  @ApiOperation("请假查询")
  @GetMapping
  public Collection<VacationDTO> query(@RequestParam(name = "klassId") Integer klassId,
                                       @RequestParam(name = "date", required = false) LocalDate date) {
    if (date == null) {
      date = LocalDate.now();
    }
    return vacationService.query(klassId, date);
  }

  @ApiOperation("按月查询请假")
  @GetMapping("/getByMonth")
  public Collection<VacationDTO> queryByTime(@RequestParam(name = "klassId") Integer klassId,
                                             @RequestParam(name = "studentId") Integer studentId,
                                       @RequestParam(name = "date", required = false) LocalDate date) {
    if (date == null) {
      date = LocalDate.now();
    }
    LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
    LocalDate lastOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
    if(studentId>0){
      return vacationService.queryByStudentId(studentId, startOfMonth, lastOfMonth);
    }else if(klassId>0) {
      return vacationService.query(klassId, startOfMonth, lastOfMonth);
    }else{
      return vacationService.query(startOfMonth, lastOfMonth);
    }
  }

  @ApiOperation("家长查询自己孩子的请假记录")
  @GetMapping("/findChildrens")
  public Map<String, Object> findChildrens(@Active Actor actor,
                                           @RequestParam @Valid AttendPeriodStat.PeriodType type,
                                           @RequestParam @Valid LocalDate specialDate){
    return vacationService.findChildren(actor, type, specialDate);
  }
}
