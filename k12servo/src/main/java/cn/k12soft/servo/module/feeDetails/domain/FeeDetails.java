package cn.k12soft.servo.module.feeDetails.domain;

import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.FeeKindType;
import cn.k12soft.servo.domain.enumeration.FeeType;
import cn.k12soft.servo.module.feeDetails.service.FeeDetailsService;
import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

/**
 * 收费范围详情
 */
@Entity
@DynamicUpdate
public class FeeDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToOne
    private User userKlient;    // 客户信息
    private Integer stuId;
    private String stuName;
    private Float money;
    @Enumerated(EnumType.STRING)
    private FeeKindType scopt;   // 收费范围、收费种类
    @Enumerated(EnumType.STRING)
    private FeeType feeType;    // 收费方式
    @Enumerated(EnumType.STRING)
    private FeePeriodType feePeriodtype;    // 收费周期
    @Enumerated(EnumType.STRING)
    private FeeDetailsService.RENEW renew;  // PAY:已缴费 RENEW:续费
    private Instant startTime;  // 有效开始时间
    private Instant endTime;    // 有效结束时间
    private boolean state;      // 是否授权
    private Integer schoolId;
    private Instant createdAt;     // 创建时间
    private Integer createdBy;    // 创建人

    public FeeDetails() {
    }

    public FeeDetails(String name, User userKlient, Integer stuId, String stuName, Float money, FeeKindType scopt, FeeType feeType, FeePeriodType feePeriodtype, FeeDetailsService.RENEW renew, Instant startTime, Instant endTime, boolean state, Integer schoolId, Instant createdAt, Integer createdBy) {
        this.name = name;
        this.userKlient = userKlient;
        this.stuId = stuId;
        this.stuName = stuName;
        this.money = money;
        this.scopt = scopt;
        this.feeType = feeType;
        this.feePeriodtype = feePeriodtype;
        this.renew = renew;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
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

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
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

    public boolean getIsState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public FeeDetailsService.RENEW getRenew() {
        return renew;
    }

    public void setRenew(FeeDetailsService.RENEW renew) {
        this.renew = renew;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "FeeDetails{" +
                "id=" + id +
                ", userKlient=" + userKlient +
                ", stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                ", money=" + money +
                ", scopt=" + scopt +
                ", feeType=" + feeType +
                ", feePeriodtype=" + feePeriodtype +
                ", renew=" + renew +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", schoolId=" + schoolId +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                '}';
    }
}