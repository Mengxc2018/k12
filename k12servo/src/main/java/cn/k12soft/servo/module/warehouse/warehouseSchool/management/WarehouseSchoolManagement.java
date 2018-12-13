package cn.k12soft.servo.module.warehouse.warehouseSchool.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.enumeration.WareHouseSuper;
import cn.k12soft.servo.domain.enumeration.WarehouseSubclass;
import cn.k12soft.servo.module.warehouse.warehouseSchool.domain.WarehouseSchool;
import cn.k12soft.servo.module.warehouse.warehouseSchool.domain.form.WarehouseSchoolForm;
import cn.k12soft.servo.module.warehouse.warehouseSchool.domain.form.WarehouseSchoolUpdateForm;
import cn.k12soft.servo.module.warehouse.warehouseSchool.service.WarehouseSchoolService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/warehouseSchool/management")
public class WarehouseSchoolManagement {

    private final WarehouseSchoolService warehouseSchoolService;

    @Autowired
    public WarehouseSchoolManagement(WarehouseSchoolService warehouseSchoolService) {
        this.warehouseSchoolService = warehouseSchoolService;
    }


    @ApiOperation("新增物品，可批量")
    @PostMapping("/created")
    public void created(@Active Actor actor,
                        @RequestBody @Valid List<WarehouseSchoolForm> formList){
        warehouseSchoolService.created(actor, formList);
    }

    @ApiOperation("查询库存，条件查询，可按name查询，可按照isbn查询")
    @GetMapping("/findAllBySchoolId")
    public List<WarehouseSchool> findAllBySchoolId(@Active Actor actor,
                                                   @RequestParam(required = false) @Valid String name,
                                                   @RequestParam(required = false) @Valid String isbn){
        return warehouseSchoolService.findAllBySchool(actor, name, isbn);
    }

    @ApiOperation("分类查询库存")
    @GetMapping("/findAllBySchoolIdAndType")
    public List<WarehouseSchool> findAllBySchoolIdAndType(@Active Actor actor,
                                                          @RequestParam @Valid WarehouseSubclass subclass,
                                                          @RequestParam @Valid WareHouseSuper superClass){
        return warehouseSchoolService.findAllBySchoolIdAndType(actor, subclass, superClass);
    }

    @ApiOperation("添加库存")
    @PutMapping("/add")
    public void addWarehouseSchool(@Active Actor actor,
                                   @RequestBody @Valid List<WarehouseSchoolUpdateForm> formList){
        warehouseSchoolService.addWarehouseSchool(actor, formList);
    }

    @ApiOperation("删除，支持批量删除，将id用英文逗号隔开，中间不得有空格")
    @DeleteMapping
    public void delete(@Active Actor actor,
                       @RequestParam @Valid String ids){
        warehouseSchoolService.deleteByIds(actor, ids);
    }


}
