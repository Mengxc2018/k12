package cn.k12soft.servo.module.applyFor.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendanceTeacher;
import cn.k12soft.servo.module.AttendanceTeacher.repository.AttendanceTeacherRepository;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.*;
import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.VacationTeacher.repository.VacationTeacherRepository;
import cn.k12soft.servo.module.activiti.processNode.domain.Node;
import cn.k12soft.servo.module.activiti.processNode.repository.NodeRepository;
import cn.k12soft.servo.module.applyFor.domain.ApplyFor;
import cn.k12soft.servo.module.applyFor.domain.dto.ApplyForDTO;
import cn.k12soft.servo.module.applyFor.domain.form.ApplyForForm;
import cn.k12soft.servo.module.applyFor.repository.ApplyForRepository;
import cn.k12soft.servo.module.applyFor.service.mapper.ApplyForMapper;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.holidaysWeek.service.HolidaysWeekService;
import cn.k12soft.servo.module.scheduling.domain.Scheduling;
import cn.k12soft.servo.module.schedulingPerson.domian.SchedulingPerson;
import cn.k12soft.servo.module.schedulingPerson.repository.SchedulingPersonRepository;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.third.aliyun.AliyunPushService;
import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.VACATIONTYPE.BUSINESS;

@Service
@Transactional
public class ApplyForService extends AbstractRepositoryService<ApplyFor, Long, ApplyForRepository> {

    private final static Logger log = LoggerFactory.getLogger(ApplyForService.class);

    private final TaskService taskService;
    private final NodeRepository nodeRepository;
    private final UserRepository userRepository;
    private final ApplyForMapper applyForMapper;
    private final RuntimeService runtimeService;
    private final ActorRepository actorRepository;
    private final AliyunPushService aliyunPushService;
    private final EmployeeRepository employeeRepository;
    private final ApplyForRepository applyForRepository;
    private final HolidaysWeekService holidaysWeekService;
    private final VacationTeacherRepository vacationTeacherRepository;
    private final SchedulingPersonRepository schedulingPersonRepository;
    private final AttendanceTeacherRepository attendanceTeacherRepository;


    @Autowired
    protected ApplyForService(ApplyForRepository repository,
                              TaskService taskService,
                              NodeRepository nodeRepository,
                              UserRepository userRepository,
                              ApplyForMapper applyForMapper,
                              RuntimeService runtimeService,
                              ActorRepository actorRepository,
                              AliyunPushService aliyunPushService,
                              EmployeeRepository employeeRepository,
                              ApplyForRepository applyForRepository,
                              HolidaysWeekService holidaysWeekService,
                              VacationTeacherRepository vacationTeacherRepository,
                              SchedulingPersonRepository schedulingPersonRepository,
                              AttendanceTeacherRepository attendanceTeacherRepository) {
        super(repository);
        this.taskService = taskService;
        this.nodeRepository = nodeRepository;
        this.userRepository = userRepository;
        this.applyForMapper = applyForMapper;
        this.runtimeService = runtimeService;
        this.actorRepository = actorRepository;
        this.aliyunPushService = aliyunPushService;
        this.employeeRepository = employeeRepository;
        this.applyForRepository = applyForRepository;
        this.holidaysWeekService = holidaysWeekService;
        this.vacationTeacherRepository = vacationTeacherRepository;
        this.schedulingPersonRepository = schedulingPersonRepository;
        this.attendanceTeacherRepository = attendanceTeacherRepository;
    }


    public Collection<ApplyForDTO> query(Integer actorId, Integer processType, LocalDate date) {
        Instant first = date.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant seconds = date.atStartOfDay().withDayOfMonth(date.lengthOfMonth()).toInstant(ZoneOffset.UTC);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return applyForMapper.toDTOs(applyForRepository.findAllByMasterIdAndProcessTypeAndAuditResultIsNullAndCreateTimeBetween(actorId, processType, first, seconds, sort));
    }

