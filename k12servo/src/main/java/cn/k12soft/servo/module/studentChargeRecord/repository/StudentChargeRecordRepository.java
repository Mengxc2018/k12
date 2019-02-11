package cn.k12soft.servo.module.studentChargeRecord.repository;

import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentChargeRecordRepository extends JpaRepository<StudentChargeRecord, Long>, JpaSpecificationExecutor<StudentChargeRecord> {

    @Query(value = "select * from student_charge_record"
            + " where school_id = :schoolId"
            + " AND student_id = :studentId"
            + " AND DATE_FORMAT(DATE(create_at),'%Y-%m') = DATE_FORMAT(DATE(:localDate),'%Y-%m')", nativeQuery = true)
    Optional<StudentChargeRecord> findBySchoolIdAndMonth(@Param("schoolId") Integer schoolId,
                                                         @Param("studentId") Integer studentId,
                                                         @Param("localDate") LocalDate localDate);

    List<StudentChargeRecord> findBySchoolIdAndCreateAtBetween(Integer schoolId, Instant first, Instant second);

    @Query(value = "select * from student_charge_record"
            + " where school_id = :schoolId"
            + " and (DATE(create_at) between DATE(':formDate') and DATE(':toDate'))"
            , nativeQuery = true)
    List<StudentChargeRecord> findAllBySchoolIdAndCreateAtBetween(@Param("schoolId") Integer schoolId,
                                                            @Param("formDate") LocalDate formDate,
                                                            @Param("toDate") LocalDate toDate);
}
