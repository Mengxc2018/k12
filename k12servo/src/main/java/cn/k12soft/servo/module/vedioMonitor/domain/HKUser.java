package cn.k12soft.servo.module.vedioMonitor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.Set;

/**
 * Created by liubing on 2018/4/1
 */
@Entity
@DynamicUpdate
public class HKUser {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String accountId;
    @Column(nullable = false)
    private String accountName;
    @Column(nullable = false)
    private String pwd;
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private Integer klassId;
    @Column(nullable = false)
    private Integer stuId;
    @Column(nullable = false)
    private String stuName;
    @Column(nullable = false)
    private Integer state;  // 1、授权 0、未授权
    @Column(nullable = false)
    private Long startTime;
    @Column(nullable = false)
    private String hkDeviceIds;
    @Column(nullable = false)
    private Instant createAt;
    private Integer schoolId;
    @JsonIgnore
    private String hkToken;
    @JsonIgnore
    private Instant expireTime; // token过期时间

//    private Set<HKDevice> hkDeviceSet;

    public HKUser(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public String getHkDeviceIds() {
        return hkDeviceIds;
    }

    public void setHkDeviceIds(String hkDeviceIds) {
        this.hkDeviceIds = hkDeviceIds;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
//    public void setHkDeviceSet(Set<HKDevice> phkDeviceSet) {
//        this.hkDeviceSet = phkDeviceSet;
//    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public String getHkToken() {
        return hkToken;
    }

    public HKUser setHkToken(String hkToken) {
        this.hkToken = hkToken;
        return this;
    }

    public HKUser setExpireTime(Instant expireTime) {
        this.expireTime = expireTime;
        return this;
    }
}
