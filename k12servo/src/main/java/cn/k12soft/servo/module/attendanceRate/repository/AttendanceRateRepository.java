package cn.k12soft.servo.module.attendanceRate.repository;

import cn.k12soft.servo.module.attendanceRate.domain.TeacherSchoolRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface AttendanceRateRepository extends JpaSpecificationExecutor<TeacherSchoolRate>, JpaRepository<TeacherSchoolRate, Long>{
    TeacherSchoolRate findByActorId(Integer actorId);

    @Query(value = "select * from teacher_school_rate"
            + " WHERE school_id = :schoolId"
            + " AND DATE_FORMAT(created_at, '%y') = DATE_FORMAT(:localDate, '%y')", nativeQuery = true)
    Collection<TeacherSchoolRate> findBySchoolIdAndCreatedAt(@Param("schoolId") Integer schoolId,
                                                             @Param("localDate") LocalDate localDate);

    @Query(value = "select * from teacher_school_rate"
            + " WHERE "
            + " DATE_FORMAT(created_at, '%y') = DATE_FORMAT(:localDate, '%y')", nativeQuery = true)
    Collection<TeacherSchoolRate> findByCreatedAt(@Param("localDate") LocalDate localDate);
}
