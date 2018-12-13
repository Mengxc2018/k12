package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.module.countIncomePayout.domain.income.CountIncomeCitys;
import cn.k12soft.servo.module.zone.domain.Citys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountIncCitysRepository extends JpaRepository<CountIncomeCitys, Long>, JpaSpecificationExecutor<CountIncomeCitys>{
    CountIncomeCitys findByCitys(Citys city);

    Collection<CountIncomeCitys> findAllByProvinceId(Integer id);

    CountIncomeCitys findAllByCitys(Citys citys);
}
