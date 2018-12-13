package cn.k12soft.servo.module.AttendanceTeacher.domain;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.ATTENDANCESTATUS;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.VACATIONTYPE;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.time.Instant;

/**
 * 教师打卡信息
 */
@Entity
@DynamicUpdate
public class AttendanceTeacher{
    @Id
    @GeneratedValue
    private Long id;
    private Integer actorId;     // 教师id
    private Integer schoolId;
    private String portrait;        // 签到半身像
    private float temperature;      //体温

    private Instant amStartTime;    // 上午签到时间
    private Instant amEndTime;      // 上午签退时间
    private Instant pmStartTime;    // 下午签到时间
    private Instant pmEndTime;      // 下午签退时间

    private ATTENDANCESTATUS ast;      // 上午签到状态
    private ATTENDANCESTATUS aet;      // 上午签退状态
    private ATTENDANCESTATUS pst;      // 下午签到状态
    private ATTENDANCESTATUS pet;      // 下午签退状态

//    private Instant createdAt;      // 记录创建时间
    private Long createdAt;
    private boolean isFull;     // 是否满勤
    private VACATIONTYPE status;    // 当天出勤状态，比如满勤、缺勤、迟到、请假、出差等
    private Long times;          // 某天缺勤时长（单位：秒），如果当天的缺勤时长等于或者少于请假时长，则视为全勤
    private Long vacationTime;   // 请假时长 

    public AttendanceTeacher(){
    }

    public AttendanceTeacher(Integer actorId, Integer schoolId, String portrait, float temperature, Instant amStartTime, Instant amEndTime, Instant pmStartTime, Instant pmEndTime, ATTENDANCESTATUS ast, ATTENDANCESTATUS aet, ATTENDANCESTATUS pst, ATTENDANCESTATUS pet, Long createdAt, boolean isFull, VACATIONTYPE status, Long times) {
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.portrait = portrait;
        this.temperature = temperature;
        this.amStartTime = amStartTime;
        this.amEndTime = amEndTime;
        this.pmStartTime = pmStartTime;
        this.pmEndTime = pmEndTime;
        this.ast = ast;
        this.aet = aet;
        this.pst = pst;
        this.pet = pet;
        this.createdAt = createdAt;
        this.isFull = isFull;
        this.status = status;
        this.times = times;
    }

    public AttendanceTeacher(Long id, Integer actorId, Integer schoolId, String portrait, float temperature, Instant amStartTime, Instant amEndTime, Instant pmStartTime, Instant pmEndTime, ATTENDANCESTATUS ast, ATTENDANCESTATUS aet, ATTENDANCESTATUS pst, ATTENDANCESTATUS pet, Long createdAt, boolean isFull, VACATIONTYPE status, Long times) {
        this.id = id;
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.portrait = portrait;
        this.temperature = temperature;
        this.amStartTime = amStartTime;
        this.amEndTime = amEndTime;
        this.pmStartTime = pmStartTime;
        this.pmEndTime = pmEndTime;
        this.ast = ast;
        this.aet = aet;
        this.pst = pst;
        this.pet = pet;
        this.createdAt = createdAt;
        this.isFull = isFull;
        this.status = status;
        this.times = times;
    }

    public AttendanceTeacher(Long id, Integer actorId, Integer schoolId, String portrait, float temperature, Instant amStartTime, Instant amEndTime, Instant pmStartTime, Instant pmEndTime, ATTENDANCESTATUS ast, ATTENDANCESTATUS aet, ATTENDANCESTATUS pst, ATTENDANCESTATUS pet, Long createdAt) {
        this.id = id;
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.portrait = portrait;
        this.temperature = temperature;
        this.amStartTime = amStartTime;
        this.amEndTime = amEndTime;
        this.pmStartTime = pmStartTime;
        this.pmEndTime = pmEndTime;
        this.ast = ast;
        this.aet = aet;
        this.pst = pst;
        this.pet = pet;
        this.createdAt = createdAt;
    }

    public AttendanceTeacher(Integer actorId, Integer schoolId, Long createdAt, VACATIONTYPE status) {
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
        this.status = status;
    }
    public AttendanceTeacher(Integer actorId, Integer schoolId, Long createdAt, VACATIONTYPE status, Long times, Long vacationTime) {
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
        this.status = status;
        this.times = times;
        this.vacationTime = vacationTime;
    }


    public Long getId() {
        return id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public String getPortrait() {
        return portrait;
    }

    public float getTemperature() {
        return temperature;
    }

    public Instant getAmStartTime() {
        return amStartTime;
    }

    public Instant getAmEndTime() {
        return amEndTime;
    }

    public Instant getPmStartTime() {
        return pmStartTime;
    }

    public Instant getPmEndTime() {
        return pmEndTime;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setAmStartTime(Instant amStartTime) {
        this.amStartTime = amStartTime;
    }

    public void setAmEndTime(Instant amEndTime) {
        this.amEndTime = amEndTime;
    }

    public void setPmStartTime(Instant pmStartTime) {
        this.pmStartTime = pmStartTime;
    }

    public void setPmEndTime(Instant pmEndTime) {
        this.pmEndTime = pmEndTime;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getIsFull() {
        return isFull;
    }

    public Long getTime() {
        return times;
    }

    public void setIsFull(boolean isFull) {
        isFull = isFull;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public ATTENDANCESTATUS getAst() {
        return ast;
    }

    public void setAst(ATTENDANCESTATUS ast) {
        this.ast = ast;
    }

    public ATTENDANCESTATUS getAet() {
        return aet;
    }

    public void setAet(ATTENDANCESTATUS aet) {
        this.aet = aet;
    }

    public ATTENDANCESTATUS getPst() {
        return pst;
    }

    public void setPst(ATTENDANCESTATUS pst) {
        this.pst = pst;
    }

    public ATTENDANCESTATUS getPet() {
        return pet;
    }

    public void setPet(ATTENDANCESTATUS pet) {
        this.pet = pet;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public VACATIONTYPE getStatus() {
        return status;
    }

    public void setStatus(VACATIONTYPE status) {
        this.status = status;
    }

    public Long getTimes() {
        return times;
    }

    public Long getVacationTime() {
        return vacationTime;
    }

    public void setVacationTime(Long vacationTime) {
        this.vacationTime = vacationTime;
    }
}

