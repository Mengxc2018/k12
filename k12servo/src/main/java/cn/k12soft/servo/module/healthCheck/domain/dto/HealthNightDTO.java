package cn.k12soft.servo.module.healthCheck.domain.dto;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.Physical;

import java.time.Instant;

/**
 * 晚检
 */
public class HealthNightDTO {
    private Long id;
    private Student student;
    private Instant createdAt;
    private Integer klassId;
    private Integer schoolId;
    private String klassName;
    private Physical.TYPE type;  // 早中晚类型
    private Physical.SPIRIT spirit;  // 精神
    private Physical.BODY body;      // 身体
    private Physical.SINK sink;      // 皮肤
    private Physical.ADDFOOD addfood;    // 加餐
    private Physical.EXCRETE excrete;    // 大小便排泄
    private String remark;      // 备注

    public HealthNightDTO(Long id, Student student, Integer klassId, String klassName, Integer schoolId, Instant createdAt, Physical.TYPE type, Physical.SPIRIT spirit, Physical.BODY body, Physical.SINK sink, Physical.ADDFOOD addfood, Physical.EXCRETE excrete, String remark) {
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
        this.addfood = addfood;
        this.excrete = excrete;
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

    public Physical.ADDFOOD getAddfood() {
        return addfood;
    }

    public Physical.EXCRETE getExcrete() {
        return excrete;
    }

    public String getRemark() {
        return remark;
    }
}
