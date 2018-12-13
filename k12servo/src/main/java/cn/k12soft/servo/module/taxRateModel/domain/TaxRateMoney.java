package cn.k12soft.servo.module.taxRateModel.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "tax_rate_money")
@DynamicUpdate
public class TaxRateMoney {

    @Id
    @GeneratedValue
    private Long id;
    private Float passManey;    // 超过
    private Float toManey;      // 至
    private Float taxRate;      // 税率
    private Float theNo;        // 扣除数

    @ManyToOne
    private TaxRateModel taxRateModel;

    public TaxRateMoney() {
    }

    public TaxRateMoney(Float passManey, Float toManey, Float taxRate, Float theNo) {
        this.passManey = passManey;
        this.toManey = toManey;
        this.taxRate = taxRate;
        this.theNo = theNo;
    }

    public TaxRateModel getTaxRateModel() {
        return taxRateModel;
    }

    public void setTaxRateModel(TaxRateModel taxRateModel) {
        this.taxRateModel = taxRateModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPassManey() {
        return passManey;
    }

    public void setPassManey(Float passManey) {
        this.passManey = passManey;
    }

    public Float getToManey() {
        return toManey;
    }

    public void setToManey(Float toManey) {
        this.toManey = toManey;
    }

    public Float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public Float getTheNo() {
        return theNo;
    }

    public void setTheNo(Float theNo) {
        this.theNo = theNo;
    }

}
