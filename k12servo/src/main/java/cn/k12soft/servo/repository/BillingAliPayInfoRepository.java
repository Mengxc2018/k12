package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.BillingAliPayInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAliPayInfoRepository extends JpaRepository<BillingAliPayInfo, Integer> {

    BillingAliPayInfo findByOrderNo(String orderNo);
}
