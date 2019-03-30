package cn.k12soft.servo.module.AttendanceTeacher.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendPeriodTeac.PeriodTeacType;
import cn.k12soft.servo.module.AttendanceTeacher.domain.form.RetorForm;
import cn.k12soft.servo.module.AttendanceTeacher.domain.form.RetroAttendanceTeacherForm;
import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendTeacDTO;
import cn.k12soft.servo.module.AttendanceTeacher.service.AttendanceTeacherSerivce;
import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendanceTeacherDTO;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.TRUE_FALSE;
import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 教师打卡信息入口
 */
@RestController
@RequestMapping("/management/attendancesTeachers")
public class AttendanceTeacherManagement {

    private final AttendanceTeacherSerivce service;


    @Autowired
    public AttendanceTeacherManagement(AttendanceTeacherSerivce service) {
        this.service = service;
    }

    /**
     * 一天打四次卡时：
     * 如果多次打卡，则只更新上午或者下午的签退的时间
     *
     * 如果上午没有签到，则不会有签退，必须签到后才能签退
     * 如果上午没有签到，则视为上午缺勤
     *
     * 如果下午没有签到，则不会有签退，必须签到后才能签退
     * 如果下午没有签到，则视为下午缺勤
     *
     * 下午签退后，计算当天是否满勤
     *
     * 一天打两次卡时：
     * 如果多次打卡，则只更新签退时间
     * 如果上午或者下午没有打卡，又没有补签，则视为缺勤
     * 签退后，计算当天是否满勤
     * @param actor
     * @return
     */
    @ApiOperation("教师打卡")
    @PostMapping
    public AttendanceTeacherDTO create(@Active Actor actor){
        return service.create(actor);
    }

//    @ApiOperation("添加教师打卡测试数据")
//    @GetMapping("/teacher")
//    public List<AttendanceTeacher> create1(@Active Actor actor,@RequestParam @Valid Integer actorId,
//                                           @RequestParam @Valid LocalDate localDate){
//        return service.createTest(actor ,actorId, localDate);
//    }
//
//    @ApiOperation("添加学生打卡测试数据，学生id推荐539，为测试学校中的测试学生")
//    @GetMapping("/student")
//    public List<AttendanceTeacher> create2(@Active Actor actor,
//                                           @RequestParam @Valid Integer studentId,
//                                           @RequestParam @Valid LocalDate localDate){
//        return service.createTest(actor ,studentId, localDate);
//    }

    /**
     * 员工申请信息接口
     * @param form
     */
    @ApiOperation("员工申请信息接口")
    @PutMapping
    public void applyFor(@Active Actor actor,
                         @RequestBody @Valid RetroAttendanceTeacherForm form) {
      service.applyFor(actor, form);
    }

    /**
     * 员工补签申请，走流程
     * 需要一个坐标，指明是要补签哪一个时间
     * @param actor
     */
    @ApiOperation("员工打卡补签")
    @PutMapping("/retor")
    public VacationTeacher retor(@Active Actor actor,
                                 @RequestBody @Valid RetorForm form ) {
        return service.retor(actor, form);
    }

    /**
     * 教师普通查询某一天
     * 按照教师 id 查询某一天的打卡信息，有补签则带出
     * @param actor
     * @param date
     * @return
     */
    @ApiOperation("有actorId时查询某个员工，没有actorId时，查询所有员工，按天查询")
    @GetMapping(params = {"date"})
    public AttendTeacDTO query(@Active Actor actor,
                               @RequestParam(value = "actorId", required = false) Integer actorId,
                               @RequestParam("date") LocalDate date){
        return service.query(actor.getSchoolId(), date, actor, actorId);
    }

    @ApiOperation("查询单个员工某一天打卡信息")
    @GetMapping("/queryOne")
    public AttendTeacDTO queryOne(@Active Actor actor,
                                  @RequestParam @Valid LocalDate spdate){
        return service.findByActorIdAndDate(actor, spdate);
    }

    /**
     * 教师周期查询全部
     * @param periodType
     * @param specialDate
     * @return
     */
    @ApiOperation("教师周期查询")
    @GetMapping(value="/period", params = {"schoolId", "periodType", "specialDate"})
    public Collection<AttendTeacDTO> queryPeriod(@Active Actor actor,
                                                 @RequestParam("periodType") PeriodTeacType periodType,
                                                 @RequestParam("specialDate") LocalDate specialDate){
        return service.queryPeriod(actor, periodType, specialDate);
    }

    /**
     * 没打卡即为缺勤
     * @return
     */
    @ApiOperation("统计周期考勤信息，包括出勤、迟到、早退、请假、缺勤次数")
    @GetMapping("/countAttendance")
    public Map<String, Object> countAttendance(@Active Actor actor,
                                        @RequestParam("specialDate") @Valid LocalDate specialDate){
        return service.countAttendance(actor, specialDate);
    }

    /**
     * 查询所有满勤或者不满勤打卡（包括补签后）,true为满勤，false为不满勤
     * @param actor
     * @param actorId
     * @param trueOrFalse
     * @param date
     * @return
     */
    @ApiOperation("有actorId时查询某个员工，没有actorId时，查询所有员工，某个月满勤或者不满勤打卡信息（包括补签）")
    @GetMapping(value = "/queryFull")
    public Collection<AttendTeacDTO> queryFull(@Active Actor actor,
                                               @RequestParam(value = "actorId", required = false) Integer actorId,
                                               @RequestParam("trueOrFalse") TRUE_FALSE trueOrFalse,
                                               @RequestParam @Valid LocalDate date){
        boolean isTrue = true;
        if (trueOrFalse.equals("TRUE")){
            isTrue = true;
        }else{
            isTrue = false;
        }

        return service.queryFull(actor.getSchoolId(), actorId, actor, isTrue, date);
    }

    @ApiOperation("查询异常接口，主要有早退、迟到、旷工，按照自然月查询")
    @GetMapping("/queryByAll")
    public Map<String, Object> queryByAll(@Active Actor actor,
                                          @RequestParam @Valid LocalDate date){
        return service.queryByAll(actor, date);
    }

    @ApiOperation("获取学校教师当月出勤率，返回百分比")
    @GetMapping("/findTeacherRate")
    public List<Map<String, Object>> findTeacherRate(@Active Actor actor,
                                                     @RequestParam @Valid LocalDate localDate){
        return service.findTeacherRate(actor, localDate);
    }

    @ApiOperation("添加虚拟数据")
    @GetMapping("/addDate")
    public void addDate(@RequestParam @Valid Integer num,
                        @RequestParam @Valid LocalDate localDate){
        this.service.addDate(num, localDate);
    }

}
