package cn.k12soft.servo.module.rateProvince.repository;

import cn.k12soft.servo.module.rateProvince.domain.ProvinceStudentsRate;
import cn.k12soft.servo.module.zone.domain.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProvinceStudentsRateRepository extends JpaRepository<ProvinceStudentsRate, Long>, JpaSpecificationExecutor<ProvinceStudentsRate>{

    Collection<ProvinceStudentsRate> findByRegionId(Integer regionsId);

    Collection<ProvinceStudentsRate> findByProvinces(Provinces province);

    ProvinceStudentsRate queryByProvinces(Provinces province);

}
