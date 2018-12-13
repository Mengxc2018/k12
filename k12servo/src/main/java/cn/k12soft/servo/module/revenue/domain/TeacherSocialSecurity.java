package cn.k12soft.servo.module.revenue.domain;

import cn.k12soft.servo.domain.SchoolEntity;
import cn.k12soft.servo.module.empFlowRate.domain.FolwEnum;
import cn.k12soft.servo.module.employees.domain.Employee;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 老师社保
 */
@Entity
@DynamicUpdate
@ApiModel
public class TeacherSocialSecurity extends SchoolEntity{
    @Id
    @GeneratedValue
    @ApiModelProperty("编号")
    private Integer id;

    private String actorId;

    @OneToOne
    @ApiModelProperty("员工，包括姓名、身份证号、部门、所属部门、职务等")
    private Employee employee; // 员工

    @Column(nullable = false)
    @ApiModelProperty("员工姓名")
    private String name;

    @Column(nullable = false)
    @ApiModelProperty("银行卡号")
    private String bankId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FolwEnum type;

    @Column(nullable = false)
    @ApiModelProperty("基本工资:")
    private Float salary = 0f;

    @Column(nullable = false)
    @ApiModelProperty("绩效工资，没有就填0:")
    private Float salaryPerf = 0f;

    @Column(nullable = false)
    @ApiModelProperty("工龄工资，没有就填0:")
    private Float salaryAge = 0f;

    @Column(nullable = false)
    @ApiModelProperty("工资小计")
    private Float salarySubtotal = 0f;

    @Column(nullable = false)
    @ApiModelProperty("生源奖:")
    public Float stuAward = 0f;

    @Column(nullable = false)
    @ApiModelProperty("加班补助:")
    private Float subsidyOvertime = 0f;

    @Column(nullable = false)
    @ApiModelProperty("班车补助:")
    private Float subsidyShuttle = 0f;

    @Column(nullable = false)
    @ApiModelProperty("勋章奖励:")
    private Float subsidyMedal = 0f;

    @ApiModelProperty("其他补助:")
    @Column(nullable = false)
    private Float subsidyOther = 0f;

    @Column(nullable = false)
    @ApiModelProperty("事假扣款:")
    private Float cutAffair = 0f;

    @ApiModelProperty("病假扣款:")
    @Column(nullable = false)
    private Float cutSick = 0f;

    @ApiModelProperty("考勤扣款:")
    @Column(nullable = false)
    private Float cutAttend = 0f;

    @ApiModelProperty("餐费扣款:")
    @Column(nullable = false)
    private Float cutMeal = 0f;

    @ApiModelProperty("其他扣款:")
    @Column(nullable = false)
    private Float cutOther = 0f;

    @Column(nullable = false)
    @ApiModelProperty("工资总额")
    private Float salayTotal = 0f;

    @Column(nullable = false)
    @ApiModelProperty("个人养老保险8%")
    private Float personInsuranceEndowment = 0f;

    @Column(nullable = false)
    @ApiModelProperty("个人失业保险0.5%")
    private Float personInsuranceUnemployee = 0f;

    @Column(nullable = false)
    @ApiModelProperty("个人医疗保险2%")
    private Float personInsuranceMedical = 0f;

    @Column(nullable = false)
    @ApiModelProperty("个人住房公积金12%")
    private Float personFundsHouse = 0f;

    @Column(nullable = false)
    @ApiModelProperty("个人五险一金扣款小计")
    private Float personCutFivesalay = 0f;

    @Column(nullable = false)
    @ApiModelProperty("应付工资")
    private Float salaryPayment = 0f;

    @Column(nullable = false)
    @ApiModelProperty("个人所得税")
    private Float personTax = 0f;

    @Column(nullable = false)
    @ApiModelProperty("个税起征点")
    private Float taxIncome = 5000f;

    @Column(nullable = false)
    @ApiModelProperty("税后工资")
    private Float personTaxAfterSalary = 0f;

    @Column(nullable = false)
    @ApiModelProperty("避税部分:")
    private Float unTax = 0f;

    @Column(nullable = false)
    @ApiModelProperty("教育公司代缴部分:")
    private Float companyPay = 0f;

    @Column(nullable = false)
    @ApiModelProperty("其他:")
    private Float other;

