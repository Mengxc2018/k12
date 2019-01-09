package cn.k12soft.servo.module.studentChargeRecord.repository;

import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeKlassTotal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface StudentChargeKlassTotalRepository extends JpaSpecificationExecutor<StudentChargeKlassTotal>, JpaRepository<StudentChargeKlassTotal, Long> {

    @Query(value = "SELECT * FROM student_charge_klass_total "
            + " WHERE school_id = :schoolId"
            + " AND DATE(create_at) between DATE(:one) AND DATE(:two)", nativeQuery = true)
    Collection<StudentChargeKlassTotal> findAllBySchoolIdAndCreateAtBetween(@Param("schoolId") Integer schoolId,
                                                                            @Param("one") LocalDate one,
                                                                            @Param("two") LocalDate two);

    @Query(value = "SELECT * FROM student_charge_klass_total "
            + " WHERE school_id = :schoolId"
            + " AND klass_id = :klassId"
            + " AND DATE(create_at) between DATE(:one) AND DATE(:two)", nativeQuery = true)
    StudentChargeKlassTotal findBySchoolIdKlassIdAndCreateAtBetween(@Param("schoolId") Integer schoolId,
                                                                    @Param("klassId") Integer klassId,
                                                                    @Param("one") LocalDate one,
                                                                    @Param("two") LocalDate two);
}
