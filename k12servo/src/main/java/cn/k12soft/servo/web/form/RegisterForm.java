package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;

public class RegisterForm {

    @NotNull
    private String mobile;

    @NotNull
    private String code;

    public String getMobile() {
        return mobile;
    }

    public String getCode() {
        return code;
    }
}
