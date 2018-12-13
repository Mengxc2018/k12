package cn.k12soft.servo.module.vedioMonitor.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by liubing on 2018/3/31
 */
@Entity
@DynamicUpdate
public class HKDevice {
    @Id
    @GeneratedValue
    private Integer hkdevId;
    @Column(nullable = false)
    private String deviceId;
    @Column(nullable = false)
    private Integer klassId;
    @Column(nullable = false)
    private String klassName;
    @Column(nullable = false)
    private String verifyCode;
    @Column(nullable = false)
    private String deviceName;
    @Column(nullable = false)
    private String deviceCover;

    public HKDevice(){}

    public Integer getHkdevId() {
        return hkdevId;
    }

    public void setHkdevId(Integer hkdevId) {
        this.hkdevId = hkdevId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public String getKlassName() {
        return klassName;
    }

    public void setKlassName(String klassName) {
        this.klassName = klassName;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCover() {
        return deviceCover;
    }

    public void setDeviceCover(String deviceCover) {
        this.deviceCover = deviceCover;
    }
}
