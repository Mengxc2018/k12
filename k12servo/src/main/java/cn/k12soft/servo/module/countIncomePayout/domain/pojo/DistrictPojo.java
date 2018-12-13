package cn.k12soft.servo.module.countIncomePayout.domain.pojo;

public class DistrictPojo {

    private String districtName;    // 地区名
    private String code;            // 地区code
    private String incomeRate;      // 收入率
    private String payoutRate;      // 支出率

    public DistrictPojo(){}

    public DistrictPojo(String districtName, String code, String incomeRate, String payoutRate) {
        this.districtName = districtName;
        this.code = code;
        this.incomeRate = incomeRate;
        this.payoutRate = payoutRate;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getCode() {
        return code;
    }

    public String getIncomeRate() {
        return incomeRate;
    }

    public String getPayoutRate() {
        return payoutRate;
    }
}
