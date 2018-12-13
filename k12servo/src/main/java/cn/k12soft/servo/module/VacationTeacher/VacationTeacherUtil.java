package cn.k12soft.servo.module.VacationTeacher;
import org.springframework.data.util.Pair;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * 请假时长计算
 * 返回String： N天N小时N分钟
 */
public class VacationTeacherUtil {

    public enum TRUE_FALSE{
        TRUE,
        FALSE
    }

    public enum YES_NO{
        YES,
        NO
    }

    public enum WEEK_MONTH{
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

    public enum RETROTYPE{
        SICK,       // 病假
        ANNUAL,     // 年休假
        RETRO       // 补签
    }

    public enum VACATIONTYPE{
        RETOR,      // 补签0
        AFFAIR,     // 事假1
        REST,       // 调休2
        ANNUAL,     // 年休假3
        SICK,       // 病假4
        BIRTH,      // 产假5
        BIRTHWITH,  // 陪产假6
        MARRIAGE,   // 婚假7
        FUNERAL,    // 丧假8
        BUSINESS,   // 出差9
        OVERTIME,    // 加班10
        ALL,        // 满勤11
        UNALL       // 不满勤12
    }

    public enum ISGONE{
        CHECKED,    // 待审核
        PASS,       // 通过
        UNPASS      // 不通过
    }


    // 打卡签到签退的状态
    public enum ATTENDANCESTATUS{
        ALL,        // 满勤
        UNALL,      // 缺勤
        LATE,       // 迟到
        LEAVE,      // 早退

    }

}
