package cn.k12soft.servo.module.vedioMonitor.domain;

import java.util.Set;

/**
 * Created by liubing on 2018/4/3
 */
public class HKUserDTD {
    private Integer id;
    private String accountId;
    private String accountName;
    private String pwd;
    private Integer userId;
    private Integer klassId;
    private Integer stuId;
    private String stuName;
    private Integer state;
    private Long startTime;
    private String hkDeviceIds;
    private Set<HKDevice> hkDeviceSet;

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

    public Set<HKDevice> getHkDeviceSet() {
        return hkDeviceSet;
    }

    public void setHkDeviceSet(Set<HKDevice> hkDeviceSet) {
        this.hkDeviceSet = hkDeviceSet;
    }

    public String getHkDeviceIds() {
        return hkDeviceIds;
    }

    public void setHkDeviceIds(String hkDeviceIds) {
        this.hkDeviceIds = hkDeviceIds;
    }

    public static HKUserDTD create(HKUser hkUser) {
        HKUserDTD dtd = new HKUserDTD();
        dtd.setAccountId(hkUser.getAccountId());
        dtd.setAccountName(hkUser.getAccountName());
        dtd.setHkDeviceIds(hkUser.getHkDeviceIds());
        dtd.setId(hkUser.getId());
        dtd.setPwd(hkUser.getPwd());
        dtd.setKlassId(hkUser.getKlassId());
        dtd.setStartTime(hkUser.getStartTime());
        dtd.setState(hkUser.getState());
        dtd.setStuId(hkUser.getStuId());
        dtd.setStuName(hkUser.getStuName());
        dtd.setUserId(hkUser.getUserId());
        return dtd;
    }
}
