package cn.k12soft.servo.module.healthCheck.domain;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.Physical.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@DynamicUpdate
public class HealthCheck {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @NotNull
    private Student student;
    @NotNull
    private Integer klassId;
    @NotNull
    private Integer schoolId;
    @Enumerated(EnumType.STRING)
    private TYPE type;  // 早中晚类型

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HealthCondition healthCondition;

    private SPIRIT spirit;  // 精神
    private BODY body;      // 身体
    private SINK sink;      // 皮肤
    private DINNER dinner;  // 正餐
    private AFTERNAP afternap;  // 午睡
    private ADDFOOD addfood;    // 加餐
    private EXCRETE excrete;    // 大小便排泄
    private MOUTH mouth;        // 口腔
    private OTHER other;
    private float temperature;  // 体温
    private String remark;      // 备注
    private boolean issue;      // 是否发布
    private Instant createdAt;

    public HealthCheck(){}

    // 早检
    public HealthCheck(Student student, Instant createdAt, Integer klassId, Integer schoolId, TYPE type, SPIRIT spirit, BODY body, SINK sink, MOUTH mouth, OTHER other, float temperature, String remark, boolean issue) {
        this.student = student;
        this.klassId = klassId;
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
        this.issue = issue;
    }
    // 午检
    public HealthCheck(Student student, Integer klassId, Integer schoolId, Instant createdAt, TYPE type, SPIRIT spirit, BODY body, SINK sink, DINNER dinner, AFTERNAP afternap, String remark, boolean issue) {
        this.student = student;
        this.klassId = klassId;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
        this.type = type;
        this.spirit = spirit;
        this.body = body;
        this.sink = sink;
        this.dinner = dinner;
        this.afternap = afternap;
        this.remark = remark;
        this.issue = issue;
    }
    // 晚检
    public HealthCheck(Student student, Integer klassId, Integer schoolId, Instant createdAt, TYPE type, SPIRIT spirit, BODY body, SINK sink, ADDFOOD addfood, EXCRETE excrete, String remark, boolean issue) {
        this.student = student;
        this.klassId = klassId;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
        this.type = type;
        this.spirit = spirit;
        this.body = body;
        this.sink = sink;
        this.addfood = addfood;
        this.excrete = excrete;
        this.remark = remark;
        this.issue = issue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public boolean getIsIssue() {
        return issue;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public SPIRIT getSpirit() {
        return spirit;
    }

    public void setSpirit(SPIRIT spirit) {
        this.spirit = spirit;
    }

    public BODY getBody() {
        return body;
    }

    public void setBody(BODY body) {
        this.body = body;
    }

    public SINK getSink() {
        return sink;
    }

    public void setSink(SINK sink) {
        this.sink = sink;
    }

    public DINNER getDinner() {
        return dinner;
    }

    public void setDinner(DINNER dinner) {
        this.dinner = dinner;
    }

    public AFTERNAP getAfternap() {
        return afternap;
    }

    public void setAfternap(AFTERNAP afternap) {
        this.afternap = afternap;
    }

    public ADDFOOD getAddfood() {
        return addfood;
    }

    public void setAddfood(ADDFOOD addfood) {
        this.addfood = addfood;
    }

    public EXCRETE getExcrete() {
        return excrete;
    }

    public void setExcrete(EXCRETE excrete) {
        this.excrete = excrete;
    }

    public MOUTH getMouth() {
        return mouth;
    }

    public void setMouth(MOUTH mouth) {
        this.mouth = mouth;
    }

    public OTHER getOther() {
        return other;
    }

    public void setOther(OTHER other) {
        this.other = other;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setIssue(boolean issue){
        this.issue = issue;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public HealthCondition getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(HealthCondition healthCondition) {
        this.healthCondition = healthCondition;
    }

    @Override
    public String toString() {
        return "HealthCheck{" +
                "id=" + id +
                ", student=" + student +
                ", klassId=" + klassId +
                ", schoolId=" + schoolId +
                ", type=" + type +
                ", spirit=" + spirit +
                ", body=" + body +
                ", sink=" + sink +
                ", dinner=" + dinner +
                ", afternap=" + afternap +
                ", addfood=" + addfood +
                ", excrete=" + excrete +
                ", mouth=" + mouth +
                ", other=" + other +
                ", temperature=" + temperature +
                ", remark='" + remark + '\'' +
                ", issue=" + issue +
                ", createdAt=" + createdAt +
                '}';
    }
}
