package cn.k12soft.servo.module.VacationTeacher.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendPeriodTeac.PeriodTeacType;
import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendanceTeacher;
import cn.k12soft.servo.module.AttendanceTeacher.repository.AttendanceTeacherRepository;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.VacationTeacher.domain.dto.VacationTeacherDTO;
import cn.k12soft.servo.module.VacationTeacher.form.VacationTeacherForm;
import cn.k12soft.servo.module.VacationTeacher.mapper.VacaTimeMapper;
import cn.k12soft.servo.module.VacationTeacher.mapper.VacationTeacherMapper;
import cn.k12soft.servo.module.VacationTeacher.repository.VacationTeacherRepository;
import cn.k12soft.servo.module.activiti.apply.vacationService.ActivitiService;
import cn.k12soft.servo.module.applyFor.service.ApplyForService;
import cn.k12soft.servo.module.applyFor.service.mapper.ApplyForMapper;
import cn.k12soft.servo.module.holidaysWeek.service.HolidaysWeekService;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.service.SchoolService;
import cn.k12soft.servo.service.UserService;
import cn.k12soft.servo.third.aliyun.AliyunPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

import static cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.ISGONE.CHECKED;
import static cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.VACATIONTYPE.BIRTH;
import static cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.VACATIONTYPE.BIRTHWITH;
import static cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.VACATIONTYPE.MARRIAGE;

@Service
@Transactional
public class VacationTeacherService extends AbstractRepositoryService<VacationTeacher, Long, VacationTeacherRepository> {

    private final VacationTeacherMapper mapper;
    private final UserRepository userRepository;
    private final ActivitiService activitiService;

    private final HolidaysWeekService holidaysWeekService;
    private final AttendanceTeacherRepository attendanceTeacherRepository;

    @Autowired
    public VacationTeacherService(VacationTeacherRepository repository,
                                  VacationTeacherMapper mapper,
                                  ActivitiService activitiService,
                                  UserRepository userRepository,
                                  HolidaysWeekService holidaysWeekService,
                                  AttendanceTeacherRepository attendanceTeacherRepository) {
        super(repository);
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.activitiService = activitiService;
        this.holidaysWeekService = holidaysWeekService;
        this.attendanceTeacherRepository = attendanceTeacherRepository;
    }

    public VacationTeacherDTO create(VacationTeacherForm form, Actor actor ,Integer type) {

        User user = userRepository.getOne(actor.getUserId());
        Integer schoolId = actor.getSchoolId();
        Integer actorId = actor.getId();
        LocalDate now = LocalDate.now();
        LocalDate formDate = LocalDateTime.ofInstant(form.getFromDate(), ZoneId.systemDefault()).toLocalDate();
        Integer attendanceTeacherId = null;

        // 判断当天有没有打卡信息
        // 判断请假开始时间是不是当天
        if (formDate.equals(now)){
            Long nows = Long.parseLong(now.toString().replace("-",""));
            AttendanceTeacher attendanceTeacher = attendanceTeacherRepository.findBySchoolIdAndCreatedAtAndActorId(schoolId, nows, actorId);
            if (attendanceTeacher != null){
                attendanceTeacherId = Integer.valueOf(attendanceTeacher.getId().toString());
            }
        }

        // 判断除去不上班周六日的请假时长
        VacationTeacherUtil.VACATIONTYPE reasion = form.getReasion();
        String vacationTime = form.getVacationTime();
        int j = 0;
        if (reasion != BIRTH || reasion != BIRTHWITH || reasion != MARRIAGE){
            Instant fromDate = form.getFromDate();
            Instant toDate = form.getToDate();
            // 判断请假时长是否大于一天
            long days = ChronoUnit.DAYS.between(fromDate, toDate);
            if (days >= 1){
                for (int i = 1; i <= days; i++){
                    boolean isweekend = holidaysWeekService.isWeekend(fromDate);
                    if (!isweekend){// 如果返回false，则累计不上班周六日天数
                        j++;
                    }
                    fromDate = fromDate.plusMillis(86400000);
                    if (fromDate.equals(toDate) || fromDate.isAfter(toDate)){
                        break;
                    }
                }
                days = j;
                vacationTime = String.valueOf(days*3600*8);
            }
        }

        VacationTeacher vacationTeacher = new VacationTeacher(
                actor.getId(),
                user.getUsername(),
                schoolId,
                attendanceTeacherId,
                form.getReasion(),
                form.getPortrait(),
                form.getDesc(),
                form.getFromDate(),
                form.getToDate(),
                user.getPortrait(),
                Instant.now(),
                vacationTime,
                CHECKED,
                form.getIsBusiness()
        );

        // 保存数据，请假流程开始
        getRepository().save(vacationTeacher);

        // actorId, 该条请假信息的id，请假的原因或者其他
        activitiService.startProcess(actor.getId(), Integer.valueOf(vacationTeacher.getId().toString()), String.valueOf(type), form.getReasion(), vacationTeacher.getDesc());

        return  mapper.toDTO(vacationTeacher);
    }

//    /**
//     * 暂未用到
//     *
//     * 指定教师按周、月查询
//     * @param actor
//     * @param type
//     * @param date
//     * @return
//     */
//    public VacationPeriodTeac query(Actor actor, PeriodTeacType type,  LocalDate date) {
//
//        Pair<LocalDate, LocalDate> dateRange = type.toPeriodRange(date);
//        Integer actorId = actor.getId();
//        Integer schoolId = actor.getSchoolId();
//
//        School school = schoolService.get(schoolId);
//
//        List<Object[]> countings = getRepository().counting(
//            actorId,
//            schoolId,
//            dateRange.getFirst().atStartOfDay(),
//            dateRange.getSecond().plusDays(1).atStartOfDay());
//
//        List<VacationTeacCounting> obj = countings.stream()
//            .map(args -> new VacationTeacCounting(
//                args[0].toString(),
//                args[1].toString(),
//                args[2].toString(),
//                args[3].toString(),
//                args[4].toString()
//                ))
//            .collect(Collectors.toList());
//
//        return new VacationPeriodTeac(actorId, school, type, dateRange.getFirst(), dateRange.getSecond(), obj);
//    }

