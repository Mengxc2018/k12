package cn.k12soft.servo.module.AttendanceTeacher.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.attendanceCount.domain.dto.VacaTimeDTO;

import java.time.Instant;
import java.time.Instant;
import java.util.List;

public class AttendTeacDTO {

    private Integer id;
    private Integer actorId;     // 教师actorId
    private Integer schoolId;
    private String portrait;        // 签到半身像
    private float temperature;      //体温

    private Instant amStartTime;    // 上午签到时间
    private Instant amEndTime;      // 上午签退时间
    private Instant pmStartTime;    // 下午签到时间
    private Instant pmEndTime;      // 下午签退时间

    private VacationTeacherUtil.ATTENDANCESTATUS as;      // 上午签到状态
    private VacationTeacherUtil.ATTENDANCESTATUS ae;      // 上午签退状态
    private VacationTeacherUtil.ATTENDANCESTATUS ps;      // 下午签到状态
    private VacationTeacherUtil.ATTENDANCESTATUS pe;      // 下午签退状态

    private Instant createdAt;        // 记录创建时间
    private boolean isFull;         // 是否缺勤
    private Long time;              // 缺勤时长
    private VacationTeacherUtil.VACATIONTYPE status;    // 当天出勤状态，比如满勤、缺勤、迟到、请假、外出等
    private List<VacationTeacher> list;     // 补签时间段

    public AttendTeacDTO(){}

    public AttendTeacDTO(Integer id,
                         Integer actorId,
                         Integer schoolId,
                         String portrait,
                         float temperature,
                         Instant amStartTime,
                         Instant amEndTime,
                         Instant pmStartTime,
                         Instant pmEndTime,
                         VacationTeacherUtil.ATTENDANCESTATUS as,
                         VacationTeacherUtil.ATTENDANCESTATUS ae,
                         VacationTeacherUtil.ATTENDANCESTATUS ps,
                         VacationTeacherUtil.ATTENDANCESTATUS pe,
                         Instant createdAt,
                         boolean isFull,
                         Long time,
                         VacationTeacherUtil.VACATIONTYPE status,
                         List<VacationTeacher> list) {
        this.id = id;
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.portrait = portrait;
        this.temperature = temperature;
        this.amStartTime = amStartTime;
        this.amEndTime = amEndTime;
        this.pmStartTime = pmStartTime;
        this.pmEndTime = pmEndTime;
        this.as = as;
        this.ae = ae;
        this.ps = ps;
        this.pe = pe;
        this.createdAt = createdAt;
        this.isFull = isFull;
        this.time = time;
        this.status = status;
        this.list = list;
    }

    public AttendTeacDTO(Integer id,
                         Integer actorId,
                         Integer schoolId,
                         String portrait,
                         float temperature,
                         Instant amStartTime,
                         Instant amEndTime,
                         Instant pmStartTime,
                         Instant pmEndTime,
                         VacationTeacherUtil.ATTENDANCESTATUS as,
                         VacationTeacherUtil.ATTENDANCESTATUS ae,
                         VacationTeacherUtil.ATTENDANCESTATUS ps,
                         VacationTeacherUtil.ATTENDANCESTATUS pe,
                         Instant createdAt,
                         VacationTeacherUtil.VACATIONTYPE status,
                         List<VacationTeacher> list) {
        this.id = id;
        this.actorId = actorId;
        this.schoolId = schoolId;
        this.portrait = portrait;
        this.temperature = temperature;
        this.amStartTime = amStartTime;
        this.amEndTime = amEndTime;
        this.pmStartTime = pmStartTime;
        this.pmEndTime = pmEndTime;
        this.as = as;
        this.ae = ae;
        this.ps = ps;
        this.pe = pe;
        this.createdAt = createdAt;
        this.status = status;
        this.list = list;
    }

    public Integer getId() {
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<VacationTeacher> getList() {
        return list;
    }

    public void setList(List<VacationTeacher> list) {
        this.list = list;
    }

    public boolean getIsFull() {
        return isFull;
    }

    public void setIsFull(boolean isFull) {
        isFull = isFull;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public VacationTeacherUtil.ATTENDANCESTATUS getAs() {
        return as;
    }

    public VacationTeacherUtil.ATTENDANCESTATUS getAe() {
        return ae;
    }

    public VacationTeacherUtil.ATTENDANCESTATUS getPs() {
        return ps;
    }

    public VacationTeacherUtil.ATTENDANCESTATUS getPe() {
        return pe;
    }

    public boolean isFull() {
        return isFull;
    }

    public VacationTeacherUtil.VACATIONTYPE getStatus() {
        return status;
    }
}
