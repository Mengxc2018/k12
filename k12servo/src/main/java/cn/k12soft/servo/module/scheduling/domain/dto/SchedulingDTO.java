package cn.k12soft.servo.module.scheduling.domain.dto;

import java.time.Instant;
import java.time.LocalTime;

public class SchedulingDTO {

    private long id;
    private Integer schoolId;       // 学校Id
    private String name;            // 排班名

    private LocalTime amStartTime;    // 规定上午上班时间
    private LocalTime amEndTime;      // 规定上午下班时间
    private LocalTime pmStartTime;    // 规定下午上班时间
    private LocalTime pmEndTime;      // 规定下午下班时间

    private Instant createdAt;
    private Instant updatedDate;

    private String schedulingType;  // 类型，区分早上中午晚上打卡还是早上晚上打卡，前者为1，后者为2

    public SchedulingDTO(long id, Integer schoolId, String name, LocalTime amStartTime, LocalTime amEndTime, LocalTime pmStartTime, LocalTime pmEndTime, Instant createdAt, Instant updatedDate, String schedulingType) {
        this.id = id;
        this.schoolId = schoolId;
        this.name = name;
        this.amStartTime = amStartTime;
        this.amEndTime = amEndTime;
        this.pmStartTime = pmStartTime;
        this.pmEndTime = pmEndTime;
        this.createdAt = createdAt;
        this.updatedDate = updatedDate;
        this.schedulingType = schedulingType;
    }

    public long getId() {
        return id;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public String getName() {
        return name;
    }

    public LocalTime getAmStartTime() {
        return amStartTime;
    }

    public LocalTime getAmEndTime() {
        return amEndTime;
    }

    public LocalTime getPmStartTime() {
        return pmStartTime;
    }

    public LocalTime getPmEndTime() {
        return pmEndTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmStartTime(LocalTime amStartTime) {
        this.amStartTime = amStartTime;
    }

    public void setAmEndTime(LocalTime amEndTime) {
        this.amEndTime = amEndTime;
    }

    public void setPmStartTime(LocalTime pmStartTime) {
        this.pmStartTime = pmStartTime;
    }

    public void setPmEndTime(LocalTime pmEndTime) {
        this.pmEndTime = pmEndTime;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setSchedulingType(String schedulingType) {
        this.schedulingType = schedulingType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public String getSchedulingType() {
        return schedulingType;
    }
}
