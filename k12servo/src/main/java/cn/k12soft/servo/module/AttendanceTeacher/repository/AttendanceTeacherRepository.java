package cn.k12soft.servo.module.AttendanceTeacher.repository;

import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendanceTeacher;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AttendanceTeacherRepository extends JpaRepository<AttendanceTeacher, Long >, JpaSpecificationExecutor<AttendanceTeacher> {

    AttendanceTeacher findBySchoolIdAndCreatedAtAndActorId(Integer schoolId, Long lookDate, Integer actorId);

    Collection<AttendanceTeacher> findBySchoolIdAndActorIdAndCreatedAtBetween(Integer schoolId, Integer actorId, Long startTime, Long endTime);

    Collection<AttendanceTeacher> findBySchoolIdAndIsFullAndCreatedAtBetween(Integer schoolId, boolean isTrue, Long first, Long second);

    Collection<AttendanceTeacher> findBySchoolIdAndActorIdAndIsFullAndCreatedAtBetween(Integer schoolId, Integer actorId, boolean isFull, Long first, Long second);

    @Query(value = "SELECT * FROM attendance_teacher"
            + " WHERE actor_id = :id"
            + " AND created_at = :date"
            , nativeQuery = true)
    AttendanceTeacher findByActorIdAndCreatedAt(@Param("id") Integer id, @Param("date") Long date);

    @Query(value =
            "SELECT tt.created_at AS createdAt FROM ("
            + "  SELECT *  FROM  attendance_teacher att"
            + "  WHERE "
            + "   att.actor_id = :actorId"
            + "  AND att.school_id = :schoolId"
            + "  AND att.created_at BETWEEN :first  AND :second) tt"
            + " WHERE"
            + " tt.aet != 0"
            + " OR tt.ast != 0"
            + " OR tt.pst != 0"
            + " OR tt.pet != 0"
            + " OR ISNULL(tt.aet)"
            + " OR ISNULL(tt.ast)"
            + " OR ISNULL(tt.pst)"
            + " OR ISNULL(tt.pet)", nativeQuery = true)
    Collection<Object> findLateBySchoolId(@Param("schoolId") Integer schoolId,
                                          @Param("actorId") Integer actorId,
                                          @Param("first") Long first,
                                          @Param("second") Long second);

    Integer countByActorIdAndCreatedAtBetween(Integer actorId, Long first, Long second);

    @Query(value = "SELECT count(*) FROM (" +
            "  SELECT * FROM attendance_teacher a" +
            "  WHERE a.aet = 2" +
            "  OR a.ast = 2" +
            "  OR a.pst = 2" +
            "  OR a.pet = 2 ) att" +
            " WHERE" +
            " att.actor_id = :actorId" +
            " AND att.created_at BETWEEN :first" +
            " AND :second", nativeQuery = true)
    Integer countByLate(@Param("actorId") Integer actorId,
                        @Param("first") Long first,
                        @Param("second") Long second);

    @Query(value = "SELECT count(*) FROM (" +
            "  SELECT * FROM attendance_teacher a" +
            "  WHERE a.aet = 3" +
            "  OR a.ast = 3" +
            "  OR a.pst = 3" +
            "  OR a.pet = 3 ) att" +
            " WHERE" +
            " att.actor_id = :actorId" +
            " AND att.created_at BETWEEN :first" +
            " AND :second", nativeQuery = true)
    Integer countByleave(@Param("actorId") Integer actorId,
                         @Param("first") Long first,
                         @Param("second") Long second);

    @Query(value = "SELECT count(*) FROM (" +
            "  SELECT * FROM attendance_teacher a" +
            "  WHERE a.status = 3" +
            "   OR a.status = 4" +
            "   OR a.status = 5" +
            "   OR a.status = 6" +
            "   OR a.status = 7" +
            "   OR a.status = 8) att" +
            " WHERE" +
            " att.actor_id = :actorId" +
            " AND att.created_at BETWEEN :first" +
            " AND :second", nativeQuery = true)
    Integer countByVacation(@Param("actorId") Integer actorId,
                            @Param("first") Long first,
                            @Param("second") Long second);

    @Query(value = "SELECT count(*) FROM ("
            + "  SELECT * FROM attendance_teacher a"
            + "  WHERE ISNULL(a.aet)"
            + "  OR ISNULL(a.ast)"
            + "  OR ISNULL(a.pst)"
            + "  OR ISNULL(a.pet)) att" +
            " WHERE" +
            " att.actor_id = :actorId" +
            " AND att.created_at BETWEEN :first" +
            " AND :second", nativeQuery = true)
    Integer countByDel(@Param("actorId") Integer actorId,
                       @Param("first") Long first,
                       @Param("second") Long second);

    @Query(value = "select sum(times) from attendance_teacher"
            + " WHERE  school_id = :schoolId"
            + " AND actor_id = :actorId"
            + " AND  (created_at BETWEEN :first AND :second)", nativeQuery = true)
    Integer sumTimesBySchoolIdAndActorIdAndCreatedAtBetween(@Param("schoolId") Integer schoolId,
                                                            @Param("actorId") Integer actorId,
                                                            @Param("first") Long first,
                                                            @Param("second") Long second);

    @Query(value = "select sum(vacation_time) from attendance_teacher"
            + " WHERE  school_id = :schoolId"
            + " AND actor_id = :actorId"
            + " AND  (created_at BETWEEN :first AND :second)", nativeQuery = true)
    Integer sumVacationTimeBySchoolIdAndActorIdAndCreatedAtBetween(@Param("schoolId") Integer schoolId,
                                                                   @Param("actorId") Integer actorId,
                                                                   @Param("first") Long first,
                                                                   @Param("second") Long second);

    @Query(value = "select * from attendance_teacher"
            + " WHERE school_id = :schoolId"
            + " AND actor_id = :actorId"
            + " AND created_at >= :first"
            + " and created_at <= :second",nativeQuery = true)
    Collection<AttendanceTeacher> queryBySchoolIdAndActorIdAndCreatedAtBetween(@Param("schoolId") Integer schoolId,
                                                                               @Param("actorId") Integer actorId,
                                                                               @Param("first") Long first,
                                                                               @Param("second") Long second);
}
