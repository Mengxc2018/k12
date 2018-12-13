package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.module.countIncomePayout.domain.income.CountIncomeRegions;
import cn.k12soft.servo.module.zone.domain.Regions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountIncRegionsRepository extends JpaRepository<CountIncomeRegions, Long>, JpaSpecificationExecutor<CountIncomeRegions>{
    CountIncomeRegions findByRegions(Regions region);

    Collection<CountIncomeRegions> findAllByGroupId(Integer id);

    CountIncomeRegions findAllByRegions(Regions regions);
}
