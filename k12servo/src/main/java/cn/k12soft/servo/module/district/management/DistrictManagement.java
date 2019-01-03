package cn.k12soft.servo.module.district.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.district.form.CityForm;
import cn.k12soft.servo.module.district.form.ProvincesForm;
import cn.k12soft.servo.module.district.form.RegionsForm;
import cn.k12soft.servo.module.district.service.DistrictService;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.module.zone.domain.Groups;
import cn.k12soft.servo.module.zone.domain.Provinces;
import cn.k12soft.servo.module.zone.domain.Regions;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/district/management")
public class DistrictManagement {

    private final DistrictService districtService;

    @Autowired
    public DistrictManagement(DistrictService districtService) {
        this.districtService = districtService;
    }

    @ApiOperation("添加城市")
    @PostMapping("/web/addCity")
    public Citys createCitys(@RequestBody @Valid CityForm form){
        return districtService.CitySive(form);
    }

    @ApiOperation("更新城市")
    @PutMapping("/updateCitys")
    public Citys updateCitys(@Active Actor actor,
                             @RequestParam("id") Integer id,
                             @RequestBody @Valid CityForm form){
        return districtService.CityUpdate(form, id);
    }

    @ApiOperation("删除城市")
    @DeleteMapping("/deleteCitys/{id:\\d+}")
    public void deleteCitys(@Active Actor actor,
                            @PathVariable("id") Integer id){
        districtService.deleteCitys(id);
    }

    @ApiOperation("查询城市，如果不传parentId,查询的是所有的城市；反之，查询省下边的城市")
    @GetMapping("/findCitys")
    public Collection<Citys> findCitys(@Active Actor actor,
                                       @RequestParam(required =  false) @Valid Integer parentId){
        return districtService.findCitys(parentId);
    }

    @ApiOperation("添加省")
    @PostMapping("/web/addProvinces")
    public Provinces createProvinces(@RequestBody @Valid ProvincesForm form){
        return districtService.saveProvinces(form);
    }

    @ApiOperation("更新省")
    @PutMapping("/updateProvinces")
    public Provinces updateProvinces(@Active Actor actor,
                             @RequestParam("id") Integer id,
                             @RequestBody @Valid ProvincesForm form){
        return districtService.provincesUpdate(form, id);
    }

    @ApiOperation("根据id删除省")
    @DeleteMapping("/deleteProvinces/{id:\\d+}")
    public void deleteProvinces(@Active Actor actor,
                                @PathVariable @Valid Integer id){
        districtService.deleteProvinces(id);
    }

    @ApiOperation("查询省，如果传regionsId,查询的是大区下的省；反之，查询所有的省")
    @GetMapping("/findProvinces")
    public Collection<Provinces> queryProvinces(@Active Actor actor,
                                                 @RequestParam(required = false) @Valid Integer regionsId){
        return districtService.findProvinces(regionsId);
    }


    @ApiOperation("添加大区")
    @PostMapping("/web/addRegions")
    public Regions createRegions(@RequestBody @Valid RegionsForm form){
        return districtService.createRegions(form);
    }

    @ApiOperation("更新大区")
    @PutMapping("/updateRegions")
    public Regions updateRegions(@Active Actor actor,
                                 @RequestParam @Valid Integer id,
                                 @RequestBody @Valid RegionsForm form){
        return districtService.updateRegions(id, form);
    }

    @ApiOperation("删除大区")
    @DeleteMapping("/deleteRegions/{id:\\d+}")
    public void deleteRegions(@Active Actor actor,
                              @PathVariable @Valid Integer id){
        districtService.deleteRegions(id);
    }

    @ApiOperation("查询大区，groupsId有值时，查询的是集团下的大区，反之，查询所有大区")
    @GetMapping("/findRegions")
    public Collection<Regions> findRegions(@Active Actor actor,
                                           @RequestParam(required = false) @Valid Integer groupsId){
        return districtService.findRegions(groupsId);
    }

    @ApiOperation("添加集团")
    @PostMapping("/web/addGroups")
    public Groups createdGroups(@RequestParam @Valid String groupsName){
        return districtService.createdGroups(groupsName);
    }

    @ApiOperation("修改集团")
    @PutMapping("/updateGroups")
    public Groups updateGroups(@Active Actor actor,
                               @RequestParam @Valid Integer id,
                               @RequestParam @Valid String groupName){
        return districtService.updateGroups(id, groupName);
    }