    public Collection<ApplyForDTO> queryByPass(Integer actorId, Integer processType) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return applyForMapper.toDTOs(applyForRepository.findAllByMasterIdAndProcessTypeAndAuditResultIsNotNull(actorId, processType, sort));
    }


    /**
     * 审批流程
     *
     * @param form
     * @return
     */
    public ApplyForDTO updateByVacation(ApplyForForm form, Actor actor) {

        boolean isTrue = true;
        ISGONE isGone = ISGONE.CHECKED;
        ISGONE auditResult = ISGONE.PASS;
        ApplyFor applyFor = getRepository().findOne(Long.parseLong (form.getId().toString()));

        if (applyFor.getAuditResult() != null){
            throw new IllegalArgumentException("该流程已审批");
        }

        // 查询当前任务
        Task task2 = taskService.createTaskQuery().processInstanceId(applyFor.getProcessInstanceId()).singleResult();

        // 判断当前审批人是不是目前流程的审批人
        if (applyFor.getMasterId().equals(actor.getId())){
            // 判断申请是否通过
            if (form.getAuditResult().equals(ISGONE.PASS)) {
                // 完成审批任务
                taskService.complete(task2.getId());
                auditResult = ISGONE.PASS;
            } else {
                // 审批不通过，结束流程
                isTrue = false;
                auditResult = ISGONE.UNPASS;
                isGone = ISGONE.UNPASS;

                // 推送消息，申请不成功
                aliyunPushService.sendTeaApplyRejectNotification(this.getMobile(applyFor));
            }
        }else {
            return null;
        }
        // 不论申请通不通过，申请记录还是要保存的
        applyFor = new ApplyFor(
                form.getId(),
                applyFor.getMasterId(),
                applyFor.getMasterName(),
                applyFor.getMasterPortrait(),
                applyFor.getProcessInstanceId(),
                applyFor.getProcessType(),
                applyFor.getVacationType(),
                isGone,
                applyFor.getTaskId(),
                applyFor.getActorId(),
                applyFor.getUserName(),
                applyFor.getPortrait(),
                form.getMessageId(),
                auditResult,
                form.getComment(),
                applyFor.getCreateTime(),
                Instant.now()
        );
        ApplyForDTO applyForDTO = applyForMapper.toDTO(getRepository().save(applyFor));

        VACATIONTYPE applyType = form.getApplyType();
        switch (applyType) {
            case BUSINESS:
                business(isTrue, task2, isGone, applyFor);
                break;
            case OVERTIME:
                overTime(isTrue, task2, isGone, applyFor);
                break;
            default:
                vacation(isTrue, task2, isGone, applyFor);
                break;
        }

        return applyForDTO;
    }


    /**
     * 申请处理
     *
     * @param applyFor
     * @param isTrue
     * @param task2
     * @param isGone
     * @param applyFor
     */
    public void vacation(boolean isTrue, Task task2, ISGONE isGone, ApplyFor applyFor) {

        Integer massageId = Integer.valueOf(applyFor.getMassageId());

        VacationTeacher vacationTeacher = vacationTeacherRepository.findOne(Long.parseLong(massageId.toString()));

        // 请假申请为未通过
        if (!isTrue) {
            // 删除流程
            runtimeService.deleteProcessInstance(task2.getProcessInstanceId(), task2.getId());
            // 删除节点信息
            taskService.deleteTask(applyFor.getTaskId());
            // 更新员工请假状态
            vacationTeacher = vacationTeacherRepository.findOne(new Long(massageId));
            vacationTeacher.setIsGone(ISGONE.UNPASS);
            vacationTeacherRepository.save(vacationTeacher);

            // 更新参与审批人所在的整个流程状态
            List<ApplyFor> applyForList = applyForRepository.findByProcessInstanceId(applyFor.getProcessInstanceId());
            for (ApplyFor a : applyForList) {
                a.setIsGone(ISGONE.UNPASS);
                applyForRepository.save(a);
            }
        } else {
            // 申请通过
            // 查询该申请信息的任务节点 判断当前节点之后是否还有任务
            Task task4 = taskService.createTaskQuery().processInstanceId(applyFor.getProcessInstanceId()).singleResult();

            // 如果通过，判断当前节点是不是“end”，如果是，则结束
            if (task4 != null) {

                // 如果不为空，说明还有下一节点，下一审批人，在下一审批人推送消息
                nextTask(task4, applyFor, massageId, task2, isGone);

            } else {

                // 更新参与审批人所在的整个流程状态
                List<ApplyFor> applyForList = applyForRepository.findByProcessInstanceId(applyFor.getProcessInstanceId());
                for (ApplyFor a : applyForList) {
                    a.setIsGone(ISGONE.PASS);
                    applyForRepository.save(a);
                }

                Instant formDate = vacationTeacher.getFormDate();
                Instant toDate = vacationTeacher.getToDate();
                Instant createdAt = vacationTeacher.getCreatedAt();
                Long created = Long.parseLong(LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault()).toLocalDate().toString().replace("-", ""));
                Long days = ChronoUnit.DAYS.between(formDate, toDate);
                BigDecimal time = new BigDecimal(0);
                int j = 0;
                // 判断是否超一天
                if (days > 1) {
                    Long num = days * 8 * 3600; // 请假时长
                    time = new BigDecimal(num); // 核算员工假期

                    // 判断请假当天有没有打卡
                    AttendanceTeacher arrendanceTeacher = attendanceTeacherRepository.findByActorIdAndCreatedAt(applyFor.getActorId(), created);
                    if (arrendanceTeacher != null){
                        formDate = formDate.plusMillis(86400000);
                    }
                    // 保存打卡，从开始时间到结束时间
                    Actor actor = actorRepository.getOne(vacationTeacher.getActorId());
                    for (int i = 1; i <= days; i++) {
                        // 排除周六日
                        boolean weekend = holidaysWeekService.isWeekend(formDate);
                        if (weekend){
                            Long date = Long.parseLong(LocalDateTime.ofInstant(formDate, ZoneId.systemDefault()).toLocalDate().toString().replace("-", ""));
                            AttendanceTeacher attendanceTeacher = new AttendanceTeacher(
                                    actor.getId(),
                                    actor.getSchoolId(),
                                    date,
                                    vacationTeacher.getReason(),
                                    Long.parseLong("0"),
                                    num
                            );
                            attendanceTeacherRepository.save(attendanceTeacher);
                        }
                        formDate = formDate.plusMillis(86400000);

                    }
                } else {
                    Long timeOfSeconds = ChronoUnit.SECONDS.between(vacationTeacher.getFormDate(), vacationTeacher.getToDate());
                    time = new BigDecimal(timeOfSeconds);
                }

                // 判断请假开始时间是否在签退时间之前或相等，结束时间在签退时间之后，则请假时间抵消缺勤时间，判断打卡状态
                // 一种只是请假一两个小时，然后打卡是正常
                // 一种请假签退后，不回来的情况
                // 一种上午没有签退，下午没有签到、下午有签退（上午的签退跟下午的签到跟下午的签退时间很接近）

                // 判断请假类型
                SchedulingPerson schedulingPerson = schedulingPersonRepository.findByActorId(vacationTeacher.getActorId());
                Employee employee = employeeRepository.findByActorId(vacationTeacher.getActorId());
                switch (vacationTeacher.getReason()) {
                    case AFFAIR:    // 事假
                        break;
                    case REST:      // 调休
                        String rest = employee.getRest();
                        BigDecimal bigRest = new BigDecimal(rest);
                        String subtRest = bigRest.subtract(time).setScale(0).toString();
                        employee.setAnnual(subtRest);
                        break;
                    case ANNUAL:    // 年假
                        String annual = employee.getAnnual();
                        BigDecimal bigAnnual = new BigDecimal(annual);
                        String subtAnnual = bigAnnual.subtract(time).setScale(0).toString();
                        employee.setAnnual(subtAnnual);
                        break;
                    case SICK:      // 病假
                        String sick = employee.getSick();
                        BigDecimal bigSick = new BigDecimal(sick);
                        String subtSick = bigSick.subtract(time).setScale(0).toString();
                        employee.setSick(subtSick);
                        break;
                    case BIRTH:     // 产假
                        String birth = employee.getBarth();
                        BigDecimal bigBirth = new BigDecimal(birth);
                        String subtBirth = bigBirth.subtract(time).setScale(0).toString();
                        employee.setBarth(subtBirth);
                        break;
                    case BIRTHWITH: // 陪产假
                        String birthWith = employee.getBarthWith();
                        BigDecimal bigBirthWith = new BigDecimal(birthWith);
                        String subtBirthWith = bigBirthWith.subtract(time).setScale(0).toString();
                        employee.setBarthWith(subtBirthWith);
                        break;
                    case MARRIAGE:  // 婚假
                        String marriage = employee.getMarry();
                        BigDecimal bigMarriage = new BigDecimal(marriage);
                        String subtBigMarriage = bigMarriage.subtract(time).setScale(0).toString();
                        employee.setBarthWith(subtBigMarriage);
                        break;
                    case FUNERAL:   // 丧假
                        String funeral = employee.getMarry();
                        BigDecimal BigFuneral = new BigDecimal(funeral);
                        String subtFuneral = BigFuneral.subtract(time).setScale(0).toString();
                        employee.setBarthWith(subtFuneral);
                        break;
                    case RETOR:     // 补签
                        // 如果是打卡的补签，需更新times缺勤时间，isFull是否是全勤更新
                        AttendanceTeacher attendanceTeacher = attendanceTeacherRepository.findOne(new Long(vacationTeacher.getAttendanceTeacherId()));

                        // 选择补签时间点
                        Scheduling scheduling = schedulingPerson.getScheduling();
                        LocalTime ltas = LocalDateTime.ofInstant(attendanceTeacher.getAmStartTime(), ZoneId.systemDefault()).toLocalTime();
                        LocalTime ltae = LocalDateTime.ofInstant(attendanceTeacher.getAmEndTime(), ZoneId.systemDefault()).toLocalTime();
                        LocalTime ltps = LocalDateTime.ofInstant(attendanceTeacher.getPmStartTime(), ZoneId.systemDefault()).toLocalTime();
                        LocalTime lepe = LocalDateTime.ofInstant(attendanceTeacher.getPmEndTime(),ZoneId.systemDefault()).toLocalTime();
                        Instant date = vacationTeacher.getFormDate();
                        LocalDate localDate = LocalDateTime.ofInstant(date, ZoneId.systemDefault()).toLocalDate();
                        if (scheduling.getAmStartTime().isAfter(ltas)){
                            Instant instant = LocalDateTime.of(localDate, scheduling.getAmStartTime()).toInstant(ZoneOffset.UTC);
                            attendanceTeacher.setAmStartTime(instant);
                            attendanceTeacher.setAst(ATTENDANCESTATUS.ALL);
                        }
                        if (scheduling.getAmEndTime().isBefore(ltae)){
                            Instant instant = LocalDateTime.of(localDate, scheduling.getAmEndTime()).toInstant(ZoneOffset.UTC);
                            attendanceTeacher.setAmEndTime(instant);
                            attendanceTeacher.setAet(ATTENDANCESTATUS.ALL);
                        }
                        if (scheduling.getPmStartTime().isAfter(ltps)){
                            Instant instant = LocalDateTime.of(localDate, scheduling.getPmStartTime()).toInstant(ZoneOffset.UTC);
                            attendanceTeacher.setPmStartTime(instant);
                            attendanceTeacher.setPst(ATTENDANCESTATUS.ALL);
                        }
                        if (scheduling.getPmEndTime().isAfter(lepe)){
                            Instant instant = LocalDateTime.of(localDate, scheduling.getPmEndTime()).toInstant(ZoneOffset.UTC);
                            attendanceTeacher.setPmEndTime(instant);
                            attendanceTeacher.setPet(ATTENDANCESTATUS.ALL);
                        }

                        // 判断补签类型
                        String type = schedulingPerson.getScheduling().getSchedulingType();
                        if (type.equals("1")) {
                            if (scheduling.getAmStartTime().isAfter(ltas)){
                                Instant instant = LocalDateTime.of(localDate, scheduling.getAmStartTime()).toInstant(ZoneOffset.UTC);
                                attendanceTeacher.setAmStartTime(instant);
                                attendanceTeacher.setAst(ATTENDANCESTATUS.ALL);
                            }
                            if (scheduling.getAmEndTime().isBefore(ltae)){
                                Instant instant = LocalDateTime.of(localDate, scheduling.getAmEndTime()).toInstant(ZoneOffset.UTC);
                                attendanceTeacher.setAmEndTime(instant);
                                attendanceTeacher.setAet(ATTENDANCESTATUS.ALL);
                            }
                            if (scheduling.getPmStartTime().isAfter(ltps)){
                                Instant instant = LocalDateTime.of(localDate, scheduling.getPmStartTime()).toInstant(ZoneOffset.UTC);
                                attendanceTeacher.setPmStartTime(instant);
                                attendanceTeacher.setPst(ATTENDANCESTATUS.ALL);
                            }
                            if (scheduling.getPmEndTime().isAfter(lepe)){
                                Instant instant = LocalDateTime.of(localDate, scheduling.getPmEndTime()).toInstant(ZoneOffset.UTC);
                                attendanceTeacher.setPmEndTime(instant);
                                attendanceTeacher.setPet(ATTENDANCESTATUS.ALL);
                            }
                        } else if (type.equals("2")) {
                            if (scheduling.getAmStartTime().isAfter(ltas)){
                                Instant instant = LocalDateTime.of(localDate, scheduling.getAmStartTime()).toInstant(ZoneOffset.UTC);
                                attendanceTeacher.setAmStartTime(instant);
                                attendanceTeacher.setAst(ATTENDANCESTATUS.ALL);
                            }
                            if (scheduling.getPmEndTime().isAfter(lepe)){
                                Instant instant = LocalDateTime.of(localDate, scheduling.getPmEndTime()).toInstant(ZoneOffset.UTC);
                                attendanceTeacher.setPmEndTime(instant);
                                attendanceTeacher.setPet(ATTENDANCESTATUS.ALL);
                            }
                        }

                        // 补签后打卡状态判断
                        attendanceTeacher.setIsFull(true);
                        attendanceTeacher.setTimes(new Long(0));
                        attendanceTeacher.setStatus(VACATIONTYPE.ALL);
                        attendanceTeacherRepository.save(attendanceTeacher);
                        break;
                }

                // 申请结果保存
                vacationTeacher.setIsGone(ISGONE.PASS);
                vacationTeacherRepository.save(vacationTeacher);
                // 员工信息更新
                employeeRepository.save(employee);

                // 推送消息，申请成功
                aliyunPushService.sendTeaApplyPassNotification(this.getMobile(applyFor));
            }
        }
    }

    /**
     * 加班申请
     *
     * @param applyFor
     * @param isTrue
     * @param task2
     * @param isGone
     * @param applyFor
     */
    public void overTime(boolean isTrue, Task task2, ISGONE isGone, ApplyFor applyFor) {

        Integer massageId = applyFor.getMassageId();

        VacationTeacher vacationTeacher = vacationTeacherRepository.findOne(Long.parseLong(massageId.toString()));

        // 请假申请为未通过
        if (!isTrue) {
            deleteProcess(task2, applyFor, vacationTeacher);
        } else {
            // 申请通过
            Task task4 = taskService.createTaskQuery().processInstanceId(task2.getProcessInstanceId()).singleResult();

            // 如果不为空，说明还有下一节点，下一审批人
            if (task4 != null) {
                // 如果不为空，说明还有下一节点，下一审批人，在下一审批人推送消息
                nextTask(task4, applyFor, massageId, task2, isGone);
            } else {

                // 更新参与审批人所在的整个流程状态
                List<ApplyFor> applyForList = applyForRepository.findByProcessInstanceId(applyFor.getProcessInstanceId());
                for (ApplyFor applyfor : applyForList) {
                    applyfor.setIsGone(ISGONE.PASS);
                    applyForRepository.save(applyfor);
                }

                Employee employee = employeeRepository.findByActorId(applyFor.getActorId());
                Long time = ChronoUnit.SECONDS.between(vacationTeacher.getFormDate(), vacationTeacher.getToDate());
                BigDecimal bigTime = new BigDecimal(time);
                // 判断加班条件，是保存到加班还是保存到调休
                if (vacationTeacher.getIsBusiness().equals(YES_NO.YES)) {
                    BigDecimal bigRest = new BigDecimal(employee.getRest());
                    bigRest = bigRest.add(bigTime).setScale(0);
                    employee.setRest(bigRest.toString());
                    employeeRepository.save(employee);

                } else if (vacationTeacher.getIsGone().equals(YES_NO.NO)) {
                    BigDecimal bigOverTime = new BigDecimal(employee.getOvertime());
                    bigOverTime = bigOverTime.add(bigTime).setScale(0);
                    employee.setOvertime(bigOverTime.toString());
                    employeeRepository.save(employee);
                }
                // 申请状态改为通过
                vacationTeacher.setIsGone(ISGONE.PASS);
                vacationTeacherRepository.save(vacationTeacher);

                // 推送消息，申请成功
                aliyunPushService.sendTeaApplyPassNotification(this.getMobile(applyFor));
            }
        }
    }

    public Collection<ApplyForDTO> queryDetail(Actor actor, Integer massageId, String processInstanceId) {
        Collection<ApplyForDTO> applyForDTOS = applyForMapper.toDTOs(getRepository().findByMassageIdAndProcessInstanceIdOrderByCreateTimeAsc(massageId, processInstanceId));

        // 获取当前审批人的actorId
        ApplyFor applyForNow = getRepository().findByMassageIdAndProcessInstanceIdAndAuditResultIsNull(massageId, processInstanceId);
        if (applyForNow == null){
            applyForNow = getRepository().findMaxCreateTimeByProcessInstanceId(processInstanceId);
        }
        String actorId = String.valueOf(applyForNow.getMasterId());

        //获取对应的流程， 通过该员工职务，找到使用哪个流程，获取请假流程的key
        Employee employee = employeeRepository.findByActorId(applyForNow.getActorId());
        // 找到基层职位的流程
        Node node = nodeRepository.findByDiscernDutyId(Integer.valueOf(employee.getDuty().getId().toString()));

        // 如果基层职位为空，判断是否为非基础职位，如果还为空，则报错误
        if(node == null){
            List<Node> nodes = nodeRepository.findByProcessType(Integer.valueOf(1));
            for (Node nodea : nodes) {
                String[] nodeByActorIds = nodea.getActorIds().split(",");
                for (String actorIdx : nodeByActorIds) {
                    if (actorId.equals(actorIdx)) {
                        node = nodea;
                        break;
                    }
                }
            }
        }

        Collection<ApplyForDTO> actorList = new ArrayList<>();

        String[] actors = node.getActorIds().split(",");
        int actorSize = actors.length-1;

        // 审批人组装
        String[] actorIds = node.getActorIds().split(",");
        List<String> strs = new ArrayList<>(Arrays.asList(actorIds));
        int m = 1;  // 跳节点，跳到哪个位置的人，从0开始数
        for (String actorIdx : actorIds){
            if (actorId.equals(actorIdx)) {
                break;
            }
            m++;
        }
        for (int i = m; i <= actorSize; i++) {
            Integer actorIdx = Integer.valueOf(strs.get(i));
            Actor actorx = actorRepository.findOne(actorIdx);
            User user = userRepository.findOne(actorx.getUserId());
            ApplyForDTO applyForDTO = new ApplyForDTO(
                    actorIdx,
                    user.getUsername(),
                    user.getPortrait(),
                    ""
            );

            actorList.add(applyForDTO);
        }
        applyForDTOS.addAll(actorList);

        return applyForDTOS;
    }

    public void business(boolean isTrue, Task task2, ISGONE isGone, ApplyFor applyFor) {

        Integer massageId = applyFor.getMassageId();

        VacationTeacher vacationTeacher = vacationTeacherRepository.findOne(Long.parseLong(massageId.toString()));

        // 请假申请为未通过
        if (!isTrue) {
            deleteProcess(task2, applyFor, vacationTeacher);
        } else {
            // 申请通过
            Task task4 = taskService.createTaskQuery().processInstanceId(task2.getProcessInstanceId()).singleResult();

            // 如果不为空，说明还有下一节点，下一审批人
            if (task4 != null) {

                // 如果不为空，说明还有下一节点，下一审批人，在下一审批人推送消息
                nextTask(task4, applyFor, massageId, task2, isGone);

            } else {

                // 更新参与审批人所在的整个流程状态
                List<ApplyFor> applyForList = applyForRepository.findByProcessInstanceId(applyFor.getProcessInstanceId());
                for (ApplyFor a : applyForList) {
                    a.setIsGone(ISGONE.PASS);
                    applyForRepository.save(a);
                }

                Instant formDate = vacationTeacher.getFormDate();
                Instant toDate = vacationTeacher.getToDate();
                Long days = ChronoUnit.DAYS.between(formDate, toDate);
                int j = 0;
                // 大于1，说明出差超过一天 判断是否超一天
                if (days > 1) {
                    for (int i = 1; i <= days; i++) {
                        String dateStr = LocalDateTime.ofInstant(formDate, ZoneId.systemDefault()).toLocalDate().toString().replace("-","");
                        Long date = Long.parseLong(dateStr);
                        boolean weekend = holidaysWeekService.isWeekend(formDate);
                        if (weekend) {
                            AttendanceTeacher attendanceTeacher = new AttendanceTeacher(
                                    vacationTeacher.getActorId(),
                                    vacationTeacher.getSchoolId(),
                                    date,
                                    BUSINESS
                            );
                            attendanceTeacherRepository.save(attendanceTeacher);
                        }
                        formDate = formDate.plusMillis(86400000);
                    }
                }
                vacationTeacher.setIsGone(ISGONE.PASS);
                vacationTeacherRepository.save(vacationTeacher);

                // 推送消息，申请成功
                aliyunPushService.sendTeaApplyPassNotification(this.getMobile(applyFor));
            }
        }
    }

    public Collection<ApplyForDTO> queryByDate(Integer actorId, Integer processType, LocalDate date) {

        LocalTime time = LocalTime.now();
        Instant first = LocalDateTime.of(date.withDayOfMonth(1), time).toInstant(ZoneOffset.UTC);
        Instant second = LocalDateTime.of(date.withDayOfMonth(date.lengthOfMonth()), time).toInstant(ZoneOffset.UTC);
        Collection<ApplyForDTO> lists = applyForMapper.toDTOs(getRepository().findByMasterIdAndProcessTypeAndCreateTimeBetweenAndAuditResultIsNotNull(actorId, processType, first, second));
        return lists;
    }

    public void deleteProcess(Task task2, ApplyFor applyFor, VacationTeacher vacationTeacher){
        // 删除流程
        runtimeService.deleteProcessInstance(task2.getProcessInstanceId(), task2.getId());
        // 删除节点信息
        taskService.deleteTask(applyFor.getTaskId());
        // 更新状态
        vacationTeacher.setIsGone(ISGONE.UNPASS);
        vacationTeacherRepository.save(vacationTeacher);

        // 更新参与审批人所在的整个流程状态
        List<ApplyFor> applyForList = applyForRepository.findByProcessInstanceId(applyFor.getProcessInstanceId());
        for (ApplyFor a : applyForList){
            a.setIsGone(ISGONE.UNPASS);
            applyForRepository.save(a);
        }
    }

    public Collection<ApplyForDTO> queryApplyDetail(Actor actor, Integer id) {
        List<ApplyFor> applyFors = getRepository().findByMassageId(id);
        ApplyFor applyFor = applyFors.get(0);
        Collection<ApplyForDTO> applyForDTOS = queryDetail(actor, id, applyFor.getProcessInstanceId());
        return applyForDTOS;
    }

    public void nextTask(Task task4, ApplyFor applyFor, Integer massageId, Task task2, ISGONE isGone){
        Actor actor = actorRepository.findOne(Integer.valueOf(task4.getAssignee()));
        User userByMaster = userRepository.findOne(actor.getUserId());
        ApplyFor applyFor2 = new ApplyFor(
                Integer.valueOf(task4.getAssignee()),
                userByMaster.getUsername(),
                userByMaster.getPortrait(),
                task4.getProcessInstanceId(),
                applyFor.getProcessType(),
                applyFor.getVacationType(),
                isGone,
                massageId,
                task2.getId(),
                applyFor.getActorId(),
                applyFor.getUserName(),
                applyFor.getPortrait(),
                Instant.now()
        );
        getRepository().save(applyFor2);
        // 推送消息
        aliyunPushService.sendTeaApplyNotification(userByMaster.getMobile());
    }

    public String getMobile(ApplyFor applyFor){
        Actor actorApplyFor = actorRepository.findOne(applyFor.getActorId());
        User user = userRepository.findOne(actorApplyFor.getUserId());
        return user.getMobile();
    }
}
