package cn.k12soft.servo.module.empFlowRate.repository;

import cn.k12soft.servo.module.empFlowRate.domain.RateFolwGroups;
import cn.k12soft.servo.module.zone.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RateFolwGroupsRepository extends JpaRepository<RateFolwGroups, Long>, JpaSpecificationExecutor<RateFolwGroups>{
    Collection<RateFolwGroups> findByGroups(Groups group);
}