    @Column(nullable = false)
    @ApiModelProperty("实发工资")
    private Float actualPayroll = 0f;

    @Column(nullable = false)
    @ApiModelProperty("公司承担养老20%")
    private Float companyEndowment = 0f;

    @Column(nullable = false)
    @ApiModelProperty("公司承担失业0.5%")
    private Float companyUnemployee = 0f;

    @Column(nullable = false)
    @ApiModelProperty("公司承担医疗7%+4.5")
    private Float companyMedical = 0f;

    @Column(nullable = false)
    @ApiModelProperty("公司承担生育0.5%")
    private Float companyBorth = 0f;

    @Column(nullable = false)
    @ApiModelProperty("公司承担工伤0.6%+4")
    private Float companyHurt = 0f;

    @Column(nullable = false)
    @ApiModelProperty("公司承担公积金12%")
    private Float companyFunds = 0f;

    @Column(nullable = false)
    @ApiModelProperty("总人工成本")
    private Float totalCost = 0f;

    @Column(nullable = false)
    @ApiModelProperty("社保缴费基数（养老、失业）下限2834元")
    private Float socilaBaseEndoUnemp = 0f;

    @Column(nullable = false)
    @ApiModelProperty("社保缴费基数（工伤、生育、医疗）下限4252元")
    private Float socilaBaseHurtBorthMedic = 0f;

    @Column(nullable = false)
    @ApiModelProperty("公积金缴费基数(下限1955元)")
    private Float accumulationFund = 0f;

    @Column(nullable = false)
    @ApiModelProperty("备注")
    private String remark;

    public TeacherSocialSecurity(){}

    public TeacherSocialSecurity(String actorId, Employee employee, String name, String bankId, FolwEnum type, Float salary, Float salaryPerf, Float salaryAge, Float salarySubtotal, Float stuAward, Float subsidyOvertime, Float subsidyShuttle, Float subsidyMedal, Float subsidyOther, Float cutAffair, Float cutSick, Float cutAttend, Float cutMeal, Float cutOther, Float salayTotal, Float personInsuranceEndowment, Float personInsuranceUnemployee, Float personInsuranceMedical, Float personFundsHouse, Float personCutFivesalay, Float salaryPayment, Float personTax, Float taxIncome, Float personTaxAfterSalary, Float unTax, Float companyPay, Float other, Float actualPayroll, Float companyEndowment, Float companyUnemployee, Float companyMedical, Float companyBorth, Float companyHurt, Float companyFunds, Float totalCost, Float socilaBaseEndoUnemp, Float socilaBaseHurtBorthMedic, Float accumulationFund, String remark) {
        this.actorId = actorId;
        this.employee = employee;
        this.name = name;
        this.bankId = bankId;
        this.type = type;
        this.salary = salary;
        this.salaryPerf = salaryPerf;
        this.salaryAge = salaryAge;
        this.salarySubtotal = salarySubtotal;
        this.stuAward = stuAward;
        this.subsidyOvertime = subsidyOvertime;
        this.subsidyShuttle = subsidyShuttle;
        this.subsidyMedal = subsidyMedal;
        this.subsidyOther = subsidyOther;
        this.cutAffair = cutAffair;
        this.cutSick = cutSick;
        this.cutAttend = cutAttend;
        this.cutMeal = cutMeal;
        this.cutOther = cutOther;
        this.salayTotal = salayTotal;
        this.personInsuranceEndowment = personInsuranceEndowment;
        this.personInsuranceUnemployee = personInsuranceUnemployee;
        this.personInsuranceMedical = personInsuranceMedical;
        this.personFundsHouse = personFundsHouse;
        this.personCutFivesalay = personCutFivesalay;
        this.salaryPayment = salaryPayment;
        this.personTax = personTax;
        this.taxIncome = taxIncome;
        this.personTaxAfterSalary = personTaxAfterSalary;
        this.unTax = unTax;
        this.companyPay = companyPay;
        this.other = other;
        this.actualPayroll = actualPayroll;
        this.companyEndowment = companyEndowment;
        this.companyUnemployee = companyUnemployee;
        this.companyMedical = companyMedical;
        this.companyBorth = companyBorth;
        this.companyHurt = companyHurt;
        this.companyFunds = companyFunds;
        this.totalCost = totalCost;
        this.socilaBaseEndoUnemp = socilaBaseEndoUnemp;
        this.socilaBaseHurtBorthMedic = socilaBaseHurtBorthMedic;
        this.accumulationFund = accumulationFund;
        this.remark = remark;
    }

