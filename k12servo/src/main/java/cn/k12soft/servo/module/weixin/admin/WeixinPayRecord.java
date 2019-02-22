package cn.k12soft.servo.module.weixin.admin;

import cn.k12soft.servo.domain.enumeration.FeeType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@DynamicUpdate
public class WeixinPayRecord {

    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty("支付方式，默认微信支付")
    @Enumerated(EnumType.STRING)
    private FeeType feeType = FeeType.WX;

    @ApiModelProperty("订单金额")
    private float totalFee;

    @ApiModelProperty("用户在商户appid下的唯一标识")
    private String openId;

    @ApiModelProperty("商户订单号")
    private String outTradeNo;

    @ApiModelProperty("微信支付订单号")
    private String transactionId;

    @ApiModelProperty("支付完成时间，格式为yyyyMMddHHmmss")
    private String timeEnd;

    private Instant createAt;

    public WeixinPayRecord() {
    }

    public WeixinPayRecord(float totalFee, String openId, String outTradeNo, String transactionId, String timeEnd, Instant createAt) {
        this.totalFee = totalFee;
        this.openId = openId;
        this.outTradeNo = outTradeNo;
        this.transactionId = transactionId;
        this.timeEnd = timeEnd;
        this.createAt = createAt;
    }
    public WeixinPayRecord(FeeType feeType, float totalFee, String openId, String outTradeNo, String transactionId, String timeEnd, Instant createAt) {
        this.feeType = feeType;
        this.totalFee = totalFee;
        this.openId = openId;
        this.outTradeNo = outTradeNo;
        this.transactionId = transactionId;
        this.timeEnd = timeEnd;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(float totalFee) {
        this.totalFee = totalFee;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }
}
