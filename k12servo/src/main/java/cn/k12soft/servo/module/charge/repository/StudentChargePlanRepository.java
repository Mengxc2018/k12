package cn.k12soft.servo.module.charge.repository;

import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.ExpenseIdentDiscount;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodDiscount;
import java.time.Instant;
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

  List<StudentCharge> findAllBySchoolIdAndExpenseEntry(Integer schoolId, ExpenseEntry expenseEntry);

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
}
