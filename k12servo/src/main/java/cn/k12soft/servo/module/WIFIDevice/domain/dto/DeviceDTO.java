package cn.k12soft.servo.module.WIFIDevice.domain.dto;

public class DeviceDTO {
    private Long id;
    private String mac; // 设备mac地址
    private String ssrd;     // 设备ssrd
    private String dIP;      // 设备IP
    private String name;     // 设备名称
    private Integer schoolid;

    public DeviceDTO(Long id, String mac, String ssrd, String dIP, String name,Integer schoolid) {
        this.id = id;
        this.mac = mac;
        this.ssrd = ssrd;
        this.dIP = dIP;
        this.name = name;
        this.schoolid = schoolid;
    }

    public Long getId() {
        return id;
    }

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

    public Integer getSchoolid() {
        return schoolid;
    }
}
