package cn.k12soft.servo.module.wxLogin.domain;

import cn.k12soft.servo.domain.enumeration.WxSendType;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@DynamicUpdate
public class WxPushMsg {

    @Id
    @GeneratedValue
    private Long id;
    private String massage; // 推送消息
    private Integer actorId;
    private Integer klassId;
    private WxSendType wxSendType;
    private Integer schoolId;
    private Instant createAt;

    public WxPushMsg(){

    }

    public WxPushMsg(String massage, Integer actorId, Integer klassId, WxSendType wxSendType, Integer schoolId, Instant createAt) {
        this.massage = massage;
        this.actorId = actorId;
        this.klassId = klassId;
        this.wxSendType = wxSendType;
        this.schoolId = schoolId;
        this.createAt = createAt;
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

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public WxSendType getWxSendType() {
        return wxSendType;
    }

    public void setWxSendType(WxSendType wxSendType) {
        this.wxSendType = wxSendType;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "WxPushMsg{" +
                "id=" + id +
                ", massage='" + massage + '\'' +
                ", actorId=" + actorId +
                ", klassId=" + klassId +
                ", schoolId=" + schoolId +
                ", createAt=" + createAt +
                '}';
    }
}
