package cn.k12soft.servo.module.wxLogin.repository;

import cn.k12soft.servo.module.wxLogin.domain.WxActive;
import cn.k12soft.servo.module.wxLogin.domain.WxActiveDTO;
import cn.k12soft.servo.module.wxLogin.service.WxService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;

@Repository
public interface WxActiveRepository extends JpaSpecificationExecutor<WxActive>, JpaRepository<WxActive, Long>{

    @Query(value = "SELECT"
            + " count(*) AS count,"
            + " w.user_name AS username"
            + " FROM wx_active w"
            + " WHERE"
            + " w.school_id = :schoolId"
            + " AND (w.created_at BETWEEN :first AND :second)"
            + " GROUP BY w.user_name",nativeQuery = true)
    Collection<Object[]> findAllBySchoolIdAndCreatedAtBetween(@Param("schoolId") Integer schoolId,
                                                               @Param("first") Instant first,
                                                               @Param("second") Instant second);

    @Query(value = "SELECT"
            + " count(*) AS count,"
            + " w.user_name AS username"
            + " FROM wx_active w"
            + " WHERE"
            + " w.school_id = :schoolId"
            + " AND w.actor_id = :actorId"
            + " AND (w.created_at BETWEEN :first AND :second)"
            + " GROUP BY w.user_name",nativeQuery = true)
    Collection<Object[]> findAllBySchoolIdAndActorIdAndCreatedAtBetween(@Param("schoolId") Integer schoolId,
                                                                           @Param("actorId") Integer actorId,
                                                                           @Param("first") Instant first,
                                                                           @Param("second") Instant second);
}
