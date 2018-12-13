package cn.k12soft.servo.module.attendPayRoll;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.AttendanceTeacher.repository.AttendanceTeacherRepository;
import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;
import cn.k12soft.servo.module.revenue.repository.TeacherSocialSecurityRepository;
import cn.k12soft.servo.repository.SchoolRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/attendPayRoll/management")
@RestController
public class AttendancePayRollManagement {

    private final SchoolRepository schoolRepository;
    private final AttendancePayRollService attendancePayRollService;
    private final AttendanceTeacherRepository attendanceTeacherRepository;
    private final TeacherSocialSecurityRepository teacherSocialSecurityRepository;

    @Autowired
    public AttendancePayRollManagement(SchoolRepository schoolRepository,
                                       AttendancePayRollService attendancePayRollService,
                                       AttendanceTeacherRepository attendanceTeacherRepository,
                                       TeacherSocialSecurityRepository teacherSocialSecurityRepository) {
        this.schoolRepository = schoolRepository;
        this.attendancePayRollService = attendancePayRollService;
        this.attendanceTeacherRepository = attendanceTeacherRepository;
        this.teacherSocialSecurityRepository = teacherSocialSecurityRepository;
    }

    @ApiOperation("计算考勤扣除薪资")
    @GetMapping
    public void payRoll(){
        attendancePayRollService.payRoll();
    }



}
