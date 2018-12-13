package cn.k12soft.servo.module.WIFIDevice.repository;

import cn.k12soft.servo.module.WIFIDevice.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, JpaSpecificationExecutor<Device>{
    Collection<Device> findBySchoolId(Integer schoolId);
}
