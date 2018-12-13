package cn.k12soft.servo.module.wxLogin.domain;

public class WxActiveDTO {

    private String username;
    private Integer count;

    public WxActiveDTO() {
    }

    public WxActiveDTO(String username, Integer count) {
        this.username = username;
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public Integer getCount() {
        return count;
    }

}
