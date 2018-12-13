package cn.k12soft.servo.module.feeCollect.domain;

import cn.k12soft.servo.domain.enumeration.FeeKindType;
import cn.k12soft.servo.domain.enumeration.FeeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

/**
 * 视频收费、图书教材收费、课程收费等账单
 */
@ApiModel
@Entity
@DynamicUpdate
public class FeeCollect {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private Float money;

    @ApiModelProperty("收费范围，比如视频收费、图书收费等")
    @Enumerated(EnumType.STRING)
    private FeeKindType scopt;   // 收费范围、收费种类

    @ApiModelProperty("收费方式")
    @Enumerated(EnumType.STRING)
    private FeeType feeType;    // 收费方式

    @ApiModelProperty("收费明细id")
    private Integer msgId;      // 收费详情id

    private Instant createdAt;

    public FeeCollect() {
    }

    public FeeCollect(String name, Float money, FeeKindType scopt, FeeType feeType, Integer msgId, Instant createdAt) {
        this.name = name;
        this.money = money;
        this.scopt = scopt;
        this.feeType = feeType;
        this.msgId = msgId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public FeeKindType getScopt() {
        return scopt;
    }

    public void setScopt(FeeKindType scopt) {
        this.scopt = scopt;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
