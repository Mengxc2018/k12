package cn.k12soft.servo.module.visit.domain;

import cn.k12soft.servo.domain.enumeration.IsWill;
import cn.k12soft.servo.module.visitSchool.domain.VisitSchool;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

/**
 * 客户回访记录表
 */
@Entity
@DynamicUpdate
public class VisitParents {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private VisitSchool visitSchool;        // 父id
    private String teacherName;     // 老师姓名
    private String parentsName;     // 家长姓名
    private String mobile;          // 家长电话
    private String content;           // 回访内容
    @Enumerated(EnumType.STRING)
    private IsWill isWill;          // 是否同意入园
    private Instant createdAt;

    public VisitParents(){}

    public VisitParents(VisitSchool visitSchool, String teacherName, String parentsName, String mobile, String content, IsWill isWill, Instant createdAt) {
        this.visitSchool = visitSchool;
        this.teacherName = teacherName;
        this.parentsName = parentsName;
        this.mobile = mobile;
        this.content = content;
        this.isWill = isWill;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VisitSchool getVisitSchool() {
        return visitSchool;
    }

    public void setVisitSchool(VisitSchool visitSchool) {
        this.visitSchool = visitSchool;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getParentsName() {
        return parentsName;
    }

    public void setParentsName(String parentsName) {
        this.parentsName = parentsName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public IsWill getIsWill() {
        return isWill;
    }

    public void setIsWill(IsWill isWill) {
        this.isWill = isWill;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
