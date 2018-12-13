package cn.k12soft.servo.module.empFlowRate.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.empFlowRate.domain.*;
import cn.k12soft.servo.module.empFlowRate.service.EmpFlowRateService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

/**
 * 人员流动率
 */
@RequestMapping("/empFlowRate/management")
@RestController
public class EmpFlowRateManagement {

    private final EmpFlowRateService empFlowRateService;

    public EmpFlowRateManagement(EmpFlowRateService empFlowRateService) {
        this.empFlowRateService = empFlowRateService;
    }

//    // 学校为单位员工的入职率脚本
//    @ApiOperation("脚本：学校为单位统计入职率")
//    @GetMapping("/rateJoinSchools")
//    public void joinSchoolsRate(){
//        empFlowRateService.joinSchoolsRate();
//    }
//
//    // 学校为单位员工的离职率
//    @ApiOperation("脚本：学校为单位员工离职率")
//    @GetMapping("/RateLeaveSchools")
//    public void leaveSchoolsRate(){
//        empFlowRateService.leaveSchoolsRate();
//    }
//
//    // 统计人员流动率
//    @ApiOperation("脚本：统计所有区域的入职率")
//    @GetMapping("/rateJoin")
//    public void rateJoin(){
//        empFlowRateService.rateJoin();
//    }

    @ApiOperation("查询学校为单位员工人员流动率")
    @GetMapping("/findRateSchool")
    public Collection<RateFolwSchool> findRateSchool(@Active Actor actor,
                                                     @RequestParam @Valid FolwEnum folwEnum){
        return empFlowRateService.findRateSchool(actor, folwEnum);
    }

    @ApiOperation("查询城市为单位员工人员流动率")
    @GetMapping("/findRateLeaveCity")
    public Collection<RateFolwCity> findRateLeaveCity(@Active Actor actor,
                                                      @RequestParam @Valid FolwEnum folwEnum){
        return empFlowRateService.findRateLeaveCity(actor, folwEnum);
    }

    @ApiOperation("查询省为单位员工人员流动率")
    @GetMapping("/findRateProvinces")
    public Collection<RateFolwProvinces> findRateProvinces(@Active Actor actor,
                                                           @RequestParam @Valid FolwEnum folwEnum){
        return empFlowRateService.findRateProvinces(actor, folwEnum);
    }

    @ApiOperation("查询大区为单位员工人员流动率")
    @GetMapping("/findRateRegions")
    public Collection<RateFolwRegions> findRateRegions(@Active Actor actor,
                                                       @RequestParam @Valid FolwEnum folwEnum){
        return empFlowRateService.findRateRegions(actor, folwEnum);
    }


    @ApiOperation("入职率：按照地区查询所有下级")
    @GetMapping("/joinFlowDistrict")
    public Map<String, Object> joinFlowDistrict(@Active Actor actor,
                                                @RequestParam @Valid String code){
        return empFlowRateService.joinFlowDistrict(code);
    }

    @ApiOperation("离职率：按照地区查询所有下级")
    @GetMapping("/leaveFlowDistrict")
    public Map<String, Object> leaveFlowDistrict(@Active Actor actor,
                                                @RequestParam @Valid String code){
        return empFlowRateService.leaveFlowDistrict(code);
    }

}
