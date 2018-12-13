package cn.k12soft.servo.module.finance.repository;

import cn.k12soft.servo.module.finance.domain.fee.FeeBack;
import java.util.Optional;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface FeeBackRepository extends Repository<FeeBack, Integer> {

  Optional<FeeBack> findByFeeItemId(Integer feeItemId);
}
