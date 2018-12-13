package cn.k12soft.servo.module.vedioMonitor.form;

/**
 * Created by liubing on 2018/3/31
 */
public class HKDeviceForm {
    private String deviceId;
    private Integer klassId;
    private String verifyCode;
    private String deviceName;
    private String deviceCover;

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
