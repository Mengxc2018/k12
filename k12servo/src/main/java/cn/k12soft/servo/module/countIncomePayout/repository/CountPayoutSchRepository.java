package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.countIncomePayout.domain.payout.CountPayoutSchools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountPayoutSchRepository extends JpaRepository<CountPayoutSchools, Long>, JpaSpecificationExecutor<CountPayoutSchools>{
    CountPayoutSchools findBySchool(School school);

    Collection<CountPayoutSchools> findAllByCityId(Integer cityId);
}
