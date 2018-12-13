package cn.k12soft.servo.module.revenue.repository;

import cn.k12soft.servo.module.revenue.domain.Payout;
import cn.k12soft.servo.module.revenue.domain.PayoutMainType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PayoutRepository extends JpaRepository<Payout, Integer> {
    List<Payout> findBySchoolId(Integer schoolId);

    List<Payout> findAllBySchoolIdAndCreateAtBetween(Integer schoolId, Instant from, Instant to);
}
