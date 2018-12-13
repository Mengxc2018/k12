package cn.k12soft.servo.module.attendanceRate.service;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendanceTeacher;
import cn.k12soft.servo.module.AttendanceTeacher.repository.AttendanceTeacherRepository;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.VACATIONTYPE;
import cn.k12soft.servo.module.attendanceRate.domain.SchoolTeaRate;
import cn.k12soft.servo.module.attendanceRate.domain.StudentKlassRate;
import cn.k12soft.servo.module.attendanceRate.domain.StudentSchoolRate;
import cn.k12soft.servo.module.attendanceRate.domain.TeacherSchoolRate;
import cn.k12soft.servo.module.attendanceRate.domain.dto.StudentKlassRateDTO;
import cn.k12soft.servo.module.attendanceRate.domain.dto.StudentSchoolRateDTO;
import cn.k12soft.servo.module.attendanceRate.domain.dto.TeacherDTO;
import cn.k12soft.servo.module.attendanceRate.repository.*;
import cn.k12soft.servo.module.attendanceRate.service.mapper.StudentSchoolRateMapper;
import cn.k12soft.servo.module.attendanceRate.service.mapper.TeacherSchoolRateMapper;
import cn.k12soft.servo.module.attendanceRate.service.mapper.StudentKlassRateMapper;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.holidaysWeek.service.HolidaysWeekService;
import cn.k12soft.servo.repository.AttendanceRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class AttendanceRateService extends AbstractRepositoryService<TeacherSchoolRate, Long, AttendanceRateRepository>{

    private final KlassRepository klassRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;
    private final HolidaysWeekService holidaysWeekService;
    private final TeacherSchoolRateMapper attendanceRateMapper;
    private final AttendanceRepository attendanceRepository;
    private final StudentKlassRateMapper studentKlassRateMapper;
    private final StudentSchoolRateMapper studentSchoolRateMapper;
    private final SchoolTeaRateRepository schoolTeaRateRepository;
    private final StudentKlassRateRepository studentKlassRateRepository;
    private final TeacherSchoolRateRepository teacherSchoolRateRepository;
    private final StudentSchoolRateRepository studentSchoolRateRepository;
    private final AttendanceTeacherRepository attendanceTeacherRepository;

    protected AttendanceRateService(AttendanceRateRepository repository,
                                    KlassRepository klassRepository,
                                    SchoolRepository schoolRepository,
                                    StudentRepository studentRepository,
                                    EmployeeRepository employeeRepository,
                                    HolidaysWeekService holidaysWeekService,
                                    TeacherSchoolRateMapper attendanceRateMapper,
                                    AttendanceRepository attendanceRepository,
                                    StudentKlassRateMapper studentKlassRateMapper,
                                    StudentSchoolRateMapper studentSchoolRateMapper,
                                    SchoolTeaRateRepository schoolTeaRateRepository,
                                    StudentKlassRateRepository studentKlassRateRepository,
                                    TeacherSchoolRateRepository teacherSchoolRateRepository,
                                    StudentSchoolRateRepository studentSchoolRateRepository,
                                    AttendanceTeacherRepository attendanceTeacherRepository) {
        super(repository);
        this.klassRepository = klassRepository;
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
        this.employeeRepository = employeeRepository;
        this.holidaysWeekService = holidaysWeekService;
        this.attendanceRateMapper = attendanceRateMapper;
        this.attendanceRepository = attendanceRepository;
        this.studentKlassRateMapper = studentKlassRateMapper;
        this.studentSchoolRateMapper = studentSchoolRateMapper;
        this.schoolTeaRateRepository = schoolTeaRateRepository;
        this.studentKlassRateRepository = studentKlassRateRepository;
        this.teacherSchoolRateRepository = teacherSchoolRateRepository;
        this.studentSchoolRateRepository = studentSchoolRateRepository;
        this.attendanceTeacherRepository = attendanceTeacherRepository;
    }

    public Collection<TeacherDTO> queryBySchool(Integer schoolId, Integer year) {
        LocalDate localDate = LocalDate.now().withYear(year);
        Collection<TeacherSchoolRate> attendanceRates = schoolId != null
                 ? getRepository().findBySchoolIdAndCreatedAt(schoolId, localDate)
                : getRepository().findByCreatedAt(localDate);
        return attendanceRateMapper.toDTOs(attendanceRates);
    }

//    public void allStudentRate(){
//        Calendar cal = Calendar.getInstance();
//        // 开始时间、结束时间
//        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//        LocalDate localDate = LocalDate.parse(dateStr);
//        Instant to = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
//        Instant from = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
//        Integer days = holidaysWeekService.queryOfMonthToWorkDay(cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));
//
//        // 获得所有学校
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        List<School> schoolList = schoolRepository.findAll(sort);
//        for (School school : schoolList){
//            Integer schoolId = school.getId();
//            // 获取所有班级
//            Collection<Klass> klasses = klassRepository.findAllBySchoolId(schoolId);
//            for (Klass klass : klasses){
//                Collection<Student> students = studentRepository.findByKlass(klass);
//                for (Student student : students){
//                    Integer studentId = student.getId();
//
//                    Collection<Attendance> attendances = attendanceRepository.findByStudentIdAndCreatedAtBetween(studentId, from, to);
//                    // 获取出勤率并计算
//                    int attendanceSize = attendances.size();
//
//                    BigDecimal first = new BigDecimal(attendanceSize);
//                    BigDecimal second = new BigDecimal(days);
//                    BigDecimal thired = new BigDecimal(0);
//
//                    thired = first.divide(second, 2, BigDecimal.ROUND_HALF_UP);
//                    thired = thired.multiply(new BigDecimal(100));
//                    thired = thired.setScale(2, BigDecimal.ROUND_DOWN);
//
//                    StudentRate studentRate = studentRateRepository.findOne(Long.parseLong(studentId.toString()));
//                    if (studentRate == null){
//                        studentRate = new StudentRate();
//                        studentRate.setStudentId(studentId);
//                        studentRate.setSchool(school);
//                        studentRate.setKlass(klass);
//                    }
//                    switch (cal.get(Calendar.MONTH)){
//                        case Calendar.JANUARY:
//                            studentRate.setFebruary(thired.toString());
//                            break;
//                        case Calendar.FEBRUARY:
//                            studentRate.setFebruary(thired.toString());
//                            break;
//                        case Calendar.MARCH:
//                            studentRate.setMarch(thired.toString());
//                            break;
//                        case Calendar.APRIL:
//                            studentRate.setApril(thired.toString());
//                            break;
//                        case Calendar.MAY:
//                            studentRate.setMay(thired.toString());
//                            break;
//                        case Calendar.JUNE:
//                            studentRate.setJune(thired.toString());
//                            break;
//                        case Calendar.JULY:
//                            studentRate.setJuly(thired.toString());
//                            break;
//                        case Calendar.AUGUST:
//                            studentRate.setAuguest(thired.toString());
//                            break;
//                        case Calendar.SEPTEMBER:
//                            studentRate.setSeptember(thired.toString());
//                            break;
//                        case Calendar.OCTOBER:
//                            studentRate.setOctober(thired.toString());
//                            break;
//                        case Calendar.NOVEMBER:
//                            studentRate.setNovember(thired.toString());
//                            break;
//                        case Calendar.DECEMBER:
//                            studentRate.setDecember(thired.toString());
//                            break;
//                    }
//                    studentRate.setCreatedAt(Instant.now());
//                    studentRateRepository.save(studentRate);
//                }
//            }
//        }
//    }

    /**
     * 获取以班级为单位，学生的出勤率
     * 先获取每个学生从每月一号到今天的出勤率，然后将所有学生的出勤率相加，再除以班级学生的人数，即为班级学生的出勤率
     * 本月1号到昨天的时间
     */
    public void allStudentKlassRate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(-1);

        // 获取当前月应出勤天数
        Integer days = holidaysWeekService.days(localDate);

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Collection<School> schools = schoolRepository.findAll(sort);
        for (School school : schools){
            Integer schoolId = school.getId();
            Collection<Klass> klasses = klassRepository.findAllBySchoolId(schoolId);
            for (Klass klass : klasses){
                Integer klassId = klass.getId();
                String countStudentRate = "";
                String monthValue = "";

                // 获得孩子昨天的实际出勤人数
                Collection<Attendance> attendances = attendanceRepository.findByKlassAndCreatedAt(klassId, localDate);
                BigDecimal bigOne = new BigDecimal(attendances.size());
                // 获得班级中孩子的数量
                List<Student> klassList = studentRepository.findByKlassAndIsShow(klass, true);
                if (klassList.size() == 0){
                    continue;
                }
                BigDecimal bigTwo = new BigDecimal(klassList.size());
                // 当天孩子的出勤率
                BigDecimal bigThree = new BigDecimal(0);
                bigThree = bigOne.divide(bigTwo, 4, BigDecimal.ROUND_HALF_UP);
                bigThree = bigThree.multiply(new BigDecimal(100));
                bigThree = bigThree.setScale(2, BigDecimal.ROUND_HALF_UP);

                boolean isnull = true;
                // 获得班级中孩子的出勤率，如果没有，则新建一条
                StudentKlassRate studentKlassRate = studentKlassRateRepository.findByKlassIdAndSchoolId(klassId, schoolId);
                if (studentKlassRate == null){
                    studentKlassRate = new StudentKlassRate();
                    studentKlassRate.setSchool(school);
                    studentKlassRate.setKlass(klass);

                    studentKlassRate.setCreatedAt(Instant.now());
                    isnull = false;
                }

                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        monthValue = studentKlassRate.getJanuary();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setJanuary(countStudentRate);
                        break;
                    case Calendar.FEBRUARY:
                        monthValue = studentKlassRate.getFebruary();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setFebruary(countStudentRate);
                        break;
                    case Calendar.MARCH:
                        monthValue = studentKlassRate.getMarch();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setMarch(countStudentRate);
                        break;
                    case Calendar.APRIL:
                        monthValue = studentKlassRate.getApril();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setApril(countStudentRate);
                        break;
                    case Calendar.MAY:
                        monthValue = studentKlassRate.getMay();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setMay(countStudentRate);
                        break;
                    case Calendar.JUNE:
                        monthValue = studentKlassRate.getJune();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setJune(countStudentRate);
                        break;
                    case Calendar.JULY:
                        monthValue = studentKlassRate.getJuly();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setJuly(countStudentRate);
                        break;
                    case Calendar.AUGUST:
                        monthValue = studentKlassRate.getAuguest();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setAuguest(countStudentRate);
                        break;
                    case Calendar.SEPTEMBER:
                        monthValue = studentKlassRate.getSeptember();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setSeptember(countStudentRate);
                        break;
                    case Calendar.OCTOBER:
                        monthValue = studentKlassRate.getOctober();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setOctober(countStudentRate);
                        break;
                    case Calendar.NOVEMBER:
                        monthValue = studentKlassRate.getNovember();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setNovember(countStudentRate);
                        break;
                    case Calendar.DECEMBER:
                        monthValue = studentKlassRate.getDecember();
                        countStudentRate = studentRate(isnull, bigThree, monthValue);
                        studentKlassRate.setDecember(countStudentRate);
                        break;
                }
                studentKlassRateRepository.save(studentKlassRate);
            }
        }
    }

    public String studentRate(boolean isnull, BigDecimal bigThree, String studentKlassRate){
        String monthValue = bigThree.toString();
        if (!isnull){
            BigDecimal bigTwo = new BigDecimal(studentKlassRate);
            monthValue = bigThree.add(bigTwo).divide(new BigDecimal(2)).toString();
        }
        return monthValue;
    }

    public Collection<StudentKlassRateDTO> findAllStudentKlassRate(Integer schoolId, Integer year) {
        LocalDate date = LocalDate.now();
        date = date.withYear(year);
        Collection<StudentKlassRateDTO> studentKlassRatesDTO = studentKlassRateMapper.toDTOs(studentKlassRateRepository.queryByKlassIdAndSchoolId(schoolId, date));
        return studentKlassRatesDTO;
    }

    /**
     * 每个教师出勤率
     */
    public void teacherRateEveryDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(-1);
        Long first = Long.parseLong(localDate.withDayOfMonth(1).toString().replace("-", ""));
        Long second = Long.parseLong(localDate.withDayOfMonth(localDate.lengthOfMonth()).toString().replace("-", ""));

        // 获取当前月应出勤天数
        Integer one = days();
        Integer two = 0;

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Collection<School> schools = schoolRepository.findAll(sort);
        for (School school : schools){
            Integer schoolId = school.getId();
            Collection<Employee> employeeList = employeeRepository.findBySchoolId(schoolId);
            for (Employee employee : employeeList) {
                Integer actorId = employee.getActorId();
                // 初始化实际出勤天数
                two = 0;
                Collection<AttendanceTeacher> attendsnceTeachers = attendanceTeacherRepository.findBySchoolIdAndActorIdAndCreatedAtBetween(schoolId, actorId, first, second);
                for (AttendanceTeacher attendanceTeacher : attendsnceTeachers){
                    VACATIONTYPE status = attendanceTeacher.getStatus();
                    if ((status.equals(VACATIONTYPE.RETOR) || status.equals(VACATIONTYPE.BUSINESS))) {
                        continue;
                    }
                    two++;
                }
                TeacherSchoolRate teacherSchoolRate = this.getRepository().findByActorId(actorId);
                if (teacherSchoolRate == null){
                    teacherSchoolRate = new TeacherSchoolRate();
                    teacherSchoolRate.setCreatedAt(Instant.now());
                    teacherSchoolRate.setActorId(actorId);
                    teacherSchoolRate.setCityId(school.getCityId());
                    teacherSchoolRate.setSchool(school);
                }
                String value = "";
                    switch (calendar.get(Calendar.MONTH)){
                        case Calendar.JANUARY:
                            value = rateCount(one, two, teacherSchoolRate.getFebruary());
                            teacherSchoolRate.setJanuary(value);
                            break;
                        case Calendar.FEBRUARY:
                            value = rateCount(one, two, teacherSchoolRate.getFebruary());
                            teacherSchoolRate.setFebruary(value);
                            break;
                        case Calendar.MARCH:
                            value = rateCount(one, two, teacherSchoolRate.getMarch());
                            teacherSchoolRate.setMarch(value);
                            break;
                        case Calendar.APRIL:
                            value = rateCount(one, two, teacherSchoolRate.getApril());
                            teacherSchoolRate.setApril(value);
                            break;
                        case Calendar.MAY:
                            value = rateCount(one, two, teacherSchoolRate.getMay());
                            teacherSchoolRate.setMay(value);
                            break;
                        case Calendar.JUNE:
                            value = rateCount(one, two, teacherSchoolRate.getJune());
                            teacherSchoolRate.setJune(value);
                            break;
                        case Calendar.JULY:
                            value = rateCount(one, two, teacherSchoolRate.getJuly());
                            teacherSchoolRate.setJuly(value);
                            break;
                        case Calendar.AUGUST:
                            value = rateCount(one, two, teacherSchoolRate.getAuguest());
                            teacherSchoolRate.setAuguest(value);
                            break;
                        case Calendar.SEPTEMBER:
                            value = rateCount(one, two, teacherSchoolRate.getSeptember());
                            teacherSchoolRate.setSeptember(value);
                            break;
                        case Calendar.OCTOBER:
                            value = rateCount(one, two, teacherSchoolRate.getOctober());
                            teacherSchoolRate.setOctober(value);
                            break;
                        case Calendar.NOVEMBER:
                            value = rateCount(one, two, teacherSchoolRate.getDecember());
                            teacherSchoolRate.setNovember(value);
                            break;
                        case Calendar.DECEMBER:
                            value = rateCount(one, two, teacherSchoolRate.getDecember());
                            teacherSchoolRate.setDecember(value);
                            break;
                        }
                teacherSchoolRate.setCreatedAt(Instant.now());
                getRepository().save(teacherSchoolRate);
            }
        }
    }

    /**
     * 将每个班的算出来相加，除以班级数量
     */
    public void allStudentSchoolKlassRate() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Collection<School> schools = schoolRepository.findAll();
        for (School school : schools){

            Integer schoolId = school.getId();
            StudentSchoolRate studentSchoolRate = studentSchoolRateRepository.findBySchoolId(schoolId);
            if (studentSchoolRate == null){
                studentSchoolRate = new StudentSchoolRate(
                        school,
                        school.getCityId(),
                        Instant.now()
                );
            }
            Collection<Klass> klasses = klassRepository.findAllBySchoolId(schoolId);
            Integer klassSize = klasses.size();     // 班级数量
            // 班级数量判断，如果没有班级，直接跳过循环
            if (klassSize == 0){
                continue;
            }

            Collection<StudentKlassRate> studentKlassRates = studentKlassRateRepository.findBySchoolId(schoolId);

            String value = "";
            BigDecimal bigrateValue = new BigDecimal(0);

            for (StudentKlassRate studentKlassRate : studentKlassRates){
                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        value = studentKlassRate.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        value = studentKlassRate.getFebruary();
                        break;
                    case Calendar.MARCH:
                        value = studentKlassRate.getMarch();
                        break;
                    case Calendar.APRIL:
                        value = studentKlassRate.getApril();
                        break;
                    case Calendar.MAY:
                        value = studentKlassRate.getMay();
                        break;
                    case Calendar.JUNE:
                        value = studentKlassRate.getJune();
                        break;
                    case Calendar.JULY:
                        value = studentKlassRate.getJuly();
                        break;
                    case Calendar.AUGUST:
                        value = studentKlassRate.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        value = studentKlassRate.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        value = studentKlassRate.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        value = studentKlassRate.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        value = studentKlassRate.getDecember();
                        break;
                }

                if (StringUtils.isEmpty(value)){
                    value = "0.00";
                }
                BigDecimal bigValue = new BigDecimal(value);
                bigrateValue = bigrateValue.add(bigValue);
            }

            // 计算出勤率
            BigDecimal bigOne = bigrateValue;  // 班级出勤率总和
            BigDecimal bigTwo = new BigDecimal(klassSize);  // 班级数量
            BigDecimal bigThree = new BigDecimal(0);
            bigThree = bigOne.divide(bigTwo, 4, BigDecimal.ROUND_HALF_UP);
            bigThree = bigThree.setScale(2, BigDecimal.ROUND_DOWN);
            value = String.valueOf(bigThree);
            switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        studentSchoolRate.setFebruary(value);
                        break;
                    case Calendar.FEBRUARY:
                        studentSchoolRate.setFebruary(value);
                        break;
                    case Calendar.MARCH:
                        studentSchoolRate.setMarch(value);
                        break;
                    case Calendar.APRIL:
                        studentSchoolRate.setApril(value);
                        break;
                    case Calendar.MAY:
                        studentSchoolRate.setMay(value);
                        break;
                    case Calendar.JUNE:
                        studentSchoolRate.setJune(value);
                        break;
                    case Calendar.JULY:
                        studentSchoolRate.setJuly(value);
                        break;
                    case Calendar.AUGUST:
                        studentSchoolRate.setAuguest(value);
                        break;
                    case Calendar.SEPTEMBER:
                        studentSchoolRate.setSeptember(value);
                        break;
                    case Calendar.OCTOBER:
                        studentSchoolRate.setOctober(value);
                        break;
                    case Calendar.NOVEMBER:
                        studentSchoolRate.setNovember(value);
                        break;
                    case Calendar.DECEMBER:
                        studentSchoolRate.setDecember(value);
                        break;
                }
            studentSchoolRateRepository.save(studentSchoolRate);
        }
    }

    public Collection<StudentSchoolRateDTO> findBySchool(Actor actor, Integer schoolId, Integer year) {
        LocalDate date = LocalDate.now();
        date = date.withYear(year);
        School school = schoolRepository.findOne(schoolId);
        Collection<StudentSchoolRate> studentSchoolRates =  studentSchoolRateRepository.queryBySchool(school, date);
        return studentSchoolRateMapper.toDTOs(studentSchoolRates);
    }

    public void schoolTeaRate() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Collection<School> schools = schoolRepository.findAll();
        for (School school : schools){

            Integer schoolId = school.getId();
            Collection<TeacherSchoolRate> teacherSchoolRates = teacherSchoolRateRepository.findBySchoolId(schoolId);
            BigDecimal bigOne = new BigDecimal(0);  // 单个教师出勤率
            BigDecimal bigTwo = new BigDecimal(0);  // 出勤率汇总
            BigDecimal bigThree = new BigDecimal(0);// 学校出勤率

            // 将当前月份的出勤率相加
            for (TeacherSchoolRate teacherSchoolRate : teacherSchoolRates){
                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        bigOne = new BigDecimal(teacherSchoolRate.getJanuary());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.FEBRUARY:
                        bigOne = new BigDecimal(teacherSchoolRate.getFebruary());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.MARCH:
                        bigOne = new BigDecimal(teacherSchoolRate.getMarch());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.APRIL:
                        bigOne = new BigDecimal(teacherSchoolRate.getApril());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.MAY:
                        bigOne = new BigDecimal(teacherSchoolRate.getMay());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.JUNE:
                        bigOne = new BigDecimal(teacherSchoolRate.getJune());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.JULY:
                        bigOne = new BigDecimal(teacherSchoolRate.getJuly());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.AUGUST:
                        bigOne = new BigDecimal(teacherSchoolRate.getAuguest());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.SEPTEMBER:
                        bigOne = new BigDecimal(teacherSchoolRate.getSeptember());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.OCTOBER:
                        bigOne = new BigDecimal(teacherSchoolRate.getOctober());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.NOVEMBER:
                        bigOne = new BigDecimal(teacherSchoolRate.getNovember());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                    case Calendar.DECEMBER:
                        bigOne = new BigDecimal(teacherSchoolRate.getDecember());
                        bigTwo = bigTwo.add(bigOne);
                        break;
                }
            }

            // 计算以学校为单位的教师出勤率
            int schoolSize = schools.size();
            bigThree = bigTwo.divide(new BigDecimal(schoolSize), 2, BigDecimal.ROUND_HALF_UP);  // 学校教师出勤率计算，单位（%），保留两位小数
            String threeStr = String.valueOf(bigThree);

            SchoolTeaRate schoolTeaRate = schoolTeaRateRepository.findBySchool(school);
            if (schoolTeaRate == null){
                schoolTeaRate = new SchoolTeaRate();
                schoolTeaRate.setSchool(school);
                schoolTeaRate.setCityId(school.getCityId());
                schoolTeaRate.setCreatedAt(Instant.now());
            }
            switch (calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    schoolTeaRate.setJanuary(threeStr);
                    break;
                case Calendar.FEBRUARY:
                    schoolTeaRate.setFebruary(threeStr);
                    break;
                case Calendar.MARCH:
                    schoolTeaRate.setMarch(threeStr);
                    break;
                case Calendar.APRIL:
                    schoolTeaRate.setApril(threeStr);
                    break;
                case Calendar.MAY:
                    schoolTeaRate.setMay(threeStr);
                    break;
                case Calendar.JUNE:
                    schoolTeaRate.setJune(threeStr);
                    break;
                case Calendar.JULY:
                    schoolTeaRate.setJuly(threeStr);
                    break;
                case Calendar.AUGUST:
                    schoolTeaRate.setAuguest(threeStr);
                    break;
                case Calendar.SEPTEMBER:
                    schoolTeaRate.setSeptember(threeStr);
                    break;
                case Calendar.OCTOBER:
                    schoolTeaRate.setOctober(threeStr);
                    break;
                case Calendar.NOVEMBER:
                    schoolTeaRate.setNovember(threeStr);
                    break;
                case Calendar.DECEMBER:
                    schoolTeaRate.setDecember(threeStr);
                    break;
            }

            schoolTeaRateRepository.save(schoolTeaRate);
        }

    }

    public Integer days(){
        LocalDate localDate = LocalDate.now();
        LocalDate localDate2 = LocalDate.now();
        Integer one = 0;    // 当月应出勤时间
        for (int i = 1; i <= localDate2.getDayOfMonth(); i++){
            localDate = localDate.withDayOfMonth(i);
            Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
            boolean isWeek = holidaysWeekService.isWeekend(instant);
            if (!isWeek){
                one++;
            }
        }
        return one;
    }
    public String rateCount(Integer one, Integer two, String monthValue){
        BigDecimal bigOne = new BigDecimal(one);
        BigDecimal bigTwo = new BigDecimal(two);
        BigDecimal bigThree = new BigDecimal(0);
        bigThree = bigTwo.divide(bigOne, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        bigThree = bigThree.setScale(2, BigDecimal.ROUND_DOWN);
        return bigThree.toString();
    }

    /**
     * 参数：出勤天数、当月出勤数据、出勤数量、应出勤数量
     * @param monthValue
     * @param studentSize
     * @param attendanceSize
     * @return
     */
    public String studentKlassRateCount(String monthValue, Integer studentSize, Integer attendanceSize){
        BigDecimal bigAttendanceSize = new BigDecimal(attendanceSize);
        BigDecimal bigStudentSuize = new BigDecimal(studentSize);
        BigDecimal bigThree = new BigDecimal(0);
        bigThree = bigAttendanceSize.divide(bigStudentSuize, 4, BigDecimal.ROUND_HALF_UP);
        bigThree = bigThree.multiply(new BigDecimal(100));
        bigThree = bigThree.setScale(2, BigDecimal.ROUND_DOWN);
        return bigThree.toString();
    }

    public Collection<SchoolTeaRate> findByCity(Actor actor) {
        Integer cityId = Integer.valueOf(actor.getCityId());
        Collection<SchoolTeaRate> schoolTeaRates = schoolTeaRateRepository.findByCityId(cityId);
        return schoolTeaRates;
    }
}