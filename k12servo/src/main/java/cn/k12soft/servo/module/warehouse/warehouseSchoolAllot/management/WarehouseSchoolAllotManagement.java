package cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.WarehouseSchoolAllot;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.dto.WarehouseSchoolAllotDTO;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.form.WarehouseSchoolAllotForm;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.service.WarehouseSchoolAllotService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/warehouseSchoolAllot/management")
public class WarehouseSchoolAllotManagement {

    private final WarehouseSchoolAllotService warehouseSchoolAllotService;

    @Autowired
    public WarehouseSchoolAllotManagement(WarehouseSchoolAllotService warehouseSchoolAllotService) {
        this.warehouseSchoolAllotService = warehouseSchoolAllotService;
    }


    @ApiOperation("分配物品")
    @PostMapping("/create")
    public List<WarehouseSchoolAllot> create(@Active Actor actor,
                                             @RequestBody @Valid List<WarehouseSchoolAllotForm> formList){
        return this.warehouseSchoolAllotService.create(actor, formList);
    }

    @ApiOperation("确认领取物品")
    @PutMapping("/getIt")
    public void getIt(@Active Actor actor,
                      @RequestParam @Valid String ids){
        this.warehouseSchoolAllotService.getIt(actor, ids);
    }

    @ApiOperation("查询物品分配领用情况，月查询")
    @GetMapping("/findBy")
    public Collection<WarehouseSchoolAllotDTO> findBy(@Active Actor actor,
                                                      @RequestParam @Valid LocalDate localDate){
        return this.warehouseSchoolAllotService.findBy(actor, localDate);
    }

    @ApiOperation("删除物品，可批量，将id用英文逗号隔开")
    public void deleteBy(@Active Actor actor,
                         @RequestParam @Valid String ids){
        this.warehouseSchoolAllotService.deleteBy(actor, ids);
    }


}
