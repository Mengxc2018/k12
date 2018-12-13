package cn.k12soft.servo.module.revenue.domain;

/**
 * Created by liubing on 2018/3/21
 */
public class RevenueSummary {
    private int date; //时间
    private Float revenueOut; //支出
    private Float revenueIn; //收入

    public RevenueSummary(int date){
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    public Float getRevenueOut() {
        return revenueOut;
    }

    public void setRevenueOut(Float revenueOut) {
        this.revenueOut = revenueOut;
    }

    public Float getRevenueIn() {
        return revenueIn;
    }

    public void setRevenueIn(Float revenueIn) {
        this.revenueIn = revenueIn;
    }
}
