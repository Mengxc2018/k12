package cn.k12soft.servo.module.revenue.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;
import cn.k12soft.servo.module.revenue.form.TeacherSocialSecurityForm;
import cn.k12soft.servo.module.revenue.repository.TeacherSocialSecurityRepository;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeacherSocialSecurityService extends AbstractEntityService<TeacherSocialSecurity, Integer> {

    private final ActorRepository actorRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TeacherSocialSecurityService(TeacherSocialSecurityRepository entityRepository,
                                        ActorRepository actorRepository,
                                        EmployeeRepository employeeRepository) {
        super(entityRepository);
        this.actorRepository = actorRepository;
        this.employeeRepository = employeeRepository;
    }

    // 速算扣除数  0,210,1410,2660,4410,7160,15160
    private static final Float QUICK_DEDUCTION0 = 0f;
    private static final Float QUICK_DEDUCTION210 = 120f;
    private static final Float QUICK_DEDUCTION1410 = 1410f;
    private static final Float QUICK_DEDUCTION2660 = 2660f;
    private static final Float QUICK_DEDUCTION4410 = 4410f;
    private static final Float QUICK_DEDUCTION7160 = 7160f;
    private static final Float QUICK_DEDUCTION15160 = 15160f;
    // 税率   3,10,20,25,30,35,45
    private static final Float TAX_RATE3 = 0.03f;
    private static final Float TAX_RATE10 = 0.1f;
    private static final Float TAX_RATE20 = 0.2f;
    private static final Float TAX_RATE25 = 0.25f;
    private static final Float TAX_RATE30 = 0.30f;
    private static final Float TAX_RATE35 = 0.35f;
    private static final Float TAX_RATE45 = 0.45f;
    // 应纳税所得额(含税)
    private static final Float SALARY3000 = 3000f;
    private static final Float SALARY12000 = 12000f;
    private static final Float SALARY25000 = 25000f;
    private static final Float SALARY35000 = 35000f;
    private static final Float SALARY55000 = 55000f;
    private static final Float SALARY80000 = 80000f;


    @Override
    protected TeacherSocialSecurityRepository getEntityRepository() {
        return (TeacherSocialSecurityRepository) super.getEntityRepository();
    }

    public Page<TeacherSocialSecurity> findBySchoolId(Integer schoolId, Pageable pageable) {
        return getEntityRepository().findBySchoolId(schoolId, pageable);
    }

    public Page<TeacherSocialSecurity> findByNameAndSchoolId(String name, Integer schoolId, Pageable pageable) {
        return getEntityRepository().findByNameAndSchoolId(name, schoolId, pageable);
    }

    public List<TeacherSocialSecurity> findAllBySchoolId(Integer schoolId) {
        return getEntityRepository().findBySchoolId(schoolId);
    }

    public TeacherSocialSecurity createTeacherSocialSecurity(Actor actor, TeacherSocialSecurityForm form) {

        int teacherActorId = form.getActorId();
        Actor teacherActor = this.actorRepository.getOne(teacherActorId);
        if (teacherActor == null) {
            return null;
        }

        TeacherSocialSecurity teacherSocialSecurity = this.getEntityRepository().findByActorId(String.valueOf(teacherActorId));
        if(teacherSocialSecurity != null){
            return null;
        }

        Optional<Employee> employee = this.employeeRepository.queryByActorId(teacherActor.getId());
        if (!employee.isPresent()){
            return null;
        }

        TeacherSocialSecurity social = new TeacherSocialSecurity();
        social.setActorId(String.valueOf(teacherActorId));
        social.setEmployee(employee.get());
        social.setSchoolId(actor.getSchoolId());
        social.setName(form.getName());
        social.setBankId(form.getBankId());
        social.setRemark(form.getRemark());
        social.setType(form.getType());

        // 表单的值附给实体
        formToEntity(form, social);

        // 计算教师社保信息及个人所得税
        social = calculateSocila(social);

        return this.getEntityRepository().save(social);
    }

    public void updateTeacherSocialSecurity(Actor actor, TeacherSocialSecurityForm form) {
        int teacherActorId = form.getActorId();
        if (teacherActorId > 0) {
            Actor teacherActor = this.actorRepository.getOne(teacherActorId);
            if (teacherActor == null) {
                return;
            }
        }
        TeacherSocialSecurity social = this.getEntityRepository().findByActorId(String.valueOf(teacherActorId));
        if (social == null) {
            return;
        }

        Optional<Employee> employee = this.employeeRepository.queryByActorId(teacherActorId);
        if (!employee.isPresent()){
            return;
        }

        if (teacherActorId > 0) {
            social.setActorId(String.valueOf(teacherActorId));
        }

        if (!StringUtils.isEmpty(String.valueOf(form.getActorId()))){
            social.setActorId(String.valueOf(teacherActorId));
        }

        if (!StringUtils.isEmpty(form.getName())){
            social.setName(form.getName());
        }

        if (!StringUtils.isEmpty(form.getBankId())){
            social.setBankId(form.getBankId());
        }

        if (!StringUtils.isEmpty(form.getRemark())){
            social.setRemark(form.getRemark());
        }

        if (!StringUtils.isEmpty(form.getType().toString())){
            social.setType(form.getType());
        }

        // 表单的值附给实体
        formToEntity(form, social);

        // 计算教师社保信息及个人所得税
        social = calculateSocila(social);
        this.getEntityRepository().save(social);
    }

    public Page<TeacherSocialSecurity> getAllTeacherSocialSecurity(Actor actor, String name, int page, Integer size) {
        int pageSize = 10;
        if (size != null) {
            pageSize = size;
        }
        page = Math.max(0, page - 1);
        Sort sort = new Sort(Sort.Direction.DESC, "name");
        Pageable pageable = new PageRequest(page, pageSize, sort);
        if (StringUtils.isNotBlank(name)) {
            return this.getEntityRepository().findByNameAndSchoolId(name, actor.getSchoolId(), pageable);
        } else {
            return this.getEntityRepository().findBySchoolId(actor.getSchoolId(), pageable);
        }
    }

    public TeacherSocialSecurity calculateSocila(TeacherSocialSecurity social) {

        Float salary = social.getSalary();
        Float salaryPerf = social.getSalaryPerf();
        Float salaryAge = social.getSalaryAge();
        Float salarySubtotal = social.getSalarySubtotal();
        Float stuAward = social.getStuAward();
        Float subsidyOvertime = social.getSubsidyOvertime();
        Float subsidyShuttle = social.getSubsidyShuttle();
        Float subsidyMedal = social.getSubsidyMedal();
        Float subsidyOther = social.getSubsidyOther();
        Float cutAffair = social.getCutAffair();
        Float cutSick = social.getCutSick();
        Float cutAttend = social.getCutAttend();
        Float cutMeal = social.getCutMeal();
        Float cutOther = social.getCutOther();
        Float salayTotal = social.getSalayTotal();
        Float personInsuranceEndowment = social.getPersonInsuranceEndowment();
        Float personInsuranceUnemployee = social.getPersonInsuranceUnemployee();
        Float personInsuranceMedical = social.getPersonInsuranceMedical();
        Float personFundsHouse = social.getPersonFundsHouse();
        Float personCutFivesalay = social.getPersonCutFivesalay();
        Float salaryPayment = social.getSalaryPayment();
        Float personTax = social.getPersonTax();
        Float taxIncome = social.getTaxIncome();
        Float personTaxAfterSalary = social.getPersonTaxAfterSalary();
        Float unTax = social.getUnTax();
        Float companyPay = social.getCompanyPay();
        Float other = social.getOther();
        Float actualPayroll = social.getActualPayroll();
        Float companyEndowment = social.getCompanyEndowment();
        Float companyUnemployee = social.getCompanyUnemployee();
        Float companyMedical = social.getCompanyMedical();
        Float companyBorth = social.getCompanyBorth();
        Float companyHurt = social.getCompanyHurt();
        Float companyFunds = social.getCompanyFunds();
        Float totalCost = social.getTotalCost();
        Float socilaBaseEndoUnemp = social.getSocilaBaseEndoUnemp();
        Float socilaBaseHurtBorthMedic = social.getSocilaBaseHurtBorthMedic();
        Float accumulationFund = social.getAccumulationFund();

        // [工资小计] = 基本工资 + 绩效工资 + 工龄工资
        salarySubtotal = salary + salaryPerf + salaryAge;

        // [工资总额] = 工资小计 + 生源奖 + 加班补助 + 班车补助 + 勋章奖励 + 其他补助 - 事假扣款 - 病假扣款 - 考勤扣款 - 餐费扣款 - 其他扣款
        salayTotal = salarySubtotal + stuAward + subsidyOvertime + subsidyShuttle + subsidyMedal + subsidyOther
                - cutAffair - cutSick - cutAttend - cutMeal - cutOther;

        // 个人养老保险8%
        personInsuranceEndowment = socilaBaseEndoUnemp * 0.08f;
        // 个人失业保险0.5%
        personInsuranceUnemployee = socilaBaseEndoUnemp * 0.005f;
        // 个人医疗保险2%
        personInsuranceMedical = socilaBaseHurtBorthMedic * 0.02f;
        // 个人住房公积金12%
        personFundsHouse = accumulationFund * 0.12f;

        // 公司承担养老20%
        companyEndowment = socilaBaseEndoUnemp * 0.2f;
        // 公司承担失业0.5%
        companyUnemployee = socilaBaseEndoUnemp * 0.005f;
        // 公司承担医疗7%+4.5
        companyMedical = socilaBaseHurtBorthMedic * 0.07f;
        // 公司承担生育0.5%
        companyBorth = socilaBaseHurtBorthMedic * 0.005f;
        // 公司承担工伤0.6%+4
        companyHurt = socilaBaseHurtBorthMedic * 0.006f;
        // 公司承担公积金12%
        companyFunds = accumulationFund * 0.12f;

        // [个人五险一金扣款小计] = 个人养老保险 + 个人失业保险 + 个人医疗保险 + 个人住房公积金
        personCutFivesalay = personInsuranceEndowment
                + personInsuranceUnemployee
                + personInsuranceMedical
                + personFundsHouse;

        // 应付工资 = 工资总额 - 个人五险一金扣款小计
        salaryPayment = salayTotal - personCutFivesalay;

        // 个人所得税
        personTax = getPersonTaxCal(salaryPayment - taxIncome);

        // 税后工资
        personTaxAfterSalary = salaryPayment - personTax;

        // 实发工资 = 税后工资 + 避税部分 - 教育公司代缴部分 - 其他
        actualPayroll = personTaxAfterSalary + unTax - companyPay - other;

        // 总人工成本 = 工资总额 + (公司承担养老20% + 公司承担失业0.5% + 公司承担医疗7%+4.5 + 公司承担生育0.5% + 公司承担工伤0.6%+4 + 公司承担公积金12%)
        totalCost = salayTotal
                + (companyEndowment
                + companyUnemployee
                + companyMedical
                + companyBorth
                + companyHurt
                + companyFunds);

        social.setSalary(salary);
        social.setSalaryPerf(salaryPerf);
        social.setSalaryAge(salaryAge);
        social.setSalarySubtotal(salarySubtotal);
        social.setStuAward(stuAward);
        social.setSubsidyOvertime(salarySubtotal);
        social.setSubsidyShuttle(subsidyShuttle);
        social.setSubsidyMedal(subsidyMedal);
        social.setSubsidyOther(subsidyOther);
        social.setCutAffair(cutAffair);
        social.setCutSick(cutSick);
        social.setCutAttend(cutAttend);
        social.setCutMeal(cutMeal);
        social.setSalayTotal(salayTotal);
        social.setPersonInsuranceEndowment(personInsuranceEndowment);
        social.setPersonInsuranceUnemployee(personInsuranceUnemployee);
        social.setPersonInsuranceMedical(personInsuranceMedical);
        social.setPersonFundsHouse(personFundsHouse);
        social.setPersonCutFivesalay(personCutFivesalay);
        social.setSalaryPayment(salaryPayment);
        social.setPersonTax(personTax);
        social.setTaxIncome(taxIncome);
        social.setPersonTaxAfterSalary(personTaxAfterSalary);
        social.setUnTax(unTax);
        social.setCompanyPay(companyPay);
        social.setOther(other);
        social.setActualPayroll(actualPayroll);
        social.setCompanyEndowment(companyEndowment);
        social.setCompanyUnemployee(companyUnemployee);
        social.setCompanyMedical(companyMedical);
        social.setCompanyBorth(companyBorth);
        social.setCompanyHurt(companyHurt);
        social.setCompanyFunds(companyFunds);
        social.setTotalCost(totalCost);
        social.setSocilaBaseEndoUnemp(socilaBaseEndoUnemp);
        social.setSocilaBaseHurtBorthMedic(socilaBaseHurtBorthMedic);
        social.setAccumulationFund(accumulationFund);

        return social;
    }

    /**
     * 速算扣除:0,210,1410,2660,4410,7160,15160
     * 税   率:3,10,20,25,30,35,45
     * @param salaryPayment
     * @return
     */
    public Float getPersonTaxCal(Float salaryPayment) {
        Float personTax = 0f;
        // 个税税率计算 = 应付工资 * 税率 - 速算扣除
        if (salaryPayment != 0){
            if (salaryPayment <= SALARY3000){
                personTax = TAX_RATE3 * salaryPayment - QUICK_DEDUCTION0;
            }else if (salaryPayment > SALARY3000 && salaryPayment <= SALARY12000){
                personTax = TAX_RATE10 * salaryPayment - QUICK_DEDUCTION210;
            }else if (salaryPayment > SALARY12000 && salaryPayment <= SALARY25000){
                personTax = TAX_RATE20 * salaryPayment - QUICK_DEDUCTION1410;
            }else if (salaryPayment > SALARY25000 && salaryPayment <= SALARY35000){
                personTax = TAX_RATE25 * salaryPayment - QUICK_DEDUCTION2660;
            }else if (salaryPayment > SALARY35000 && salaryPayment <= SALARY55000){
                personTax = TAX_RATE30 * salaryPayment - QUICK_DEDUCTION4410;
            }else if (salaryPayment > SALARY55000 && salaryPayment <= SALARY80000){
                personTax = TAX_RATE35 * salaryPayment - QUICK_DEDUCTION7160;
            }else if (salaryPayment > SALARY80000){
                personTax = TAX_RATE45 * salaryPayment - QUICK_DEDUCTION15160;
            }
        }
        return personTax;
    }


    private TeacherSocialSecurity formToEntity(TeacherSocialSecurityForm form, TeacherSocialSecurity social){

        // 基本工资
        if (form.getSalary() != null) {
            social.setSalary(form.getSalary());
        }

        // 绩效工资
        if (form.getSalaryPerf() != null) {
            social.setSalaryPerf(form.getSalaryPerf());
        }

        //工龄工资
        if (form.getSalaryAge() != null) {
            social.setSalaryAge(form.getSalaryAge());
        }

        // 工资小计
        if (form.getSalarySubtotal() != null) {
            social.setSalayTotal(form.getSalarySubtotal());
        }

        // 生源奖
        if (form.getStuAward() != null) {
            social.setStuAward(form.getStuAward());
        }

        // 加班补助
        if (form.getSubsidyOvertime() != null) {
            social.setSubsidyOvertime(form.getSubsidyOvertime());
        }

        // 班车补助
        if (form.getSubsidyShuttle() != null) {
            social.setSubsidyShuttle(form.getSubsidyShuttle());
        }

        // 勋章奖励
        if (form.getSubsidyMedal() != null) {
            social.setSubsidyMedal(form.getSubsidyMedal());
        }

        // 其他补助
        if (form.getSubsidyOther() != null) {
            social.setSubsidyOther(form.getSubsidyOther());
        }

        // 事假扣款
        if (form.getCutAffair() != null) {
            social.setCutAffair(form.getCutAffair());
        }

        // 病假扣款
        if (form.getCutSick() != null) {
            social.setCutSick(form.getCutSick());
        }

        // 考勤扣款
        if (form.getCutAttend() != null) {
            social.setCutAttend(form.getCutAttend());
        }

        // 餐费扣款
        if (form.getCutMeal() != null) {
            social.setCutMeal(form.getCutMeal());
        }

        // 其他扣款
        if (form.getCutOther() != null) {
            social.setCutOther(form.getCutOther());
        }

        // 工资总额
        if (form.getSalayTotal() != null) {
            social.setSalayTotal(form.getSalayTotal());
        }

        // 个人养老保险8%
        if (form.getPersonInsuranceEndowment() != null) {
            social.setPersonInsuranceEndowment(form.getPersonInsuranceEndowment());
        }

        // 个人失业保险0.5%
        if (form.getPersonInsuranceUnemployee() != null) {
            social.setPersonInsuranceUnemployee(form.getPersonInsuranceUnemployee());
        }

        // 个人医疗保险2%
        if (form.getPersonInsuranceMedical() != null) {
            social.setPersonInsuranceMedical(form.getPersonInsuranceMedical());
        }

        // 个人住房公积金12%
        if (form.getPersonFundsHouse() != null) {
            social.setPersonFundsHouse(form.getPersonFundsHouse());
        }

        // 个人五险一金扣款小计
        if (form.getPersonCutFivesalay() != null) {
            social.setPersonCutFivesalay(form.getPersonCutFivesalay());
        }

        // 应付工资
        if (form.getSalaryPayment() != null) {
            social.setSalaryPayment(form.getSalaryPayment());
        }

        // 个人所得税
        if (form.getPersonTax() != null) {
            social.setPersonTax(form.getPersonTax());
        }

        // 个税起征点
        if (form.getTaxIncome() != null) {
            social.setTaxIncome(form.getTaxIncome());
        }

        // 税后工资
        if (form.getPersonTaxAfterSalary() != null) {
            social.setPersonTaxAfterSalary(form.getPersonTaxAfterSalary());
        }

        // 避税部分
        if (form.getUnTax() != null) {
            social.setUnTax(form.getUnTax());
        }

        // 教育公司代缴部分
        if (form.getCompanyPay() != null) {
            social.setCompanyPay(form.getCompanyPay());
        }

        // 其他
        if (form.getOther() != null) {
            social.setOther(form.getOther());
        }

        // 实发工资
        if (form.getActualPayroll() != null) {
            social.setActualPayroll(form.getActualPayroll());
        }

        // 公司承担养老20%
        if (form.getCompanyEndowment() != null) {
            social.setCompanyEndowment(form.getCompanyEndowment());
        }

        // 公司承担失业0.5%
        if (form.getCompanyUnemployee() != null) {
            social.setCompanyUnemployee(form.getCompanyUnemployee());
        }

        // 公司承担医疗7%+4.5
        if (form.getCompanyMedical() != null) {
            social.setCompanyMedical(form.getCompanyMedical());
        }

        // 公司承担生育0.5%
        if (form.getCompanyBorth() != null) {
            social.setCompanyBorth(form.getCompanyBorth());
        }

        // 公司承担工伤0.6%+4
        if (form.getCompanyHurt() != null) {
            social.setCompanyHurt(form.getCompanyHurt());
        }

        // 公司承担公积金12%
        if (form.getCompanyFunds() != null) {
            social.setCompanyFunds(form.getCompanyFunds());
        }

        // 总人工成本
        if (form.getTotalCost() != null) {
            social.setTotalCost(form.getTotalCost());
        }

        // 社保缴费基数（养老、失业）下限2834元
        if (form.getSocilaBaseEndoUnemp() != null) {
            social.setSocilaBaseEndoUnemp(form.getSocilaBaseEndoUnemp());
        }

        // 公积金缴费基数(下限1955元)
        if (form.getAccumulationFund() != null) {
            social.setAccumulationFund(form.getAccumulationFund());
        }

        // 社保缴费基数（工伤、生育、医疗）下限4252元
        if (form.getSocilaBaseHurtBorthMedic() != null) {
            social.setSocilaBaseHurtBorthMedic(form.getSocilaBaseHurtBorthMedic());
        }
        return social;
    }

    public TeacherSocialSecurity countTsss(Actor actor, TeacherSocialSecurityForm form) {
        TeacherSocialSecurity social = new TeacherSocialSecurity();
        // 表单的值附给实体
        formToEntity(form, social);

        // 计算教师社保信息及个人所得税
        return calculateSocila(social);
    }
}
