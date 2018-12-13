package cn.k12soft.servo.module.taxRateModel.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity(name = "tax_rate_model")
@DynamicUpdate
public class TaxRateModel {

    @Id
    @GeneratedValue
    private Long id;
    private Float taxIncome;                // 个税起征点
    private Float endowmentInsurance;       // 养老保险
    private Float unemploymentInsurance;    //失业保险
    private Float medicalInsurance;         //医疗保险
    private Float maternityInsurance;       //生育保险
    private Float injuryInsurance;          //工伤保险
    private Float accumulationFund;         //公积金
    private Float tax;                      //个人所得税

    @OneToMany(mappedBy = "taxRateModel", cascade = CascadeType.ALL)
    private Collection<TaxRateMoney> taxRateModelMoneys;

    private Instant createAt;

    public TaxRateModel() {
    }

    public TaxRateModel(Float taxIncome, Float endowmentInsurance, Float unemploymentInsurance, Float medicalInsurance, Float maternityInsurance, Float injuryInsurance, Float accumulationFund, Float tax, Collection<TaxRateMoney> taxRateModelMoneys, Instant createAt) {
        this.taxIncome = taxIncome;
        this.endowmentInsurance = endowmentInsurance;
        this.unemploymentInsurance = unemploymentInsurance;
        this.medicalInsurance = medicalInsurance;
        this.maternityInsurance = maternityInsurance;
        this.injuryInsurance = injuryInsurance;
        this.accumulationFund = accumulationFund;
        this.tax = tax;
        this.taxRateModelMoneys = taxRateModelMoneys;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTaxIncome() {
        return taxIncome;
    }

    public void setTaxIncome(Float taxIncome) {
        this.taxIncome = taxIncome;
    }

    public Float getEndowmentInsurance() {
        return endowmentInsurance;
    }

    public void setEndowmentInsurance(Float endowmentInsurance) {
        this.endowmentInsurance = endowmentInsurance;
    }

    public Float getUnemploymentInsurance() {
        return unemploymentInsurance;
    }

    public void setUnemploymentInsurance(Float unemploymentInsurance) {
        this.unemploymentInsurance = unemploymentInsurance;
    }

    public Float getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(Float medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public Float getMaternityInsurance() {
        return maternityInsurance;
    }

    public void setMaternityInsurance(Float maternityInsurance) {
        this.maternityInsurance = maternityInsurance;
    }

    public Float getInjuryInsurance() {
        return injuryInsurance;
    }

    public void setInjuryInsurance(Float injuryInsurance) {
        this.injuryInsurance = injuryInsurance;
    }

    public Float getAccumulationFund() {
        return accumulationFund;
    }

    public void setAccumulationFund(Float accumulationFund) {
        this.accumulationFund = accumulationFund;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Collection<TaxRateMoney> getTaxRateModelMoneys() {
        return taxRateModelMoneys;
    }

    public void setTaxRateModelMoneys(Collection<TaxRateMoney> taxRateModelMoneys) {
        this.taxRateModelMoneys = taxRateModelMoneys;
    }
}
