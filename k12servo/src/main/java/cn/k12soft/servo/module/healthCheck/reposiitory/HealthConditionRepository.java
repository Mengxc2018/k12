package cn.k12soft.servo.module.healthCheck.reposiitory;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.module.healthCheck.domain.HealthCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface HealthConditionRepository extends JpaRepository<HealthCondition, Long>, JpaSpecificationExecutor<HealthCondition>{
    List<HealthCondition> findBySchoolIdAndKlassAndCreatedAtBetween(Integer schoolId, Klass klass, Instant first, Instant second);
}
