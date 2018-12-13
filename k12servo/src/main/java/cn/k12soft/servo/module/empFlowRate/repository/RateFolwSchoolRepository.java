package cn.k12soft.servo.module.empFlowRate.repository;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.empFlowRate.domain.RateFolwSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RateFolwSchoolRepository extends JpaSpecificationExecutor<RateFolwSchool>, JpaRepository<RateFolwSchool, Long>{
    Collection<RateFolwSchool> findBySchoolAndStatus(School school, String join);

    Collection<RateFolwSchool> findByCityId(Integer cityId);

    RateFolwSchool findAllBySchoolAndStatus(School school, String join);

    Collection<RateFolwSchool> findBySchool(School school);

    Collection<RateFolwSchool> findByCityIdAndStatus(Integer id, String join);
}
