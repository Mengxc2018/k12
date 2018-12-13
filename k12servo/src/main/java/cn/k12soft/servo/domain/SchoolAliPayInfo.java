package cn.k12soft.servo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by liubing on 2018/7/4
 */
@Entity
public class SchoolAliPayInfo{
    @Id
    @GeneratedValue
    private int id;
    private int schoolId;
    private String userAppId; // 授权商户 appId
    private String userId; // 幼儿园 授权商户id
    private String appAuthToken;// 商户授权令牌
    private String appRefreshToken;// 刷新令牌时使用
    private String schoolNo;

    public SchoolAliPayInfo(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public String getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(String userAppId) {
        this.userAppId = userAppId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppAuthToken() {
        return appAuthToken;
    }

    public void setAppAuthToken(String appAuthToken) {
        this.appAuthToken = appAuthToken;
    }

    public String getAppRefreshToken() {
        return appRefreshToken;
    }

    public void setAppRefreshToken(String appRefreshToken) {
        this.appRefreshToken = appRefreshToken;
    }
}
