package cn.k12soft.servo.module.charge.form;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * 费种转入
 */
public class RefundForm {
    @NotNull
    private Integer studentId;
    @NotNull
    private Integer studentChargeId; // 转出费种 studentCharge id
    @NotNull
    private Integer toExpenseId; // 转入费种
    @NotNull
    private Integer cycleId; //周期
    @NotNull
    private Integer identityId; // 应用类型(普通，业主，员工)
    @NotNull
    private Instant endAt;// 截止日期
    @NotNull
    private Float totalMoney;// 费种金额(转入费种，需要的钱)
    @NotNull
    private Float descMoney;// 扣除金额(转出费种剩余的钱)
    @NotNull
    private Float money;// 应交金额(totalMoney - descMoney), 可以为0，为0表示不用再交钱了

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentChargeId() {
        return studentChargeId;
    }

    public void setStudentChargeId(Integer studentChargeId) {
        this.studentChargeId = studentChargeId;
    }

    public Integer getToExpenseId() {
        return toExpenseId;
    }

    public void setToExpenseId(Integer toExpenseId) {
        this.toExpenseId = toExpenseId;
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

    public Instant getEndAt() {
        return endAt;
    }

    public void setEndAt(Instant endAt) {
        this.endAt = endAt;
    }

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Float getDescMoney() {
        return descMoney;
    }

    public void setDescMoney(Float descMoney) {
        this.descMoney = descMoney;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
