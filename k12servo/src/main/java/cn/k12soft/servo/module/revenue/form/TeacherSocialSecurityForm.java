package cn.k12soft.servo.module.revenue.form;

import javax.validation.constraints.NotNull;

public class TeacherSocialSecurityForm {
    private int actorId;// 老师的actorId
    private String name;
    private String department;
    private String post;
    @NotNull
    private Float salary;
    private Float taxIncome;    // 个税起征点
    private Float endowmentInsurance;// 养老保险
    private Float unemploymentInsurance;//失业保险
    private Float medicalInsurance;//医疗保险
    private Float maternityInsurance;//生育保险
    private Float injuryInsurance;//工伤保险
    private Float accumulationFund; //公积金
    private Float tax;//个人所得税
    private Float actualPayroll;//实发工资

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Float getTaxIncome() {
        return taxIncome;
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

    public Float getActualPayroll() {
        return actualPayroll;
    }

    public void setActualPayroll(Float actualPayroll) {
        this.actualPayroll = actualPayroll;
    }

}
