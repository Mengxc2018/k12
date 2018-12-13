package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.module.countIncomePayout.domain.payout.CountPayoutRegions;
import cn.k12soft.servo.module.zone.domain.Regions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountPayoutRegionsRepository extends JpaRepository<CountPayoutRegions, Long>, JpaSpecificationExecutor<CountPayoutRegions>{
    CountPayoutRegions findByRegions(Regions region);

    Collection<CountPayoutRegions> findAllByGroupId(Integer id);

    CountPayoutRegions findAllByRegions(Regions regions);
}
