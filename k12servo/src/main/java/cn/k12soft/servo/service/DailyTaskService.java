package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.module.attendPayRoll.AttendancePayRollService;
import cn.k12soft.servo.module.revenue.domain.Payout;
import cn.k12soft.servo.module.revenue.domain.PayoutSubType;
import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;
import cn.k12soft.servo.module.revenue.service.PayoutService;
import cn.k12soft.servo.module.revenue.service.PayoutSubTypeService;
import cn.k12soft.servo.module.revenue.service.TeacherSocialSecurityService;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.util.Constants;
import cn.k12soft.servo.util.Times;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

/**
 * 每天执行任务
 * Created by liubing on 2018/8/8
 */
@Component
public class DailyTaskService {
    private static final Logger logger = LoggerFactory.getLogger(DailyTaskService.class);

    private final SchoolService schoolService;
    private final ActorRepository actorRepository;
    private final TeacherSocialSecurityService teacherSocialSecurityService;
    private final PayoutSubTypeService payoutSubTypeService;
    private final PayoutService payoutService;
    private final AttendancePayRollService attendancePayRollService;

    public DailyTaskService(SchoolService schoolService
            , ActorRepository actorRepository
            , TeacherSocialSecurityService teacherSocialSecurityService
            , PayoutSubTypeService payoutSubTypeService
            , PayoutService payoutService
            , AttendancePayRollService attendancePayRollService){
        this.schoolService = schoolService;
        this.actorRepository = actorRepository;
        this.teacherSocialSecurityService = teacherSocialSecurityService;
        this.payoutSubTypeService = payoutSubTypeService;
        this.payoutService = payoutService;
        this.attendancePayRollService = attendancePayRollService;
    }

    // 速算扣除数
    private static final Float QUICK_DEDUCTION0 = 0f;
    private static final Float QUICK_DEDUCTION105 = 105f;
    private static final Float QUICK_DEDUCTION555 = 555f;
    private static final Float QUICK_DEDUCTION1005 = 1005f;
    private static final Float QUICK_DEDUCTION2755 = 2755f;
    private static final Float QUICK_DEDUCTION5505 = 5505f;
    private static final Float QUICK_DEDUCTION13505 = 13505f;
    // 税率
    private static final Float TAX_RATE3 = 0.03f;
    private static final Float TAX_RATE10 = 0.1f;
    private static final Float TAX_RATE20 = 0.2f;
    private static final Float TAX_RATE25 = 0.25f;
    private static final Float TAX_RATE30 = 0.30f;
    private static final Float TAX_RATE35 = 0.35f;
    private static final Float TAX_RATE45 = 0.45f;
    // 应纳税所得额(含税)
    private static final Float SALARY1500 = 1500f;
    private static final Float SALARY4500 = 4500f;
    private static final Float SALARY9000 = 9000f;
    private static final Float SALARY35000 = 35000f;
    private static final Float SALARY55000 = 55000f;
    private static final Float SALARY80000 = 80000f;

    @Scheduled(cron = "0 10 1 * * ?") // 每天1点10分执行
//    @Scheduled(cron = "0/5 * * * * *") // 测试
    public void execute() {
        try {
            _calcTeacherPayroll();
        } catch (Exception e) {
            logger.error("[DailyTaskService] ", e);
        }
    }

    // 计算老师工资，写入支出表
    @Transactional
    void _calcTeacherPayroll() {
        try{
            long needCalcTime = System.currentTimeMillis() - Times.ONE_DAY_MILLIS; // 计算头一天的
            int yyyyMM = Times.time2yyyyMM(needCalcTime);
            List<School> schoolList = this.schoolService.getAll();
            for (School school : schoolList) {
                Integer schoolId = school.getId();
                try{
                    if(Times.isTheSameDate(school.getLastCalcTeacherPayoutTime(), needCalcTime)){
                        continue;
                    }
                    if(school.getLastCalcTeacherPayoutTime()<=0){
                        school.setLastCalcTeacherPayoutTime(needCalcTime - Times.ONE_DAY_MILLIS);
                    }

                    long lastCalcTeacherPayoutTime = school.getLastCalcTeacherPayoutTime();
                    int intervalDays = Times.getDayInterval(needCalcTime, lastCalcTeacherPayoutTime);
                    if(intervalDays<=0){
                        continue;
                    }

                    PayoutSubType payoutSubType = null;
                    List<PayoutSubType> payoutSubTypeList= this.payoutSubTypeService.findBySchoolId(school.getId());
                    for (PayoutSubType pst : payoutSubTypeList) {
                        if(pst.getName().equals(Constants.PAY_OUT_SUB_TYPE_NAME_TEACHER_SALARY)){
                            payoutSubType = pst;
                            break;
                        }
                    }
                    if(payoutSubType == null){
                        logger.info("dailytaskservice calcpayout no payoutsubtype schoolId={}", school.getId());
                        continue;
                    }

                    Actor managerActor = null;
                    List<Actor> managerActorList = actorRepository.findAllBySchoolIdAndTypesContains(school.getId(), ActorType.MANAGER);
                    if(managerActorList.size()>0){
                        managerActor = managerActorList.get(0);
                    }
                    for(int i=0; i< intervalDays; i++){
                        List<TeacherSocialSecurity> tssList = this.teacherSocialSecurityService.findAllBySchoolId(school.getId());

                        float totalPayout = 0f;

                        for (TeacherSocialSecurity tss : tssList) {

                            // 计算实发工资
                            totalPayout = countActualPayroll(tss, needCalcTime, school);

                            // 保存实发工资
                            tss.setActualPayroll(totalPayout);
                            this.teacherSocialSecurityService.save(tss);

                            totalPayout += tss.getActualPayroll(); //tss.getEndowmentInsurance() + tss.getUnemploymentInsurance() + tss.getMedicalInsurance() + tss.getMaternityInsurance() + tss.getInjuryInsurance() + tss.getAccumulationFund() + tss.getTax() + tss.getActualPayroll();
                        }

                        totalPayout = totalPayout/22;
                        Payout payout = new Payout(school.getId());
                        payout.setPayoutSubType(payoutSubType);
                        payout.setTheYearMonth(yyyyMM);
                        payout.setMoney(totalPayout);
                        payout.setCreateAt(Instant.now());
                        payout.setCreatedBy(managerActor);
                        this.payoutService.save(payout);
                    }
                    school.setLastCalcTeacherPayoutTime(needCalcTime);
                    this.schoolService.save(school);
                }catch (Exception e){
                    logger.error("[DailyTaskService] schoolId="+school.getId(), e);
                }
            }
        }catch (Exception e){
            logger.error("[DailyTaskService] ", e);
        }
    }

