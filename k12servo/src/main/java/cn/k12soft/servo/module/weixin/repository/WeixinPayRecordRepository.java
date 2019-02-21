package cn.k12soft.servo.module.weixin.repository;

import cn.k12soft.servo.module.weixin.admin.WeixinPayRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface WeixinPayRecordRepository extends JpaRepository<WeixinPayRecord, Long>, JpaSpecificationExecutor<WeixinPayRecord> {
    Collection<WeixinPayRecord> findAllByCreateAtBetween(Instant first, Instant second);
}
