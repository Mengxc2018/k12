package cn.k12soft.servo.module.expense.service;

import cn.k12soft.servo.module.expense.domain.PaybackContext;
import cn.k12soft.servo.repository.AttendanceRepository;
import cn.k12soft.servo.repository.VacationRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.Pair;

public class PaybackContextImpl implements PaybackContext {

  private final Integer studentId;
  private final Pair<LocalDate, LocalDate> dataRange;
  private final ApplicationContext context;

  PaybackContextImpl(Integer studentId, Pair<LocalDate, LocalDate> dataRange, ApplicationContext context) {
    this.studentId = studentId;
    this.dataRange = dataRange;
    this.context = context;
  }

  @Override
  public int getStudentId() {
    return studentId;
  }

  @Override
  public Pair<LocalDate, LocalDate> getDateRange() {
    return dataRange;
  }

  @Override
  public int getTotalDays() {
    LocalDate from = dataRange.getFirst();
    LocalDate to = dataRange.getSecond();
    int days = 0;
    for (int i = 0; i < from.until(to.plusDays(1)).getDays(); i++) {
      LocalDate date = from.plusDays(i);
      if (date.getDayOfWeek() != DayOfWeek.SATURDAY
        && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
        days++;
      }
    }
    return days;
  }

  @Override
  public int getAbsentDays() {
    return getTotalDays() - getAttendedDays();
  }

  @Override
  public int getAttendedDays() {
    return getAttendanceRepository().countStudent(studentId,
            2,
            dataRange.getFirst(),
            dataRange.getSecond());
  }

  @Override
  public int getLeftDays() {
    return getVacationRepository()
      .findAllByStudentIdAndCreatedAtBetween(studentId,
        dataRange.getFirst().atStartOfDay(ZoneId.systemDefault()).toInstant(),
        dataRange.getSecond().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())
      .size();
  }

  private AttendanceRepository getAttendanceRepository() {
    return context.getBean(AttendanceRepository.class);
  }

  private VacationRepository getVacationRepository() {
    return context.getBean(VacationRepository.class);
  }
}
