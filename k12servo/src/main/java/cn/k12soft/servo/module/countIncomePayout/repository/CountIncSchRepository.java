package cn.k12soft.servo.module.countIncomePayout.repository;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.countIncomePayout.domain.income.CountIncomeSchools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountIncSchRepository extends JpaRepository<CountIncomeSchools, Long>, JpaSpecificationExecutor<CountIncomeSchools>{
    CountIncomeSchools findBySchool(School school);

    Collection<CountIncomeSchools> findAllByCityId(Integer id);
}
