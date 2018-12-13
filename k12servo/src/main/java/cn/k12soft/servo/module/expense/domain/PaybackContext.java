package cn.k12soft.servo.module.expense.domain;

import java.time.LocalDate;
import org.springframework.data.util.Pair;

public interface PaybackContext {

  /**
   * 学生ID
   */
  int getStudentId();

  /**
   * 日期区间
   */
  Pair<LocalDate, LocalDate> getDateRange();

  /**
   * 正常考勤统计天数
   */
  int getTotalDays();

  /**
   * 缺勤日
   */
  int getAbsentDays();

  /**
   * 出勤日
   */
  int getAttendedDays();

  /**
   * 请假日
   */
  int getLeftDays();
}
