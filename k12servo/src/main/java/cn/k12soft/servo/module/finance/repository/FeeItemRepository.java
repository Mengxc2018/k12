package cn.k12soft.servo.module.finance.repository;

import cn.k12soft.servo.module.finance.domain.fee.FeeItem;
import java.util.List;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface FeeItemRepository extends Repository<FeeItem, Integer> {

  List<FeeItem> findAllBySchoolId(Integer schoolId);
}