    @ApiOperation("删除集团")
    @DeleteMapping("/deleteGroups/{id:\\d+}")
    public void deleteGroups(@Active Actor actor,
                             @PathVariable @Valid Integer id){
        districtService.deleteGroups(id);
    }

    @ApiOperation("查询集团")
    @GetMapping("/findGroups")
    public Collection<Groups> findGroups(){
        return districtService.findGroups();
    }

//    @ApiOperation("集团关联大区")
//    @PutMapping("/relevanceRegions")
//    public Regions relevanceRegions(@Active Actor actor,
//                                    @RequestParam @Valid String code){
//        return districtService.relevanceRegions(actor, code);
//    }
//
//    @ApiOperation("大区关联省")
//    @PutMapping("/relevanceProvinces")
//    public Provinces relevanceProvinces(@Active Actor actor,
//                                       @RequestParam @Valid String code){
//        return districtService.relevanceProvinces(actor, code);
//    }
//
//    @ApiOperation("省关联市")
//    @PutMapping("/relevanceCity")
//    public Citys relevanceCity(@Active Actor actor,
//                                   @RequestParam @Valid String code){
//        return districtService.relevanceCity(actor, code);
//    }
//
//    @ApiOperation("市关联学校")
//    @PutMapping("/relevanceSchool")
//    public Citys relevanceSchool(@Active Actor actor,
//                               @RequestParam @Valid String code){
//        return districtService.relevanceSchool(actor, code);
//    }


    @ApiOperation("取消关联")
    @PutMapping("/cancal")
    public void cancel(@RequestParam @Valid String code){
        districtService.cancel(code);
    }


    @ApiOperation("集团取消关联大区")
    @PutMapping("/cancelRegions")
    public Regions cancelRegions(@Active Actor actor,
                                 @RequestParam @Valid String code){
        return districtService.cancelRegions(code);
    }

    @ApiOperation("大区取消关联省")
    @PutMapping("/cancelProvince")
    public Provinces cancelProvince(@Active Actor actor,
                                 @RequestParam @Valid String code){
        return districtService.cancelProvince(code);
    }

    @ApiOperation("省取消关联市")
    @PutMapping("/cancelCitys")
    public Citys cancelCitys(@Active Actor actor,
                                    @RequestParam @Valid String code){
        return districtService.cancelCitys(code);
    }

    @ApiOperation("市取消关联学校")
    @PutMapping("/cancelSchool")
    public School cancelSchool(@Active Actor actor,
                              @RequestParam @Valid String code){
        return districtService.cancelSchool(code);
    }


    @ApiOperation("查询所有未分配的大区")
    @GetMapping("/queryRegions")
    public Collection<Regions> queryRegions(@Active Actor actor){
        return districtService.queryRegions(actor);
    }

    @ApiOperation("查询所有未分配的省")
    @GetMapping("/queryProvinces")
    public Collection<Provinces> queryProvinces(@Active Actor actor){
        return districtService.queryProvinces(actor);
    }

    @ApiOperation("查询所有未分配的市")
    @GetMapping("/queryCitys")
    public Collection<Citys> queryCitys(@Active Actor actor){
        return districtService.queryCitys(actor);
    }

    @ApiOperation("查询所有未分配的学校")
    @GetMapping("/querySchool")
    public Collection<School> querySchool(@Active Actor actor){
        return districtService.querySchool(actor);
    }

    @ApiOperation("树状图返回省市大区")
    @GetMapping("/findTree")
    public Map<String, Object> findTree(@Active Actor actor){
        return districtService.findTree(actor);
    }

    @ApiOperation("区域关联, first 为要关联的那一级，second 为下一级")
    @PutMapping("correlation")
    public void correlation(@RequestParam @Valid String first,
                            @RequestParam @Valid String second){
        districtService.correlation(first, second);
    }

    @ApiOperation("添加部门")
    @PutMapping("/addDept")
    public void addDept(@Active Actor actor,
                               @RequestParam @Valid String districtCode,
                               @RequestParam @Valid String deptIds){
        this.districtService.addDept(actor, districtCode, deptIds);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/deleteDept")
    public void deleteDeptByCity(@Active Actor actor,
                                 @RequestParam @Valid String districtCode,
                                 @RequestParam @Valid String deptIds){
        this.districtService.deleteDept(actor, districtCode, deptIds);
    }


}
