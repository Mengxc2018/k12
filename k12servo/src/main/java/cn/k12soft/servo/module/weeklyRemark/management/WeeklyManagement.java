package cn.k12soft.servo.module.weeklyRemark.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.weeklyRemark.domain.Weekly;
import cn.k12soft.servo.module.weeklyRemark.domain.form.WeeklyForm;
import cn.k12soft.servo.module.weeklyRemark.service.WeeklyService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weeklyrRemark/management")
public class WeeklyManagement {
    private final WeeklyService weeklyService;

    @Autowired
    public WeeklyManagement(WeeklyService weeklyService) {
        this.weeklyService = weeklyService;
    }

    @ApiOperation("新建")
    @PostMapping("/created")
    public Weekly created(@Active Actor actor,
                          @RequestBody @Valid WeeklyForm form){
        return this.weeklyService.created(actor, form);
    }

    @ApiOperation("家长评语")
    @PutMapping("/parent")
    public Weekly Parent(@Active Actor actor,
                         @RequestParam @Valid Integer id,
                         @RequestParam @Valid String message){
        return this.weeklyService.parent(actor, id, message);
    }

    @ApiOperation("查询，")
    @GetMapping("/find")
    public List<Weekly> findBy(@Active Actor actor,
                               @RequestParam @Valid Integer studentId,
                               @RequestParam @Valid Integer klassId,
                               @RequestParam @Valid LocalDate date){
        return this.weeklyService.findBy(actor, studentId, klassId, date);
    }

    @ApiOperation("返回提交时间")
    @GetMapping("/findDate")
    public List<Map<String, Object>> findDate(@Active Actor actor,
                                              @RequestParam @Valid Integer studentId){
        return this.weeklyService.findDate(actor, studentId);
    }

    @ApiOperation("返回周点评家长未提交的数量")
    @GetMapping("/countUnRead")
    public Integer countUnRead(@Active Actor actor){
        return this.weeklyService.countUnRead(actor);
    }
}
