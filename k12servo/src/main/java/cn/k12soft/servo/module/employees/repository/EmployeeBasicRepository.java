package cn.k12soft.servo.module.employees.repository;

import cn.k12soft.servo.module.employees.domain.EmployeeBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface EmployeeBasicRepository extends JpaSpecificationExecutor<EmployeeBasic>, JpaRepository<EmployeeBasic, Long> {
    Collection<EmployeeBasic> findBySchoolIdAndIsLeaveAndLeaveAtBetween(Integer schoolId, boolean b, Instant first, Instant second);

    Collection<EmployeeBasic> findAllBySchoolIdAndJoinAtBetween(Integer schoolId, Instant first, Instant second);

    Integer countAllBySchoolId(Integer schoolId);

    Integer countAllBySchoolIdAndJoinAtBetween(Integer schoolId, Instant first, Instant second);

    Integer countAllBySchoolIdAndLeaveAtBetween(Integer schoolId, Instant first, Instant second);
}
