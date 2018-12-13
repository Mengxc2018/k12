package cn.k12soft.servo.module.attendanceCount.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.sql.Timestamp;
import java.time.Instant;

public class VacaTimeDTO {
    private Instant formDate;         // 开始时间
    private Instant toDate;         // 结束时间
    private String vacationTime;    // 请假时长
    private Instant createdAt;      // 记录创建时间
    private VacationTeacherUtil.ISGONE isGone;            // 异常打卡审核

    public VacaTimeDTO(){}

    public VacaTimeDTO(Instant formDate, Instant toDate, String vacationTime, Instant createdAt, VacationTeacherUtil.ISGONE isGone) {
        this.formDate = formDate;
        this.toDate = toDate;
        this.vacationTime = vacationTime;
        this.createdAt = createdAt;
        this.isGone = isGone;
    }


    public Instant getFormDate() {
        return formDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getVacationTime() {
        return vacationTime;
    }

    public VacationTeacherUtil.ISGONE getIsGone() {
        return isGone;

    }
}
