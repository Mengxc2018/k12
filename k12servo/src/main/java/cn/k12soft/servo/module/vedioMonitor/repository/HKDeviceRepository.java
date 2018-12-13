package cn.k12soft.servo.module.vedioMonitor.repository;

import cn.k12soft.servo.module.vedioMonitor.domain.HKDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by liubing on 2018/3/31
 */
@Repository
public interface HKDeviceRepository extends JpaRepository<HKDevice, Integer> {
    HKDevice findByDeviceIdAndKlassId(String deviceId, Integer klassId);

    void deleteByKlassIdAndDeviceId(Integer klassId, String deviceId);

    Page<HKDevice> findByKlassId(Integer klassId, Pageable pageable);

    HKDevice findByDeviceId(String deviceId);

    Integer[] queryIdByKlassId(Integer klassId);
}
