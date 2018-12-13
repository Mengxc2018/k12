package cn.k12soft.servo.module.VacationTeacher.domain;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * 教师请假记录
 */
@Entity
public class VacationTeacher implements Serializable{

    @Id
    @GeneratedValue
    private Long id;
    private Integer actorId;    // 用户的actorId
    private String userName;    // 请假人姓名
    private Integer schoolId;
    private Integer attendanceTeacherId;  // 教师签到ID
    private VacationTeacherUtil.VACATIONTYPE reason;   // 假期类型
    @Column(name = "`desc`")
    private String desc;        // 请假原因
    private String pictrue;     // 请假图片
    private Instant formDate;   // 开始时间
    private Instant toDate;     // 结束时间
    private VacationTeacherUtil.YES_NO isBusiness;  // 是否加班
    private String portrait;    // 图片半身像
    private String vacationTime;   // 请假时长（单位：秒）
    private VacationTeacherUtil.ISGONE isGone;      // 是否准假
    private Instant createdAt;  // 创建时间
    private Instant updateTime; // 审批时间

    public VacationTeacher(){

    }


    public VacationTeacher(Long id, VacationTeacherUtil.ISGONE isGone){
        this.id = id;
        this.isGone = isGone;
    }

    public VacationTeacher(Integer actorId, String userName, Integer schoolId, Integer attendanceTeacherId, VacationTeacherUtil.VACATIONTYPE reasion, String pictrue, String desc, Instant fromDate, Instant toDate, String portrait, Instant createdAt, String vacationTime, VacationTeacherUtil.ISGONE checked, VacationTeacherUtil.YES_NO isBusiness) {
        this.actorId = actorId;
        this.userName = userName;
        this.schoolId = schoolId;
        this.attendanceTeacherId = attendanceTeacherId;
        this.reason = reasion;
        this.pictrue = pictrue;
        this.desc = desc;
        this.formDate = fromDate;
        this.toDate = toDate;
        this.portrait = portrait;
        this.createdAt = createdAt;
        this.vacationTime = vacationTime;
        this.isGone = checked;
        this.isBusiness = isBusiness;
    }

    /**
     * 补卡有参构造
     * @param actorId
     * @param username
     * @param schoolId
     * @param attendanceTeacherId
     * @param reason
     * @param content
     * @param fromDate
     * @param toDate
     * @param portrait
     * @param isgone
     * @param createdAt
     */
    public VacationTeacher(Integer actorId, String username, Integer schoolId, Integer attendanceTeacherId, VacationTeacherUtil.VACATIONTYPE reason, String content, Instant fromDate, Instant toDate, String portrait, VacationTeacherUtil.ISGONE isgone, Instant createdAt) {
        this.actorId = actorId;
        this.userName = username;
        this.schoolId = schoolId;
        this.attendanceTeacherId = attendanceTeacherId;
        this.reason = reason;
        this.desc = content;
        this.formDate = fromDate;
        this.toDate = toDate;
        this.portrait = portrait;
        this.isGone = isgone;
        this.createdAt = createdAt;
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

    public Integer getAttendanceTeacherId() {
        return attendanceTeacherId;
    }

    public void setAttendanceTeacherId(Integer attendanceTeacherId) {
        this.attendanceTeacherId = attendanceTeacherId;
    }

    public String getPictrue() {
        return pictrue;
    }

    public void setPictrue(String pictrue) {
        this.pictrue = pictrue;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Instant getFormDate() {
        return formDate;
    }

    public void setFormDate(Instant formDate) {
        this.formDate = formDate;
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

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public VacationTeacherUtil.VACATIONTYPE getReason() {
        return reason;
    }

    public void setReason(VacationTeacherUtil.VACATIONTYPE reason) {
        this.reason = reason;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public VacationTeacherUtil.YES_NO getIsBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(VacationTeacherUtil.YES_NO isBusiness) {
        this.isBusiness = isBusiness;
    }

}
