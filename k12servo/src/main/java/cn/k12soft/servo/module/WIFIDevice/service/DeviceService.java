package cn.k12soft.servo.module.WIFIDevice.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.WIFIDevice.domain.Device;
import cn.k12soft.servo.module.WIFIDevice.domain.dto.DeviceDTO;
import cn.k12soft.servo.module.WIFIDevice.domain.form.DeviceForm;
import cn.k12soft.servo.module.WIFIDevice.repository.DeviceRepository;
import cn.k12soft.servo.module.WIFIDevice.service.mapper.DeviceMapper;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.google.common.base.Strings.isNullOrEmpty;

@Service
@Transactional
public class DeviceService extends AbstractRepositoryService<Device, Long, DeviceRepository> {

    private final DeviceMapper deviceMapper;

    protected DeviceService(DeviceRepository repository, DeviceMapper deviceMapper) {
        super(repository);
        this.deviceMapper = deviceMapper;
    }

    public DeviceDTO create(Actor actor, DeviceForm form) {
        Device device = new Device(
                form.getMac(),
                form.getSsrd(),
                form.getdIP(),
                form.getName(),
                actor.getSchoolId()
        );
        return deviceMapper.toDTO(getRepository().save(device));
    }

    public DeviceDTO update(Integer id, DeviceForm form) {

        Device device = getRepository().findOne(Long.parseLong(id.toString()));

        if (!isNullOrEmpty(form.getMac())){
            device.setMac(form.getMac());
        }
        if (!isNullOrEmpty(form.getSsrd())){
            device.setSsrd(form.getSsrd());
        }
        if (!isNullOrEmpty(form.getdIP())){
            device.setdIP(form.getdIP());
        }
        if (!isNullOrEmpty(form.getName())){
            device.setName(form.getName());
        }

        return deviceMapper.toDTO(getRepository().save(device));
    }

    public Collection<DeviceDTO> findBySchoolId(Actor actor) {
        Integer schoolId = actor.getSchoolId();
        return deviceMapper.toDTOs(getRepository().findBySchoolId(schoolId));
    }
}
