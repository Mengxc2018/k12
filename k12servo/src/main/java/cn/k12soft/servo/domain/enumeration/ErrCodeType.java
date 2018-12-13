package cn.k12soft.servo.domain.enumeration;

public enum ErrCodeType {
    ERR_4000(4000, "OK"),
    ERR_4001(4001, "用户没有被邀清"),
    ERR_4002(4002, "验证码不匹配"),
    ERR_4003(4003, "用户不存在"),
    ERR_4004(4004, "手机号已存在"),
    ERR_4005(4005, "用户未激活");

    private Integer id;
    private String errCode;

    ErrCodeType(Integer id, String errCode) {
        this.id = id;
        this.errCode = errCode;
    }

    public Integer getId() {
        return id;
    }

    public String getErrCode() {
        return errCode;
    }
}