    public Collection<VacationTeacherDTO> queryAll(Actor actor, Integer month, Integer year) {
        Integer schoolId = actor.getSchoolId();
        LocalDate date = LocalDate.of(year, month, 1);
        return mapper.toDTOs(getRepository().findByschoolIdAndTime(schoolId, date));
    }

    public Collection<VacationTeacherDTO> queryByType(Actor actor, LocalDate date, VacationTeacherUtil.ISGONE isGone, PeriodTeacType type) {

        int actorId = actor.getId();
        int schoolId = actor.getSchoolId();
        int month = date.getMonth().getValue();

        Pair<LocalDate, LocalDate> pair = type.toPeriodRange(date);
        LocalDate startOfDay = pair.getFirst();
        LocalDate endOfDay = pair.getSecond().withMonth(month+1).withDayOfMonth(1);

        LocalTime lo = LocalTime.of(0,0);

        Instant startDay = LocalDateTime.of(startOfDay, lo).toInstant(ZoneOffset.UTC);
        Instant endDay = LocalDateTime.of(endOfDay, lo).toInstant(ZoneOffset.UTC);

        Sort sort = new Sort(Sort.Direction.DESC, "createdAt");

        return mapper.toDTOs(getRepository().findAllByActorIdAndSchoolIdAndIsGoneAndCreatedAtBetween(actorId, schoolId, isGone, startDay, endDay, sort));
    }

    public VacationTeacher getByEarliestFormDateAndActorId(Integer attendanceTeacherId, Integer actorId) {
        return getRepository().getByEarliestFormDateAndTeacherId(attendanceTeacherId, actorId);
    }

    public VacationTeacher getByAmOutAndDateAndActorId(LocalDateTime attAmEndTime, Integer attId, Integer teacherIdx) {
        return getRepository().getAmOutByTimeAndActorIdAndAttId(attAmEndTime, attId, teacherIdx);
    }

    public VacationTeacher getByPmOutAndDateAndActorId(LocalDateTime attAmEndTime, Integer attId, Integer teacherIdx) {
        return getRepository().getPmOutByTimeAndActorIdAndAttId(attAmEndTime, attId, teacherIdx);
    }

    public VacationTeacher getByPmInFormDateAndActorId(Integer attId, Integer actorIdx) {
        return getRepository().getByPmInFormDateAndTeacherId(attId, actorIdx);
    }

    public List<VacationTeacher> findBySchoolIdAndActorIdAndAttendanceTeacherId(Integer schoolId, Integer actorId, Integer attendanceTeacherId) {
        return getRepository().findBySchoolIdAndActorIdAndAttendanceTeacherId(schoolId, actorId, attendanceTeacherId);
    }

    public Collection<VacationTeacherDTO> findBySchoolIdAndActorIdAndCreatedAtBetween(Actor actor, LocalDate specialDate) {
        Instant first = specialDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = specialDate.withDayOfMonth(specialDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
        Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
        return mapper.toDTOs(getRepository().findBySchoolIdAndActorIdAndCreatedAtBetween(actor.getSchoolId(), actor.getId(), first, second, sort));
    }

}
