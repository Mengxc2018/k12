package cn.k12soft.servo.module.WIFIDevice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Device {

    @Id
    @GeneratedValue
    private Long id;
    private String mac; // 设备mac地址
    private String ssrd;     // 设备ssrd
    private String dIP;      // 设备IP
    private String name;     // 设备名称
    private Integer schoolId;

    public Device(){}

    public Device(Long id, String mac, String ssrd, String dIP, String name, Integer schoolId) {
        this.id = id;
        this.mac = mac;
        this.ssrd = ssrd;
        this.dIP = dIP;
        this.name = name;
        this.schoolId = schoolId;
    }
    public Device(String mac, String ssrd, String dIP, String name, Integer schoolId) {
        this.mac = mac;
        this.ssrd = ssrd;
        this.dIP = dIP;
        this.name = name;
        this.schoolId = schoolId;
    }


    public Long getId() {
        return id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSsrd() {
        return ssrd;
    }

    public void setSsrd(String ssrd) {
        this.ssrd = ssrd;
    }

    public String getdIP() {
        return dIP;
    }

    public void setdIP(String dIP) {
        this.dIP = dIP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSchoolid() {
        return schoolId;
    }

    public void setSchoolid(Integer schoolId) {
        this.schoolId = schoolId;
    }
}
