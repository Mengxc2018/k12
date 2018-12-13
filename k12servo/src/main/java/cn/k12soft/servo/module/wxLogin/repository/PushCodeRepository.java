package cn.k12soft.servo.module.wxLogin.repository;

import cn.k12soft.servo.module.wxLogin.domain.pojo.PushCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PushCodeRepository extends JpaSpecificationExecutor<PushCode>, JpaRepository<PushCode, Long>{

    @Query(value = " SELECT * FROM push_code p"
            + " WHERE TIMESTAMPDIFF(DAY, p.created_at, p.timeout_at) > 7"
            + " AND p.actor_id = :actorId", nativeQuery = true)
    Collection<PushCode> findAllByTimeOut7Days(@Param("actorId") Integer actorId);

    @Query(value = "SELECT * FROM push_code p"
            + " WHERE p.actor_id = :actorId"
            + " AND p.created_at = "
            + " (SELECT MAX(pc.created_at) FROM push_code pc "
            + " WHERE pc.actor_id = :actorId)", nativeQuery = true)
    List<PushCode> findAllByActorIdAndCreatedAtMax(@Param("actorId") Integer actorId);

    void deleteByFormId(String formid);
}
