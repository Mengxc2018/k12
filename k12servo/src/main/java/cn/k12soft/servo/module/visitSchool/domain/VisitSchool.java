package cn.k12soft.servo.module.visitSchool.domain;

import cn.k12soft.servo.domain.enumeration.IsVisit;
import cn.k12soft.servo.domain.enumeration.IsWill;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@DynamicUpdate
public class VisitSchool {
    @Id
    @GeneratedValue
    private Long id;
    private String parentName;  // 家长姓名
    private String mobile;      // 家长手机号
    private String babyName;    // 宝宝姓名
    private String babyAge;     // 宝宝年龄
    private Integer schoolId;
    private Instant createdAt;  // 创建时间
    @Enumerated(EnumType.STRING)
    private IsVisit isVisit;    // 家长是否被接待
    @Enumerated(EnumType.STRING)
    private IsWill isWill;      // 最终意愿，是否愿意

    public VisitSchool(){}

    public VisitSchool(String parentName, String mobile, String babyName, String babyAge, Integer schoolId, Instant createdAt, IsVisit isVisit, IsWill isWill) {
        this.parentName = parentName;
        this.mobile = mobile;
        this.babyName = babyName;
        this.babyAge = babyAge;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
        this.isVisit = isVisit;
        this.isWill = isWill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getBabyAge() {
        return babyAge;
    }

    public void setBabyAge(String babyAge) {
        this.babyAge = babyAge;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public IsVisit getIsVisit() {
        return isVisit;
    }

    public void setIsVisit(IsVisit isVisit) {
        this.isVisit = isVisit;
    }

    public IsWill getIsWill() {
        return isWill;
    }

    public void setIsWill(IsWill isWill) {
        this.isWill = isWill;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
}
