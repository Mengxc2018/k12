package cn.k12soft.servo.module.charge.form;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

public class ChargePlanForm {
    @NotNull
    private Integer expenseId; // 费种
    @NotNull
    private Integer cycleId; //周期
    @NotNull
    private Integer identityId; // 应用类型
    @NotNull
    private Integer targetType; // 应用对象类型
    @NotNull
    private String target;// 应用对象
    @NotNull
    private Instant endAt;// 截止日期
    @NotNull
    private Float money;// 金额

    @ApiModelProperty("兴趣班的id，当选择儿童并且为兴趣班时，为兴趣班的di，其他情况请填0，默认为0")
    private Integer klassInterestId = 0;

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public Integer getCycleId() {
        return cycleId;
    }

    public void setCycleId(Integer cycleId) {
        this.cycleId = cycleId;
    }

    public Integer getIdentityId() {
        return identityId;
    }

    public void setIdentityId(Integer identityId) {
        this.identityId = identityId;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public String getTarget() {
        return target;
    }

    public Instant getEndAt() {
        return endAt;
    }

    public Float getMoney() {
        return money;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setEndAt(Instant endAt) {
        this.endAt = endAt;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getKlassInterestId() {
        return klassInterestId;
    }
}
