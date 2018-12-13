package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Guardian;
import cn.k12soft.servo.domain.Student;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@org.springframework.stereotype.Repository
public interface GuardianRepository extends JpaRepository<Guardian, Integer> {

    Set<Guardian> findAllByPatriarchId(Integer patriarchId);

    Set<Guardian> findAllByStudent_Id(Integer studentId);

    Guardian findByPatriarchIdAndStudent_Id(Integer patriarchId, Integer studentId);

    Collection<Guardian> findByPatriarchId(Integer id);

    @Query(value = "select * from guardian g"
            + " JOIN student s ON s.id = g.student_id"
            + " WHERE g.school_id = :schoolId"
            + " AND s.KLASS_ID = :klassId", nativeQuery = true)
    Collection<Guardian> findAllBySchoolIdAndKlassId(@Param("schoolId") Integer schoolId,
                                                     @Param("klassId") Integer klassId);

    @Query(value = "select * from guardian g"
            + " JOIN actor a ON a.id = g.patriarch_id"
            + " JOIN user u ON u.id = a.USER_ID"
            + " WHERE g.school_id = :schoolId"
            + " AND u.is_update = :isUpdate"
            + " AND s.KLASS_ID = :klassId", nativeQuery = true)
    Collection<Guardian> findAllBySchoolIdAndKlassIdAndIsUpdate(@Param("schoolId") Integer schoolId,
                                                                @Param("klassId") Integer klassId,
                                                                @Param("isUpdate") boolean isUpdate);

    @Query(value = "select * from guardian g"
            + " JOIN actor a ON a.id = g.patriarch_id"
            + " JOIN user u ON u.id = a.USER_ID"
            + " WHERE g.school_id = :schoolId"
            + " AND u.is_update = :isUpdate", nativeQuery = true)
    Collection<Guardian> findAllBySchoolIdAndIsUpdate(@Param("schoolId") Integer schoolId,
                                                      @Param("isUpdate") boolean isUpdate);
}
