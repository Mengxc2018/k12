package cn.k12soft.servo.module.rateProvince.repository;

import cn.k12soft.servo.module.rateProvince.domain.ProvinceTeachersRate;
import cn.k12soft.servo.module.zone.domain.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProvinceTeachersRateRepository extends JpaRepository<ProvinceTeachersRate, Long>, JpaSpecificationExecutor<ProvinceTeachersRate>{

    Collection<ProvinceTeachersRate> findByProvinces(Provinces provinces);

    ProvinceTeachersRate queryByProvinces(Provinces provinces);

    Collection<ProvinceTeachersRate> findByRegionsId(Integer regionsId);
}
