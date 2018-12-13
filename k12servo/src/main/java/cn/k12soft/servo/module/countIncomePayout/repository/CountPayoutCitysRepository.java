package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.module.countIncomePayout.domain.payout.CountPayoutCitys;
import cn.k12soft.servo.module.zone.domain.Citys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountPayoutCitysRepository extends JpaRepository<CountPayoutCitys, Long>, JpaSpecificationExecutor<CountPayoutCitys>{
    CountPayoutCitys findByCitys(Citys city);

    Collection<CountPayoutCitys> findAllByProvinceId(Integer id);

    CountPayoutCitys findAllByCitys(Citys byId);
}