    public TeacherSocialSecurity(Integer schoolId, String actorId, Employee employee, String name, String bankId, FolwEnum type, Float salary, Float salaryPerf, Float salaryAge, Float salarySubtotal, Float stuAward, Float subsidyOvertime, Float subsidyShuttle, Float subsidyMedal, Float subsidyOther, Float cutAffair, Float cutSick, Float cutAttend, Float cutMeal, Float cutOther, Float salayTotal, Float personInsuranceEndowment, Float personInsuranceUnemployee, Float personInsuranceMedical, Float personFundsHouse, Float personCutFivesalay, Float salaryPayment, Float personTax, Float taxIncome, Float personTaxAfterSalary, Float unTax, Float companyPay, Float other, Float actualPayroll, Float companyEndowment, Float companyUnemployee, Float companyMedical, Float companyBorth, Float companyHurt, Float companyFunds, Float totalCost, Float socilaBaseEndoUnemp, Float socilaBaseHurtBorthMedic, Float accumulationFund, String remark) {
        super(schoolId);
        this.actorId = actorId;
        this.employee = employee;
        this.name = name;
        this.bankId = bankId;
        this.type = type;
        this.salary = salary;
        this.salaryPerf = salaryPerf;
        this.salaryAge = salaryAge;
        this.salarySubtotal = salarySubtotal;
        this.stuAward = stuAward;
        this.subsidyOvertime = subsidyOvertime;
        this.subsidyShuttle = subsidyShuttle;
        this.subsidyMedal = subsidyMedal;
        this.subsidyOther = subsidyOther;
        this.cutAffair = cutAffair;
        this.cutSick = cutSick;
        this.cutAttend = cutAttend;
        this.cutMeal = cutMeal;
        this.cutOther = cutOther;
        this.salayTotal = salayTotal;
        this.personInsuranceEndowment = personInsuranceEndowment;
        this.personInsuranceUnemployee = personInsuranceUnemployee;
        this.personInsuranceMedical = personInsuranceMedical;
        this.personFundsHouse = personFundsHouse;
        this.personCutFivesalay = personCutFivesalay;
        this.salaryPayment = salaryPayment;
        this.personTax = personTax;
        this.taxIncome = taxIncome;
        this.personTaxAfterSalary = personTaxAfterSalary;
        this.unTax = unTax;
        this.companyPay = companyPay;
        this.other = other;
        this.actualPayroll = actualPayroll;
        this.companyEndowment = companyEndowment;
        this.companyUnemployee = companyUnemployee;
        this.companyMedical = companyMedical;
        this.companyBorth = companyBorth;
        this.companyHurt = companyHurt;
        this.companyFunds = companyFunds;
        this.totalCost = totalCost;
        this.socilaBaseEndoUnemp = socilaBaseEndoUnemp;
        this.socilaBaseHurtBorthMedic = socilaBaseHurtBorthMedic;
        this.accumulationFund = accumulationFund;
        this.remark = remark;
    }

    public TeacherSocialSecurity(String actorId, Employee employee, String name) {
        this.actorId = actorId;
        this.employee = employee;
        this.name = name;
    }

