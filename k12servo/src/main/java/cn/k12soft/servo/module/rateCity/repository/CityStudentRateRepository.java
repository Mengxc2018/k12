package cn.k12soft.servo.module.rateCity.repository;

import cn.k12soft.servo.module.rateCity.domain.CityStudentRate;
import cn.k12soft.servo.module.zone.domain.Citys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CityStudentRateRepository extends JpaSpecificationExecutor<CityStudentRate>, JpaRepository<CityStudentRate, Long>{
    Collection<CityStudentRate> findByCitys(Citys citys);

    Collection<CityStudentRate> findByProvincesId(Integer provinceId);

    CityStudentRate queryByCitys(Citys city);
}
