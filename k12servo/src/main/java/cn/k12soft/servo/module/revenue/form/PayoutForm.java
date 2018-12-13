package cn.k12soft.servo.module.revenue.form;

import javax.validation.constraints.NotNull;

public class PayoutForm {
    @NotNull
    private Integer payoutSubTypeId;
    @NotNull
    private Float money;

    public Integer getPayoutSubTypeId() {
        return payoutSubTypeId;
    }

    public void setPayoutSubTypeId(Integer payoutSubTypeId) {
        this.payoutSubTypeId = payoutSubTypeId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
