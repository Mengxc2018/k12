package cn.k12soft.servo.module.warehouse.warehouseKlassLog.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import cn.k12soft.servo.module.warehouse.warehouseKlassLog.domain.WarehouseKlassLog;
import cn.k12soft.servo.module.warehouse.warehouseKlassLog.service.WarehouseKlassLogService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/warehouseKlassLog")
public class WarehouseKlassLogManagement {

    private final WarehouseKlassLogService warehouseKlassService;

    @Autowired
    public WarehouseKlassLogManagement(WarehouseKlassLogService warehouseKlassService){
        this.warehouseKlassService = warehouseKlassService;
    }


    @ApiOperation("查询操作记录，月查询")
    @GetMapping("/findBy")
    public List<WarehouseKlassLog> findBy(@Active Actor actor,
                                          @RequestParam(required = false) @Valid Integer klassId,
                                          @RequestParam(required = false) @Valid LocalDate localDate,
                                          @RequestParam(required = false) @Valid String isbn,
                                          @RequestParam(required = false) @Valid FeePeriodType type){
        return this.warehouseKlassService.findBy(actor, klassId, localDate, isbn, type);
    }
}
