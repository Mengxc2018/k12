package cn.k12soft.servo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

/**
 * Created by liubing on 2018/7/4
 */
@Entity
public class BillingAliPayInfo {
    @Id
    private int id;
    @Column(nullable = false)
    private int studentId;
    private String billingNo; // 我们自己生成的订单号(out_trade_no)
    private String billingTitle;
    private String orderNo; //  教育缴费平台的订单号
    private int zhifubaoState; // 支付包退费状态，1=退费成功
    private int state;// 0=已发送, 1=缴费成功
    private Instant creatAt;
    private Instant payAt; // 缴费时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getBillingNo() {
        return billingNo;
    }

    public void setBillingNo(String billingNo) {
        this.billingNo = billingNo;
    }

    public String getBillingTitle() {
        return billingTitle;
    }

    public void setBillingTitle(String billingTitle) {
        this.billingTitle = billingTitle;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Instant getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(Instant creatAt) {
        this.creatAt = creatAt;
    }

    public Instant getPayAt() {
        return payAt;
    }

    public void setPayAt(Instant payAt) {
        this.payAt = payAt;
    }

    public int getZhifubaoState() {
        return zhifubaoState;
    }

    public void setZhifubaoState(int zhifubaoState) {
        this.zhifubaoState = zhifubaoState;
    }
}
