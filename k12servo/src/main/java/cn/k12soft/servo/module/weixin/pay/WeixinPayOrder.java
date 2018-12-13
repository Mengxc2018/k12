package cn.k12soft.servo.module.weixin.pay;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by liubing on 2018/9/4
 */
@Entity
public class WeixinPayOrder {
    @Id
    @GeneratedValue
    private int id;
    private String openId;
    private String orderId;
    private String prePayId;//预支付交易会话标识, 下单成功后，微信返回来的
    private int totalFee; // 支付金额，单位分
    private int state;//0=发起支付
    private long createTime;

    public WeixinPayOrder(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
