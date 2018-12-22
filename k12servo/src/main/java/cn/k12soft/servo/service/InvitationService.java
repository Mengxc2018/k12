package cn.k12soft.servo.service;

import static cn.k12soft.servo.module.empFlowRate.domain.FolwEnum.PROBATION;
import static org.slf4j.LoggerFactory.getLogger;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.domain.enumeration.Permission;
import cn.k12soft.servo.module.duty.domain.Duty;
import cn.k12soft.servo.module.duty.repositpry.DutyRepository;
import cn.k12soft.servo.module.empFlowRate.domain.FolwEnum;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.domain.EmployeeBasic;
import cn.k12soft.servo.module.employees.repository.EmployeeBasicRepository;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;
import cn.k12soft.servo.module.revenue.repository.TeacherSocialSecurityRepository;
import cn.k12soft.servo.module.schedulingPerson.repository.SchedulingPersonRepository;
import cn.k12soft.servo.repository.*;
import cn.k12soft.servo.third.aliyun.AliyunSMSService;
import cn.k12soft.servo.web.form.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:wangfenghua@bonusstudio.com">fenghua.wang</a> Created on 2017/7/15.
 */
@Service
@Transactional
public class InvitationService {

    private static final Logger logger = getLogger(InvitationService.class);
    private static final int SECRET_LENGTH = 6;

    private final SchoolRepository schoolRepository;
    private final InvitationRepository invitationRepository;
    private final GuardianRepository guardianRepository;
    private final UserService userService;
    private final AliyunSMSService smsService;
    private final RoleService roleService;
    private final ActorRepository actorRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;
    private final DutyRepository dutyRepository;
    private final SchedulingPersonRepository schedulingPersonRepository;
    private final EmployeeBasicRepository employeeBasicRepository;
    private final TeacherSocialSecurityRepository teacherSocialSecurityRepository;
    private final PasswordEncoder passwordEncoder;

