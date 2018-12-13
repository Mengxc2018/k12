package cn.k12soft.servo.module.wxLogin.domain.pojo;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class WxPojo {

    private String openid;    // 微信openid
    private String session_key;    // 微信session_key

    public WxPojo(String openid, String session_key) {
        this.openid = openid;
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
}

