package cn.k12soft.servo.module.revenue.domain;

/**
 * Created by liubing on 2018/3/20
 */
public enum IncomeSrc {
    MONTHLY_DEDUCT(0, "每月自动扣除收费计划余额"),
    PAY_DEDUCT(1, "缴费是补交费用"),
    ;

    IncomeSrc(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){return this.name;}

    private int id;
    private String name;
}