    public InvitationService(SchoolRepository schoolRepository, InvitationRepository invitationRepository,
                             GuardianRepository guardianRepository,
                             UserService userService,
                             AliyunSMSService smsService,
                             RoleService roleService,
                             ActorRepository actorRepository,
                             UserRepository userRepository,
                             StudentRepository studentRepository,
                             EmployeeRepository employeeRepository, DutyRepository dutyRepository,
                             SchedulingPersonRepository schedulingPersonRepository, EmployeeBasicRepository employeeBasicRepository, TeacherSocialSecurityRepository teacherSocialSecurityRepository, PasswordEncoder passwordEncoder) {
        this.schoolRepository = schoolRepository;
        this.invitationRepository = invitationRepository;
        this.guardianRepository = guardianRepository;
        this.userService = userService;
        this.smsService = smsService;
        this.roleService = roleService;
        this.actorRepository = actorRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.employeeRepository = employeeRepository;
        this.dutyRepository = dutyRepository;
        this.schedulingPersonRepository = schedulingPersonRepository;
        this.employeeBasicRepository = employeeBasicRepository;
        this.teacherSocialSecurityRepository = teacherSocialSecurityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void inviteGuardian(Actor invitor, GuardianForm form) {
//        int secretCode = generateSecretCode();
        String mobile = form.getMobile();
        Integer schoolId = invitor.getSchoolId();
        Invitation invitation = createInvitation(schoolId, mobile, form.getUsername());
        Actor actor = invitation.getActor();
        if (actor.getTypes().add(ActorType.PATRIARCH)) {
            actor.addType(ActorType.PATRIARCH);
            Role rootRole = roleService.getRoleByName(schoolId, "root");
            actor.addRole(rootRole);
            actorRepository.save(actor);
        }

        Student student = studentRepository.findOne(form.getKidId());
        Guardian guardian = new Guardian(actor.getId(), student, form.getRelationType());
        guardianRepository.save(guardian);

//        invitation.secretCode(secretCode);
//        invitationRepository.save(invitation);
//        sendSecretCode(secretCode, mobile);
    }

    public void inviteTeacher(Actor invitor, TeacherForm form) {
//        int secretCode = generateSecretCode();
        String mobile = form.getMobile();
        Integer schoolId = invitor.getSchoolId();
        Invitation invitation = createInvitation(schoolId, mobile, form.getUsername());
        Actor actor = invitation.getActor();
        if (actor.addType(ActorType.TEACHER)) {
            Role rootRole = roleService.getRoleByName(schoolId, "root");
            actor.addRole(rootRole);//TODO:: make role correct
            actorRepository.save(actor);
        }
//        invitation.secretCode(secretCode);
//        invitationRepository.save(invitation);
//        sendSecretCode(secretCode, mobile);
    }

    public void inviteTeacherTest(Actor invitor, Integer dutyId, TeacherTestForm form) {
//        int secretCode = generateSecretCode();
        String mobile = form.getMobile();
        Integer schoolId = invitor.getSchoolId();
        Invitation invitation = createInvitation(schoolId, mobile, form.getUsername());
        Actor actor = invitation.getActor();
        if (actor.addType(ActorType.TEACHER)) {
            Role rootRole = roleService.getRoleByName(schoolId, "root");
            actor.addRole(rootRole);//TODO:: make role correct
            actorRepository.save(actor);
        }

        // 验证角色数量
//        User user = userRepository.queryByMobile(mobile);
//        Collection<Actor> actorCollection = actorRepository.findByUserId(user.getId());
//        if (actorCollection.size() == 0){
//            invitation.secretCode(secretCode);      // 生成邀请码
//            invitationRepository.save(invitation);  // 保存邀请码
//            sendSecretCode(secretCode, mobile);   // 如果存在角色，则不需要发送验证码，直接使用
//        }

        // 插入员工表
        // 员工工号生成策略：学校id+四位数
        StringBuffer numMix = new StringBuffer(String.valueOf(schoolId));
        StringBuffer numMax = new StringBuffer(String.valueOf(schoolId));
        numMix.append("0000");
        numMax.append("9999");

        // 某校员工编号最大值
        Long num = employeeRepository.findMaxActorNumAndActorNumBetween(numMix.toString(), numMax.toString());
        if (num == null) {
            num = new Long(schoolId + "0000");
        }
        num++;

        // 添加入职基本信息
        EmployeeBasic employeeBasic = new EmployeeBasic(
                form.getJoinTime(),
                form.getIsOfficial(),
                form.getIsGraduate(),
                form.getIsHasDiploma(),
                actor.getSchoolId()
        );
        // 是否转正、试用期处理
        String probation = form.getProbation() == null ? "0" : form.getProbation();
        if (form.getIsOfficial()) {
            employeeBasic.setOfficialAt(form.getJoinTime());
            probation = "0";
        } else {
            probation = form.getProbation();
        }
        employeeBasic.setHasSocial(form.getIsHasSocial());
        employeeBasic.setProbation(probation);
        employeeBasic.setHasSocial(form.getIsHasSocial());

        // 创建员工表信息
        Duty duty = dutyRepository.findOne(Long.parseLong(dutyId.toString()));
        School school = schoolRepository.findOne(schoolId);
        Employee employee = new Employee(
                duty,
                actor.getId(),
                Integer.valueOf(num.toString()),
                null,
                actor.getSchoolId(),
                true,
                employeeBasic,
                "0",    // 加班时间，新建时默认为0
                "0",    // 调休时间，新建时默认为0
                school.getAnnual(),
                school.getSick(),
                school.getBarth(),
                school.getBarthWith(),
                school.getMarry(),
                school.getFuneral(),
                Instant.now()
        );
        employeeRepository.save(employee);
        employeeBasicRepository.save(employee.getEmployeeBasic());
        // 邀请员工后插入工资及社保表
        teacherSocialSecurity(actor, employee, form);

    }

    // 工资薪金
    public TeacherSocialSecurity teacherSocialSecurity(Actor actor, Employee employee, TeacherTestForm form) {
        User user = userRepository.findOne(actor.getUserId());
        TeacherSocialSecurity teacherSocialSecurity = new TeacherSocialSecurity(
                actor.getId().toString(),
                employee,
                user.getUsername(),
                employee.getDuty().getName(),
                employee.getDuty().getName()
        );
        // 工资判断
        // 是否转正，转正前80%工资，转正100%工资
        Float salaryf = new Float(form.getSalary());
        Float actualPayroll = new Float(0f);
        salaryf = salaryf == null ? 0 : salaryf;

        // 试用、正式判断
        FolwEnum folwEnum = FolwEnum.JOINBY;

        if (!form.getIsOfficial()) {
            BigDecimal bigOne = new BigDecimal(0.8);
            BigDecimal bigTwo = new BigDecimal(salaryf);
            BigDecimal bigThree = bigOne.multiply(bigTwo).setScale(2, BigDecimal.ROUND_HALF_UP);
            actualPayroll = bigThree.floatValue();
            folwEnum = PROBATION;
        }
        teacherSocialSecurity.setType(folwEnum);
        teacherSocialSecurity.setSalary(salaryf);
        teacherSocialSecurity.setActualPayroll(actualPayroll);
        teacherSocialSecurity.setSchoolId(actor.getSchoolId());
        teacherSocialSecurity.setTaxIncome(0f);

        teacherSocialSecurityRepository.save(teacherSocialSecurity);
        return teacherSocialSecurity;
    }

    public void inviteManager(Integer schoolId, ManagerForm form) {
//        int secretCode = generateSecretCode();
        String mobile = form.getMobile();
        Invitation invitation = createInvitation(schoolId, mobile, form.getUsername());
        Actor actor = invitation.getActor();
        if (actor.addType(ActorType.MANAGER)) {
            Role rootRole = roleService.getRoleByName(schoolId, "root");
            if (rootRole == null) {
                rootRole = new Role(schoolId, "root", "root role");
                rootRole.getPermissions().add(Permission.ALL);
                rootRole = roleService.save(rootRole);
            }
            actor.addRole(rootRole);
            actorRepository.save(actor);
        }
//        invitation.secretCode(secretCode);
//        invitationRepository.save(invitation);
//        sendSecretCode(secretCode, mobile);
    }

    private Invitation createInvitation(Integer schoolId, String mobile, String username) {
        return invitationRepository
                .findOneByMobile(mobile)
                .orElseGet(() -> {
                    User user = userService.findByMobile(mobile).orElseGet(() -> userService.createUserNotActivated(mobile, username, false));
                    String name = user.getUsername() == null ? "此家伙很懒，还没改名字" : user.getUsername();
                    Actor assoc = user.getActors().stream()
                            .filter(actor -> Objects.equals(actor.getSchoolId(), schoolId))
                            .findAny()
                            .orElseGet(() -> {
                                Actor actor = new Actor(schoolId, user.getId());
                                actor = actorRepository.save(actor);
                                user.getActors().add(actor);

                                // 默认密码：123456
                                user.setPassword(passwordEncoder.encode("123456"));
                                user.setUsername(name);
                                user.setUpdate(true);
                                user.setOneSelf(false);
                                userRepository.save(user);
                                return actor;
                            });
                    return new Invitation().actor(assoc).mobile(mobile);
                });
    }

    private void sendSecretCode(int secretCode, String mobile) {
        Object message = smsService.sendInvitationCode(mobile, secretCode);
        logger.info("Send secret code:{} to mobile:{} ,message:{}",
                secretCode, mobile, message);
    }

    private static int generateSecretCode() {
        return Integer.parseInt(RandomStringUtils.randomNumeric(SECRET_LENGTH));
    }

    public void createOpManager(Actor actor, ActorForm form) {
        int type = form.getType();
        ActorType actorType = null;

        String name = form.getName();
        String mobile = form.getMobile();
        User user = userService.createUser4OpManager(mobile, name); // 默认密码是123456
        Actor groupActor = new Actor(0, user.getId());

        if (type == ActorType.REGION.ordinal()) {
            if (!actor.getTypes().contains(ActorType.GROUP)) {
                return;
            }
            actorType = ActorType.REGION;
            groupActor.setRegionId(actor.getRegionId());
        } else if (type == ActorType.PROVINCE.ordinal()) {
            if (!actor.getTypes().contains(ActorType.GROUP)
                    && !actor.getTypes().contains(ActorType.REGION)) {
                return;
            }
            actorType = ActorType.PROVINCE;
            groupActor.setProvinceId(actor.getProvinceId());
        } else if (type == ActorType.CITY.ordinal()) {
            if (!actor.getTypes().contains(ActorType.GROUP)
                    && !actor.getTypes().contains(ActorType.REGION)
                    && !actor.getTypes().contains(ActorType.PROVINCE)) {
                return;
            }
            actorType = ActorType.CITY;
            groupActor.setCityId(actor.getCityId());
        } else {
            return;
        }


        groupActor.addType(actorType);
        groupActor.setParentId(actor.getId());

        this.actorRepository.save(groupActor);
    }

    public void createGroupManager(Actor actor, ActorForm form) {
        String name = form.getName();
        String mobile = form.getMobile();
        User user = userService.createUser4OpManager(mobile, name); // 默认密码是123456
        Actor groupActor = new Actor(0, user.getId());
        groupActor.addType(ActorType.GROUP);
        groupActor.setGroupId(actor.getGroupId());
        this.actorRepository.save(groupActor);
    }

    public List<User> findByPid(Integer actorId) {
        Actor actor = actorRepository.getOne(actorId);
        List<Actor> actorList = actorRepository.findByParentId(actor.getId());
        List<User> userList = new ArrayList<>();
        for (Actor subActor : actorList) {
            userList.add(userRepository.getOne(subActor.getUserId()));
        }
        return userList;
    }

    public void delSub(Actor actor, Integer userId) {
        User user = userRepository.getOne(userId);
        if (user == null) {
            return;
        }
        actorRepository.deleteByUserId(userId);
        userRepository.delete(user);
    }

}
