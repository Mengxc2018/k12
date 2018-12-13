package cn.k12soft.servo.module.wxApplication.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WxApplication {

    @Id
    @GeneratedValue
    private Long id;
    private String secret;
    private String appid;
    private String grantType = "authorization_code";

    public WxApplication(){}

    public WxApplication(String secret, String appid, String grantType) {
        this.secret = secret;
        this.appid = appid;
        this.grantType = grantType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
