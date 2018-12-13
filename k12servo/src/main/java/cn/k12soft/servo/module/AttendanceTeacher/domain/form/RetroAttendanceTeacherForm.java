package cn.k12soft.servo.module.AttendanceTeacher.domain.form;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.time.Instant;

/**
 * 教师补签
 */
public class RetroAttendanceTeacherForm extends AttendanceTeacherForm{

    private Integer attendanceTeacherId;    // 打卡信息id

    private VacationTeacherUtil.VACATIONTYPE retroType; // 补签类型

    private String desc;        // 原因

    private Instant startTime;  // 开始时间

    private Instant endTime;    // 结束时间

    public Integer getAttendanceTeacherId() {
        return attendanceTeacherId;
    }

    public VacationTeacherUtil.VACATIONTYPE getRetroType() {
        return retroType;
    }

    public String getDesc() {
        return desc;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}
