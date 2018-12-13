package cn.k12soft.servo.module.applyFor.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.applyFor.domain.dto.ApplyForDTO;
import cn.k12soft.servo.module.applyFor.domain.form.ApplyForForm;
import cn.k12soft.servo.module.applyFor.service.ApplyForService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@RequestMapping("/applyForVacation/management")
@RestController
public class ApplyForManagement {

    private final ApplyForService applyForService;

    @Autowired
    public ApplyForManagement(ApplyForService applyForService) {
        this.applyForService = applyForService;
    }

    /**
     * 查询该角色下待审批的任务
     * @param actor
     * @return
     */
    @ApiOperation("查询该角色待审批的任务")
    @GetMapping("/query")
    public Collection<ApplyForDTO> queryByVacation(@Active Actor actor,
                                          @RequestParam @Valid Integer processType,
                                          @RequestParam @Valid LocalDate date){
        return applyForService.query(actor.getId(), processType, date);
    }

    /**
     * 查询某个月该角色下已审批的任务
     * @param actor
     * @return
     */
    @ApiOperation("按月查询该角色已审批的任务")
    @GetMapping("/queryByMonthAndActorId")
    public Collection<ApplyForDTO> queryByVacationAndDay(@Active Actor actor,
                                                         @RequestParam @Valid Integer processType,
                                                         @RequestParam @Valid LocalDate date){
        return applyForService.queryByDate(actor.getId(), processType, date);
    }

    @ApiOperation("查询该角色已审批的任务")
    @GetMapping("/queryByPass")
    public Collection<ApplyForDTO> queryByVacationPass(@Active Actor actor,
                                                       @RequestParam @Valid Integer processType){
        return applyForService.queryByPass(actor.getId(), processType);
    }

    @ApiOperation("领导审批")
    @PutMapping("/updateByVacation")
    public ApplyForDTO updateByVacation(@Active Actor actor,
                                        @RequestBody ApplyForForm form ){
        return applyForService.updateByVacation(form, actor);
    }

    @ApiOperation("流程明细查询")
    @GetMapping("/queryDetail")
    public Collection<ApplyForDTO> queryDetail(@Active Actor actor,
                                               @RequestParam @Valid Integer massageId,
                                               @RequestParam @Valid String processInstanceId){
        return applyForService.queryDetail(actor, massageId, processInstanceId);
    }

    /**
     * id为申请记录的id
     * @param actor
     * @param id
     * @return
     */
    @ApiOperation("查询申请记录明细")
    @GetMapping("/queryApplyDetail")
    public Collection<ApplyForDTO> queryApplyDetail(@Active Actor actor, @RequestParam("id") @Valid Integer id){
        return applyForService.queryApplyDetail(actor, id);
    }

}

