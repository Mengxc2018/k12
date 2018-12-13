package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.module.countIncomePayout.domain.payout.CountPayoutProvinces;
import cn.k12soft.servo.module.zone.domain.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountPayoutProvincesRepository extends JpaRepository<CountPayoutProvinces, Long>, JpaSpecificationExecutor<CountPayoutProvinces>{
    Collection<CountPayoutProvinces> findAllByRegionId(Integer id);

    CountPayoutProvinces findByProvinces(Provinces province);

    CountPayoutProvinces findAllByProvinces(Provinces provinces);
}
