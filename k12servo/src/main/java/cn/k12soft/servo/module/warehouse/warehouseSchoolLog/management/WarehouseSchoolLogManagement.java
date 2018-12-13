package cn.k12soft.servo.module.warehouse.warehouseSchoolLog.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.domain.WarehouseSchoolLog;
import cn.k12soft.servo.module.warehouse.warehouseSchoolLog.service.WarehouseSchoolLogService;
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
@RequestMapping("/warehouseSchoolLog/management")
public class WarehouseSchoolLogManagement {

    private final WarehouseSchoolLogService warehouseSchoolLogService;

    @Autowired
    public WarehouseSchoolLogManagement(WarehouseSchoolLogService warehouseSchoolLogService) {
        this.warehouseSchoolLogService = warehouseSchoolLogService;
    }

    @ApiOperation("查询学校仓库增删记录,条件组合查询 ")
    @GetMapping("/findAll")
    public List<WarehouseSchoolLog> findAll(@Active Actor actor,
                                            @RequestParam(required = false) @Valid LocalDate localDate,
                                            @RequestParam(required = false) @Valid String isbn,
                                            @RequestParam(required = false) @Valid FeePeriodType type){
        return warehouseSchoolLogService.findAllBySchoolId(actor, localDate, isbn, type);
    }
}
