package cn.k12soft.servo.module.visitSchool.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.enumeration.IsVisit;
import cn.k12soft.servo.module.visitSchool.service.VisitSchoolService;
import cn.k12soft.servo.module.visitSchool.domain.VisitSchool;
import cn.k12soft.servo.module.visitSchool.domain.form.VisitSchoolForm;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/visit/management")
public class VisitSchoolManagement {


    public final VisitSchoolService visitSchoolService;

    @Autowired
    public VisitSchoolManagement(VisitSchoolService visitSchoolService) {
        this.visitSchoolService = visitSchoolService;
    }

    @ApiOperation("家长提交信息")
    @PostMapping("/web/addVisit")
    public VisitSchool create(@RequestBody @Valid VisitSchoolForm form){
        return visitSchoolService.create(form);
    }

    @ApiOperation("查询家长入园参观信息，没有被其他老师访问的")
    @GetMapping("/find")
    public Collection<VisitSchool> findNoVisit(@Active Actor actor,
                                               @RequestParam @Valid IsVisit isVisit,
                                               @RequestParam @Valid LocalDate specialDate){
        return visitSchoolService.findNoVisit(actor, isVisit, specialDate);
    }

}
