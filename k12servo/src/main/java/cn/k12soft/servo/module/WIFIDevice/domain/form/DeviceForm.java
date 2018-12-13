package cn.k12soft.servo.module.WIFIDevice.domain.form;

public class DeviceForm {
    private String mac; // 设备mac地址
    private String ssrd;     // 设备ssrd
    private String dIP;      // 设备IP
    private String name;     // 设备名称

    public String getMac() {
        return mac;
    }

    public String getSsrd() {
        return ssrd;
    }

    public String getdIP() {
        return dIP;
    }

    public String getName() {
        return name;
    }
}
