package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.module.countIncomePayout.domain.income.CountIncomeProvinces;
import cn.k12soft.servo.module.zone.domain.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountIncProvincesRepository extends JpaRepository<CountIncomeProvinces, Long>, JpaSpecificationExecutor<CountIncomeProvinces>{
    CountIncomeProvinces findByProvinces(Provinces province);

    Collection<CountIncomeProvinces> findAllByRegionId(Integer id);

    CountIncomeProvinces findAllByProvinces(Provinces byId);
}
