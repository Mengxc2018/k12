package cn.k12soft.servo.module.feeDetails.domain;

import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.FeeKindType;
import cn.k12soft.servo.domain.enumeration.FeeType;
import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@DynamicUpdate
public class FeeDetailsLog {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User userKlient;    // 客户信息
    private Integer stuId;
    private String stuName;
    private float money;    // 收费金额
    private FeeKindType scopt;   // 收费范围
    @Enumerated(EnumType.STRING)
    private FeeType feeType;    // 收费方式
    @Enumerated(EnumType.STRING)
    private FeePeriodType feePeriodtype;    // 收费类型
    private Instant startTime;  // 有效开始时间
    private Instant oldEndTime;    // 续费前有效结束时间
    private Instant endTimeReal;    // 续费后实际结束时间
    private Integer schoolId;
    private Instant createdAt;     // 创建时间
    private Integer createdBy;    // 创建人

    public FeeDetailsLog() {
    }

    public FeeDetailsLog(User userKlient, Integer stuId, String stuName, float money, FeeKindType scopt, FeeType feeType, FeePeriodType feePeriodtype, Instant startTime, Instant oldEndTime, Instant endTimeReal, Integer schoolId, Instant createdAt, Integer createdBy) {
        this.userKlient = userKlient;
        this.stuId = stuId;
        this.stuName = stuName;
        this.money = money;
        this.scopt = scopt;
        this.feeType = feeType;
        this.feePeriodtype = feePeriodtype;
        this.startTime = startTime;
        this.oldEndTime = oldEndTime;
        this.endTimeReal = endTimeReal;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserKlient() {
        return userKlient;
    }

    public void setUserKlient(User userKlient) {
        this.userKlient = userKlient;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public FeeKindType getScopt() {
        return scopt;
    }

    public void setScopt(FeeKindType scopt) {
        this.scopt = scopt;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public FeePeriodType getFeePeriodtype() {
        return feePeriodtype;
    }

    public void setFeePeriodtype(FeePeriodType feePeriodtype) {
        this.feePeriodtype = feePeriodtype;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getOldEndTime() {
        return oldEndTime;
    }

    public void setOldEndTime(Instant oldEndTime) {
        this.oldEndTime = oldEndTime;
    }

    public Instant getEndTimeReal() {
        return endTimeReal;
    }

    public void setEndTimeReal(Instant endTimeReal) {
        this.endTimeReal = endTimeReal;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
}
