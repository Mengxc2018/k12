package cn.k12soft.servo.module.revenue.repository;

import cn.k12soft.servo.module.revenue.domain.PayoutMainType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayoutMainTypeRepository extends JpaRepository<PayoutMainType, Integer> {
    PayoutMainType findByNameAndSchoolId(String name, Integer schoolId);

    List<PayoutMainType> findBySchoolId(Integer schoolId);
}
