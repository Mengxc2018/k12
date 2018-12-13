package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.SchoolAliPayInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolAliPayInfoRepository extends JpaRepository<SchoolAliPayInfo, Integer> {

    SchoolAliPayInfo findByUserId(String userId);
}
