package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.module.countIncomePayout.domain.income.CountIncomeGroups;
import cn.k12soft.servo.module.zone.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountIncGroupsRepository extends JpaRepository<CountIncomeGroups, Long>, JpaSpecificationExecutor<CountIncomeGroups>{
    CountIncomeGroups findByGroups(Groups group);

    CountIncomeGroups findAllByGroups(Groups groups);
}
