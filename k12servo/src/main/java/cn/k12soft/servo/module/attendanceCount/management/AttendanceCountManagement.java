package cn.k12soft.servo.module.attendanceCount.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.attendanceCount.service.AttendanceCountService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询员工一个月的缺勤时间
 */
@RestController
@RequestMapping("/attendanceCount/managenent")
public class AttendanceCountManagement {

    private final AttendanceCountService service;

    @Autowired
    public AttendanceCountManagement(AttendanceCountService service) {
        this.service = service;
    }

    @ApiOperation("按月查询不满勤时间,只有时间,单位“秒”")
    @GetMapping
    public Long times(@Active Actor actor,
                      @RequestParam(name = "month") Integer month,
                      @RequestParam(name = "year") Integer year){
        return service.isAttendacne(actor, year, month);
    }

}
