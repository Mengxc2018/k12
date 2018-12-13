package cn.k12soft.servo.module.attendanceRate.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.attendanceRate.domain.SchoolTeaRate;
import cn.k12soft.servo.module.attendanceRate.domain.dto.StudentKlassRateDTO;
import cn.k12soft.servo.module.attendanceRate.domain.dto.StudentSchoolRateDTO;
import cn.k12soft.servo.module.attendanceRate.domain.dto.TeacherDTO;
import cn.k12soft.servo.module.attendanceRate.service.AttendanceRateService;
import cn.k12soft.servo.module.attendanceRate.service.mapper.TeacherSchoolRateMapper;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(value = "attendanceRate/management")
public class AttendanceRateManagement {
    private final TeacherSchoolRateMapper attendanceRateMapper;
    private final AttendanceRateService attendanceRateService;

    public AttendanceRateManagement(TeacherSchoolRateMapper attendanceRateMapper, AttendanceRateService attendanceRateService) {
        this.attendanceRateMapper = attendanceRateMapper;
        this.attendanceRateService = attendanceRateService;
    }

    /**
     * 每天统计一次教师出勤率，加入脚本定时任务 RateTask
     */
//    @GetMapping("/everyTeacherRate")
//    @ApiOperation("统计所有员工出勤率")
//    public void TeacherRateEveryDay()
//    {
//        attendanceRateService.teacherRateEveryDay();
//    }

    @GetMapping("/findBySchool")
    @ApiOperation("按照学校查询员工出勤率, 学校为空时，查询全部员工的出勤率")
    public Collection<TeacherDTO> queryBySchool(@Active Actor actor,
                                                @RequestParam(required = false) @Valid Integer schoolId,
                                                @RequestParam @Valid Integer year){
        return attendanceRateService.queryBySchool(schoolId, year);
    }

//    @GetMapping("/everyStudentRate")
//    @ApiOperation("统计所有学生出勤率")
//    public void allStudent(@Active Actor actor){
//        attendanceRateService.allStudentRate();
//    }

    /**
     * 每天统计一次以班为单位学生出勤率，加入脚本定时任务 RateTask
     */
//    @GetMapping("/everyStudentKlassRate")
//    @ApiOperation("统计班级学生总的出勤率")
//    public void allStudentKlass(){
//        attendanceRateService.allStudentKlassRate();
//    }

    @GetMapping("/findStudentKlassRate")
    @ApiOperation("查询学校中班级学生总的出勤率， schoolId为空时，查询全部，反之，按照学校查询")
    public Collection<StudentKlassRateDTO> findStudent(@Active Actor actor,
                                                       @RequestParam(required = false) @Valid Integer schoolId,
                                                       @RequestParam @Valid Integer year){
        return attendanceRateService.findAllStudentKlassRate(schoolId, year);
    }

    /**
     * 每天统计一次以学校为单位学生出勤率，加入脚本定时任务 RateTask
     */
//    @GetMapping("/everyStudentSchoolKlassRate")
//    @ApiOperation("统计学校中班级学生总的出勤率")
//    public void allStudentSchoolRate(){
//        attendanceRateService.allStudentSchoolKlassRate();
//    }

    @ApiOperation("查询学校出勤率")
    @GetMapping("/findSchool")
    public Collection<StudentSchoolRateDTO> findBySchool(@Active Actor actor,
                                                         @RequestParam @Valid Integer schoolId,
                                                         @RequestParam @Valid Integer year){
        return attendanceRateService.findBySchool(actor, schoolId, year);
    }

//    @ApiOperation("以学校为单位教师的出勤率汇总")
//    @GetMapping("/schoolTeaRate")
//    public void schoolTeaRate(){
//        attendanceRateService.schoolTeaRate();
//    }

    @ApiOperation("查询以学校为单位教师的出勤率")
    @GetMapping("/findSchoolTeaRate")
    public Collection<SchoolTeaRate> findschoolTeaRates(@Active Actor actor){
        return attendanceRateService.findByCity(actor);
    }

}
