package cn.k12soft.servo.module.revenue.domain;

import cn.k12soft.servo.domain.SchoolEntity;
import cn.k12soft.servo.module.empFlowRate.domain.FolwEnum;
import cn.k12soft.servo.module.employees.domain.Employee;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 老师社保
 */
@Entity
@DynamicUpdate
public class TeacherSocialSecurity extends SchoolEntity{
    @Id
    @GeneratedValue
    private Integer id;
    private String actorId;
    @OneToOne
    private Employee employee; // 员工
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String department;
    @Column(nullable = false)
    private String post; //职位
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FolwEnum type;
    @Column(nullable = false)
    private Float salary = 0f;
    @Column(nullable = false)
    private Float taxIncome;    // 个税起征点
    @Column(nullable = false)
    private Float endowmentInsurance = 0f;// 养老保险
    @Column(nullable = false)
    private Float unemploymentInsurance = 0f;//失业保险
    @Column(nullable = false)
    private Float medicalInsurance = 0f;//医疗保险
    @Column(nullable = false)
    private Float maternityInsurance = 0f;//生育保险
    @Column(nullable = false)
    private Float injuryInsurance = 0f;//工伤保险
    @Column(nullable = false)
    private Float accumulationFund = 0f; //公积金
    @Column(nullable = false)
    private Float tax = 0f;//个人所得税
    @Column(nullable = false)
    private Float actualPayroll = 0f;//实发工资

    public TeacherSocialSecurity(){}

    public TeacherSocialSecurity(String actorId, Employee employee, String name, String department, String post, FolwEnum type, Float salary, Float taxIncome, Float endowmentInsurance, Float unemploymentInsurance, Float medicalInsurance, Float maternityInsurance, Float injuryInsurance, Float accumulationFund, Float tax, Float actualPayroll) {
        this.actorId = actorId;
        this.employee = employee;
        this.name = name;
        this.department = department;
        this.post = post;
        this.type = type;
        this.salary = salary;
        this.taxIncome = taxIncome;
        this.endowmentInsurance = endowmentInsurance;
        this.unemploymentInsurance = unemploymentInsurance;
        this.medicalInsurance = medicalInsurance;
        this.maternityInsurance = maternityInsurance;
        this.injuryInsurance = injuryInsurance;
        this.accumulationFund = accumulationFund;
        this.tax = tax;
        this.actualPayroll = actualPayroll;
    }

    public TeacherSocialSecurity(String actorId, Employee employee, String name, String department, String post) {
        this.actorId = actorId;
        this.employee = employee;
        this.name = name;
        this.department = department;
        this.post = post;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public TeacherSocialSecurity(Integer schoolId)
    {
        super(schoolId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Float getTaxIncome() {
        return taxIncome;
    }

    public void setTaxIncome(Float taxIncome) {
        this.taxIncome = taxIncome;
    }

    public FolwEnum getType() {
        return type;
    }

    public void setType(FolwEnum type) {
        this.type = type;
    }
}
