package cn.k12soft.servo.module.wxLogin.domain;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@DynamicUpdate
public class WxUsers {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String mobile;  // 手机号
    @NotNull
    private String openid;    // 微信openid
    private String session_key;    // 微信session_key
    @OneToOne
    private User user;
    @OneToMany
    private List<Actor> actor;

    public WxUsers(){}

    public WxUsers(String mobile, String openid, String session_key, User user, List<Actor> actor) {
        this.mobile = mobile;
        this.openid = openid;
        this.session_key = session_key;
        this.user = user;
        this.actor = actor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Actor> getActor() {
        return actor;
    }

    public void setActor(List<Actor> actor) {
        this.actor = actor;
    }
}

