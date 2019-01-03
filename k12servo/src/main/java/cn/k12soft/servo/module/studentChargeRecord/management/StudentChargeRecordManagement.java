package cn.k12soft.servo.module.studentChargeRecord.management;


import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeRecord;
import cn.k12soft.servo.module.studentChargeRecord.service.StudentChargeRecordService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/studentChargeRecord/management")
public class StudentChargeRecordManagement {

    private final StudentChargeRecordService studentChargeRecordService;

    public StudentChargeRecordManagement(StudentChargeRecordService studentChargeRecordService) {
        this.studentChargeRecordService = studentChargeRecordService;
    }

    @ApiOperation("查询当前")
    @GetMapping("/find")
    public List<StudentChargeRecord> find(@Active Actor actor,
                                          @RequestParam LocalDate formDate,
                                          @RequestParam LocalDate toDate){
        return studentChargeRecordService.findBy(actor, formDate, toDate);
    }
}
