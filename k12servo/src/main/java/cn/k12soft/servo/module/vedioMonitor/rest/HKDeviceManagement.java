package cn.k12soft.servo.module.vedioMonitor.rest;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.module.vedioMonitor.domain.HKDevice;
import cn.k12soft.servo.module.vedioMonitor.form.HKDeviceForm;
import cn.k12soft.servo.module.vedioMonitor.service.HKDeviceService;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static cn.k12soft.servo.domain.enumeration.Permission.*;

/**
 * Created by liubing on 2018/3/31
 */
@RestController
public class HKDeviceManagement {
    private HKDeviceService hkDeviceService;
    private final KlassRepository klassRepository;

    @Autowired
    public HKDeviceManagement(HKDeviceService phkDeviceService, KlassRepository klassRepository) {
        this.hkDeviceService = phkDeviceService;
        this.klassRepository = klassRepository;
    }

    @ApiOperation("添加班级监控设备")
    @PostMapping(value = "/hkdevice/create")
    @PermissionRequired(HKDEVICE_CREATE)
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public HKDevice create(@Active Actor actor, @RequestBody HKDeviceForm form){
        Klass klass = this.klassRepository.findOne(form.getKlassId());
        HKDevice hkDevice = this.hkDeviceService.findByDeviceIdAndKlassId(form.getDeviceId(), form.getKlassId());
        if(hkDevice != null){
            return hkDevice;
        }

        hkDevice = new HKDevice();
        hkDevice.setDeviceId(form.getDeviceId());
        hkDevice.setKlassId(form.getKlassId());
        hkDevice.setVerifyCode(form.getVerifyCode());
        hkDevice.setDeviceName(form.getDeviceName());
        hkDevice.setDeviceCover(form.getDeviceCover());
        hkDevice.setKlassName(klass.getName());
        this.hkDeviceService.save(hkDevice);
        return hkDevice;
    }

    @ApiOperation("根据ID删除班级监控设备")
    @DeleteMapping(value = "/hkdevice/delete/{id:\\d+}")
    @PermissionRequired(HKDEVICE_DELETE)
    @Timed
    public void delete(@PathVariable Integer id){
        this.hkDeviceService.delete(id);
    }

    @ApiOperation("根据班级设备ID删除班级监控设备")
    @DeleteMapping(value="/hkdevice/deleteByKD")
    @PermissionRequired(HKDEVICE_DELETE)
    @Timed
    public void deleteByKlassIdAndDeviceId(@RequestParam(value = "klassId", required = true) Integer klassId,
                                           @RequestParam(value = "deviceId", required = true) String deviceId){
        this.hkDeviceService.deleteByKlassIdAndDeviceId(klassId, deviceId);
    }


    @ApiOperation("根据班级设备ID更新班级监控设备")
    @DeleteMapping(value="/hkdevice/updateByKD")
    @PermissionRequired(HKDEVICE_PUT)
    @Timed
    public HKDevice updateByKlassIdAndDeviceId(@RequestParam(value = "klassId", required = true) Integer klassId,
                                           @RequestParam(value = "deviceId", required = true) String deviceId,
                                           @RequestParam(value = "displayName", required = false) String displayName){

        HKDevice hkDevice = this.hkDeviceService.findByDeviceIdAndKlassId(deviceId,klassId);
        if(hkDevice != null){
            if(displayName != null){
                hkDevice.setDeviceName(displayName);
            }
            return hkDevice;
        }
        return null;
    }

    @ApiOperation("根据班级查询所有班级监控设备")
    @GetMapping(value="/hkdevice/findByKlassId")
    @PermissionRequired(HKDEVICE_VIEW)
    @Timed
    public Page<HKDevice> findByKlassId(@RequestParam(value = "klassId", required = true) Integer klassId,
                                        @RequestParam(value = "page", required = true) int page,
                                        @RequestParam(value = "size", required = false) Integer size){
        int pageSize = 10;
        if (size != null) {
            pageSize = size;
        }
        page = Math.max(0, page - 1);
        Pageable pageable = new PageRequest(page, pageSize, null);
        return this.hkDeviceService.findByKlassId(klassId, pageable);
    }

    @ApiOperation("根据deviceId查询班级监控设备")
    @GetMapping(value="/hkdevice/findByDeviceId")
    @Timed
    public HKDevice findByDeviceId(@RequestParam(value="deviceId", required = true) String deviceId){
        return this.hkDeviceService.findByDeviceId(deviceId);
    }

}
