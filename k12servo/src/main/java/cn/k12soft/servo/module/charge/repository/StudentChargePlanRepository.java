package cn.k12soft.servo.module.charge.repository;

import cn.k12soft.servo.domain.enumeration.StudentChargeStatus;
import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.ExpenseIdentDiscount;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodDiscount;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentChargePlanRepository extends JpaRepository<StudentCharge, Integer> {

  List<StudentCharge> findByStudentId(int studentId);

  List<StudentCharge> findByStudentIdAndCreateAtBetween(int studentId, Instant monthStartTime, Instant monthEndTime);

  List<StudentCharge> findByCreateAtBetween(Instant startInstant, Instant endInstant);

  List<StudentCharge> findAllByStudentIdAndPaymentAtBetween(int studentId, Instant monthStartTime, Instant monthEndTime);

  List<StudentCharge> findAllByKlassIdAndPaymentAtBetween(int klassId, Instant monthStartTime, Instant monthEndTime);

//  List<StudentCharge> findAllByStudentIdAndPaymentAtIsNullAndCreateAtBetween(int studentId, Instant monthStartTime, Instant monthEndTime);

  Page<StudentCharge> findAllByStudentIdAndPaymentAtIsNullAndCreateAtAfter(int studentId, Instant monthStartTime, Pageable pageable);

  //  List<StudentCharge> findAllByKlassIdAndPaymentAtIsNullAndCreateAtBetween(int klassId, Instant monthStartTime, Instant monthEndTime);
  Page<StudentCharge> findAllByKlassIdAndPaymentAtIsNullAndCreateAtAfter(int klassId, Instant monthStartTime, Pageable pageable);

  Page<StudentCharge> findAllBySchoolIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtAfter(Integer schoolId, Instant zeroHourTime,
                                                                                        Pageable pageable);

//  List<StudentCharge> findAllByStudentIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtBetween(int studentId, Instant zeroHourTime, Instant monthStartTime, Instant monthEndTime);

  Page<StudentCharge> findAllByStudentIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtAfter(int studentId, Instant zeroHourTime,
                                                                                         Instant monthStartTime, Pageable pageable);

//  List<StudentCharge> findAllByKlassIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtBetween(int klassId, Instant zeroHourTime, Instant monthStartTime, Instant monthEndTime);

  Page<StudentCharge> findAllByKlassIdAndPaymentAtIsNullAndEndAtBeforeAndCreateAtAfter(int klassId, Instant zeroHourTime,
                                                                                       Instant monthStartTime, Pageable pageable);

  List<StudentCharge> findByKlassIdAndCreateAtBetween(int klassId, Instant monthStartTime, Instant monthEndTime);

  Page<StudentCharge> findAllBySchoolIdAndRemainMoneyLessThan(Integer schoolId, Float remainMoney, Pageable pageable);

  Page<StudentCharge> findAllByStudentIdAndRemainMoneyLessThan(Integer studentId, Float remainMoney, Pageable pageable);

  Page<StudentCharge> findAllByKlassIdAndRemainMoneyLessThan(Integer classId, Float remainMoney, Pageable pageable);

  @Modifying
  @Query(value = "UPDATE StudentCharge stuChar SET stuChar.periodDiscount=:periodDiscount,stuChar.identDiscount=:identDiscount,stuChar.endAt=:endAt,stuChar.money=:money WHERE stuChar.expenseEntry=:expenseEntry")
  void updateByChargePlan(@Param("expenseEntry") ExpenseEntry expenseEntry, @Param("periodDiscount") ExpensePeriodDiscount periodDiscount,
                          @Param("identDiscount") ExpenseIdentDiscount identDiscount, @Param("endAt") Instant endAt,
                          @Param("money") Float money);

  @Modifying
  @Query(value = "DELETE FROM StudentCharge stuChar WHERE stuChar.planId=:changePlanId")
  void deleteByChargePlan(@Param("changePlanId") Integer changePlanId);

  StudentCharge findByStudentIdAndExpenseEntry(int studentId, ExpenseEntry expenseEntry);

  Page<StudentCharge> findBySchoolIdAndPaymentAtIsNullAndCreateAtAfter(Integer schoolId, Instant instant, Pageable pageable);

  // 周期类型过滤掉一次性的
    @Query(value = "SELECT * FROM student_charge sc"
            + " JOIN expense_entry ee ON ee.id = sc.EXPENSE_ENTRY_ID"
            + " WHERE 1=1"
            + "  AND sc.SCHOOL_ID = :schoolId"
            + "  AND sc.EXPENSE_ENTRY_ID = :expenseEntryId"
            + "  AND ee.PERIOD_TYPE != 5"
            + " GROUP BY sc.id"
            ,nativeQuery = true)
  List<StudentCharge> findAllBySchoolIdAndExpenseEntry(@Param("schoolId") Integer schoolId,
                                                       @Param("expenseEntryId") Integer expenseEntryId);

  void deleteByExpenseEntry(ExpenseEntry entry);

  List<StudentCharge> findByKlassIdAndExpenseEntryAndCreateAtBetween(int klassId, ExpenseEntry expenseEntry, Instant startInstant, Instant endInstant);

  @Query(value = "select * from student_charge sc"
          + " WHERE sc.student_id = :studentId"
          + " AND ( SELECT MAX( s.CREATE_AT ) FROM student_charge s WHERE s.STUDENT_ID = :studentId ) = sc.CREATE_AT", nativeQuery = true)
  StudentCharge findByStudentIdAndLastCreateAt(@Param("studentId") Integer studentId);

  @Query(value = "SELECT * FROM student_charge s "
          + " WHERE s.student_id = :studentId"
          + " AND (DATE(s.create_at) BETWEEN DATE(:first ) AND DATE(:second))", nativeQuery = true)
  List<StudentCharge> findByStudentIdAndCreateAtBetweenForSql(@Param("studentId") Integer studentId,
                                                              @Param("first") Instant first,
                                                              @Param("second") Instant second);

  @Query(value = "select * from student_charge sc"
          + " where sc.SCHOOL_ID = :schoolId"
          + " and (DATE(sc.create_at) BETWEEN DATE(:formDate) AND DATE(:toDate))"
          ,nativeQuery = true)
  List<StudentCharge> findAllBySchoolIdAndCreateAtBetween(@Param("schoolId") Integer schoolId,
                                                          @Param("formDate") LocalDate formDate,
                                                          @Param("toDate") LocalDate toDate);

  // 需要过滤周期为一次性、已经结束周期的、教师没有确认的
  @Query(value = "select * from student_charge sc"
          + " join expense_period_discount epd ON epd.id = sc.PERIOD_DISCOUNT_ID"
          + " where sc.SCHOOL_ID = :schoolId"
          + "  AND (DATE(sc.create_at) BETWEEN DATE(:firstDayOfMonth) AND DATE(:lastDayOfMonth))"
          + "  AND (epd.period_type != 'ONCE')"
          + "  AND NOW() < sc.END_AT"
          + "  AND sc.t_check = '1'"
          + "  AND sc.status = 'EXCUTE'"
          + " GROUP BY sc.id"
          , nativeQuery = true)
  List<StudentCharge> findAllByStatusAndCreateAt(@Param("schoolId") Integer schoolId,
                                                 @Param("firstDayOfMonth") LocalDate firstDayOfMonth,
                                                 @Param("lastDayOfMonth") LocalDate lastDayOfMonth);

  List<StudentCharge> findAllBySchoolIdAndKlassIdAndStatusAndCreateAtBetween(Integer schoolId, Integer klassId, StudentChargeStatus excute, Instant first, Instant second);
}
