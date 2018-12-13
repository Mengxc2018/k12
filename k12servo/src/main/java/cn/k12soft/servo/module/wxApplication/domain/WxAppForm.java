package cn.k12soft.servo.module.wxApplication.domain;

public class WxAppForm {

    private Long id;
    private String secret;
    private String appid;
    private String grantType;

    public Long getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getAppid() {
        return appid;
    }

    public String getGrantType() {
        return grantType;
    }
}
