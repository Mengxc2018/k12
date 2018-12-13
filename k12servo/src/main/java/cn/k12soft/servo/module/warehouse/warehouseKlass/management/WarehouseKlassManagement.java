package cn.k12soft.servo.module.warehouse.warehouseKlass.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.warehouse.warehouseKlass.domian.WarehouseKlass;
import cn.k12soft.servo.module.warehouse.warehouseKlass.domian.form.WarehouseKlassForm;
import cn.k12soft.servo.module.warehouse.warehouseKlass.service.WarehouseKlassService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/warehouseKlass/management")
public class WarehouseKlassManagement {

    private final WarehouseKlassService warehouseKlassService;

    @Autowired
    public WarehouseKlassManagement(WarehouseKlassService warehouseKlassService) {
        this.warehouseKlassService = warehouseKlassService;
    }


    @ApiOperation("/班级新建物品，一般都是从园所拨物资，一般不用新建")
    @PostMapping("/created")
    public List<WarehouseKlass> create(@Active Actor actor,
                                       @RequestParam @Valid Integer klassId,
                                       @RequestBody @Valid List<WarehouseKlassForm> formList){
        return this.warehouseKlassService.created(actor, klassId, formList);
    }

    @ApiOperation("查询班级库存,条件查询，可按name查询，可按照isbn查询")
    @GetMapping("/findByKlass")
    public List<WarehouseKlass> findByKlass(@Active Actor actor,
                                                @RequestParam @Valid Integer klassId,
                                                @RequestParam(required = false) @Valid String name,
                                                @RequestParam(required = false) @Valid String isbn){
        return this.warehouseKlassService.findByKlass(actor, klassId, name, isbn);
    }

    @ApiOperation("删除，可批量删除，多个id用英文逗号隔开")
    @DeleteMapping("/deleteBy")
    private void deleteBy(@Active Actor actor,
                          @RequestParam @Valid Integer klassId,
                          @RequestParam @Valid String ids){
        this.warehouseKlassService.deleteBy(actor, klassId, ids);
    }


}
