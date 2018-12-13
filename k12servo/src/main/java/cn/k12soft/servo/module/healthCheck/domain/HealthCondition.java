package cn.k12soft.servo.module.healthCheck.domain;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.Physical;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

/**
 * 孩子健康异常表
 */
@Entity
@DynamicUpdate
@ApiModel
public class HealthCondition {

    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty("早午晚检查类型")
    @Enumerated(EnumType.STRING)
    private Physical.TYPE type;

    @ApiModelProperty("班级")
    @OneToOne
    private Klass klass;

    @OneToOne
    private Student student;

    @ApiModelProperty("症状")
    private String healthMsg;

    @ApiModelProperty("症状相片，多张请用英文逗号隔开")
    @JsonIgnore
    private String picture;

    @ApiModelProperty("医嘱")
    private String advice;

    @ApiModelProperty("备注")
    private String remark;

    private Integer schoolId;

    private Instant createdAt;

    public HealthCondition() {
    }

    public HealthCondition(Physical.TYPE type, Klass klass, Student student, String healthMsg, String picture, String advice, String remark, Integer schoolId, Instant createdAt) {
        this.type = type;
        this.klass = klass;
        this.student = student;
        this.healthMsg = healthMsg;
        this.picture = picture;
        this.advice = advice;
        this.remark = remark;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Physical.TYPE getType() {
        return type;
    }

    public void setType(Physical.TYPE type) {
        this.type = type;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getHealthMsg() {
        return healthMsg;
    }

    public void setHealthMsg(String healthMsg) {
        this.healthMsg = healthMsg;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
