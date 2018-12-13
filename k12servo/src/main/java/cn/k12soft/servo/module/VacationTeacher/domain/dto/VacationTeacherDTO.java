package cn.k12soft.servo.module.VacationTeacher.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.time.Instant;

public class VacationTeacherDTO {
    private Long id;
    private Integer actorId;
    private Integer schoolId;
    private String userName;    // 请假人姓名
    private VacationTeacherUtil.VACATIONTYPE reason;
    private String desc;
    private Instant fromDate;
    private Instant toDate;
    private String portrait;
    private Instant createdAt;
    private String vacationTime;
    private VacationTeacherUtil.ISGONE isGone;
    private Instant updateTIme; // 审批时间

    public VacationTeacherDTO(Long id, Integer actorId, String userName, Integer schoolId, VacationTeacherUtil.VACATIONTYPE reason, String desc, Instant fromDate, Instant toDate, String portrait, Instant createdAt, String vacationTime, VacationTeacherUtil.ISGONE isGone, Instant updateTIme) {
        this.id = id;
        this.actorId = actorId;
        this.userName = userName;
        this.schoolId = schoolId;
        this.reason = reason;
        this.desc = desc;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.portrait = portrait;
        this.createdAt = createdAt;
        this.vacationTime = vacationTime;
        this.isGone = isGone;
        this.updateTIme = updateTIme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public VacationTeacherUtil.VACATIONTYPE getReason() {
        return reason;
    }

    public void setReason(VacationTeacherUtil.VACATIONTYPE reason) {
        this.reason = reason;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getVacationTime() {
        return vacationTime;
    }

    public void setVacationTime(String vacationTime) {
        this.vacationTime = vacationTime;
    }

    public VacationTeacherUtil.ISGONE getIsGone() {
        return isGone;
    }

    public void setIsGone(VacationTeacherUtil.ISGONE isGone) {
        this.isGone = isGone;
    }

    public Instant getUpdateTIme() {
        return updateTIme;
    }

    public void setUpdateTIme(Instant updateTIme) {
        this.updateTIme = updateTIme;
    }
}
