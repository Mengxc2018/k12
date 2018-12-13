package cn.k12soft.servo.module.wxLogin.domain;

import cn.k12soft.servo.domain.Actor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.Instant;

@Entity
@DynamicUpdate
public class WxMsgBoard {

    @Id
    @GeneratedValue
    private Long id;
    private String massage;
    @OneToOne
    private Actor actor;
    private Integer schoolId;
    private Instant createdAt;

    public WxMsgBoard() {
    }

    public WxMsgBoard(String massage, Actor actor, Integer schoolId, Instant createdAt) {
        this.massage = massage;
        this.actor = actor;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
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
}
