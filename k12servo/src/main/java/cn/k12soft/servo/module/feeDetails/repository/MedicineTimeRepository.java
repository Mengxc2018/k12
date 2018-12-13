package cn.k12soft.servo.module.feeDetails.repository;

import cn.k12soft.servo.module.medicine.domain.MedicineTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineTimeRepository extends JpaRepository<MedicineTime, Long>, JpaSpecificationExecutor<MedicineTime>{
}
