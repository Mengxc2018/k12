package cn.k12soft.servo.module.visit.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.visit.domain.VisitParents;
import cn.k12soft.servo.module.visit.domain.form.VisitParentsForm;
import cn.k12soft.servo.module.visit.service.VisitService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/visit/management")
public class VisitManagement {

    public final VisitService visitService;

    public VisitManagement(VisitService visitService) {
        this.visitService = visitService;
    }

    @ApiOperation("添加教师回访详情")
    @PostMapping("/addVisitParam")
    public VisitParents createdVisitParents(@Active Actor actor,
                                            @RequestParam @Valid Integer visitSchoolId,
                                            @RequestBody @Valid VisitParentsForm form){
        return visitService.createdVisitParents(actor, visitSchoolId, form);
    }

    @ApiOperation("查询教师回访记录")
    @GetMapping("/findVisitParents")
    public Collection<VisitParents> findVisitParents(@Active Actor actor,
                                                     @RequestParam @Valid Integer visitSchoolId){
        return visitService.findVisitParents(actor, visitSchoolId);
    }

}
