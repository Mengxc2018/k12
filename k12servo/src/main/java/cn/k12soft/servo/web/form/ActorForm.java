package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;

/**
 * Created by liubing on 2018/7/24
 */
public class ActorForm {
    @NotNull
    private int type;
    @NotNull
    private String name;
    @NotNull
    private String mobile;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }
}
