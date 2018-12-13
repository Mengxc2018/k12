package cn.k12soft.servo.module.wxLogin.repository;

import cn.k12soft.servo.domain.enumeration.WxSendType;
import cn.k12soft.servo.module.wxLogin.domain.WxPushMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface WxPushMsgRepository extends JpaRepository<WxPushMsg, Long>, JpaSpecificationExecutor<WxPushMsg>{

    Collection<WxPushMsg> findByActorIdAndWxSendTypeAndCreateAtBetween(Integer actorId, WxSendType wxSend, Instant first, Instant second);

}
