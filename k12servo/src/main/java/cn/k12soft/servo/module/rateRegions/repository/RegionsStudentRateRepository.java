package cn.k12soft.servo.module.rateRegions.repository;

import cn.k12soft.servo.module.rateRegions.domain.RegionsStudentRate;
import cn.k12soft.servo.module.zone.domain.Regions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RegionsStudentRateRepository extends JpaRepository<RegionsStudentRate, Long>, JpaSpecificationExecutor<RegionsStudentRate>{
    Collection<RegionsStudentRate> findByRegions(Regions regions);

    RegionsStudentRate queryByRegions(Regions region);

    Collection<RegionsStudentRate> findByRegionsId(Integer groupsId);

    Collection<RegionsStudentRate> findByGroupId(Integer id);
}
