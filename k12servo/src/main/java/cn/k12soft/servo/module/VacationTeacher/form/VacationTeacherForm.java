package cn.k12soft.servo.module.VacationTeacher.form;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;

public class VacationTeacherForm {

    private VacationTeacherUtil.VACATIONTYPE reasion;  // 请假类型

    private String desc;

    private Instant fromDate;       // 请假开始时间

    private Instant toDate;         // 请假结束时间

    private String vacationTime;    // 请假时长

    private VacationTeacherUtil.YES_NO isBusiness;  // 是否加班

    private String portrait;        // 半身像

    public VacationTeacherUtil.VACATIONTYPE getReasion() {
        return reasion;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getVacationTime() {
        return vacationTime;
    }

    public String getDesc() {
        return desc;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public VacationTeacherUtil.YES_NO getIsBusiness() {
        return isBusiness;
    }
}
