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
import java.time.*;
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
                        float totalPayout = 0f;
                        List<TeacherSocialSecurity> tssList = this.teacherSocialSecurityService.findAllBySchoolId(school.getId());
                        LocalDate localDate = LocalDate.now();
                        Long first = localDate.withMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
                        Long second = localDate.withDayOfMonth(localDate.lengthOfMonth()).plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond();
                        if (school.getFormDate() != null && school.getToDate() != null){
                            first = localDate.withDayOfMonth(school.getFormDate()).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
                            second = localDate.withDayOfMonth(school.getToDate()).atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond();
                        }
                        for (TeacherSocialSecurity tss : tssList) {

                            // 事假、病假、考勤扣除工资 扣除数计算
                            Map<String, Float> map = attendancePayRollService.payroll(tss, schoolId, first, second);
                            tss.setCutSick(map.get("sick"));
                            tss.setCutAffair(map.get("affair"));
                            tss.setCutAttend(map.get("attend"));

                            TeacherSocialSecurity socila = this.teacherSocialSecurityService.calculateSocila(tss);
                            teacherSocialSecurityService.save(socila);

                            // 工资成本
                            totalPayout += socila.getActualPayroll(); //tss.getEndowmentInsurance() + tss.getUnemploymentInsurance() + tss.getMedicalInsurance() + tss.getMaternityInsurance() + tss.getInjuryInsurance() + tss.getAccumulationFund() + tss.getTax() + tss.getActualPayroll();
                        }
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
}