    /**
     * tss：教师薪资表信息
     * needCalcTime：时间
     * school：学校
     * @param tss
     * @param needCalcTime
     * @param school
     * @return
     */
    public float countActualPayroll(TeacherSocialSecurity tss, Long needCalcTime, School school){
        float totalPayout = 0f;
        Integer schoolId = school.getId();

        // 工资薪金所得
        float salaryf = tss.getSalary();
        // 个税起征点
        float taxInComef = tss.getTaxIncome();
        // 扣除个税起征点后的金额
        float taxInComeOutf = salaryf - taxInComef;
        // 五险一金
        float sacailSecurity = salaryf*(tss.getEndowmentInsurance() // 社保参数
                + tss.getUnemploymentInsurance()
                + tss.getMedicalInsurance()
                + tss.getMaternityInsurance()
                + tss.getInjuryInsurance()
                + tss.getAccumulationFund());
        float attendanceRoll = 0f;  // 考勤扣除工资 扣除数
        float taxRate = tss.getTax(); // 个人适用税率
        float quickDeduction = 0f;  // 速算扣除数
        float ratal = 0f;       // 应纳税额
        LocalDate localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(needCalcTime), ZoneId.systemDefault()).toLocalDate();
        Map<String, Long> map = attendancePayRollService.period(school, localDate);

        Long first = map.get("first");
        Long second = map.get("second");

        // 获取考勤扣除工资
        attendanceRoll = attendancePayRollService.payroll(tss, schoolId, first, second);

        // 个税税率跟速算
        if (taxInComeOutf != 0){
            if (taxInComeOutf <= SALARY1500){
                quickDeduction = QUICK_DEDUCTION0;
                taxRate = TAX_RATE3;
            }else if (taxInComeOutf > SALARY1500 && taxInComeOutf <= SALARY4500){
                quickDeduction = QUICK_DEDUCTION105;
                taxRate = TAX_RATE10;
            }else if (taxInComeOutf > SALARY4500 && taxInComeOutf <= SALARY9000){
                quickDeduction = QUICK_DEDUCTION555;
                taxRate = TAX_RATE20;
            }else if (taxInComeOutf > SALARY9000 && taxInComeOutf <= SALARY35000){
                quickDeduction = QUICK_DEDUCTION1005;
                taxRate = TAX_RATE25;
            }else if (taxInComeOutf > SALARY35000 && taxInComeOutf <= SALARY55000){
                quickDeduction = QUICK_DEDUCTION2755;
                taxRate = TAX_RATE30;
            }else if (taxInComeOutf > SALARY55000 && taxInComeOutf <= SALARY80000){
                quickDeduction = QUICK_DEDUCTION5505;
                taxRate = TAX_RATE35;
            }else if (taxInComeOutf > SALARY80000){
                quickDeduction = QUICK_DEDUCTION13505;
                taxRate = TAX_RATE45;
            }
        }

//      工资个税的计算公式：应纳税额=（工资薪金所得 - “五险一金” - 扣除数）* 适用税率 - 速算扣除数
        ratal = (salaryf - sacailSecurity - attendanceRoll) * taxRate - quickDeduction;
        // 实发工资数 = 应发工资数 - 应纳税额 - 考勤扣除工资
        totalPayout = salaryf - ratal - attendanceRoll;
        return totalPayout;
    }

}
