package cn.k12soft.servo.module.attendanceRate.rateTask;

import cn.k12soft.servo.module.attendanceRate.service.AttendanceRateService;
import cn.k12soft.servo.module.countIncomePayout.service.CountMoneyService;
import cn.k12soft.servo.module.empFlowRate.service.EmpFlowRateService;
import cn.k12soft.servo.module.rateCity.service.CityRateService;
import cn.k12soft.servo.module.rateGroup.service.GroupRateService;
import cn.k12soft.servo.module.rateProvince.service.ProvinceRateService;
import cn.k12soft.servo.module.rateRegions.service.RegionsRateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RateTask {

    private final AttendanceRateService attendanceRateService;
    private final CityRateService cityRateService;
    private final ProvinceRateService provinceRateService;
    private final RegionsRateService regionsRateService;
    private final GroupRateService groupRateService;
    private final EmpFlowRateService empFlowRateService;
    private final CountMoneyService countMoneyService;

    public RateTask(AttendanceRateService attendanceRateService,
                    CityRateService cityRateService,
                    ProvinceRateService provinceRateService,
                    RegionsRateService regionsRateService,
                    GroupRateService groupRateService,
                    EmpFlowRateService empFlowRateService,
                    CountMoneyService countMoneyService) {
        this.attendanceRateService = attendanceRateService;
        this.cityRateService = cityRateService;
        this.provinceRateService = provinceRateService;
        this.regionsRateService = regionsRateService;
        this.groupRateService = groupRateService;
        this.empFlowRateService = empFlowRateService;
        this.countMoneyService = countMoneyService;
    }

    @Scheduled(cron = "0 30 3 * * ?")
    public void task(){
        _rate();
    }

    @Scheduled(cron = "0 0 3 1 * ?")
    public void rateFlowTask(){
        _rateFlowTask();
    }

    private void _rateFlowTask() {
        empFlowRateService.joinSchoolsRate();   // 学校为单位所有员工入职率
        empFlowRateService.leaveSchoolsRate();  // 学校为单位所有员工离职率
        empFlowRateService.rateJoin();          // 人员入职离职汇总到省市大区

        countMoneyService.countIncome();        // 收入支出汇总统计
    }

    public void _rate(){
        // 员工、教师出勤率汇总
        attendanceRateService.teacherRateEveryDay();
        attendanceRateService.allStudentKlassRate();
        attendanceRateService.allStudentSchoolKlassRate();
        attendanceRateService.schoolTeaRate();
        cityRateService.cityStudentRate();
        cityRateService.cityTeacherRate();
        provinceRateService.provinceStudentsRate();
        provinceRateService.provinceTeachersRate();
        regionsRateService.regionsStudentRate();
        regionsRateService.regionsTeacherRate();
        groupRateService.groupStuRate();
        groupRateService.groupTeaRate();
    }

}
