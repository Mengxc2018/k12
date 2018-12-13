package cn.k12soft.servo.module.rateCity.repository;

import cn.k12soft.servo.module.rateCity.domain.CityTeacherRate;
import cn.k12soft.servo.module.zone.domain.Citys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CityTeacherRateRepository extends JpaSpecificationExecutor<CityTeacherRate>, JpaRepository<CityTeacherRate, Long>{
    Collection<CityTeacherRate> findByCitys(Citys citys);

    Collection<CityTeacherRate> findByProvinceId(Integer provinceId);

    CityTeacherRate queryByCitys(Citys city);
}
