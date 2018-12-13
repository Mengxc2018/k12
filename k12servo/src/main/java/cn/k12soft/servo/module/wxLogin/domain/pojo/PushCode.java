package cn.k12soft.servo.module.wxLogin.domain.pojo;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.wxLogin.domain.WxUsers;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Entity
@DynamicUpdate
public class PushCode {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private WxUsers wxUsers;
    private Integer actorId;
    private Integer userId;
    private String formId;        // 推送码
    private Instant timeoutAt;  // 过期时间
    private Instant createdAt;  // 创建时间

    private PushCode(){}

    public PushCode(WxUsers wxUsers, Integer actorId, Integer userId, String formId, Instant timeoutAt, Instant createdAt) {
        this.wxUsers = wxUsers;
        this.actorId = actorId;
        this.userId = userId;
        this.formId = formId;
        this.timeoutAt = timeoutAt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WxUsers getWxUsers() {
        return wxUsers;
    }

    public void setWxUsers(WxUsers wxUsers) {
        this.wxUsers = wxUsers;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Instant getTimeoutAt() {
        return timeoutAt;
    }

    public void setTimeoutAt(Instant timeoutAt) {
        this.timeoutAt = timeoutAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
