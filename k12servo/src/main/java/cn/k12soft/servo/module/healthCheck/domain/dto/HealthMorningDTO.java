package cn.k12soft.servo.module.healthCheck.domain.dto;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.Physical;

import java.time.Instant;

/**
 * 晨检
 */
public class HealthMorningDTO {
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
    private Physical.MOUTH mouth;        // 口腔
    private Physical.OTHER other;
    private float temperature;  // 体温
    private String remark;      // 备注

    public HealthMorningDTO(Long id, Student student, Integer klassId, String klassName, Integer schoolId, Instant createdAt, Physical.TYPE type, Physical.SPIRIT spirit, Physical.BODY body, Physical.SINK sink, Physical.MOUTH mouth, Physical.OTHER other, float temperature, String remark) {
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
        this.mouth = mouth;
        this.other = other;
        this.temperature = temperature;
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

    public Physical.MOUTH getMouth() {
        return mouth;
    }

    public Physical.OTHER getOther() {
        return other;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public void setKlassName(String klassName) {
        this.klassName = klassName;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setType(Physical.TYPE type) {
        this.type = type;
    }

    public void setSpirit(Physical.SPIRIT spirit) {
        this.spirit = spirit;
    }

    public void setBody(Physical.BODY body) {
        this.body = body;
    }

    public void setSink(Physical.SINK sink) {
        this.sink = sink;
    }

    public void setMouth(Physical.MOUTH mouth) {
        this.mouth = mouth;
    }

    public void setOther(Physical.OTHER other) {
        this.other = other;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
