package cn.k12soft.servo.module.scheduling.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.scheduling.domain.Scheduling;
import cn.k12soft.servo.module.scheduling.domain.form.SchedulingForm;
import cn.k12soft.servo.module.scheduling.domain.dto.SchedulingDTO;
import cn.k12soft.servo.module.scheduling.service.SchedulingService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequestMapping("/scheduling/management")
public class SchedulingManagement {

    private final SchedulingService schedulingService;

    @Autowired
    public SchedulingManagement(SchedulingService schedulingSerivce){
        this.schedulingService = schedulingSerivce;
    }

    @ApiOperation("查询排班时间")
    @GetMapping
    @Timed
    public Collection<SchedulingDTO> query(@Active Actor actor){
        Collection<SchedulingDTO> list = schedulingService.getAllBySchoolId(actor);
        return list;
    }


    /**
     * 传时间时，时间格式用 “ 08:00 ” 方式传
     * @param form
     * @return
     */
    @ApiOperation(value = "创建排班时间 时间格式：（HH:mm）,如果是早上晚上两次打卡，type填2，如果早中晚四次打卡，type填1")
    @PostMapping
    public SchedulingDTO create(@Active Actor actor,
                                @RequestBody @Valid SchedulingForm form){
        return schedulingService.create(actor, form);
    }

    @ApiOperation(value = "删除排班")
    @DeleteMapping("/{id:\\d+}")
    public void delete(@Active Actor actor,
                       @PathVariable("id") @Valid Long id){
        schedulingService.deleteOne(id);
    }


    @ApiOperation(value = "修改排班时间")
    @PutMapping(params = "schedulingId")
    public SchedulingDTO update( @Active Actor actor,
                                 @RequestParam Integer schedulingId,
                                 @RequestBody @Valid SchedulingForm form){
        return schedulingService.update(actor, form, schedulingId);
    }

    @ApiOperation("按照打卡次数查询，早午晚四次传1，早晚两次传2")
    @GetMapping("/queryByType")
    public Collection<SchedulingDTO> queryByType(@Active Actor actor,
                                                 @RequestParam("schedulingType") String schedulingType){
        return schedulingService.queryByType(actor, schedulingType);

    }

}
