package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.domain.AttendPeriodStat.PeriodType;
import cn.k12soft.servo.domain.enumeration.AttendanceType;
import cn.k12soft.servo.module.holidaysWeek.service.HolidaysWeekService;
import cn.k12soft.servo.repository.*;
import cn.k12soft.servo.service.dto.AttendanceDTO;
import cn.k12soft.servo.service.mapper.AttendanceMapper;
import cn.k12soft.servo.web.form.AttendanceForm;
import cn.k12soft.servo.web.form.RetroAttendanceForm;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@Service
public class AttendanceService extends
        AbstractRepositoryService<Attendance, Long, AttendanceRepository> {

    private final StudentService studentService;
    private final AttendanceMapper mapper;
    private final UserRepository userRepository;
    private final GuardianRepository guardianRepository;
    private final StudentRepository studentRepository;
    private final VacationRepository vacationRepository;
    private final KlassRepository klassRepository;
    private final HolidaysWeekService holidaysWeekService;

    @Autowired
    public AttendanceService(AttendanceRepository repository,
                             StudentService studentService,
                             AttendanceMapper mapper, UserRepository userRepository, GuardianRepository guardianRepository, StudentRepository studentRepository, VacationRepository vacationRepository, KlassRepository klassRepository, HolidaysWeekService holidaysWeekService) {
        super(repository);
        this.studentService = studentService;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.guardianRepository = guardianRepository;
        this.studentRepository = studentRepository;
        this.vacationRepository = vacationRepository;
        this.klassRepository = klassRepository;
        this.holidaysWeekService = holidaysWeekService;
    }

    private static Logger logger = LoggerFactory.getLogger(AttendanceService.class);

    public AttendanceDTO create(AttendanceForm form, AttendanceType type) {
        Student student = studentService.get(form.getStudentId());
        Attendance attendance = new Attendance(
                student.getSchoolId(),
                student.getId(),
                student.getName(),
                student.getKlass().getId(),
                form.getPortrait(),
                type,
                form.getTemperature());
        attendance.setRemarks(form == null ? "" : form.getRemarks());
        attendance = getRepository().save(attendance);
        return mapper.toDTO(attendance);
    }

    public void createMany(List<AttendanceForm> forms, AttendanceType type) {
        for (AttendanceForm form : forms){
            CompletableFuture<AttendanceDTO> completable = CompletableFuture.supplyAsync(()->{
                try{
                    create(form, type);
                }catch (Exception e){
                    logger.error("学生ID：{}有误，请注意", form.getStudentId());
                }
                return null;
            });
        }
    }

    public AttendanceDTO retro(RetroAttendanceForm form) {
        Student student = studentService.get(form.getStudentId());
        if (!Objects.equals(form.getPortrait(), student.getPortrait())) {
            student.setPortrait(form.getPortrait());
            studentService.save(student);
        }
        Attendance attendance = new Attendance(
                student.getSchoolId(),
                student.getId(),
                student.getName(),
                student.getKlass().getId(),
                form.getPortrait(),
                form.getTemperature(),
                form.getRetroAt().toInstant());
        attendance.setRemarks(form == null ? "" : form.getRemarks());
        attendance = getRepository().save(attendance);
        return mapper.toDTO(attendance);
    }

    public List<AttendanceDTO> retroMany(List<RetroAttendanceForm> forms) {
        List<AttendanceDTO> list = new ArrayList<>();
        for (RetroAttendanceForm form : forms){
            form.setRemarks("教师代替补签");
            list.add(retro(form));
        }
        return list;
    }

    public List<DailyAttendStat> query(Integer klassId,
                                       LocalDate date,
                                       Integer studentId) {
        LocalDate lookDate = date.atStartOfDay().toLocalDate();
        AttendanceRepository repository = getRepository();
        List<Object[]> list = studentId != null
                ? repository.queryStudent(klassId, studentId, lookDate)
                : repository.query(klassId, lookDate);

        List<DailyAttendStat> collect = list.stream().map(DailyAttendStat::new).collect(Collectors.toList());
        if(list.size() != 0){
            Optional<String> portrait = this.getRepository().findLastPortrait(klassId, date, studentId);
            collect.get(0).setPortrait(portrait.orElse(""));
        }
        return collect;
    }

    public AttendPeriodStat queryPeriod(Integer klassId, PeriodType type, LocalDate specDate, Integer studentId) {
        Pair<LocalDate, LocalDate> dateRange = type.toPeriodRange(specDate);
        List<Object[]> countings = getRepository().counting(klassId,
                2,
                dateRange.getFirst().atStartOfDay().toInstant(ZoneOffset.UTC),
                dateRange.getSecond().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC));
        List<AttendCounting> objects = countings.stream()
                .map(args -> new AttendCounting(((Number) args[0]).intValue(), args[1].toString(), ((Number) args[2]).intValue()))
                .collect(Collectors.toList());
        if (studentId != null) {
            objects = objects.stream().filter(attendCounting -> attendCounting.getStudentId().equals(studentId)).collect(Collectors.toList());
        }
        return new AttendPeriodStat(klassId, type, dateRange.getFirst(), dateRange.getSecond(), objects);
    }

    public Map<String, Object> findChildrenAttendance(Actor actor, PeriodType type, LocalDate specialDate) {
        Map<String, Object> map = new HashMap<>();
        Pair<LocalDate, LocalDate> dateRang = type.toPeriodRange(specialDate);
        Instant first = dateRang.getFirst().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = dateRang.getSecond().atStartOfDay().toInstant(ZoneOffset.UTC);
        // 查询该监护人下有几个儿童
        User user = userRepository.findOne(actor.getUserId());
        List<Guardian> guardians = (List<Guardian>) guardianRepository.findByPatriarchId(user.getId());
        if (guardians != null) {
            for (int i = 0; i < guardians.size(); i++) {
                Collection<AttendanceDTO> attendances = mapper.toDTOs(getRepository().findByStudentIdAndCreatedAtBetween(guardians.get(i).getStudent().getId(), first, second));
                map.put("attendances" + i, attendances);
            }
        }
        return map;
    }

    public void createdName() {
        Collection<Attendance> attendances = getAll();
        for (Attendance attendance : attendances){
            String name = "0";
            Student student = studentRepository.findOne(attendance.getStudentId());
            if (student != null){
                name = student.getName();
            }
            attendance.setName(name);
            getRepository().save(attendance);
        }
    }

    public List<Student> findUnAttend(Actor actor, Integer klassId, LocalDate localDate) {
        Integer schoolId = actor.getSchoolId();
        if (localDate == null){
            localDate = LocalDate.now();
        }
        Klass klass =  this.klassRepository.findOne(klassId);
        if (klass == null){
            return null;
        }
        List<Student> stuNot = new ArrayList<>();
        Instant first = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        List<Student> studentList = this.studentRepository.findByKlassAndIsShow(klass, true);
        for (Student student : studentList){
            Integer stuId = student.getId();
            List<Vacation> vacations = this.vacationRepository.findAllBySchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(schoolId, klassId, stuId, first, second);
            Collection<Attendance> attendances = this.getRepository().findBySchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(schoolId, klassId, stuId, first, second);
            if (vacations.size() == 0 && attendances.size() == 0){
                stuNot.add(student);
            }
        }
        return stuNot;
    }

    public List<Map<String, Object>> findKlassRateOfStu(Actor actor, LocalDate localDate) {
        Integer schoolId = actor.getSchoolId();
        String attDate = "";
        String one = localDate.withDayOfMonth(1).toString();
        String two = localDate.withDayOfMonth(localDate.lengthOfMonth()).toString();
        attDate = one + " 至 " + two;

        // 月应出勤天数
        Integer days = holidaysWeekService.days(localDate);

        List<Map<String, Object>> list = new ArrayList<>();
        Collection<Klass> klasses = this.klassRepository.findAllBySchoolId(schoolId);
        for (Klass klass : klasses){
            Map<String, Object> map = new LinkedHashMap<>();
            Map<String, Object> param = getKlassOfStuRate(klass, localDate, days);
            map.put("klassId", klass.getId());
            map.put("klassName", klass.getName());
            map.put("stuCount", param.get("stuCount"));
            map.put("attRate", param.get("attRate"));
            map.put("attDate", attDate);
            list.add(map);
        }

        return list;
    }

    public Map<String, Object> getKlassOfStuRate(Klass klass, LocalDate localDate, Integer days){
        Map<String, Object> param = new HashMap<>();
        Integer schoolId = klass.getSchoolId();
        Instant first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
        Collection<Student> students = this.studentRepository.findByKlassAndIsShow(klass, true);

        Float attRate = 0f;
        for (Student student : students){
            Integer studentId = student.getId();
            Collection<Attendance> attendances = this.getRepository().findBySchoolIdAndKlassIdAndStudentIdAndCreatedAtBetweenAndCreatdAtGroupBy(schoolId, klass.getId(), studentId, first, second);
            Float one = attendances.size() / days.floatValue();
            attRate += one;
        }
        attRate = attRate/((float)students.size()) * 100;
        param.put("stuCount", students.size());
        param.put("attRate", attRate);
        return param;
    }

}
