package cn.k12soft.servo.module.medicine.repository;

import cn.k12soft.servo.module.medicine.domain.Medicine;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>, JpaSpecificationExecutor<Medicine> {
    @Query(value = "select * from medicine"
            + " WHERE DATE(execute_time) = DATE(:date)"
            + " AND is_over = :b"
            + " AND is_stop = :b1"
            + " AND actor_id = :actorId", nativeQuery = true)
    Collection<Medicine> findDoneForParent(@Param("actorId") Integer actorId,
                                           @Param("b1") boolean b1,
                                           @Param("b") boolean b,
                                           @Param("date") LocalDate date);
    @Query(value = "select * from medicine"
            + " WHERE DATE(execute_time) = DATE(:date)"
            + " AND is_over = :b"
            + " AND is_stop = :b1"
            + " AND klass_id = :klassId"
            + " AND actor_id = :actorId", nativeQuery = true)
    Collection<Medicine> findDoneForTeacher(@Param("actorId") Integer actorId,
                                            @Param("klassId") Integer klassId,
                                            @Param("b1") boolean b1,
                                            @Param("b") boolean b,
                                            @Param("date") LocalDate date);

    Collection<Medicine> findAllBySchoolIdAndActorIdAndCreatedAtBetween(Integer schoolId, Integer id, Instant first, Instant second, Sort sort);

    Collection<Medicine> findAllBySchoolIdAndCreatedAtBetween(Integer schoolId, Instant first, Instant second, Sort sort);

    @Query(value = "select * from medicine"
            + " WHERE DATE(execute_time) = DATE(:date)"
            + " AND is_over = :b"
            + " AND is_stop = :b1"
            + " AND actor_id = :actorId", nativeQuery = true)
    Collection<Medicine> findDoneForTeacherByAll(@Param("actorId") Integer actorId,
                                                 @Param("b1") boolean b1,
                                                 @Param("b") boolean b,
                                                 @Param("date") LocalDate date);
}
