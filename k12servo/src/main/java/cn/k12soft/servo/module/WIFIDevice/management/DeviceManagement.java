package cn.k12soft.servo.module.WIFIDevice.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.WIFIDevice.domain.dto.DeviceDTO;
import cn.k12soft.servo.module.WIFIDevice.domain.form.DeviceForm;
import cn.k12soft.servo.module.WIFIDevice.service.DeviceService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/device/management")
public class DeviceManagement {

    private final DeviceService deviceService;

    @Autowired
    public DeviceManagement(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * 新增设备
     * @param form
     * @return
     */
    @ApiOperation("新增WIFI打卡设备")
    @PostMapping
    public DeviceDTO create(@Active Actor actor,
                            @RequestBody @Valid DeviceForm form){
        return deviceService.create(actor, form);
    }

    @ApiOperation("更新WIFI打卡设备")
    @PutMapping
    public DeviceDTO update(@Active Actor actor,
                            @RequestParam @Valid Integer id,
                            @RequestBody @Valid DeviceForm form){
        return deviceService.update(id, form);
    }

    @ApiOperation("查询WIFI打卡设备")
    @GetMapping
    public Collection<DeviceDTO> query(@Active Actor actor){
        return deviceService.findBySchoolId(actor);
    }

    @ApiOperation("删除WIFI打卡设备")
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable @Valid Long id){
        deviceService.delete(id);
    }
}
