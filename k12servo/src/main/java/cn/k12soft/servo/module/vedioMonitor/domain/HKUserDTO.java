package cn.k12soft.servo.module.vedioMonitor.domain;

import cn.k12soft.servo.domain.User;

import java.util.Set;

/**
 * Created by liubing on 2018/4/3
 */
public class HKUserDTO {
    private Integer id;
    private String accountId;
    private String accountName;
    private String pwd;
    private User user;
    private Integer klassId;
    private String klassName;
    private Integer stuId;
    private String stuName;
    private Integer state;
    private Long startTime;
    private Set<HKDevice> hkDevices;

    public Integer getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getPwd() {
        return pwd;
    }

    public User getUser() {
        return user;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public String getKlassName() {
        return klassName;
    }

    public Integer getStuId() {
        return stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public Integer getState() {
        return state;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Set<HKDevice> getHkDevices() {
        return hkDevices;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public void setKlassName(String klassName) {
        this.klassName = klassName;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setHkDevices(Set<HKDevice> hkDevices) {
        this.hkDevices = hkDevices;
    }
}
