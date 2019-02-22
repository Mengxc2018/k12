package cn.k12soft.servo.web.api;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.AttendPeriodStat;
import cn.k12soft.servo.domain.AttendPeriodStat.PeriodType;
import cn.k12soft.servo.domain.DailyAttendStat;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.AttendanceType;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.AttendanceService;
import cn.k12soft.servo.service.dto.AttendanceDTO;
import cn.k12soft.servo.web.form.AttendanceForm;
import cn.k12soft.servo.web.form.RetroAttendanceForm;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@RestController
@RequestMapping("/api/attendances")
public class AttendanceResource {

  private final AttendanceService service;

  @Autowired
  public AttendanceResource(AttendanceService service) {
    this.service = service;
  }

  @ApiOperation("打卡")
  @PostMapping
  public AttendanceDTO create(@RequestBody @Valid AttendanceForm form) {
    return service.create(form, null);
  }

  @ApiOperation("批量：打卡")
  @PostMapping("/createMany")
  public void createMany(@RequestBody @Valid List<AttendanceForm> forms,
                         @RequestParam @Valid AttendanceType type) {
    service.createMany(forms, type);
  }

  @ApiOperation("补签")
  @PutMapping
  public AttendanceDTO retro(@RequestBody @Valid RetroAttendanceForm form) {
    return service.retro(form);
  }

  @ApiOperation("批量：补签")
  @PostMapping("/retroMany")
  public List<AttendanceDTO> retroMany(@RequestBody @Valid List<RetroAttendanceForm> forms) {
    return service.retroMany(forms);
  }

  @ApiOperation("查询")
  @GetMapping(params = {"klassId", "date"})
  public List<DailyAttendStat> query(@RequestParam("klassId") Integer klassId,
                                     @RequestParam("date") LocalDate date,
                                     @RequestParam(value = "studentId", required = false) Integer studentId) {
    return service.query(klassId, date, studentId);
  }

  @ApiOperation("周期查询")
  @GetMapping(value = "/period", params = {"klassId", "type", "specialDate"})
  public AttendPeriodStat queryPeriod(@RequestParam("klassId") Integer klassId,
                                      @RequestParam("type") PeriodType type,
                                      @RequestParam("specialDate") LocalDate specialDate,
                                      @RequestParam(value = "studentId", required = false) Integer studentId) {
    return service.queryPeriod(klassId, type, specialDate, studentId);
  }

  @ApiOperation("家长查询自己孩子的打卡记录")
  @GetMapping("/findChildrenAttendance")
  public Map<String, Object> findChildrenAttendance(@Active Actor actor,
                                                    @RequestParam("type") PeriodType type,
                                                    @RequestParam("specialDate") LocalDate specialDate){
    return service.findChildrenAttendance(actor, type, specialDate);
  }

  @ApiOperation("查询未打卡未请假的儿童")
  @GetMapping("/findUnAttend")
  public List<Student> findUnAttend(@Active Actor actor,
                                    @RequestParam @Valid Integer klassId,
                                    @RequestParam @Valid LocalDate localDate){
    return this.service.findUnAttend(actor, klassId, localDate);
  }

  @ApiOperation("将所有的打卡添加name值")
  @GetMapping("/createdName")
  public void createdName(){
    service.createdName();
  }

  @ApiOperation("获取班级学生出勤率，周期为月，返回百分比")
  @GetMapping("/findKlassRateOfStu")
  public List<Map<String, Object>> findKlassRateOfStu(@Active Actor actor,
                                                @RequestParam @Valid LocalDate localDate){
    return this.service.findKlassRateOfStu(actor, localDate);
  }

}
