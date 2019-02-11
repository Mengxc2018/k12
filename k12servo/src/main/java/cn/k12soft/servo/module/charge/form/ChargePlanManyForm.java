package cn.k12soft.servo.module.charge.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class ChargePlanManyForm {
    @NotNull
    private String expenseName; // 费种
    @NotNull
    private String cycleId; //周期
    private String cycleType; // 身份类型周期
    @NotNull
    private String identityId; // 应用类型
    private String identityType; // 费用周期
    @NotNull
    private Integer targetType; // 应用对象类型
    @NotNull
    private String target;// 应用对象
    @NotNull
    private Instant endAt;// 截止日期
    @NotNull
    private Float money;// 金额

    @ApiModelProperty("兴趣班的id，当选择儿童并且为兴趣班时，启用此属性，其他情况请填0")
    private Integer klassInterestId;

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getCycleType() {
        return cycleType;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public String getCycleId() {
        return cycleId;
    }

    public void setCycleId(String cycleId) {
        this.cycleId = cycleId;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
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
