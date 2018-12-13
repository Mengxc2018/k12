package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.MsgRecordStat;
import cn.k12soft.servo.domain.MsgRecordStat.MsgType;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface MsgRecordStatRepository extends JpaRepository<MsgRecordStat, Long> {

  int countAllByTypeAndMsgId(MsgType type, Long msgId);

  boolean existsByTypeAndMsgIdAndUserId(MsgType type, Long msgId, Integer userId);
}
