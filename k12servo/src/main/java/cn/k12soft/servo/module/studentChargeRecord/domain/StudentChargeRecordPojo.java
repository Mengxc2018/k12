package cn.k12soft.servo.module.studentChargeRecord.domain;

public class StudentChargeRecordPojo {

    private String otherName; // 其他费用名称
    private String otherMoney;    // 其他费用金额
    private String otherTotal;    // 其他费用总额，不计入费用总额，在此单独计算

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public void setOtherMoney(String otherMoney) {
        this.otherMoney = otherMoney;
    }

    public void setOtherTotal(String otherTotal) {
        this.otherTotal = otherTotal;
    }
}
