package cn.k12soft.servo.module.rateRegions.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceStudentsRate;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceTeachersRate;
import cn.k12soft.servo.module.rateRegions.domain.RegionsStudentRate;
import cn.k12soft.servo.module.rateRegions.domain.RegionsTeacherRate;
import cn.k12soft.servo.module.rateRegions.service.RegionsRateService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/regionsRate/management")
public class RegionsRateManagement {

    private final RegionsRateService regionsRateService;

    public RegionsRateManagement(RegionsRateService regionsRateService) {
        this.regionsRateService = regionsRateService;
    }

//    @ApiOperation("统计大区教师出勤率")
//    @GetMapping("/regionsTeacherRate")
//    public void regionsTeacherRate(){
//        regionsRateService.regionsTeacherRate();
//    }

    @ApiOperation("查询大区教师出勤率")
    @GetMapping("/findRegionsTeacherRate")
    public Collection<RegionsTeacherRate> findRegionsTeacherRate(@Active Actor actor){
        return regionsRateService.findRegionsTeacherRate(actor);
    }

    @ApiOperation("查询大区下面每个省的教师出勤率")
    @GetMapping("/findProvinceTeachersRate")
    public Collection<ProvinceTeachersRate> findProvinceTeachersRate(@Active Actor actor){
        return regionsRateService.findProvinceTeachersRate(actor);
    }


//    @ApiOperation("统计大区学生出勤率")
//    @GetMapping("/regionsStudentRate")
//    public void regionsStudentRate(){
//        regionsRateService.regionsStudentRate();
//    }

    @ApiOperation("查询大区学生出勤率")
    @GetMapping("/findRegionsStudentRate")
    public Collection<RegionsStudentRate> findRegionsStudentRate(@Active Actor actor){
        return regionsRateService.findRegionsStudentRate(actor);
    }

    @ApiOperation("查询大区下面每个省的学生出勤率")
    @GetMapping("/findProvinceStudentsRate")
    public Collection<ProvinceStudentsRate> findProvinceStudentsRate(@Active Actor actor){
        return regionsRateService.findProvinceStudentsRate(actor);
    }


    @ApiOperation("查询大区每个下级的学生出勤率汇总")
    @GetMapping("/getProvinceStuRate")
    public Collection<ProvinceStudentsRate> getProvinceStuRate(@Active Actor actor,
                                                               @RequestParam @Valid String code){
        return regionsRateService.getProvinceStuRate(code);
    }

    @ApiOperation("查询大区每个下级的学生出勤率汇总")
    @GetMapping("/getProvinceTeaRate")
    public Collection<ProvinceTeachersRate> getProvinceTeaRate(@Active Actor actor,
                                                               @RequestParam @Valid String code){
        return regionsRateService.getProvinceTeaRate(code);
    }

}
