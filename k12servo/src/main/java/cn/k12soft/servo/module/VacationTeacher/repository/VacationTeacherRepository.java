package cn.k12soft.servo.module.VacationTeacher.repository;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface VacationTeacherRepository extends JpaRepository<VacationTeacher, Long> {


    @Query(value = "SELECT DATE_FORMAT(created_at,'%Y-%m-%d %H:%i:%s') AS created, DATE_FORMAT(form_date,'%Y-%m-%d %H:%i:%s') AS form, DATE_FORMAT(to_date,'%Y-%m-%d %H:%i:%s') AS tod, (IFNULL (is_gone , '')) AS status, (IFNULL (reason, '')) AS reason, (IFNULL ( annual, '')) AS annual, (IFNULL ( sick, '')) AS sick FROM vacation_teacher v"
            + " WHERE actor_id = :actorId"
            + " AND school_id = :schoolId"
            + " AND DATE(created_at) >= DATE(:fromDate)"
            + " AND DATE(created_at) < DATE(:toDate)"
            , nativeQuery = true)
    List<Object[]> counting(@Param("actorId") Integer actorId,
                            @Param("schoolId") Integer schoolId,
                            @Param("fromDate") LocalDateTime fromDate,
                            @Param("toDate") LocalDateTime toDate);


    @Query(value = "SELECT *"
            + " FROM vacation_teacher vtt"
            + " WHERE attendance_teacher_id = :attendanceTeacherId"
            + " AND vtt.actor_id = :actorId"
            + " AND vtt.form_date = ("
            + " SELECT MIN(v.form_date) AS form_date"
            + " FROM vacation_teacher v"
            + " WHERE attendance_teacher_id = :attendanceTeacherId"
            + " AND v.actor_id = :actorId)",nativeQuery = true)
    VacationTeacher getByEarliestFormDateAndTeacherId(@Param("attendanceTeacherId") Integer attendanceTeacherId,
                                                      @Param("actorId") Integer actorId);

    @Query(value = "SELECT *"
            + " FROM vacation_teacher vtt "
            + " WHERE DATE(vtt.created_at) = DATE(:attAmEndTime) "
            + " AND vtt.actor_id = :actorId "
            + " AND vtt.attendance_teacher_id = :attId"
            + " AND vtt.form_date = ( "
            + "  SELECT MIN(v.form_date) AS form_date "
            + "   FROM vacation_teacher v "
            + "  WHERE "
            + "       v.attendance_teacher_id = :attId"
            + "   AND v.actor_id = :actorId "
            + "   AND DATE_FORMAT(v.form_date, '%H:%i:%s') >= DATE_FORMAT(:attAmEndTime, '%H:%i:%s'))",nativeQuery = true)
    VacationTeacher getAmOutByTimeAndActorIdAndAttId(@Param("attAmEndTime") LocalDateTime attAmEndTime,
                                                     @Param("attId") Integer attId,
                                                     @Param("actorId") Integer actorId);

    @Query(value = "SELECT *"
            + " FROM vacation_teacher vtt "
            + " WHERE DATE(vtt.created_at) = DATE(:attAmEndTime) "
            + " AND vtt.actor_id = :actorId "
            + " AND vtt.attendance_teacher_id = :attId"
            + " AND vtt.form_date = ( "
            + "  SELECT MIN(v.form_date) AS form_date "
            + "   FROM vacation_teacher v "
            + "  WHERE "
            + "       v.attendance_teacher_id = :attId"
            + "   AND DATE_FORMAT(v.form_date, '%H') > 12"
            + "   AND v.actor_id = :actorId "
            + "   AND DATE_FORMAT(v.form_date, '%H:%i:%s') >= DATE_FORMAT(:attAmEndTime, '%H:%i:%s'))",nativeQuery = true)
    VacationTeacher getPmOutByTimeAndActorIdAndAttId(@Param("attAmEndTime") LocalDateTime attAmEndTime,
                                                     @Param("attId") Integer attId,
                                                     @Param("actorId") Integer actorId);

    @Query(value = "SELECT *"
            + " FROM vacation_teacher vtt"
            + " WHERE vtt.attendance_teacher_id = :attId"
            + " AND vtt.actor_id = :actorId"
            + " AND vtt.form_date = ("
            + " SELECT MIN(v.form_date) AS form_date"
            + " FROM vacation_teacher v"
            + " WHERE DATE_FORMAT(v.form_date, '%H') > 12"
            + " AND v.actor_id = :actorId"
            + " AND v.attendance_teacher_id = :attId)",nativeQuery = true)
    VacationTeacher getByPmInFormDateAndTeacherId(@Param("attId") Integer attId,
                                                  @Param("actorId") Integer actorId);

    List<VacationTeacher> findBySchoolIdAndActorIdAndAttendanceTeacherId(Integer schoolId, Integer actorId, Integer attendanceTeacherId);

    Collection<VacationTeacher> findAllByActorIdAndSchoolIdAndIsGoneAndCreatedAtBetween(Integer actorId, Integer schoolId, VacationTeacherUtil.ISGONE isGone, Instant startOfDay, Instant endOfDay, Sort sort);

    @Query(value = "SELECT *"
            + " FROM vacation_teacher "
            + " WHERE school_id = :schoolId"
            + " AND DATE_FORMAT(created_at, '%Y-%m') = DATE_FORMAT(:date, '%Y-%m')"
            + " ORDER BY created_at DESC", nativeQuery = true)
    Collection<VacationTeacher> findByschoolIdAndTime(@Param("schoolId") Integer schoolId,
                                                      @Param("date") LocalDate date);

    Collection<VacationTeacher> findBySchoolIdAndActorIdAndCreatedAtBetween(Integer schoolId, Integer id, Instant first, Instant second, Sort sort);

    @Query(value = "select sum(vacation_time) from vacation_teacher"
            + " WHERE  school_id = :schoolId"
            + " AND actor_id = :actorId"
            + " AND reason = :sick"
            + " AND  (created_at BETWEEN :first AND :second)", nativeQuery = true)
    Integer sumSickVacationTimeBySchoolIdAndActorIdAndCreatedAtBetween(@Param("schoolId") Integer schoolId,
                                                                       @Param("actorId") Integer actorId,
                                                                       @Param("sick") VacationTeacherUtil.VACATIONTYPE sick,
                                                                       @Param("first") Long first,
                                                                       @Param("second") Long second);

    @Query(value = "select sum(vacation_time) from vacation_teacher"
            + " WHERE  school_id = :schoolId"
            + " AND actor_id = :actorId"
            + " AND reason = :affair"
            + " AND  (created_at BETWEEN :first AND :second)", nativeQuery = true)
    Integer sumAffairVacationTimeBySchoolIdAndActorIdAndCreatedAtBetween(@Param("schoolId") Integer schoolId,
                                                                         @Param("actorId") Integer actorId,
                                                                         @Param("affair") VacationTeacherUtil.VACATIONTYPE affair,
                                                                         @Param("first") Long first,
                                                                         @Param("second") Long second);

}