    public TeacherSocialSecurity(String actorId, Employee employee, String username, FolwEnum type) {
        this.actorId = actorId;
        this.employee = employee;
        this.name = username;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public FolwEnum getType() {
        return type;
    }

    public void setType(FolwEnum type) {
        this.type = type;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Float getSalaryPerf() {
        return salaryPerf;
    }

    public void setSalaryPerf(Float salaryPerf) {
        this.salaryPerf = salaryPerf;
    }

    public Float getSalaryAge() {
        return salaryAge;
    }

    public void setSalaryAge(Float salaryAge) {
        this.salaryAge = salaryAge;
    }

    public Float getSalarySubtotal() {
        return salarySubtotal;
    }

    public void setSalarySubtotal(Float salarySubtotal) {
        this.salarySubtotal = salarySubtotal;
    }

    public Float getStuAward() {
        return stuAward;
    }

    public void setStuAward(Float stuAward) {
        this.stuAward = stuAward;
    }

    public Float getSubsidyOvertime() {
        return subsidyOvertime;
    }

    public void setSubsidyOvertime(Float subsidyOvertime) {
        this.subsidyOvertime = subsidyOvertime;
    }

    public Float getSubsidyShuttle() {
        return subsidyShuttle;
    }

    public void setSubsidyShuttle(Float subsidyShuttle) {
        this.subsidyShuttle = subsidyShuttle;
    }

    public Float getSubsidyMedal() {
        return subsidyMedal;
    }

    public void setSubsidyMedal(Float subsidyMedal) {
        this.subsidyMedal = subsidyMedal;
    }

    public Float getSubsidyOther() {
        return subsidyOther;
    }

    public void setSubsidyOther(Float subsidyOther) {
        this.subsidyOther = subsidyOther;
    }

    public Float getCutAffair() {
        return cutAffair;
    }

    public void setCutAffair(Float cutAffair) {
        this.cutAffair = cutAffair;
    }

    public Float getCutSick() {
        return cutSick;
    }

    public void setCutSick(Float cutSick) {
        this.cutSick = cutSick;
    }

    public Float getCutAttend() {
        return cutAttend;
    }

    public void setCutAttend(Float cutAttend) {
        this.cutAttend = cutAttend;
    }

    public Float getCutMeal() {
        return cutMeal;
    }

    public void setCutMeal(Float cutMeal) {
        this.cutMeal = cutMeal;
    }

    public Float getCutOther() {
        return cutOther;
    }

    public void setCutOther(Float cutOther) {
        this.cutOther = cutOther;
    }

    public Float getSalayTotal() {
        return salayTotal;
    }

    public void setSalayTotal(Float salayTotal) {
        this.salayTotal = salayTotal;
    }

    public Float getPersonInsuranceEndowment() {
        return personInsuranceEndowment;
    }

    public void setPersonInsuranceEndowment(Float personInsuranceEndowment) {
        this.personInsuranceEndowment = personInsuranceEndowment;
    }

    public Float getPersonInsuranceUnemployee() {
        return personInsuranceUnemployee;
    }

    public void setPersonInsuranceUnemployee(Float personInsuranceUnemployee) {
        this.personInsuranceUnemployee = personInsuranceUnemployee;
    }

    public Float getPersonInsuranceMedical() {
        return personInsuranceMedical;
    }

    public void setPersonInsuranceMedical(Float personInsuranceMedical) {
        this.personInsuranceMedical = personInsuranceMedical;
    }

    public Float getPersonFundsHouse() {
        return personFundsHouse;
    }

    public void setPersonFundsHouse(Float personFundsHouse) {
        this.personFundsHouse = personFundsHouse;
    }

    public Float getPersonCutFivesalay() {
        return personCutFivesalay;
    }

    public void setPersonCutFivesalay(Float personCutFivesalay) {
        this.personCutFivesalay = personCutFivesalay;
    }

    public Float getSalaryPayment() {
        return salaryPayment;
    }

    public void setSalaryPayment(Float salaryPayment) {
        this.salaryPayment = salaryPayment;
    }

    public Float getPersonTax() {
        return personTax;
    }

    public void setPersonTax(Float personTax) {
        this.personTax = personTax;
    }

    public Float getPersonTaxAfterSalary() {
        return personTaxAfterSalary;
    }

    public void setPersonTaxAfterSalary(Float personTaxAfterSalary) {
        this.personTaxAfterSalary = personTaxAfterSalary;
    }

    public Float getUnTax() {
        return unTax;
    }

    public void setUnTax(Float unTax) {
        this.unTax = unTax;
    }

    public Float getCompanyPay() {
        return companyPay;
    }

    public void setCompanyPay(Float companyPay) {
        this.companyPay = companyPay;
    }

    public Float getOther() {
        return other;
    }

    public void setOther(Float other) {
        this.other = other;
    }

    public Float getActualPayroll() {
        return actualPayroll;
    }

    public void setActualPayroll(Float actualPayroll) {
        this.actualPayroll = actualPayroll;
    }

    public Float getCompanyEndowment() {
        return companyEndowment;
    }

    public void setCompanyEndowment(Float companyEndowment) {
        this.companyEndowment = companyEndowment;
    }

    public Float getCompanyUnemployee() {
        return companyUnemployee;
    }

    public void setCompanyUnemployee(Float companyUnemployee) {
        this.companyUnemployee = companyUnemployee;
    }

    public Float getCompanyMedical() {
        return companyMedical;
    }

    public void setCompanyMedical(Float companyMedical) {
        this.companyMedical = companyMedical;
    }

    public Float getCompanyBorth() {
        return companyBorth;
    }

    public void setCompanyBorth(Float companyBorth) {
        this.companyBorth = companyBorth;
    }

    public Float getCompanyHurt() {
        return companyHurt;
    }

    public void setCompanyHurt(Float companyHurt) {
        this.companyHurt = companyHurt;
    }

    public Float getCompanyFunds() {
        return companyFunds;
    }

    public void setCompanyFunds(Float companyFunds) {
        this.companyFunds = companyFunds;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public Float getSocilaBaseEndoUnemp() {
        return socilaBaseEndoUnemp;
    }

    public void setSocilaBaseEndoUnemp(Float socilaBaseEndoUnemp) {
        this.socilaBaseEndoUnemp = socilaBaseEndoUnemp;
    }

    public Float getSocilaBaseHurtBorthMedic() {
        return socilaBaseHurtBorthMedic;
    }

    public void setSocilaBaseHurtBorthMedic(Float socilaBaseHurtBorthMedic) {
        this.socilaBaseHurtBorthMedic = socilaBaseHurtBorthMedic;
    }

    public Float getAccumulationFund() {
        return accumulationFund;
    }

    public void setAccumulationFund(Float accumulationFund) {
        this.accumulationFund = accumulationFund;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Float getTaxIncome() {
        return taxIncome;
    }

    public void setTaxIncome(Float taxIncome) {
        this.taxIncome = taxIncome;
    }

    @Override
    public String toString() {
        return "TeacherSocialSecurity{" +
                "id=" + id +
                ", actorId='" + actorId + '\'' +
                ", employee=" + employee +
                ", name='" + name + '\'' +
                ", bankId='" + bankId + '\'' +
                ", type=" + type +
                ", salary=" + salary +
                ", salaryPerf=" + salaryPerf +
                ", salaryAge=" + salaryAge +
                ", salarySubtotal=" + salarySubtotal +
                ", stuAward=" + stuAward +
                ", subsidyOvertime=" + subsidyOvertime +
                ", subsidyShuttle=" + subsidyShuttle +
                ", subsidyMedal=" + subsidyMedal +
                ", subsidyOther=" + subsidyOther +
                ", cutAffair=" + cutAffair +
                ", cutSick=" + cutSick +
                ", cutAttend=" + cutAttend +
                ", cutMeal=" + cutMeal +
                ", cutOther=" + cutOther +
                ", salayTotal=" + salayTotal +
                ", personInsuranceEndowment=" + personInsuranceEndowment +
                ", personInsuranceUnemployee=" + personInsuranceUnemployee +
                ", personInsuranceMedical=" + personInsuranceMedical +
                ", personFundsHouse=" + personFundsHouse +
                ", personCutFivesalay=" + personCutFivesalay +
                ", salaryPayment=" + salaryPayment +
                ", personTax=" + personTax +
                ", taxIncome=" + taxIncome +
                ", personTaxAfterSalary=" + personTaxAfterSalary +
                ", unTax=" + unTax +
                ", companyPay=" + companyPay +
                ", other='" + other + '\'' +
                ", actualPayroll=" + actualPayroll +
                ", companyEndowment=" + companyEndowment +
                ", companyUnemployee=" + companyUnemployee +
                ", companyMedical=" + companyMedical +
                ", companyBorth=" + companyBorth +
                ", companyHurt=" + companyHurt +
                ", companyFunds=" + companyFunds +
                ", totalCost=" + totalCost +
                ", socilaBaseEndoUnemp=" + socilaBaseEndoUnemp +
                ", socilaBaseHurtBorthMedic=" + socilaBaseHurtBorthMedic +
                ", accumulationFund=" + accumulationFund +
                ", remark='" + remark + '\'' +
                '}';
    }
}
