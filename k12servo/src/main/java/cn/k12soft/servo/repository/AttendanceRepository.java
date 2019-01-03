package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Attendance;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

  @Query(value = "SELECT klass_id, student_id, name, portrait, DATE(sign_at), MIN(sign_at) AS earliest, MAX(sign_at) AS latest "
    + "FROM attendance "
    + "WHERE klass_id = :klassId "
    + "AND DATE(sign_at) = DATE(:date) "
    + "GROUP BY student_id, DATE(sign_at)", nativeQuery = true)
  List<Object[]> query(@Param("klassId") Integer klassId, @Param("date") LocalDate date);

  // TODO:暂时去掉两个小时的限制，只要打卡，就能查到。以前前后打卡满两个小时才算在园里
  @Query(value = "SELECT student_id, name, COUNT(student_id) FROM "
    + "(SELECT student_id, name AS name, HOUR(MAX(sign_at)) AS max_hour,HOUR(MIN(sign_at)) AS min_hour "
    + " FROM attendance"
    + " WHERE klass_id = :klassId"
    + " AND sign_at >= :fromDate AND sign_at < :toDate"
    + " GROUP BY student_id,DATE(sign_at)"
    + ") stat "
    + "WHERE stat.max_hour - stat.min_hour >= :hourPeriod "
    + "GROUP BY student_id", nativeQuery = true)
  List<Object[]> counting(@Param("klassId") Integer klassId,
                          @Param("hourPeriod") int hourPeriod,
                          @Param("fromDate") Instant fromDate,
                          @Param("toDate") Instant toDate);

  @Query(value = "SELECT count(student_id)"
    + " FROM attendance "
    + " WHERE student_id = :studentId "
    + " AND sign_at >= :fromDate AND sign_at < :toDate "
    + " AND max(HOUR(sign_at)) - min(HOUR(sign_at)) >= :hourCondition"
    + " GROUP BY student_id,YEAR(sign_at),MONTH(sign_at),DAY(sign_at)", nativeQuery = true)
  int countStudent(@Param("studentId") Integer studentId,
                   @Param("hourCondition") int hourCondition,
                   @Param("fromDate") LocalDate fromDate,
                   @Param("toDate") LocalDate toDate);

  @Query(value = "SELECT klass_id, student_id, name, portrait, DATE(sign_at), MIN(sign_at) AS earliest, MAX(sign_at) AS latest "
    + "FROM attendance "
    + "WHERE klass_id = :klassId "
    + "AND student_id = :studentId "
    + "AND DATE(sign_at) = DATE(:date) "
    + "GROUP BY DATE(sign_at)", nativeQuery = true)
  List<Object[]> queryStudent(@Param("klassId") Integer klassId,
                              @Param("studentId") Integer studentId,
                              @Param("date") LocalDate lookDate);

  @Query(value = "SELECT * FROM attendance a"
          + " WHERE a.klass_id = :klassId"
          + " AND DATE(a.created_at) = DATE(:localDate)", nativeQuery = true)
  Collection<Attendance> findByKlassAndCreatedAt(@Param("klassId") Integer klassId,
                                                 @Param("localDate") LocalDate localDate);

  Collection<Attendance> findByStudentIdAndCreatedAtBetween(Integer studentId, Instant first, Instant second);

  @Query(value = "SELECT COUNT(*) FROM attendance a"
          + " WHERE a.student_id = :studentId"
          + " AND a.school_id = :schoolId"
          + " AND DATE(a.created_at) = DATE(:now)", nativeQuery = true)
  Integer countAllByStudentIdAndSchoolIdAndCreatedAt(@Param("studentId")Integer studentId,
                                                     @Param("schoolId") Integer schoolId,
                                                     @Param("now") LocalDate now);

  @Query(value = "SELECT * FROM attendance a"
          + " WHERE a.school_id = :schoolId"
          + " AND a.klass_id = :klassId"
          + " AND student_id = :studentId"
          + " AND ("
          + "  a.created_at BETWEEN :first AND :second"
          + "  )"
          + " GROUP BY a.student_id", nativeQuery = true)
  Collection<Attendance> findBySchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(@Param("schoolId") Integer schoolId,
                                                                                 @Param("klassId")Integer klassId,
                                                                                 @Param("studentId") Integer studentId,
                                                                                 @Param("first") Instant first,
                                                                                 @Param("second") Instant second);

  @Query(value = "select a.portrait from attendance a"
          + " WHERE a.klass_id = :klassId"
          + " AND a.student_id = :studentId"
          + " AND a.sign_at = ("
          + "  SELECT MAX(sign_at) FROM attendance aa"
          + "   WHERE aa.klass_id = :klassId"
          + "   AND aa.student_id = :studentId"
          + "   AND DATE(aa.sign_at) = :date)", nativeQuery = true)
  Optional<String> findLastPortrait(@Param("klassId") Integer klassId, @Param("date") LocalDate date, @Param("studentId") Integer studentId);

  @Query(value = "SELECT * FROM attendance a"
          + " WHERE a.school_id = :schoolId"
          + " AND a.klass_id = :klassId"
          + " AND student_id = :studentId"
          + " AND ("
          + "  a.created_at BETWEEN :first AND :second"
          + "  )"
          + " GROUP BY DATE(a.created_at)", nativeQuery = true)
  Collection<Attendance> findBySchoolIdAndKlassIdAndStudentIdAndCreatedAtBetweenAndCreatdAtGroupBy(@Param("schoolId") Integer schoolId,
                                                                                                   @Param("klassId")Integer klassId,
                                                                                                   @Param("studentId") Integer studentId,
                                                                                                   @Param("first") Instant first,
                                                                                                   @Param("second") Instant second);

  @Query(value = "SELECT * FROM attendance a"
          + " WHERE a.student_id = :studentId"
          + " AND DATE(a.sign_at) = DATE(:date)", nativeQuery = true)
  List<Attendance> findByStudentIdAndSignAt(@Param("studentId") Integer studentId,
                                               @Param("date") Instant date);
}