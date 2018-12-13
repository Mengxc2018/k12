package cn.k12soft.servo.module.employeeFlow.repositpry;

import cn.k12soft.servo.module.employeeFlow.domain.EmployeeFlow;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface EmployeeFlowRepository extends JpaRepository<EmployeeFlow, Long>, JpaSpecificationExecutor<EmployeeFlow>{
    Collection<EmployeeFlow> findBySchoolId(Integer schoolId);
    Collection<EmployeeFlow> findBySchoolIdAndTypesAndCreatedAtBetween(Integer schoolId, String leave, Instant first, Instant second, Sort orders);
    Collection<EmployeeFlow> findBySchoolIdAndActorIdAndTypesAndCreatedAtBetween(Integer schoolId, Integer actorId, String leave, Instant first, Instant second, Sort orders);
}
