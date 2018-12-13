package cn.k12soft.servo.module.rateGroup.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.rateGroup.domain.GroupStudentRate;
import cn.k12soft.servo.module.rateGroup.domain.GroupTeacherRate;
import cn.k12soft.servo.module.rateGroup.service.GroupRateService;
import cn.k12soft.servo.module.rateRegions.domain.RegionsStudentRate;
import cn.k12soft.servo.module.rateRegions.domain.RegionsTeacherRate;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/groupRate/management")
public class GroupRateManagement {

    private final GroupRateService groupRateService;


    public GroupRateManagement(GroupRateService groupRateService) {
        this.groupRateService = groupRateService;
    }

//    @ApiOperation("统计集团教师的出勤率")
//    @GetMapping("/groupTeaRate")
//    public void groupTeaRate(){
//        groupRateService.groupTeaRate();
//    }

    @ApiOperation("查询集团教师的出勤率")
    @GetMapping("/findGroupTeaRate")
    public Collection<GroupTeacherRate> findGroupTeaRate(@Active Actor actor){
        return groupRateService.findGroupTeaRate(actor);
    }

    @ApiOperation("查询集团下面每个大区的教师出勤率")
    @GetMapping("/findRegionsTeacherRate")
    public Collection<RegionsTeacherRate> findRegionsTeacherRate(@Active Actor actor){
        return groupRateService.findRegionsTeacherRate(actor);
    }

//    @ApiOperation("统计集团教师的出勤率")
//    @GetMapping("/groupStuRate")
//    public void groupStuRate(){
//        groupRateService.groupStuRate();
//    }

    @ApiOperation("查询集团学生的出勤率")
    @GetMapping("/findGroupStuRate")
    public Collection<GroupStudentRate> findGroupStuRate(@Active Actor actor){
        return groupRateService.findGroupStuRate(actor);
    }

    @ApiOperation("查询集团下面每个大区的学生出勤率")
    @GetMapping("/findRegionsStudentRate")
    public Collection<RegionsStudentRate> findRegionsStudentRate(@Active Actor actor){
        return groupRateService.findRegionsStudentRate(actor);
    }

    @ApiOperation("查询当前集团下级每个区域的学生出勤率汇总")
    @GetMapping("/getRegionsStuRate")
    public Collection<RegionsStudentRate> getRegionsStuRate(@Active Actor actor,
                                                            @RequestParam @Valid String code){
        return groupRateService.getRegionsStuRate(actor, code);
    }

    @ApiOperation("查询当前集团下级每个区域的员工出勤率汇总")
    @GetMapping("/getRegionsTeaRate")
    public Collection<RegionsTeacherRate> getRegionsTeaRate(@Active Actor actor,
                                                            @RequestParam @Valid String code){
        return groupRateService.getRegionsTeaRate(actor, code);
    }

}
