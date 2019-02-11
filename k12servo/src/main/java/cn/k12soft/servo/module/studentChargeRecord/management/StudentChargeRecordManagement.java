package cn.k12soft.servo.module.studentChargeRecord.management;


import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeRecord;
import cn.k12soft.servo.module.studentChargeRecord.domain.dto.StudentChargeKlassSummarySheetDTO;
import cn.k12soft.servo.module.studentChargeRecord.domain.dto.StudentChargeKlassTotalDTO;
import cn.k12soft.servo.module.studentChargeRecord.service.StudentChargeRecordService;
import cn.k12soft.servo.module.studentChargeRecord.service.mapper.StudentChargeKlassSummarySheetMapper;
import cn.k12soft.servo.module.studentChargeRecord.service.mapper.StudentChargeKlassTotalMapper;
import cn.k12soft.servo.security.Active;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/studentChargeRecord/management")
public class StudentChargeRecordManagement {

    private final StudentChargeRecordService studentChargeRecordService;
    private final StudentChargeKlassTotalMapper studentChargeKlassTotalMapper;
    private final StudentChargeKlassSummarySheetMapper studentChargeKlassSummarySheetMapper;

    @Autowired
    public StudentChargeRecordManagement(StudentChargeRecordService studentChargeRecordService, StudentChargeKlassTotalMapper studentChargeKlassTotalMapper, StudentChargeKlassSummarySheetMapper studentChargeKlassSummarySheetMapper) {
        this.studentChargeRecordService = studentChargeRecordService;
        this.studentChargeKlassTotalMapper = studentChargeKlassTotalMapper;
        this.studentChargeKlassSummarySheetMapper = studentChargeKlassSummarySheetMapper;
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
    public Collection<StudentChargeKlassTotalDTO> findStudentChargeKlassTotal(@Active Actor actor,
                                                                              @RequestParam LocalDate formDate,
                                                                              @RequestParam LocalDate toDate){
        return this.studentChargeKlassTotalMapper.toDTOs(studentChargeRecordService.findStudentChargeKlassTotal(actor, formDate, toDate));
    }

    @ApiOperation("园所收费汇总表")
    @GetMapping("/findStudentChargeKlassSummarySheet")
    @Timed
    public Collection<StudentChargeKlassSummarySheetDTO> findStudentChargeKlassSummarySheet(@Active Actor actor,
                                                                        @RequestParam LocalDate formDate,
                                                                        @RequestParam LocalDate toDate){
        return this.studentChargeRecordService.findAllBySchoolIdAndCreateAtBetween(actor, formDate, toDate);
    }




}
