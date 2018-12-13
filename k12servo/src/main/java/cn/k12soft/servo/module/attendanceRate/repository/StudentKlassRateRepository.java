package cn.k12soft.servo.module.attendanceRate.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.module.attendanceRate.domain.StudentKlassRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface StudentKlassRateRepository extends JpaSpecificationExecutor<StudentKlassRate>, JpaRepository<StudentKlassRate, Long>{

    StudentKlassRate findByKlassIdAndSchoolId(Integer klassId, Integer schoolId);

    @Query(value = "select * from student_klass_rate"
            + " where school_id = :schoolId"
            + " AND DATE_FORMAT(created_at, '%Y') = DATE_FORMAT(:date, '%Y')",nativeQuery = true)
    Collection<StudentKlassRate> queryByKlassIdAndSchoolId(@Param("schoolId") Integer schoolId,
                                                           @Param("date") LocalDate date);

    Collection<StudentKlassRate> findBySchoolId(Integer schoolId);

    StudentKlassRate findByKlass(Klass klass);
}
