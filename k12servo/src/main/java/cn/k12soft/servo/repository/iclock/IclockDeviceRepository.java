package cn.k12soft.servo.repository.iclock;

import cn.k12soft.servo.domain.iclock.IclockDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@Repository
public interface IclockDeviceRepository extends JpaRepository<IclockDevice, String> {

}
