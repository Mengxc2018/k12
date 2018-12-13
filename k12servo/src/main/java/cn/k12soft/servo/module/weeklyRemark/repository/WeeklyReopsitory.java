package cn.k12soft.servo.module.weeklyRemark.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.module.weeklyRemark.domain.Weekly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyReopsitory extends JpaRepository<Weekly, Long>, JpaSpecificationExecutor<Weekly >{
    List<Weekly> findAllBySchoolIdAndKlassAndStudentAndCreatedAtBetween(Integer schoolId, Klass klass, Student student, Instant first, Instant second);

    @Query(value = "SELECT created_at AS createdAt, p_status as status FROM weekly"
            + " WHERE klass_id = :klassId"
            + " AND  student_id = :studentId", nativeQuery = true)
    List<Object[]> findCreatedAtByKlassAndStudent(@Param("klassId") Integer klassId,
                                                   @Param("studentId") Integer studentId);

    List<Weekly> findAllBySchoolIdAndStudentAndPStatus(Integer schoolId, Student student, boolean b);

    Optional<Weekly> findBySchoolIdAndStudentAndCreatedAtBetween(Integer schoolId, Student student, Instant first, Instant second);
}
