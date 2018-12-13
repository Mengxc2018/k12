package cn.k12soft.servo.module.countIncomePayout.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.countIncomePayout.service.CountMoneyService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/countMoney/management")
public class CountMoneyManagement {

    private final CountMoneyService service;

    @Autowired
    public CountMoneyManagement(CountMoneyService service) {
        this.service = service;
    }

    /**
     * 1、cityID为空，上级查询学校的收入
     * 2、cityID不为空，查询当前城市收入情况
     * @param actor
     * @param cityId
     * @return
     */
    @ApiOperation("收入：如果cityId为空，查询市内所有学校的汇总。 如果cityId不为空，查询当前市的收入")
    @GetMapping("/findIncomeCitys")
    public Map<String, Object> findIncomeCitys(@Active Actor actor,
                                               @RequestParam(required = false) @Valid Integer cityId){
        return service.findIncomeCity(actor, cityId);
    }

    /**
     * 1、cityID为空，上级查询学校的支出
     * 2、cityID不为空，查询当前城市支出情况
     * @param actor
     * @param cityId
     * @return
     */
    @ApiOperation("支出：如果cityId为空，查询市内所有学校的汇总。 如果cityId不为空，查询当前市的支出")
    @GetMapping("/findPayoutCitys")
    public Map<String, Object> findPayoutCitys(@Active Actor actor,
                                               @RequestParam(required = false) @Valid Integer cityId){
        return service.findPayoutCitys(actor, cityId);
    }


    /**
     * 1、provinceID为空，上级查询市的收入
     * 2、provinceId不为空，查询当前省的收入
     * @param actor
     * @param provinceId
     * @return
     */
    @ApiOperation("收入：如果provinceId为空，查询省内所有市的汇总。 如果provinceId不为空，查询当前市的收入")
    @GetMapping("/findIncomeProvince")
    public Map<String, Object> findIncomeProvince(@Active Actor actor,
                                               @RequestParam(required = false) @Valid Integer provinceId){
        return service.findIncomeProvince(actor, provinceId);
    }

    /**
     * 1、provinceID为空，上级查询市的支出
     * 2、provinceId不为空，查询当前省的支出
     * @param actor
     * @param provinceId
     * @return
     */
    @ApiOperation("支出：如果provinceId为空，查询省内所有市的汇总。 如果provinceId不为空，查询当前市的支出")
    @GetMapping("/findPayoutProvince")
    public Map<String, Object> findPayoutProvince(@Active Actor actor,
                                               @RequestParam(required = false) @Valid Integer provinceId){
        return service.findPayoutProvince(actor, provinceId);
    }


    /**
     * 1、regionId为空，上级查询省的收入
     * 2、regionId不为空，查询当前大区收入
     * @param actor
     * @param regionId
     * @return
     */
    @ApiOperation("收入：如果regionId为空，查询大区内所有省的汇总。 如果regionId不为空，查询当前大区的收入")
    @GetMapping("/findIncomeRegions")
    public Map<String, Object> findIncomeRegions(@Active Actor actor,
                                                  @RequestParam(required = false) @Valid Integer regionId){
        return service.findIncomeRegions(actor, regionId);
    }

    /**
     * 1、regionId为空，上级查询省的支出
     * 2、regionId不为空，查询当前大区支出
     * @param actor
     * @param regionId
     * @return
     */
    @ApiOperation("支出：如果provinceId为空，查询省内所有市的汇总。 如果provinceId不为空，查询当前市的支出")
    @GetMapping("/findPayoutRegions")
    public Map<String, Object> findPayoutRegions(@Active Actor actor,
                                                  @RequestParam(required = false) @Valid Integer regionId){
        return service.findPayoutRegions(actor, regionId);
    }



    /**
     * 1、groupId，上级查询大区的收入
     * 2、groupId，查询当前集团的收入
     * @param actor
     * @param groupId
     * @return
     */
    @ApiOperation("收入：如果regionId为空，查询大区内所有省的汇总。 如果regionId不为空，查询当前大区的收入")
    @GetMapping("/findIncomeGroups")
    public Map<String, Object> findIncomeGroups(@Active Actor actor,
                                                 @RequestParam(required = false) @Valid Integer groupId){
        return service.findIncomeGroups(actor, groupId);
    }

    /**
     * 1、groupId，上级查询大区的支出
     * 2、groupId，查询当前集团的支出
     * @param actor
     * @param groupId
     * @return
     */
    @ApiOperation("支出：如果regionId为空，查询省内所有市的汇总。 如果regionId不为空，查询当前集团的支出")
    @GetMapping("/findPayoutGroups")
    public Map<String, Object> findPayoutGroups(@Active Actor actor,
                                                 @RequestParam(required = false) @Valid Integer groupId){
        return service.findPayoutGroups(actor, groupId);
    }

    // 通过区域id查询下级有多少区域
    @ApiOperation("收入：获取区域的所有下级收入情况")
    @GetMapping("/findIncomeByDistrict")
    public Map<String, Object> findIncomeByDistrict(@Active Actor actor,
                                              @RequestParam @Valid String code){
        return service.findIncomeByDistrict(actor, code);
    }

    // 通过区域id查询下级有多少区域
    @ApiOperation("支出：获取区域的所有下级支出情况")
    @GetMapping("/findPayoutByDistrict")
    public Map<String, Object> findPayoutByDistrict(@Active Actor actor,
                                                    @RequestParam @Valid String code){
        return service.findPayoutByDistrict(actor, code);
    }

    @ApiOperation("通过code获取 当前角色所在地区 收入支出率")
    @GetMapping("/findDistrictByCode")
    public Map<String, Object> findDistrictByCode(@Active Actor actor,
                                                  @RequestParam @Valid String code){
        return service.findDistrictByCode(code);
    }

    @ApiOperation("通过code获取下级地区收入支出率")
    @GetMapping("/findAllDistrictByCode")
    public Map<String, Object> findAllDistrictByCode(@Active Actor actor,
                                                     @RequestParam @Valid String code){
        return service.findAllDistrictByCode(code);
    }


}
