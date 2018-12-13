package cn.k12soft.servo.module.vedioMonitor.service;

import cn.k12soft.servo.module.vedioMonitor.domain.HKDevice;
import cn.k12soft.servo.module.vedioMonitor.repository.HKDeviceRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liubing on 2018/3/31
 */
@Service
@Transactional
public class HKDeviceService extends AbstractEntityService<HKDevice, Integer> {
    public HKDeviceService(HKDeviceRepository entityRepository) {
        super(entityRepository);
    }

    @Override
    protected HKDeviceRepository getEntityRepository(){
        return (HKDeviceRepository)super.getEntityRepository();
    }


    public HKDevice findByDeviceIdAndKlassId(String deviceId, Integer klassId) {
        return getEntityRepository().findByDeviceIdAndKlassId(deviceId, klassId);
    }

    public void deleteByKlassIdAndDeviceId(Integer klassId, String deviceId) {
        getEntityRepository().deleteByKlassIdAndDeviceId(klassId, deviceId);
    }

    public Page<HKDevice> findByKlassId(Integer klassId, Pageable pageable) {
        return getEntityRepository().findByKlassId(klassId, pageable);
    }

    public HKDevice findByDeviceId(String deviceId) {
        return getEntityRepository().findByDeviceId(deviceId);
    }
}
