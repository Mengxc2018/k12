package cn.k12soft.servo.module.rateProvince.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.rateCity.domain.CityStudentRate;
import cn.k12soft.servo.module.rateCity.domain.CityTeacherRate;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceStudentsRate;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceTeachersRate;
import cn.k12soft.servo.module.rateProvince.service.ProvinceRateService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/rateProvince")
public class ProvinceRateManagement {
    private final ProvinceRateService provinceRateService;

    public ProvinceRateManagement(ProvinceRateService provinceRateService) {
        this.provinceRateService = provinceRateService;
    }

//    @ApiOperation("统计省教师出勤率")
//    @GetMapping("/provinceTeachersRate")
//    public void provinceTeachersRate(){
//        provinceRateService.provinceTeachersRate();
//    }

    @ApiOperation("查询省教师出勤率")
    @GetMapping("/findProvinceTeachersRate")
    public Collection<ProvinceTeachersRate> findProvinceTeachersRate(@Active Actor actor){
        return provinceRateService.findProvinceTeachersRate(actor);
    }

    @ApiOperation("查询省下面每个市的教师出勤率")
    @GetMapping("/findCityTeacherRate")
    public Collection<CityTeacherRate> findCityTeacherRate(@Active Actor actor){
        return provinceRateService.findCityTeacherRate(actor);
    }

//    @ApiOperation("统计省学生出勤率")
//    @GetMapping("/provinceStudentsRate")
//    public void provinceStudentsRate(){
//        provinceRateService.provinceStudentsRate();
//    }

    @ApiOperation("查询省学生出勤率")
    @GetMapping("/findProvinceStudentsRate")
    public Collection<ProvinceStudentsRate> findProvinceStudentsRate(@Active Actor actor){
        return provinceRateService.findProvinceStudentsRate(actor);
    }

    @ApiOperation("查询省下面每个市的学生出勤率")
    @GetMapping("/findCityStudentRate")
    public Collection<CityStudentRate> findCityStudentRate(@Active Actor actor){
        return provinceRateService.findCityStudentRate(actor);
    }

    @ApiOperation("查询省下级区域 学生的出勤率汇总")
    @GetMapping("/getCityStuRate")
    private Collection<CityStudentRate> getCityStuRate(@Active Actor actor,
                                                       @RequestParam @Valid String code){
        return provinceRateService.getCityStuRate(code);
    }

    @ApiOperation("查询省下级区域 员工的出勤率汇总")
    @GetMapping("/getCityTeaRate")
    private Collection<CityTeacherRate> getCityTeaRate(@Active Actor actor,
                                                       @RequestParam @Valid String code){
        return provinceRateService.getCityTeaRate(code);
    }

}
