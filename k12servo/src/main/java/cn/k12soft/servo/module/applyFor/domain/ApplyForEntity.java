package cn.k12soft.servo.module.applyFor.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ApplyForEntity {
    private Integer masterId;           // 审批人id
    private String masterName;          // 审批人姓名
    private String masterPortrait;      // 审批人头像
    private Integer actorId;            // 申请用户ID
    private String userName;            // 申请人姓名
    private String portrait;            // 申请人头像

    public ApplyForEntity(Integer masterId, String masterName, String masterPortrait, Integer actorId, String userName, String portrait) {
        this.masterId = masterId;
        this.masterName = masterName;
        this.masterPortrait = masterPortrait;
        this.actorId = actorId;
        this.userName = userName;
        this.portrait = portrait;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getMasterPortrait() {
        return masterPortrait;
    }

    public void setMasterPortrait(String masterPortrait) {
        this.masterPortrait = masterPortrait;
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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
