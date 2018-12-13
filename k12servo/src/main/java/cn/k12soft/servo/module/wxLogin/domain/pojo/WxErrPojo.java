package cn.k12soft.servo.module.wxLogin.domain.pojo;

public class WxErrPojo {
    private String errcode;
    private String errmsg;

    public WxErrPojo(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    @Override
    public String toString() {
        return "WxErrPojo{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
