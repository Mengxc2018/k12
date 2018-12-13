package cn.k12soft.servo.module.empFlowRate.repository;

import cn.k12soft.servo.module.empFlowRate.domain.RateFolwCity;
import cn.k12soft.servo.module.empFlowRate.domain.RateFolwSchool;
import cn.k12soft.servo.module.zone.domain.Citys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RateFolwCityRepository extends JpaRepository<RateFolwCity, Long> ,JpaSpecificationExecutor<RateFolwCity>{
    Collection<RateFolwCity> findByCitys(Citys city);

    Collection<RateFolwCity> findByProvinceIdAndStatus(Integer provinceId, String status);

    Collection<RateFolwCity> findByProvinceId(Integer provinceId);
}
