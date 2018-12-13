package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.module.countIncomePayout.domain.payout.CountPayoutGroups;
import cn.k12soft.servo.module.zone.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountPayoutGroupsRepository extends JpaRepository<CountPayoutGroups, Long>, JpaSpecificationExecutor<CountPayoutGroups>{
    CountPayoutGroups findByGroups(Groups group);

    CountPayoutGroups findAllByGroups(Groups groups);
}
