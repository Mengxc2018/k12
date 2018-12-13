package cn.k12soft.servo.module.rateRegions.repository;

import cn.k12soft.servo.module.rateRegions.domain.RegionsTeacherRate;
import cn.k12soft.servo.module.zone.domain.Regions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RegionsTeacherRateRepository extends JpaRepository<RegionsTeacherRate, Long>, JpaSpecificationExecutor<RegionsTeacherRate>{
    Collection<RegionsTeacherRate> findByRegions(Regions regions);

    RegionsTeacherRate queryByRegions(Regions region);

    Collection<RegionsTeacherRate> findByRegionsId(Integer groupsId);

    Collection<RegionsTeacherRate> findByGroupId(Integer integer);
}
