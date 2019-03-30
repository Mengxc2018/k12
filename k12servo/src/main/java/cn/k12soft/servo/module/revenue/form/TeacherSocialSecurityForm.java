package cn.k12soft.servo.module.revenue.form;

import cn.k12soft.servo.module.empFlowRate.domain.FolwEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class TeacherSocialSecurityForm {

    private int actorId; // 员工

    @ApiModelProperty("员工姓名")
    private String name;

    @ApiModelProperty("银行卡号")
    private String bankId;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty("员工状态")
    private FolwEnum type;

    @ApiModelProperty("基本工资")
    private Float salary ;
     
    @ApiModelProperty("绩效工资，没有就填0")
    private Float salaryPerf ;

    @ApiModelProperty("工龄工资，没有就填0")
    private Float salaryAge ;

    @ApiModelProperty("工资小计")
    private Float salarySubtotal ;

    @ApiModelProperty("生源奖")
    private Float stuAward ;

    @ApiModelProperty("加班补助")
    private Float subsidyOvertime ;
    
    @ApiModelProperty("班车补助")
    private Float subsidyShuttle ;

    @ApiModelProperty("勋章奖励")
    private Float subsidyMedal ;

    @ApiModelProperty("其他补助")
    private Float subsidyOther ;

    @ApiModelProperty("事假扣款")
    private Float cutAffair ;

    @ApiModelProperty("病假扣款")
    private Float cutSick ;

    @ApiModelProperty("考勤扣款")
    private Float cutAttend ;

    @ApiModelProperty("餐费扣款")
    private Float cutMeal ;

    @ApiModelProperty("其他扣款")
    private Float cutOther ;

    @ApiModelProperty("工资总额")
    private Float salayTotal ;

    @ApiModelProperty("个人养老保险8%")
    private Float personInsuranceEndowment ;

    @ApiModelProperty("个人失业保险0.5%")
    private Float personInsuranceUnemployee ;

    @ApiModelProperty("个人医疗保险2%")
    private Float personInsuranceMedical ;

    @ApiModelProperty("个人住房公积金12%")
    private Float personFundsHouse ;

    @ApiModelProperty("个人五险一金扣款小计")
    private Float personCutFivesalay ;

    @ApiModelProperty("应付工资")
    private Float salaryPayment ;

    @ApiModelProperty("个人所得税")
    private Float personTax ;

    @ApiModelProperty("个税起征点")
    private Float taxIncome;

    @ApiModelProperty("税后工资")
    private Float personTaxAfterSalary ;

    @ApiModelProperty("避税部分")
    private Float unTax ;

    @ApiModelProperty("教育公司代缴部分")
    private Float companyPay ;

    @ApiModelProperty("其他")
    private Float other;

    @ApiModelProperty("实发工资")
    private Float actualPayroll ;

    @ApiModelProperty("公司承担养老20%")
    private Float companyEndowment ;

    @ApiModelProperty("公司承担失业0.5%")
    private Float companyUnemployee ;

    @ApiModelProperty("公司承担医疗7%+4.5")
    private Float companyMedical ;

    @ApiModelProperty("公司承担生育0.5%")
    private Float companyBorth ;

    @ApiModelProperty("公司承担工伤0.6%+4")
    private Float companyHurt ;

    @ApiModelProperty("公司承担公积金12%")
    private Float companyFunds ;

    @ApiModelProperty("总人工成本")
    private Float totalCost ;

    @ApiModelProperty("社保缴费基数（养老、失业）下限2834元")
    private Float socilaBaseEndoUnemp ;

    @ApiModelProperty("社保缴费基数（工伤、生育、医疗）下限4252元")
    private Float socilaBaseHurtBorthMedic ;

    @ApiModelProperty("公积金缴费基数(下限1955元)")
    private Float accumulationFund ;

    @ApiModelProperty("备注")
    private String remark;


    public int getActorId() {
        return actorId;
    }

    public String getName() {
        return name;
    }

    public String getBankId() {
        return bankId;
    }

    public FolwEnum getType() {
        return type;
    }

    public Float getSalary() {
        return salary;
    }

    public Float getSalaryPerf() {
        return salaryPerf;
    }

    public Float getSalaryAge() {
        return salaryAge;
    }

    public Float getSalarySubtotal() {
        return salarySubtotal;
    }

    public Float getStuAward() {
        return stuAward;
    }

    public Float getSubsidyOvertime() {
        return subsidyOvertime;
    }

    public Float getSubsidyShuttle() {
        return subsidyShuttle;
    }

    public Float getSubsidyMedal() {
        return subsidyMedal;
    }

    public Float getSubsidyOther() {
        return subsidyOther;
    }

    public Float getCutAffair() {
        return cutAffair;
    }

    public Float getCutSick() {
        return cutSick;
    }

    public Float getCutAttend() {
        return cutAttend;
    }

    public Float getCutMeal() {
        return cutMeal;
    }

    public Float getCutOther() {
        return cutOther;
    }

    public Float getSalayTotal() {
        return salayTotal;
    }

    public Float getPersonInsuranceEndowment() {
        return personInsuranceEndowment;
    }

    public Float getPersonInsuranceUnemployee() {
        return personInsuranceUnemployee;
    }

    public Float getPersonInsuranceMedical() {
        return personInsuranceMedical;
    }

    public Float getPersonFundsHouse() {
        return personFundsHouse;
    }

    public Float getPersonCutFivesalay() {
        return personCutFivesalay;
    }

    public Float getSalaryPayment() {
        return salaryPayment;
    }

    public Float getPersonTax() {
        return personTax;
    }

    public Float getTaxIncome() {
        return taxIncome;
    }

    public Float getPersonTaxAfterSalary() {
        return personTaxAfterSalary;
    }

    public Float getUnTax() {
        return unTax;
    }

    public Float getCompanyPay() {
        return companyPay;
    }

    public Float getOther() {
        return other;
    }

    public Float getActualPayroll() {
        return actualPayroll;
    }

    public Float getCompanyEndowment() {
        return companyEndowment;
    }

    public Float getCompanyUnemployee() {
        return companyUnemployee;
    }

    public Float getCompanyMedical() {
        return companyMedical;
    }

    public Float getCompanyBorth() {
        return companyBorth;
    }

    public Float getCompanyHurt() {
        return companyHurt;
    }

    public Float getCompanyFunds() {
        return companyFunds;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public Float getSocilaBaseEndoUnemp() {
        return socilaBaseEndoUnemp;
    }

    public Float getSocilaBaseHurtBorthMedic() {
        return socilaBaseHurtBorthMedic;
    }

    public Float getAccumulationFund() {
        return accumulationFund;
    }

    public String getRemark() {
        return remark;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public void setType(FolwEnum type) {
        this.type = type;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public void setSalaryPerf(Float salaryPerf) {
        this.salaryPerf = salaryPerf;
    }

    public void setSalaryAge(Float salaryAge) {
        this.salaryAge = salaryAge;
    }

    public void setSalarySubtotal(Float salarySubtotal) {
        this.salarySubtotal = salarySubtotal;
    }

    public void setStuAward(Float stuAward) {
        this.stuAward = stuAward;
    }

    public void setSubsidyOvertime(Float subsidyOvertime) {
        this.subsidyOvertime = subsidyOvertime;
    }

    public void setSubsidyShuttle(Float subsidyShuttle) {
        this.subsidyShuttle = subsidyShuttle;
    }

    public void setSubsidyMedal(Float subsidyMedal) {
        this.subsidyMedal = subsidyMedal;
    }

    public void setSubsidyOther(Float subsidyOther) {
        this.subsidyOther = subsidyOther;
    }

    public void setCutAffair(Float cutAffair) {
        this.cutAffair = cutAffair;
    }

    public void setCutSick(Float cutSick) {
        this.cutSick = cutSick;
    }

    public void setCutAttend(Float cutAttend) {
        this.cutAttend = cutAttend;
    }

    public void setCutMeal(Float cutMeal) {
        this.cutMeal = cutMeal;
    }

    public void setCutOther(Float cutOther) {
        this.cutOther = cutOther;
    }

    public void setSalayTotal(Float salayTotal) {
        this.salayTotal = salayTotal;
    }

    public void setPersonInsuranceEndowment(Float personInsuranceEndowment) {
        this.personInsuranceEndowment = personInsuranceEndowment;
    }

    public void setPersonInsuranceUnemployee(Float personInsuranceUnemployee) {
        this.personInsuranceUnemployee = personInsuranceUnemployee;
    }

    public void setPersonInsuranceMedical(Float personInsuranceMedical) {
        this.personInsuranceMedical = personInsuranceMedical;
    }

    public void setPersonFundsHouse(Float personFundsHouse) {
        this.personFundsHouse = personFundsHouse;
    }

    public void setPersonCutFivesalay(Float personCutFivesalay) {
        this.personCutFivesalay = personCutFivesalay;
    }

    public void setSalaryPayment(Float salaryPayment) {
        this.salaryPayment = salaryPayment;
    }

    public void setPersonTax(Float personTax) {
        this.personTax = personTax;
    }

    public void setTaxIncome(Float taxIncome) {
        this.taxIncome = taxIncome;
    }

    public void setPersonTaxAfterSalary(Float personTaxAfterSalary) {
        this.personTaxAfterSalary = personTaxAfterSalary;
    }

    public void setUnTax(Float unTax) {
        this.unTax = unTax;
    }

    public void setCompanyPay(Float companyPay) {
        this.companyPay = companyPay;
    }

    public void setOther(Float other) {
        this.other = other;
    }

    public void setActualPayroll(Float actualPayroll) {
        this.actualPayroll = actualPayroll;
    }

    public void setCompanyEndowment(Float companyEndowment) {
        this.companyEndowment = companyEndowment;
    }

    public void setCompanyUnemployee(Float companyUnemployee) {
        this.companyUnemployee = companyUnemployee;
    }

    public void setCompanyMedical(Float companyMedical) {
        this.companyMedical = companyMedical;
    }

    public void setCompanyBorth(Float companyBorth) {
        this.companyBorth = companyBorth;
    }

    public void setCompanyHurt(Float companyHurt) {
        this.companyHurt = companyHurt;
    }

    public void setCompanyFunds(Float companyFunds) {
        this.companyFunds = companyFunds;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public void setSocilaBaseEndoUnemp(Float socilaBaseEndoUnemp) {
        this.socilaBaseEndoUnemp = socilaBaseEndoUnemp;
    }

    public void setSocilaBaseHurtBorthMedic(Float socilaBaseHurtBorthMedic) {
        this.socilaBaseHurtBorthMedic = socilaBaseHurtBorthMedic;
    }

    public void setAccumulationFund(Float accumulationFund) {
        this.accumulationFund = accumulationFund;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
