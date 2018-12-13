package cn.k12soft.servo.module.AttendanceTeacher.domain;

import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

public class AttendPeriodTeac {

    public enum PeriodTeacType{
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
        public abstract Pair<LocalDate, LocalDate> toPeriodRange(LocalDate specialDate);
    }

    private Integer id;
    private Integer schoolId;
    private PeriodTeacType periodTeacType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private List<AttendTeacCounting> teachers;

    public AttendPeriodTeac(Integer id,
                            Integer schoolId,
                            PeriodTeacType periodTeacType,
                            LocalDate fromDate,
                            LocalDate toDate,
                            List<AttendTeacCounting> teachers) {
        this.id = id;
        this.schoolId = schoolId;
        this.periodTeacType = periodTeacType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.teachers = teachers;
    }

    public Integer getId() {
        return id;
    }

    public PeriodTeacType getPeriodTeacType() {
        return periodTeacType;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public PeriodTeacType getPeriodteacType() {
        return periodTeacType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public List<AttendTeacCounting> getTeachers() {
        return teachers;
    }
}
