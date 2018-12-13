package cn.k12soft.servo.module.revenue.form;

import javax.validation.constraints.NotNull;

public class PayDetailForm {
    @NotNull
    private Integer type; // 支出类别id
    @NotNull
    private String name;//名称

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
