package cn.k12soft.servo.module.feeDetails.repository;

import cn.k12soft.servo.module.feeDetails.domain.FeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface FeeDetailsRepository extends JpaRepository<FeeDetails, Long>, JpaSpecificationExecutor<FeeDetails>{

    List<FeeDetails> findAllBySchoolIdAndCreatedAtBetween(Integer schoolId, Instant first, Instant second);

    FeeDetails findOneByCreatedByAndState(Integer id, boolean b);

    List<FeeDetails> findAllBySchoolIdAndCreatedByAndCreatedAtBetween(Integer schoolId, Integer actorId, Instant first, Instant second);
}
