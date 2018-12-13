package cn.k12soft.servo.module.employeeFlow.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.employeeFlow.domain.dto.LeaveDTO;
import cn.k12soft.servo.module.employeeFlow.domain.dto.OfficialDTO;
import cn.k12soft.servo.module.employeeFlow.domain.form.Applyform;
import cn.k12soft.servo.module.employeeFlow.domain.form.LeaveForm;
import cn.k12soft.servo.module.employeeFlow.domain.form.OfficialForm;
import cn.k12soft.servo.module.employeeFlow.service.EmployeeFlowService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/employeeFlow")
public class EmpFlowManagement {

    private final EmployeeFlowService employeeFlowService;

    public EmpFlowManagement(EmployeeFlowService employeeFlowService) {
        this.employeeFlowService = employeeFlowService;
    }

    // 员工转正申请
    @ApiOperation("员工转正申请")
    @PostMapping("/applyOfficial")
    public void become(@Active Actor actor,
                       @RequestBody @Valid OfficialForm form){
        employeeFlowService.become(actor, form);
    }

    // 教师离职申请
    @ApiOperation("员工离职申请")
    @PostMapping("/applyLeave")
    public void leave(@Active Actor actor,
                      @RequestBody @Valid LeaveForm form){
        employeeFlowService.leave(actor, form);
    }

    @ApiOperation("查询员工转正申请，有actorId时查询自己的申请，没有时查询全部")
    @GetMapping("/findApplyOfficial")
    public Collection<OfficialDTO> findApplyOfficial(@Active Actor actor,
                                                     @RequestParam(required = false) @Valid Integer actorId,
                                                     @RequestParam @Valid LocalDate localDate){
        return employeeFlowService.findApplyOfficial(actor, actorId, localDate);
    }

    @ApiOperation("查询员工离职申请，有actorId时查询自己的申请，没有时查询全部")
    @GetMapping("/findApplyLeave")
    public Collection<LeaveDTO> findApplyLeave(@Active Actor actor,
                                               @RequestParam(required = false) @Valid Integer actorId,
                                               @RequestParam @Valid LocalDate localDate){
        return employeeFlowService.findApplyLeave(actor, actorId, localDate);
    }


    @ApiOperation("园长审批转正、离职")
    @PutMapping("/applyBecome")
    public void applyBecome(@Active Actor actor,
                            @RequestBody @Valid Applyform applyform){
        employeeFlowService.applyBecome(actor, applyform);
    }



}
