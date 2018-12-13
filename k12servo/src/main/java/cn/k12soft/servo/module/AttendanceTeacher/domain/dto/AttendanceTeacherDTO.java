package cn.k12soft.servo.module.AttendanceTeacher.domain.dto;

import java.time.Instant;

public class AttendanceTeacherDTO {

    private Long id;
    private float temperature;      // 体温
    private String portrait;        // 签到半身像
    private Instant signAt;         // 打卡时间
    private Instant createdAt;      // 记录创建时间

    public AttendanceTeacherDTO(Long id, float temperature,String portrait, Instant signAt, Instant createdAt) {
        this.id = id;
        this.temperature = temperature;
        this.portrait = portrait;
        this.signAt = signAt;
        this.createdAt = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setSignAt(Instant signAt) {
        this.signAt = signAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Instant getSignAt() {
        return signAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }


    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
