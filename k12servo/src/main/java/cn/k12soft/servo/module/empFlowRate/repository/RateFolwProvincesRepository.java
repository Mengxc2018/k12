package cn.k12soft.servo.module.empFlowRate.repository;

import cn.k12soft.servo.module.empFlowRate.domain.RateFolwProvinces;
import cn.k12soft.servo.module.empFlowRate.domain.RateFolwRegions;
import cn.k12soft.servo.module.zone.domain.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RateFolwProvincesRepository extends JpaSpecificationExecutor<RateFolwProvinces>, JpaRepository<RateFolwProvinces, Long>{
    Collection<RateFolwProvinces> findByProvinces(Provinces province);

    Collection<RateFolwProvinces> findByRegionId(Integer regionId);

    Collection<RateFolwProvinces> findByRegionIdAndStatus(Integer regionId, String status);
}
