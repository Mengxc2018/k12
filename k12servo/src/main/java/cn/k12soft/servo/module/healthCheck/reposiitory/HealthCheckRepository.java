package cn.k12soft.servo.module.healthCheck.reposiitory;

import cn.k12soft.servo.domain.enumeration.Physical;
import cn.k12soft.servo.module.healthCheck.domain.HealthCheck;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Repository
public interface HealthCheckRepository extends JpaRepository<HealthCheck, Long>, JpaSpecificationExecutor<HealthCheck>{

    Collection<HealthCheck> findAllByTypeAndSchoolIdAndKlassIdAndCreatedAtBetween(Physical.TYPE type, Integer schoolId, Integer klassId, Instant first, Instant second);

    Collection<HealthCheck> findAllByTypeAndSchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(Physical.TYPE type, Integer schoolId, Integer klassId, Integer studentId, Instant first, Instant second);

    Collection<HealthCheck> findAllByTypeAndSchoolIdAndKlassIdAndIssueAndCreatedAtBetween(Physical.TYPE type, Integer schoolId, Integer klassId, boolean issue, Instant first, Instant second);

    Collection<HealthCheck> findAllBySchoolIdAndKlassIdAndIssueAndCreatedAtBetween(Integer schoolId, Integer klassId, boolean issue, Instant first, Instant second);

    @Query(value = "SELECT student_id AS id FROM health_check"
            + " where klass_id = :klassId"
            + " And school_id = :schoolId"
            + " AND issue = :issue"
            + " AND (created_at BETWEEN :first AND :second)"
            + " GROUP BY student_id "
            + " ORDER BY student_id ASC"
            ,nativeQuery = true)
    List<Integer> findAllStudentIdByKlassAndCreatedAtBetween(@Param("klassId") Integer klassId,
                                                             @Param("schoolId") Integer schoolId,
                                                             @Param("issue") boolean issue,
                                                             @Param("first") Instant first,
                                                             @Param("second") Instant second);


    Collection<HealthCheck> findAllBySchoolIdAndKlassIdAndStudentIdAndIssueAndCreatedAtBetween(Integer id, Integer schoolId, Integer klassId, boolean b, Instant first, Instant second);

    HealthCheck queryAllByTypeAndSchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(Physical.TYPE type, Integer schoolId, Integer klassId, Integer id, Instant first, Instant second);

    @Query(value = "SELECT student_id AS id FROM health_check"
            + " where klass_id = :klassId"
            + " And school_id = :schoolId"
            + " AND issue = :issue"
            + " AND type = :type"
            + " AND (created_at BETWEEN :first AND :second)"
            + " GROUP BY student_id "
            + " ORDER BY student_id ASC"
            ,nativeQuery = true)
    List<Integer> findAlLBySchoolIdAndKlassIDAndIssueAndTypeAndCreatedAtBetween(@Param("klassId") Integer klassId,
                                                                                @Param("schoolId") Integer schoolId,
                                                                                @Param("issue") boolean issue,
                                                                                @Param("type") Physical type,
                                                                                @Param("first") Instant first,
                                                                                @Param("second") Instant second);
}
