package cn.k12soft.servo.module.studentChargeRecord.management;


import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.charge.repository.StudentChargePlanRepository;
import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeKlassTotal;
import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeRecord;
import cn.k12soft.servo.module.studentChargeRecord.service.StudentChargeRecordService;
import cn.k12soft.servo.security.Active;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/studentChargeRecord/management")
public class StudentChargeRecordManagement {

    private final StudentChargeRecordService studentChargeRecordService;
    private final StudentChargePlanRepository studentChargePlanRepository;

    public StudentChargeRecordManagement(StudentChargeRecordService studentChargeRecordService, StudentChargePlanRepository studentChargePlanRepository) {
        this.studentChargeRecordService = studentChargeRecordService;
        this.studentChargePlanRepository = studentChargePlanRepository;
    }

    @ApiOperation("查询当前")
    @GetMapping("/findStudentChargeRecord")
    @Timed
    public List<StudentChargeRecord> findStudentChargeRecord(@Active Actor actor,
                                          @RequestParam @Valid Integer klassId,
                                          @RequestParam LocalDate formDate,
                                          @RequestParam LocalDate toDate){
        return studentChargeRecordService.findStudentChargeRecord(actor,klassId, formDate, toDate);
    }


    @ApiOperation("查询所有班级收入")
    @GetMapping("/findStudentChargeKlassTotal")
    @Timed
    public List<StudentChargeKlassTotal> findStudentChargeKlassTotal(@Active Actor actor,
                                                                     @RequestParam LocalDate formDate,
                                                                     @RequestParam LocalDate toDate){
        return this.studentChargeRecordService.findStudentChargeKlassTotal(actor, formDate, toDate);
    }

    @ApiOperation("---")
    @GetMapping("/countStudentChargeKlass")
    @Timed
    public void countStudentChargeKlass(){
        Instant first = Instant.ofEpochSecond(1546272000);
        Instant second = Instant.ofEpochSecond(1548950400);
        List<StudentCharge> lists = studentChargePlanRepository.findByCreateAtBetween(first,second);
        for (StudentCharge list : lists){
            studentChargeRecordService.countStudentChargeKlass(list);
        }
    }

}
