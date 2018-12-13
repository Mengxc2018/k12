package cn.k12soft.servo.module.feeDetails.repository;

import cn.k12soft.servo.module.feeDetails.domain.FeeDetailsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface FeeDetailsLogRepository extends JpaRepository<FeeDetailsLog, Long>, JpaSpecificationExecutor<FeeDetailsLog>{
    List<FeeDetailsLog> findAllBySchoolIdAndCreatedAtBetween(Integer schoolId, Instant first, Instant second);

    List<FeeDetailsLog> findAllBySchoolIdAndCreatedByAndCreatedAtBetween(Integer schoolId, Integer actorId, Instant first, Instant second);
}
