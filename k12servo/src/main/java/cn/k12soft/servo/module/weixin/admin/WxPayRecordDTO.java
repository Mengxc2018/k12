package cn.k12soft.servo.module.weixin.admin;

import cn.k12soft.servo.domain.enumeration.FeeType;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

public class WxPayRecordDTO {

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

    public WxPayRecordDTO() {
    }

    public WxPayRecordDTO(FeeType feeType, float totalFee, String openId, String outTradeNo, String transactionId, String timeEnd, Instant createAt) {
        this.feeType = feeType;
        this.totalFee = totalFee;
        this.openId = openId;
        this.outTradeNo = outTradeNo;
        this.transactionId = transactionId;
        this.timeEnd = timeEnd;
        this.createAt = createAt;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public float getTotalFee() {
        return totalFee;
    }

    public String getOpenId() {
        return openId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public Instant getCreateAt() {
        return createAt;
    }
}
