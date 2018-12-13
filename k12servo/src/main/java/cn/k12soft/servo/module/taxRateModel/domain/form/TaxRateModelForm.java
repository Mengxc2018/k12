package cn.k12soft.servo.module.taxRateModel.domain.form;

import cn.k12soft.servo.module.taxRateModel.domain.TaxRateMoney;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

public class TaxRateModelForm {

    private Float taxIncome;                // 个税起征点
    private Float endowmentInsurance;       // 养老保险
    private Float unemploymentInsurance;    //失业保险
    private Float medicalInsurance;         //医疗保险
    private Float maternityInsurance;       //生育保险
    private Float injuryInsurance;          //工伤保险
    private Float accumulationFund;         //公积金
    private Float tax;                      //个人所得税
    private Collection<TaxRateMoney> taxRateModelMoneys;

    public TaxRateModelForm() {
    }

    public TaxRateModelForm(Float taxIncome, Float endowmentInsurance, Float unemploymentInsurance, Float medicalInsurance, Float maternityInsurance, Float injuryInsurance, Float accumulationFund, Float tax, Collection<TaxRateMoney> taxRateModelMoneys) {
        this.taxIncome = taxIncome;
        this.endowmentInsurance = endowmentInsurance;
        this.unemploymentInsurance = unemploymentInsurance;
        this.medicalInsurance = medicalInsurance;
        this.maternityInsurance = maternityInsurance;
        this.injuryInsurance = injuryInsurance;
        this.accumulationFund = accumulationFund;
        this.tax = tax;
        this.taxRateModelMoneys = taxRateModelMoneys;
    }

    public Float getTaxIncome() {
        return taxIncome;
    }

    public Float getEndowmentInsurance() {
        return endowmentInsurance;
    }

    public Float getUnemploymentInsurance() {
        return unemploymentInsurance;
    }

    public Float getMedicalInsurance() {
        return medicalInsurance;
    }

    public Float getMaternityInsurance() {
        return maternityInsurance;
    }

    public Float getInjuryInsurance() {
        return injuryInsurance;
    }

    public Float getAccumulationFund() {
        return accumulationFund;
    }

    public Float getTax() {
        return tax;
    }

    public Collection<TaxRateMoney> getTaxRateModelMoneys() {
        return taxRateModelMoneys;
    }
}
