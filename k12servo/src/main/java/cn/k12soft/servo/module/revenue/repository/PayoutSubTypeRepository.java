package cn.k12soft.servo.module.revenue.repository;

import cn.k12soft.servo.module.revenue.domain.PayoutSubType;
import cn.k12soft.servo.module.revenue.domain.PayoutMainType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayoutSubTypeRepository extends JpaRepository<PayoutSubType, Integer> {
    PayoutSubType findByNameAndPayoutMainTypeAndSchoolId(String name, PayoutMainType payoutMainType, Integer schoolId);

    List<PayoutSubType> findBySchoolId(Integer schoolId);

    void deleteByPayoutMainType(PayoutMainType payoutMainType);

    List<PayoutSubType> findByPayoutMainTypeAndSchoolId(PayoutMainType payoutMainType, Integer schoolId);
}
