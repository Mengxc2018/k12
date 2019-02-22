package cn.k12soft.servo.module.healthCheck.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.AttendPeriodStat;
import cn.k12soft.servo.domain.enumeration.Physical;
import cn.k12soft.servo.module.healthCheck.domain.HealthCondition;
import cn.k12soft.servo.module.healthCheck.domain.form.HealthMorningForm;
import cn.k12soft.servo.module.healthCheck.domain.form.HealthNightForm;
import cn.k12soft.servo.module.healthCheck.domain.form.HealthNoonForm;
import cn.k12soft.servo.module.healthCheck.service.HealthCheckService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.dto.StudentWithGuardiansDTO;
import cn.k12soft.servo.service.mapper.StudentWithGuardiansMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/healthCheck/management")
public class HealthCheckManagement {

    private final HealthCheckService healthCheckService;
    private final StudentWithGuardiansMapper studentMapper;

    public HealthCheckManagement(HealthCheckService healthCheckService, StudentWithGuardiansMapper studentMapper) {
        this.healthCheckService = healthCheckService;
        this.studentMapper = studentMapper;
    }

    @ApiOperation("宝宝晨检")
    @PostMapping("/addMorning")
    public void createMorning(@Active Actor actor,
                              @RequestParam @Valid Integer klassId,
                              @RequestBody @Valid List<HealthMorningForm> forms){
        healthCheckService.createMorning(actor, forms, klassId);
    }

    @ApiOperation("宝宝午检")
    @PostMapping("/addNoon")
    public void createNoon(@Active Actor actor,
                           @RequestParam @Valid Integer klassId,
                           @RequestBody @Valid List<HealthNoonForm> forms){
        healthCheckService.createNoon(actor, forms, klassId);
    }

    @ApiOperation("宝宝晚检")
    @PostMapping("/addNight")
    public void createNight(@Active Actor actor,
                            @RequestParam @Valid Integer klassId,
                            @RequestBody @Valid List<HealthNightForm> forms){
        healthCheckService.createNight(actor, forms, klassId);
    }

    @ApiOperation("确认发布, 上传多条信息时，将id用逗号隔开")
    @PutMapping("/issue")
    public void issue(@Active Actor actor,
                      @RequestParam @Valid String ids,
                      @RequestParam @Valid Physical.TYPE type,
                      @RequestParam @Valid Integer klassId){
        healthCheckService.issue(actor, ids, klassId, type);
    }

    // 查询早午晚未发布的记录，如果type为空，则查询所有
    @ApiOperation("查询早午晚未发布的记录，如果type为空，则查询所有")
    @GetMapping("/findUnIssue")
    public Map<String, Object> findUnIssue(@Active Actor actor,
                                           @RequestParam(required = false) @Valid Physical.TYPE type,
                                           @RequestParam(required = false) @Valid LocalDate localDate,
                                           @RequestParam @Valid boolean issue,
                                           @RequestParam @Valid Integer klassId){
        return healthCheckService.findUnIssue(actor, type, localDate, issue, klassId);
    }

    // 查询当天、周、月宝宝记录，按照创建时间、学生ID倒序
    @ApiOperation("查询当天、周、月记录，如果periodType为空，则查询某一天所有学生检查记录，反之优先按照周、月查询。其他条件有则按照条件查询")
    @GetMapping("/findPeriod")
    public Map<String, Object> get(@Active Actor actor,
                                       @RequestParam @Valid Physical.TYPE type,
                                       @RequestParam(required = false) @Valid AttendPeriodStat.PeriodType periodType,
                                       @RequestParam(required = false) @Valid LocalDate date,
                                       @RequestParam(required = false) @Valid Integer studentId,
                                       @RequestParam @Valid Integer klassId){
        return healthCheckService.getAllByCondition(actor, type, periodType, date, studentId, klassId);
    }

    @ApiOperation("查询未体检宝宝接口，过滤已体检的宝宝")
    @GetMapping("/findUnChecked")
    public Collection<StudentWithGuardiansDTO> findUnChecked(@Active Actor actor,
                                                             @RequestParam @Valid Integer klassId,
                                                             @RequestParam @Valid Physical.TYPE type){
        return studentMapper.toDTOs(healthCheckService.findUnChecked(actor, klassId, type));
    }

    @ApiOperation("删除，可批量删除，中间用英文逗号隔开")
    @DeleteMapping
    public void deleteChecked(@Active Actor actor,
                              @RequestParam @Valid String ids){
        healthCheckService.deleteChecked(ids);
    }

    @ApiOperation("修改早上体检状况")
    @PutMapping("/updateMorning")
    public void updateMorning(@Active Actor actor,
                              @RequestBody @Valid HealthMorningForm morning,
                              @RequestParam @Valid Integer id){
        healthCheckService.updateMorning(id, morning);
    }

    @ApiOperation("修改中午体检状况")
    @PutMapping("/updateNoon")
    public void updateNoon(@Active Actor actor,
                           @RequestBody @Valid HealthNoonForm noon,
                           @RequestParam @Valid Integer id){
        healthCheckService.updateNoon(id, noon);
    }

    @ApiOperation("修改下午体检状况")
    @PutMapping("/updateNightn")
    public void updateNightn(@Active Actor actor,
                           @RequestBody @Valid HealthNightForm night,
                             @RequestParam @Valid Integer id){
        healthCheckService.updateNightn(id, night);
    }

    @ApiOperation("删除一个，也可批量删除，批量删除时用英文逗号隔开")
    @DeleteMapping("/delete")
    public void delete(@Active Actor actor,
                              @RequestParam @Valid String ids){
        healthCheckService.deleteById(ids);
    }

    @ApiOperation("可发布一条，也可多条，发布指定体检信息时将id用英文逗号隔开")
    @PutMapping("/send")
    public void updateOne(@Active Actor actor,
                                 @RequestParam @Valid String ids){
        healthCheckService.updateOne(actor, ids);
    }

    @ApiOperation("查询已发布，查询条件为日期、班级id, type不为空时按照早午晚查询，为空时查询所有")
    @GetMapping("/findByDataAndKlass")
    public Map<String, Object> findByDataAndKlass(@Active Actor actor,
                                         @RequestParam(required = false) @Valid Physical type,
                                         @RequestParam @Valid Integer klassId,
                                         @RequestParam @Valid LocalDate date){
        return healthCheckService.findByDateAndKlass(actor, type, klassId, date);
    }

    @ApiOperation("教师查询所有异常儿童信息")
    @GetMapping("/findSymptom")
    public List<HealthCondition> findByKlass(@Active Actor actor,
                                             @RequestParam @Valid Integer klassId,
                                             @RequestParam @Valid LocalDate localDate){
        return this.healthCheckService.findHealthConditionByKlass(actor, klassId, localDate);
    }
}
