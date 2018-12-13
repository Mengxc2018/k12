package cn.k12soft.servo.module.wxLogin.repository;

import cn.k12soft.servo.module.wxLogin.domain.WxMsgBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface WxMsgBoardRepository extends JpaRepository<WxMsgBoard, Long>, JpaSpecificationExecutor<WxMsgBoard>{
    Collection<WxMsgBoard> findAllBySchoolIdAndCreatedAtBetween(Integer schoolId, Instant first, Instant second);
}
