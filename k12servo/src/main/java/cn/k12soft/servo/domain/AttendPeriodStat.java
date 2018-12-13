package cn.k12soft.servo.domain;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import org.springframework.data.util.Pair;

public class AttendPeriodStat {

  public enum PeriodType {
    WEEK {
      @Override
      public Pair<LocalDate, LocalDate> toPeriodRange(LocalDate specDate) {
        TemporalField field = WeekFields.of(Locale.getDefault()).dayOfWeek();
        return Pair.of(specDate.with(field, 1), specDate.with(field, 7));
      }
    },
    MONTH {
      @Override
      public Pair<LocalDate, LocalDate> toPeriodRange(LocalDate specDate) {
        return Pair.of(specDate.withDayOfMonth(1), specDate.withDayOfMonth(specDate.lengthOfMonth()));
      }
    };

    public abstract Pair<LocalDate, LocalDate> toPeriodRange(LocalDate specDate);
  }

  private Integer klassId;
  private PeriodType periodType;
  private LocalDate fromDate;
  private LocalDate toDate;
  private List<AttendCounting> students;

  public AttendPeriodStat(Integer klassId,
                          PeriodType periodType,
                          LocalDate fromDate,
                          LocalDate toDate,
                          List<AttendCounting> students) {
    this.klassId = klassId;
    this.periodType = periodType;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.students = students;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public LocalDate getFromDate() {
    return fromDate;
  }

  public LocalDate getToDate() {
    return toDate;
  }

  public PeriodType getPeriodType() {
    return periodType;
  }

  public List<AttendCounting> getStudents() {
    return students;
  }
}
