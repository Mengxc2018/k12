package cn.k12soft.servo.module.attendanceCount.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

public class AttendTimeDTO {
    private Integer id;            // id
    private Integer actorId;     // 员工id
    private Instant amStartTime;    // 上午签到时间
    private Instant amEndTime;      // 上午签退时间
    private Instant pmStartTime;    // 下午签到时间
    private Instant pmEndTime;      // 下午签退时间
//    private Instant createdAt;     // 创建时间
    private Long createdAt;     // 创建时间
    private Collection<VacaTimeDTO> vacaList;  // 补签
    @JsonIgnore
    private Integer count;

    public AttendTimeDTO(){}

    public AttendTimeDTO(Integer id, Integer actorId, Instant amStartTime, Instant amEndTime, Instant pmStartTime, Instant pmEndTime, Long createdAt, Collection<VacaTimeDTO> vacaList,Integer count) {
        this.id = id;
        this.actorId = actorId;
        this.amStartTime = amStartTime;
        this.amEndTime = amEndTime;
        this.pmStartTime = pmStartTime;
        this.pmEndTime = pmEndTime;
        this.createdAt = createdAt;
        this.vacaList = vacaList;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public Instant getAmStartTime() {
        return amStartTime;
    }

    public void setAmStartTime(Instant amStartTime) {
        this.amStartTime = amStartTime;
    }

    public Instant getAmEndTime() {
        return amEndTime;
    }

    public void setAmEndTime(Instant amEndTime) {
        this.amEndTime = amEndTime;
    }

    public Instant getPmStartTime() {
        return pmStartTime;
    }

    public void setPmStartTime(Instant pmStartTime) {
        this.pmStartTime = pmStartTime;
    }

    public Instant getPmEndTime() {
        return pmEndTime;
    }

    public void setPmEndTime(Instant pmEndTime) {
        this.pmEndTime = pmEndTime;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Collection<VacaTimeDTO> getVacaList() {
        return vacaList;
    }

    public void setVacaList(Collection<VacaTimeDTO> vacaList) {
        this.vacaList = vacaList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
