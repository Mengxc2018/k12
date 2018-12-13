package cn.k12soft.servo.module.healthCheck.domain.dto;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.Physical;

import java.time.Instant;

public class HealthNoonDTO {
    private Long id;
    private Student student;
    private Integer klassId;
    private Integer schoolId;
    private String klassName;
    private Instant createdAt;
    private Physical.TYPE type;  // 早中晚类型
    private Physical.SPIRIT spirit;  // 精神
    private Physical.BODY body;      // 身体
    private Physical.SINK sink;      // 皮肤
    private Physical.DINNER dinner;  // 正餐
    private Physical.AFTERNAP afternap;  // 午睡
    private String remark;      // 备注

    public HealthNoonDTO(Long id, Student student, Integer klassId, String klassName, Integer schoolId, Instant createdAt, Physical.TYPE type, Physical.SPIRIT spirit, Physical.BODY body, Physical.SINK sink, Physical.DINNER dinner, Physical.AFTERNAP afternap, String remark) {
        this.id = id;
        this.student = student;
        this.klassId = klassId;
        this.klassName = klassName;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
        this.type = type;
        this.spirit = spirit;
        this.body = body;
        this.sink = sink;
        this.dinner = dinner;
        this.afternap = afternap;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public String getKlassName() {
        return klassName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Physical.TYPE getType() {
        return type;
    }

    public Physical.SPIRIT getSpirit() {
        return spirit;
    }

    public Physical.BODY getBody() {
        return body;
    }

    public Physical.SINK getSink() {
        return sink;
    }

    public Physical.DINNER getDinner() {
        return dinner;
    }

    public Physical.AFTERNAP getAfternap() {
        return afternap;
    }

    public String getRemark() {
        return remark;
    }
}
