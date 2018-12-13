package cn.k12soft.servo.module.WIFIDevice.service.mapper;

import cn.k12soft.servo.module.WIFIDevice.domain.Device;
import cn.k12soft.servo.module.WIFIDevice.domain.dto.DeviceDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper extends EntityMapper<Device, DeviceDTO>{

    @Override
    protected DeviceDTO convert(Device device) {
        return new DeviceDTO(
                device.getId(),
                device.getMac(),
                device.getSsrd(),
                device.getdIP(),
                device.getName(),
                device.getSchoolid()
        );
    }
}
