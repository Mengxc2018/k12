package cn.k12soft.servo.module.feeCollect.repository;

import cn.k12soft.servo.module.feeCollect.domain.FeeCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.Collection;

public interface FeeCollectRepository extends JpaRepository<FeeCollect, Long>, JpaSpecificationExecutor<JpaRepository>{
    Collection<FeeCollect> findAllByCreatedAtBetween(Instant first, Instant second);
}
