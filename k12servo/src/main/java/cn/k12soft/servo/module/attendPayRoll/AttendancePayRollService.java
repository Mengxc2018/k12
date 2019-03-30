package cn.k12soft.servo.module.attendPayRoll;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.AttendanceTeacher.repository.AttendanceTeacherRepository;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.VacationTeacher.repository.VacationTeacherRepository;
import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;
import cn.k12soft.servo.module.revenue.repository.TeacherSocialSecurityRepository;
import cn.k12soft.servo.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AttendancePayRollService {

    private final SchoolRepository schoolRepository;
    private final AttendanceTeacherRepository attendanceTeacherRepository;
    private final TeacherSocialSecurityRepository teacherSocialSecurityRepository;
    private final VacationTeacherRepository vacationTeacherRepository;

    @Autowired
    public AttendancePayRollService(SchoolRepository schoolRepository,
                                    AttendanceTeacherRepository attendanceTeacherRepository,
                                    TeacherSocialSecurityRepository teacherSocialSecurityRepository, VacationTeacherRepository vacationTeacherRepository) {
        this.schoolRepository = schoolRepository;
        this.attendanceTeacherRepository = attendanceTeacherRepository;
        this.teacherSocialSecurityRepository = teacherSocialSecurityRepository;
        this.vacationTeacherRepository = vacationTeacherRepository;
    }

    public void payRoll() {

        Collection<School> schools = schoolRepository.findAll();
        for (School school : schools){
            forSchool(school);
        }

    }

    public void forSchool(School school){
        Integer schoolId = school.getId();
        Map<String, Long> map = period(school, LocalDate.now());
        Long first = map.get("first");
        Long second = map.get("second");

        Collection<TeacherSocialSecurity> teacherSocialSecurities = teacherSocialSecurityRepository.findBySchoolId(schoolId);
        for (TeacherSocialSecurity security : teacherSocialSecurities){
            payroll(security, schoolId, first, second);
        }

    }

    /**
     * 计算考勤扣除的薪资
     * @param security
     * @param schoolId
     * @param first
     * @param second
     * @return
     */
    public Map<String, Float> payroll(TeacherSocialSecurity security, Integer schoolId, Long first, Long second){
        Map<String, Float> map = new HashMap<>();
        Integer actorId = Integer.valueOf(security.getActorId());
        BigDecimal bigSalary = new BigDecimal(security.getSalaryPayment());
        // 统计员工考勤信息
        // 缺勤时长
        Integer attendSecond = attendanceTeacherRepository.sumTimesBySchoolIdAndActorIdAndCreatedAtBetween(schoolId, actorId, first, second);
        // 病假请假时长
        Integer sickSecond = vacationTeacherRepository.sumSickVacationTimeBySchoolIdAndActorIdAndCreatedAtBetween(schoolId, actorId, VacationTeacherUtil.VACATIONTYPE.SICK, first, second);
        // 事假请假时长
        Integer affairSecond = vacationTeacherRepository.sumAffairVacationTimeBySchoolIdAndActorIdAndCreatedAtBetween(schoolId, actorId, VacationTeacherUtil.VACATIONTYPE.AFFAIR, first, second);


        if (attendSecond == null || attendSecond == 0){
            attendSecond = 0;
        }
        if (affairSecond == null || affairSecond == 0){
            affairSecond = 0;
        }
        if (sickSecond == null || sickSecond == 0){
            sickSecond = 0;
        }

        BigDecimal bigAttend = new BigDecimal(attendSecond);    // 考勤
        BigDecimal bigAffair = new BigDecimal(affairSecond);    // 事假
        BigDecimal bigSick = new BigDecimal(sickSecond);    // 病假
        bigAttend = bigAttend.divide(new BigDecimal(3600));   // 单位：小时
        bigAffair = bigAffair.divide(new BigDecimal(3600));   // 单位：小时
        bigSick = bigSick.divide(new BigDecimal(3600));   // 单位：小时

        BigDecimal bigOne = new BigDecimal(22);    // 上班工作日
        BigDecimal bigTwo = new BigDecimal(8);     // 每天工作时长

        // 计算需要扣除的薪资 应发工资/22/8*缺勤请假时长
        bigAttend = bigSalary.divide(bigOne, 2, BigDecimal.ROUND_UP).divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP).multiply(bigAttend);
        bigAffair = bigSalary.divide(bigOne, 2, BigDecimal.ROUND_UP).divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP).multiply(bigAffair);
        bigSick = bigSalary.divide(bigOne, 2, BigDecimal.ROUND_UP).divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP).multiply(bigSick);
        Float attend = bigAttend.floatValue();
        Float affair = bigAffair.floatValue();
        Float sick = bigSick.floatValue();
        map.put("attend", attend);
        map.put("affair", affair);
        map.put("sick", sick);
        return map;
    }

    /**
     * 返回学校周期，如果没有周期，则默认为一号到最后一天
     * @param school
     * @param date
     * @return
     */
    public Map<String, Long> period(School school, LocalDate date){
        Long first = null;
        Long second = null;
        if (school.getFormDate() != null || school.getToDate() != null){
            Integer form = school.getFormDate();
            Integer to = school.getToDate();
            Integer monthOfDay = date.getDayOfMonth();

            // 根据周期结束时间判断是哪个周期
            if(monthOfDay < to){
                first = Long.parseLong(date.plusMonths(-1).withDayOfMonth(form).toString().replace("-",""));
                second = new Long(date.withDayOfMonth(to).toString().replace("-",""));
            }else {
                first = Long.parseLong(date.withDayOfMonth(form).toString().replace("-",""));
                second = new Long(date.plusMonths(+1).withDayOfMonth(to).toString().replace("-",""));
            }
        }else{
            // 如果当前学校没有安排考核周期，则默认为这个月1号到今天
            LocalDate firststr = date.withDayOfMonth(1);
            LocalDate secondstr = date.withDayOfMonth(date.lengthOfMonth());
            first = new Long(firststr.toString().replace("-", ""));
            second = new Long(secondstr.toString().replace("-", ""));
        }
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("first", first);
        map.put("second", second);
        return map;
    }
}
