package cn.k12soft.servo.module.wxLogin.domain;

import cn.k12soft.servo.domain.enumeration.ActiveSourceType;
import cn.k12soft.servo.domain.enumeration.WxActiveType;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

/**
 * 微信 活跃度统计
 */
@Entity
@DynamicUpdate
public class WxActive {

    @Id
    @GeneratedValue
    private Long id;
    private Integer actorId;
    private String userName;
    private Instant createdAt;
    private Integer schoolId;
    @Enumerated(EnumType.STRING)
    private WxActiveType wxActiveType;  // 访问类型
    @Enumerated(EnumType.STRING)
    private ActiveSourceType activeSourceType;  // 来源，app、小程序


    public WxActive() {
    }

    public WxActive(Integer actorId, String userName, Instant createdAt, Integer schoolId, WxActiveType wxActiveType, ActiveSourceType activeSourceType) {
        this.actorId = actorId;
        this.userName = userName;
        this.createdAt = createdAt;
        this.schoolId = schoolId;
        this.wxActiveType = wxActiveType;
        this.activeSourceType = activeSourceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public WxActiveType getWxActiveType() {
        return wxActiveType;
    }

    public void setWxActiveType(WxActiveType wxActiveType) {
        this.wxActiveType = wxActiveType;
    }

    public ActiveSourceType getActiveSourceType() {
        return activeSourceType;
    }

    public void setActiveSourceType(ActiveSourceType activeSourceType) {
        this.activeSourceType = activeSourceType;
    }
}
