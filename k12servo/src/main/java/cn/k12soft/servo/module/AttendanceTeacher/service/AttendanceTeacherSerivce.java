package cn.k12soft.servo.module.AttendanceTeacher.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.module.AttendanceTeacher.VUtils.AttendTeacTwoUtils;
import cn.k12soft.servo.module.AttendanceTeacher.domain.*;
import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendPeriodTeac.PeriodTeacType;
import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendTeacDTO;
import cn.k12soft.servo.module.AttendanceTeacher.domain.form.RetorForm;
import cn.k12soft.servo.module.AttendanceTeacher.domain.form.RetroAttendanceTeacherForm;
import cn.k12soft.servo.module.AttendanceTeacher.service.mapper.AttendTeacMapper;
import cn.k12soft.servo.module.AttendanceTeacher.service.mapper.AttendanceTeacherMapper;
import cn.k12soft.servo.module.AttendanceTeacher.repository.AttendanceTeacherRepository;
import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendanceTeacherDTO;
import cn.k12soft.servo.module.AttendanceTeacher.VUtils.IsAttendacne;
import cn.k12soft.servo.module.AttendanceTeacher.VUtils.AttendTeacFourUtils;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.VacationTeacher.repository.VacationTeacherRepository;
import cn.k12soft.servo.module.VacationTeacher.service.VacationTeacherService;
import cn.k12soft.servo.module.activiti.apply.vacationService.ActivitiService;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.holidaysWeek.service.HolidaysWeekService;
import cn.k12soft.servo.module.schedulingPerson.domian.SchedulingPerson;
import cn.k12soft.servo.module.schedulingPerson.repository.SchedulingPersonRepository;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class AttendanceTeacherSerivce extends
        AbstractRepositoryService<AttendanceTeacher, Long, AttendanceTeacherRepository>{

    private final Integer one = 1;  // 打四次卡
    private final Integer two = 2;  // 打两次卡
    private final AttendTeacFourUtils attendTeacUtils;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final AttendTeacTwoUtils attendTeacTwoUtils;
    private final AttendanceTeacherMapper mapper;
    private final ActivitiService activitiService;
    private final ActorRepository actorRepository;
    private final AttendTeacMapper attendTeacMapper;
    private final VacationTeacherService vacationTeacherService;
    private final VacationTeacherRepository vacationTeacherRepository;
    private final SchedulingPersonRepository schedulingPersonRepository;
    private final AttendanceTeacherRepository attendanceTeacherRepository;
    private final HolidaysWeekService holidaysWeekService;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public AttendanceTeacherSerivce(AttendanceTeacherRepository repository,
                                    UserRepository userRepository,
                                    AttendTeacFourUtils attendTeacUtils,
                                    SchoolRepository schoolRepository,
                                    AttendTeacTwoUtils attendTeacTwoUtils,
                                    AttendanceTeacherMapper mapper,
                                    ActivitiService activitiService,
                                    ActorRepository actorRepository,
                                    AttendTeacMapper attendTeacMapper,
                                    VacationTeacherService vacationTeacherService,
                                    VacationTeacherRepository vacationTeacherRepository,
                                    SchedulingPersonRepository schedulingPersonRepository,
                                    AttendanceTeacherRepository attendanceTeacherRepository,
                                    HolidaysWeekService holidaysWeekService, EmployeeRepository employeeRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.attendTeacUtils = attendTeacUtils;
        this.schoolRepository = schoolRepository;
        this.attendTeacTwoUtils = attendTeacTwoUtils;
        this.mapper = mapper;
        this.activitiService = activitiService;
        this.actorRepository = actorRepository;
        this.attendTeacMapper = attendTeacMapper;
        this.vacationTeacherService = vacationTeacherService;
        this.vacationTeacherRepository = vacationTeacherRepository;
        this.schedulingPersonRepository = schedulingPersonRepository;
        this.attendanceTeacherRepository = attendanceTeacherRepository;
        this.holidaysWeekService = holidaysWeekService;
        this.employeeRepository = employeeRepository;
    }



    /**
     * 教师打卡
     * 如果多次打卡，则只更新上午或者下午的签退的时间
     * @param actor
     * @return
     */
    public AttendanceTeacherDTO create(Actor actor){
        Long date = Long.parseLong(LocalDate.now().toString().replace("-",""));
        Integer schoolId = actor.getSchoolId();

        // 区分打卡类型，一天两次还是一天四次
        SchedulingPerson schedulingPerson = schedulingPersonRepository.findByActorId(actor.getId());
        if (schedulingPerson == null){
            throw new IllegalArgumentException("没有分配排班");
        }
        String type = schedulingPerson.getScheduling().getSchedulingType();

        // 判断昨天考勤情况
        yester(actor,schedulingPerson);

        // 查询当天是否有打卡记录
        AttendanceTeacher attendanceTeacher = getRepository().findByActorIdAndCreatedAt(actor.getId(), date);

        // type:  1:打 4 次卡   2:打 2 次卡
        Integer IType = Integer.valueOf(type);
        if (IType.equals(one)){
            attendanceTeacher = four(attendanceTeacher, schedulingPerson);
        }
        if (IType.equals(two)){
            attendanceTeacher = two(attendanceTeacher, schedulingPerson);
        }

        attendanceTeacher = new AttendanceTeacher(
                attendanceTeacher.getId(),
                actor.getId(),
                schoolId,
                null,
                0,
                attendanceTeacher.getAmStartTime(),     // AS
                attendanceTeacher.getAmEndTime(),       // AE
                attendanceTeacher.getPmStartTime(),     // PS
                attendanceTeacher.getPmEndTime(),       // PE
                attendanceTeacher.getAst(),
                attendanceTeacher.getAet(),
                attendanceTeacher.getPst(),
                attendanceTeacher.getPet(),
                date,
                attendanceTeacher.getIsFull(),
                attendanceTeacher.getStatus(),
                attendanceTeacher.getTime()
        );
        attendanceTeacher = getRepository().save(attendanceTeacher);
        return mapper.toDTO(attendanceTeacher);
    }

    public void yester(Actor actor, SchedulingPerson schedulingPerson){
        boolean isgo = true;
        boolean isweek = false;
        Integer schoolId = actor.getSchoolId();
        // 计算昨天是否满勤
        LocalDate date = LocalDate.now();
        // 计算昨天
        date = date.plusDays(-1);

        // 验证是否为工作日
        while(!isweek){
            isgo = holidaysWeekService.isWeekend(date.atStartOfDay().toInstant(ZoneOffset.UTC));
            if (isgo){
                date = date.plusDays(-1);
            }else{
                isweek = true;
            }
        }
        Long yesterday = Long.parseLong(date.toString().replace("-",""));
        AttendanceTeacher attendacneTeacherYesterday = attendanceTeacherRepository.findBySchoolIdAndCreatedAtAndActorId(schoolId, yesterday, actor.getId());
        if (attendacneTeacherYesterday != null) {
            IsAttendacne attendacneAll = attendTeacTwoUtils.isAttendacneTwo(attendTeacMapper.toDTO(attendacneTeacherYesterday), schedulingPerson);
            if (attendacneAll.getIsAll()) {
                attendacneTeacherYesterday.setStatus(VacationTeacherUtil.VACATIONTYPE.ALL);  // 满勤
            } else {
                attendacneTeacherYesterday.setStatus(VacationTeacherUtil.VACATIONTYPE.UNALL);    // 不满勤
                attendacneTeacherYesterday.setTimes(attendacneAll.getTime());
            }
        }else{
            attendacneTeacherYesterday = new AttendanceTeacher();
            attendacneTeacherYesterday.setStatus(VacationTeacherUtil.VACATIONTYPE.UNALL);
            attendacneTeacherYesterday.setTimes(new Long(28800));
            attendacneTeacherYesterday.setCreatedAt(yesterday);
            attendacneTeacherYesterday.setActorId(actor.getId());
            attendacneTeacherYesterday.setIsFull(false);
            attendacneTeacherYesterday.setSchoolId(actor.getSchoolId());
        }
        attendanceTeacherRepository.save(attendacneTeacherYesterday);
    }

    /**
     * 一天四次打卡
     * @param attendanceTeacher
     * @param schedulingPerson
     * @return
     */
    public AttendanceTeacher  four(AttendanceTeacher attendanceTeacher, SchedulingPerson schedulingPerson){

        LocalTime sAmStartTime = schedulingPerson.getScheduling().getAmStartTime();
        LocalTime sAmEndTime = schedulingPerson.getScheduling().getAmEndTime();
        LocalTime sPmStartTime = schedulingPerson.getScheduling().getPmStartTime();
        LocalTime sPmEndTinme = schedulingPerson.getScheduling().getPmEndTime();
        LocalTime now = LocalTime.now();

        if (attendanceTeacher == null){
            attendanceTeacher = new AttendanceTeacher();
        }

        // 上午
        // 上午签退之后，可以签下午的签到。如果上午不到签退时间就签退了，则每次打卡时间为更新上午签退时间。如果上午没有签退，到下午签到的时间后上午的签退就不能签了
        if (attendanceTeacher.getAmStartTime() == null){     // 上午签到
            attendanceTeacher.setAmStartTime(Instant.now());
            attendanceTeacher.setStatus(VacationTeacherUtil.VACATIONTYPE.UNALL);
            // 判断是否迟到
            if (now.isBefore(sAmStartTime)){
                attendanceTeacher.setAst(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
            }else {
                attendanceTeacher.setAst(VacationTeacherUtil.ATTENDANCESTATUS.LATE);
            }
            // 如果在请假或者出差等状态下提前回来等原因打卡，则重置状态
            attendanceTeacher.setStatus(null);
        }else if(now.isBefore(sPmStartTime)){       // 如果上午签退时间在规定签退时间之前，则视为早退。如果继续打卡，则更新签退时间    上午签退
            // 如果已经签退的时间在规定签退时间之前，则一直更新；如果已经签退的时间在规定的签退时间之后，则开始签下午的签到
            // 先判断当前时间是否在规定下午签到之前，如果不是，则早退；
            if (now.isBefore(sAmEndTime)){
                attendanceTeacher.setAmEndTime(Instant.now());
                attendanceTeacher.setAet(VacationTeacherUtil.ATTENDANCESTATUS.LEAVE);    // 早退
                // 如果当前时间在规定上午签退之后下午签到之前，则先判断上午签退是否为空，如果为空，则赋值，状态为正常；如果不为空，，则可以下午签到，状态为正常
            } else if (sAmEndTime.isBefore(now) && sPmStartTime.isAfter(now)){
                // 空值判断
                LocalTime amEnd = null;
                if (attendanceTeacher.getAmEndTime() != null){
                    amEnd = LocalDateTime.ofInstant(attendanceTeacher.getAmEndTime(), ZoneId.systemDefault()).toLocalTime();
                }
                if (attendanceTeacher.getAmEndTime() == null || amEnd.isBefore(sAmEndTime)) {
                    attendanceTeacher.setAmEndTime(Instant.now());
                    attendanceTeacher.setAet(VacationTeacherUtil.ATTENDANCESTATUS.ALL);  // 正常
                }else{
                    if (attendanceTeacher.getPmStartTime() == null){
                        attendanceTeacher.setPmStartTime(Instant.now());
                        attendanceTeacher.setPst(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
                    }
                }
            }
        }else if (attendanceTeacher.getAmEndTime() == null){ // 下午签到的时候 如果上午签退没签退，则先上午签退
            attendanceTeacher.setAmEndTime(Instant.now());
            attendanceTeacher.setAet(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
        }
        // 如果当前时间在规定下午签到时间之后，则先判断下午签到时间是否为空，为空则赋值，状态为迟到；如果不为空，则下午签退，状态
        else if (attendanceTeacher.getPmStartTime() == null) { // 如果下午签到为null
            attendanceTeacher.setPmStartTime(Instant.now());
            attendanceTeacher.setPst(VacationTeacherUtil.ATTENDANCESTATUS.LATE);
        } else{
            // 下午签退
            attendanceTeacher.setPmEndTime(Instant.now());
            if (now.isBefore(sPmEndTinme)){
                attendanceTeacher.setPet(VacationTeacherUtil.ATTENDANCESTATUS.LEAVE);
            }else {
                attendanceTeacher.setPet(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
            }
            // 保存数据
            getRepository().save(attendanceTeacher);

            // 计算当天是否满勤
            IsAttendacne attendacneAll = attendTeacUtils.isAttendacneAll(attendTeacMapper.toDTO(attendanceTeacher), schedulingPerson);
            attendanceTeacher.setIsFull(attendacneAll.getIsAll());
            if (attendacneAll.getIsAll()){
                attendanceTeacher.setStatus(VacationTeacherUtil.VACATIONTYPE.ALL);  // 当天为满勤
            } else {
                attendanceTeacher.setStatus(VacationTeacherUtil.VACATIONTYPE.UNALL);    // 当天不满勤
                attendanceTeacher.setTimes(attendacneAll.getTime());
            }

        }
        return attendanceTeacher;
    }

    /**
     * 一天打两次卡
     * @param attendanceTeacher
     * @param schedulingPerson
     * @return
     */
    public AttendanceTeacher two(AttendanceTeacher  attendanceTeacher, SchedulingPerson schedulingPerson){
        Instant i = Instant.now();
        i.atOffset(ZoneOffset.UTC);
        LocalTime now = LocalTime.now();
        LocalTime sAmStartTime = schedulingPerson.getScheduling().getAmStartTime();
        LocalTime sPmEndTinme = schedulingPerson.getScheduling().getPmEndTime();

        // 当天第一次签到，新增一条
        if (attendanceTeacher == null){
            attendanceTeacher = new AttendanceTeacher();
            attendanceTeacher.setAmStartTime(Instant.now());
            // 判断是否迟到
            if (now.isBefore(sAmStartTime)){
                attendanceTeacher.setAst(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
            }else {
                attendanceTeacher.setAst(VacationTeacherUtil.ATTENDANCESTATUS.LATE);
            }
        }else {
            // 如果早上没有打卡，
            if (attendanceTeacher == null) {
                attendanceTeacher = new AttendanceTeacher();
                attendanceTeacher.setPmEndTime(Instant.now());
                // 判断是否迟到
                if (now.isBefore(sAmStartTime)){
                    attendanceTeacher.setAst(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
                }else {
                    attendanceTeacher.setAst(VacationTeacherUtil.ATTENDANCESTATUS.LATE);
                }
            } else {
                // 下午签退判断
                attendanceTeacher.setPmEndTime(Instant.now());
                if (now.isBefore(sPmEndTinme)){
                    attendanceTeacher.setPet(VacationTeacherUtil.ATTENDANCESTATUS.LEAVE);
                }else {
                    attendanceTeacher.setPet(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
                }
                getRepository().save(attendanceTeacher);

                // 计算当天是否满勤
                IsAttendacne attendacneAll = attendTeacTwoUtils.isAttendacneTwo(attendTeacMapper.toDTO(attendanceTeacher), schedulingPerson);
                if (attendacneAll.getIsAll()){
                    attendanceTeacher.setStatus(VacationTeacherUtil.VACATIONTYPE.ALL);  // 当天为满勤
                } else {
                    attendanceTeacher.setStatus(VacationTeacherUtil.VACATIONTYPE.UNALL);    // 当天不满勤
                    attendanceTeacher.setTimes(attendacneAll.getTime());
                }

            }
        }
        return attendanceTeacher;
    }

    /**
     * 教师普通查询，按照教师的actorId查询
     * @param schoolId
     * @param date
     * @param actorId
     * @return
     */
    public AttendTeacDTO query(Integer schoolId, LocalDate date, Actor actor, Integer actorId) {

        // 两种用法：如果actorIdx不为空，则是管理员查询某个员工的信息，如果为空，则是管理员查看自己或者用户自己查询自己的信息
        if (actorId == null){
            actorId = actor.getId();
        }
        Long lookDate = Long.parseLong(date.atStartOfDay().toLocalDate().toString().replace("-", ""));
        AttendTeacDTO attendTeacDTO = new AttendTeacDTO();
        AttendanceTeacher attendanceTeacher = attendanceTeacherRepository.findBySchoolIdAndCreatedAtAndActorId(schoolId, lookDate, actorId);
        if(attendanceTeacher == null){
            return null;
        }else {
            Integer attId = Integer.valueOf(attendanceTeacher.getId().toString());
            List<VacationTeacher> list = vacationTeacherService.findBySchoolIdAndActorIdAndAttendanceTeacherId(schoolId, actorId, attId);
            attendTeacDTO = attendTeacMapper.toDTO(attendanceTeacher);
            attendTeacDTO.setList(list);
        }
        return attendTeacDTO;
    }

    /**
     * 员工申请
     * @param form
     * @return
     */
    public void applyFor(Actor actor, RetroAttendanceTeacherForm form) {
        Integer schoolId = actor.getSchoolId();
        Integer attendanceTeacherId = form.getAttendanceTeacherId();
        User user = userRepository.getOne(actor.getUserId());

        // 如果form.getAttendanceTeacherId为空，则根据时间跟actorId确定要补签的是哪一个考勤
        if (form.getAttendanceTeacherId() == null){
            Instant instant = form.getStartTime();
            String str = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate().toString().replace("-", "");
            Long startTime = Long.parseLong(str);
            AttendanceTeacher attendanceTeacher = attendanceTeacherRepository.findByActorIdAndCreatedAt(actor.getId(), startTime);
            if (attendanceTeacher == null){
                attendanceTeacher = new AttendanceTeacher(
                        actor.getId(),
                        schoolId,
                        startTime,
                        VacationTeacherUtil.VACATIONTYPE.RETOR
                );
                attendanceTeacher = attendanceTeacherRepository.save(attendanceTeacher);
            }
            attendanceTeacherId = Integer.valueOf(attendanceTeacher.getId().toString());
        }
        VacationTeacher vacationTeacher = new VacationTeacher(
                actor.getId(),
                user.getUsername(),
                schoolId,
                attendanceTeacherId,
                form.getRetroType(),
                form.getDesc(),
                user.getPortrait(),
                form.getStartTime(),
                form.getEndTime(),
                form.getPortrait(),
                Instant.now(),
                null,
                VacationTeacherUtil.ISGONE.CHECKED,
                VacationTeacherUtil.YES_NO.NO
        );
        // 启动请假流程，给上层管理员推送消息请求
        activitiService.startProcess(actor.getId(), vacationTeacher.getAttendanceTeacherId() ,"1", form.getRetroType() , vacationTeacher.getDesc());
        vacationTeacherService.save(vacationTeacher);
    }

    /**
     * 教师周期打卡查询
     * @param specialDate
     * @return
     */
    public Collection<AttendTeacDTO> queryPeriod(Actor actor,
                                                 PeriodTeacType periodType,
                                                 LocalDate specialDate) {
        Long first = null;
        Long second = null;
        first = Long.parseLong(specialDate.withDayOfMonth(1).toString().replace("-", ""));
        second = Long.parseLong(specialDate.withDayOfMonth(specialDate.lengthOfMonth()).toString().replace("-", ""));

        // 查询时间范围内全部打卡信息
        Collection<AttendTeacDTO> attendTeac = attendTeacMapper.toDTOs(getRepository().findBySchoolIdAndActorIdAndCreatedAtBetween(actor.getSchoolId(), actor.getId(), first, second));

        return attendTeac;
    }

    public Collection<AttendTeacDTO> queryFull(Integer schoolId, Integer actorId, Actor actor, boolean isTrue, LocalDate date) {

        School school = schoolRepository.findOne(actor.getSchoolId());
        Long first = null;
        Long second = null;

        // 判断周期时间
        Map<String, Long> lmap = period(school, date);
        first = lmap.get("first");
        second = lmap.get("second");

        Collection<AttendTeacDTO> attendTeacDTOS = new ArrayList<>();

        attendTeacDTOS = actorId == null ? attendTeacMapper.toDTOs(getRepository().findBySchoolIdAndIsFullAndCreatedAtBetween(schoolId, isTrue, first, second))
                : attendTeacMapper.toDTOs(getRepository().findBySchoolIdAndActorIdAndIsFullAndCreatedAtBetween(schoolId, actor.getId(), isTrue, first, second));

        for (AttendTeacDTO atd : attendTeacDTOS){
            List<VacationTeacher> vtl = vacationTeacherService.findBySchoolIdAndActorIdAndAttendanceTeacherId(schoolId, actor.getId(), atd.getId());
            atd.setList(vtl);
        }

        return attendTeacDTOS;
    }

    public Map<String, Object> queryByAll(Actor actor, LocalDate date) {

        List<String> list = new ArrayList<>();
        for (int i = 1; i < date.getDayOfMonth(); i++){
            LocalDate localDate = date.withDayOfMonth(i);
            LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.now());
            boolean go = holidaysWeekService.isWeekend(localDateTime.toInstant(ZoneOffset.UTC));
            if (go){    // 返回为true，是周六日或者是节假日
                continue;
            }

            // 判断这一天有没有打卡或者缺勤
            String dateToStr = localDate.format(DateTimeFormatter.ofPattern("yyyy-M-d"));
            Long one = Long.parseLong(localDate.toString().replace("-", ""));
            AttendanceTeacher attendanceTeacher = getRepository().findByActorIdAndCreatedAt(actor.getId(), one);
            if (attendanceTeacher == null){
                list.add(dateToStr);
            }else if (attendanceTeacher.getAst() == null
                    || attendanceTeacher.getAet() == null
                    || attendanceTeacher.getPst() == null
                    || attendanceTeacher.getPet() == null) {
                list.add(dateToStr);

            }else {
                int as = attendanceTeacher.getAst().ordinal();
                int ae = attendanceTeacher.getAet().ordinal();
                int ps = attendanceTeacher.getPst().ordinal();
                int pe = attendanceTeacher.getPet().ordinal();
                if (as != 0 || ae != 0 || ps != 0 || pe != 0){
                    list.add(dateToStr);
                }
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exception",list);
        return map;
    }

    public VacationTeacher retor(Actor actor, RetorForm form) {
        Integer actorId = actor.getId();
        Integer attendanceTeacherId = form.getAttendanceTeacherId();

        SchedulingPerson schedulingPerson = schedulingPersonRepository.findByActorId(actorId);
        LocalTime eas = schedulingPerson.getScheduling().getAmStartTime();
        LocalTime epe = schedulingPerson.getScheduling().getPmEndTime();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(form.getRetorTime(), ZoneId.systemDefault());
        Instant first = localDateTime.withHour(eas.getHour()).withMinute(eas.getMinute()).withSecond(eas.getSecond()).toInstant(ZoneOffset.ofHours(8));
        Instant second = localDateTime.withHour(epe.getHour()).withMinute(epe.getMinute()).withSecond(epe.getSecond()).toInstant(ZoneOffset.ofHours(8));

        VacationTeacherUtil.VACATIONTYPE reason = VacationTeacherUtil.VACATIONTYPE.RETOR;
        VacationTeacherUtil.ISGONE isgone = VacationTeacherUtil.ISGONE.CHECKED;
        User user = userRepository.findOne(actor.getUserId());

        // 打卡id为空效验并查询
        if (form.getAttendanceTeacherId() == null){
            String str = LocalDateTime.ofInstant(form.getRetorTime(), ZoneId.systemDefault()).toLocalDate().toString().replace("-", "");
            Long createdAt = Long.parseLong(str);
            AttendanceTeacher attendanceTeacher = attendanceTeacherRepository.findByActorIdAndCreatedAt(actorId, createdAt);
            if (attendanceTeacher == null){
                attendanceTeacher = new AttendanceTeacher(
                        actorId,
                        actor.getSchoolId(),
                        createdAt,
                        VacationTeacherUtil.VACATIONTYPE.UNALL
                );
                attendanceTeacherRepository.save(attendanceTeacher);
            }
            attendanceTeacherId = Integer.valueOf(attendanceTeacher.getId().toString());
        }
        VacationTeacher vacationTeacher = new VacationTeacher(
                actorId,
                user.getUsername(),
                actor.getSchoolId(),
                attendanceTeacherId,
                reason,
                form.getContent(),
                first,
                second,
                user.getPortrait(),
                isgone,
                Instant.now()
        );
        // 消息推送
        VacationTeacher teacher = vacationTeacherRepository.save(vacationTeacher);
        Integer massageId = Integer.valueOf(vacationTeacher.getId().toString());
        activitiService.startProcess(actorId, massageId,"1", reason , vacationTeacher.getDesc());
        return teacher;
    }

    public AttendTeacDTO findByActorIdAndDate(Actor actor, LocalDate spdate) {
        Integer actorId = actor.getId();
        Long date = Long.parseLong(spdate.toString().replace("-", ""));
        AttendTeacDTO attendTeacDTO = attendTeacMapper.toDTO(getRepository().findByActorIdAndCreatedAt(actorId, date));
        return attendTeacDTO;
    }

    public Map<String, Object> countAttendance(Actor actor, LocalDate specialDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer schoolId = actor.getSchoolId();
        Integer actorId = actor.getId();
        Long first = null;
        Long second = null;

        School school = schoolRepository.findOne(schoolId);

        // 判断周期时间
        Map<String, Long> lmap = period(school, specialDate);
        first = lmap.get("first");
        second = lmap.get("second");

        // 出勤次数
        Integer countAtt = getRepository().countByActorIdAndCreatedAtBetween(actorId, first, second);
        // 迟到次数
        Integer late = getRepository().countByLate(actorId, first, second);
        // 早退次数
        Integer leave = getRepository().countByleave(actorId, first, second);
        // 请假次数
        Integer vacation = getRepository().countByVacation(actorId, first, second);
        // 缺勤天数，没有打卡
        int j = 0;
        for (int i = 1; i < specialDate.lengthOfMonth(); i++) {
            LocalDate localDate = specialDate.withDayOfMonth(i);
            LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.now());
            boolean go = holidaysWeekService.isWeekend(localDateTime.toInstant(ZoneOffset.UTC));
            if (go) {    // 返回为true，是周六日或者是节假日
                continue;
            }

            // 判断这一天有没有打卡或者缺勤
            String dateStr = localDate.toString().replace("-", "");
            Long one = Long.parseLong(dateStr);
            AttendanceTeacher attendanceTeacher = getRepository().findByActorIdAndCreatedAt(actorId, one);
            if (attendanceTeacher == null){
                j++;
            }
        }
        Integer delAtt = j;

        map.put("count", countAtt);
        map.put("late", late);
        map.put("leave", leave);
        map.put("vacation", vacation);
        map.put("del", delAtt);
        return map;
    }

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

    public List<Map<String, Object>> findTeacherRate(Actor actor, LocalDate localDate) {
        List<Map<String, Object>> list =  new ArrayList<>();
        Integer schoolId = actor.getSchoolId();
        Integer days = holidaysWeekService.days(localDate); // 应出勤天数
        Map<String, Object> mapDays = new HashMap<>();
        mapDays.put("days", days);
        list.add(mapDays);

        LocalDate a = localDate.withDayOfMonth(1);
        LocalDate b = localDate.withDayOfMonth(localDate.lengthOfMonth());

        String attDate = a.toString() + "至" + b.toString();

        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        Long first = Long.parseLong(a.format(formatter));
        Long second = Long.parseLong(b.format(formatter));
        List<Actor> actors = this.actorRepository.findAllBySchoolIdAndTypesContains(schoolId, ActorType.TEACHER);
        for(Actor actorx : actors){
            Integer actorId = actorx.getId();
            User user = userRepository.findOne(actorx.getUserId());
            Map<String, Object> map = new HashMap<>();
            Collection<AttendanceTeacher> attendanceTeachers = this.attendanceTeacherRepository.queryBySchoolIdAndActorIdAndCreatedAtBetween(schoolId, actorId, first, second);
            Float attRate = (float)(attendanceTeachers.size()) / days * 100;
            map.put("name", user.getUsername());
            map.put("attCount", attendanceTeachers.size());
            map.put("attRate", attRate);
            map.put("attDate", attDate);
            map.put("actorId", actorId);
            list.add(map);
        }
        return list;
    }

    public void addDate(Integer num, LocalDate localDate) {
        LocalDate one = localDate.withDayOfMonth(1);
        LocalDate two = localDate.withDayOfMonth(localDate.lengthOfMonth());
        List<Actor> actorsTeach = actorRepository.findAllBySchoolIdAndTypesContains(1, ActorType.TEACHER);
        List<Actor> actorsManag = actorRepository.findAllBySchoolIdAndTypesContains(1, ActorType.MANAGER);
        actorsTeach.addAll(actorsManag);

        for (int i = localDate.lengthOfMonth(); i > 0; i--){
            Long date = Long.parseLong(one.toString().replace("-",""));
            for (Actor actor : actorsTeach){
                AttendanceTeacher attendanceTeacher = getRepository().findByActorIdAndCreatedAt(actor.getId(), date);
                if (attendanceTeacher != null){
                    continue;
                }
                attendanceTeacher = new AttendanceTeacher();
                String random = RandomStringUtils.random(1,1,4,false, false);
                attendanceTeacher.setActorId(actor.getId());
                attendanceTeacher.setSchoolId(actor.getSchoolId());
                attendanceTeacher.setCreatedAt(date);
                attendanceTeacher.setTimes(new Long(0));
                date--;
                switch (random){
                    case "1":
                        attendanceTeacher.setAmStartTime(Instant.ofEpochSecond(1544142600));
                        attendanceTeacher.setAst(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
                        break;
                    case "2":
                        attendanceTeacher.setAmEndTime(Instant.ofEpochSecond(1544155200));
                        attendanceTeacher.setAet(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
                        break;
                    case "3":
                        attendanceTeacher.setPmStartTime(Instant.ofEpochSecond(1544158800));
                        attendanceTeacher.setPst(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
                        break;
                    case "4":
                        attendanceTeacher.setPmEndTime(Instant.ofEpochSecond(1544176800));
                        attendanceTeacher.setPet(VacationTeacherUtil.ATTENDANCESTATUS.ALL);
                        break;
                }
                this.getRepository().save(attendanceTeacher);
            }
        }
    }
}
