package cn.k12soft.servo.module.rateCity.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.attendanceRate.domain.SchoolTeaRate;
import cn.k12soft.servo.module.attendanceRate.domain.StudentSchoolRate;
import cn.k12soft.servo.module.rateCity.domain.CityStudentRate;
import cn.k12soft.servo.module.rateCity.domain.CityTeacherRate;
import cn.k12soft.servo.module.rateCity.service.CityRateService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/rateCity/management")
public class CityRateManagement {

    private final CityRateService cityService;

    @Autowired
    public CityRateManagement(CityRateService cityService) {
        this.cityService = cityService;
    }

//    @ApiOperation("市教师统计出勤率")
//    @GetMapping("/cityTeacherRate")
//    public void cityTeacherRate(){
//        cityService.cityTeacherRate();
//    }

    @ApiOperation("查询市为单位教师出勤率")
    @GetMapping("/findCityTeacherRates")
    public Collection<CityTeacherRate> findCityTeacherRates(@Active Actor actor){
        return cityService.findCityTeacherRates(actor);
    }

    @ApiOperation("查询该市下每个学校的老师出勤率")
    @GetMapping("/findSchoolTeaRates")
    public Collection<SchoolTeaRate> findSchoolTeaRates(@Active Actor actor){
        return cityService.findSchoolTeaRates(actor);
    }

//    @ApiOperation("市学生统计出勤率")
//    @GetMapping("/cityStudentRate")
//    public void cityStudentRate(){
//        cityService.cityStudentRate();
//    }

    @ApiOperation("查询市学生出勤率")
    @GetMapping("/findCityStudentRate")
    public Collection<CityStudentRate> findCityStudentRate(@Active Actor actor){
        return cityService.findCityStudentRate(actor);
    }

//    @ApiOperation("查询该市下每个学校的学生出勤率")
//    @RequestMapping("/findSchoolStuRates")
//    public Collection<SchoolStuRate> findSchoolStuRates(@Active Actor actor){
//        return cityService.findSchoolStuRates(actor);
//    }

    @ApiOperation("查询市的下级地区学生出勤率")
    @GetMapping("/getCityStudentRate")
    public Collection<StudentSchoolRate> getCityStudentRate(@Active Actor actor,
                                                            @RequestParam @Valid String code){
        return cityService.getCityStudentRate(code, actor);
    }

    @ApiOperation("查询市的下级地区员工出勤率")
    @GetMapping("/getCityTeacherRate")
    public Collection<SchoolTeaRate> getCityTeacherRate(@Active Actor actor,
                                                            @RequestParam @Valid String code){
        return cityService.getCityTeacherRate(code, actor);
    }

}
