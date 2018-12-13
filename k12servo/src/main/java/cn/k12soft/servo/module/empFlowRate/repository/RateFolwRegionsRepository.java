package cn.k12soft.servo.module.empFlowRate.repository;

import cn.k12soft.servo.module.empFlowRate.domain.RateFolwRegions;
import cn.k12soft.servo.module.zone.domain.Regions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RateFolwRegionsRepository extends JpaRepository<RateFolwRegions, Long>, JpaSpecificationExecutor<RateFolwRegions>{

    Collection<RateFolwRegions> findByGroupIdAndStatus(Integer groupId, String join);

    Collection<RateFolwRegions> findByGroupId(Integer groupId);

    Collection<RateFolwRegions> findByRegions(Regions regions);
}
