package cn.k12soft.servo.module.employeeFlow.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.empFlowRate.domain.FolwEnum;
import cn.k12soft.servo.module.employeeFlow.domain.EmployeeFlow;
import cn.k12soft.servo.module.employeeFlow.domain.dto.LeaveDTO;
import cn.k12soft.servo.module.employeeFlow.domain.dto.OfficialDTO;
import cn.k12soft.servo.module.employeeFlow.domain.form.Applyform;
import cn.k12soft.servo.module.employeeFlow.domain.form.LeaveForm;
import cn.k12soft.servo.module.employeeFlow.domain.form.OfficialForm;
import cn.k12soft.servo.module.employeeFlow.service.mapper.LeaveMapper;
import cn.k12soft.servo.module.employeeFlow.service.mapper.OfficialMapper;
import cn.k12soft.servo.module.employeeFlow.repositpry.EmployeeFlowRepository;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.domain.EmployeeBasic;
import cn.k12soft.servo.module.employees.repository.EmployeeBasicRepository;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;
import cn.k12soft.servo.module.revenue.repository.TeacherSocialSecurityRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.util.Times;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;

@Service
@Transactional
public class EmployeeFlowService extends AbstractRepositoryService<EmployeeFlow, Long, EmployeeFlowRepository> {

    private final LeaveMapper leaveMapper;
    private final OfficialMapper officialMapper;
    private final EmployeeRepository employeeRepository;
    private final EmployeeBasicRepository employeeBasicRepository;
    private final TeacherSocialSecurityRepository teacherSocialSecurityRepository;


    protected EmployeeFlowService(EmployeeFlowRepository repository, LeaveMapper leaveMapper, OfficialMapper officialMapper, EmployeeRepository employeeRepository, EmployeeBasicRepository employeeBasicRepository, TeacherSocialSecurityRepository teacherSocialSecurityRepository) {
        super(repository);
        this.leaveMapper = leaveMapper;
        this.officialMapper = officialMapper;
        this.employeeRepository = employeeRepository;
        this.employeeBasicRepository = employeeBasicRepository;
        this.teacherSocialSecurityRepository = teacherSocialSecurityRepository;
    }

    private static final String LEAVE = "leave";
    private static final String OFFICIAL = "official";
    private static final FolwEnum LEAVEBy = FolwEnum.LEAVEBY;
    private static final FolwEnum JOINBY = FolwEnum.JOINBY;


    public void become(Actor actor, OfficialForm form) {
        EmployeeFlow employeeFlow = new EmployeeFlow(
                actor.getId(),
                form.getUserName(),
                form.getDutyName(),
                actor.getSchoolId(),
                form.getJoinAt(),
                form.getDepartment(),
                form.getContent(),
                form.getSalaryDay(),
                Instant.now(),
                OFFICIAL
        );
        getRepository().save(employeeFlow);
    }

    public void leave(Actor actor, LeaveForm form) {
        EmployeeFlow employeeFlow = new EmployeeFlow(
                actor.getId(),
                form.getUserName(),
                actor.getSchoolId(),
                form.getJoinAt(),
                form.getContent(),
                form.getSalaryDay(),
                Instant.now(),
                LEAVE
        );
        getRepository().save(employeeFlow);
    }

    public Collection<LeaveDTO> findApplyLeave(Actor actor, Integer actorId, LocalDate localDate) {
        Pair pair = Times.getFirstAndSecond(localDate);
        Instant first = (Instant) pair.getFirst();
        Instant second = (Instant) pair.getSecond();
        Collection<LeaveDTO> leaveDTOS = actorId == null ? leaveMapper.toDTOs(getRepository().findBySchoolIdAndTypesAndCreatedAtBetween(actor.getSchoolId(), LEAVE, first, second, new Sort(Sort.Direction.DESC)))
                : leaveMapper.toDTOs(getRepository().findBySchoolIdAndActorIdAndTypesAndCreatedAtBetween(actor.getSchoolId(), actorId, LEAVE,  first, second, new Sort(Sort.Direction.DESC)));
        return leaveDTOS;
    }

    public Collection<OfficialDTO> findApplyOfficial(Actor actor, Integer actorId, LocalDate localDate) {
        Pair pair = Times.getFirstAndSecond(localDate);
        Instant first = (Instant) pair.getFirst();
        Instant second = (Instant) pair.getSecond();
        Collection<OfficialDTO> officialDTOS = actorId == null ? officialMapper.toDTOs(getRepository().findBySchoolIdAndTypesAndCreatedAtBetween(actor.getSchoolId(), OFFICIAL, first, second, new Sort(Sort.Direction.DESC)))
                : officialMapper.toDTOs(getRepository().findBySchoolIdAndActorIdAndTypesAndCreatedAtBetween(actor.getSchoolId(), actorId, OFFICIAL, first, second, new Sort(Sort.Direction.DESC)));
        return officialDTOS;
    }

    public void applyBecome(Actor actor, Applyform form) {
        Employee employee = employeeRepository.findOne(Long.parseLong(form.getId().toString()));
        EmployeeBasic employeeBasic = employee.getEmployeeBasic();
        if (form.getFolw().compareTo(JOINBY) > 0) {  // 转正
            employeeBasic.setOfficialAt(form.getDate());
            employeeBasic.setOfficial(form.getIsTransform());
            employeeBasic.setGraduate(form.getIsGraduate());
            employeeBasic.setHasDoploma(form.getIsHasDoploma());
            // 转正后修改工资信息
            updateSalary(employee.getActorId());
        } else if (form.getFolw().compareTo(LEAVEBy) > 0) {   // 离职
            employeeBasic.setLeave(form.getIsTransform());
            employeeBasic.setLeaveAt(form.getDate());
            // 标记员工离职
            employee.setShow(false);
            employeeRepository.save(employee);

            // 薪资表标记员工离职
            TeacherSocialSecurity teacherSocialSecurity = teacherSocialSecurityRepository.findByActorId(employee.getActorId().toString());
            teacherSocialSecurity.setType(FolwEnum.LEAVEBY);
            teacherSocialSecurityRepository.save(teacherSocialSecurity);

        }
        employeeBasicRepository.save(employeeBasic);
    }

    private void updateSalary(Integer actorId) {
        TeacherSocialSecurity teacherSocialSecurity = teacherSocialSecurityRepository.findByActorId(actorId.toString());
        float actual = teacherSocialSecurity.getActualPayroll();
        BigDecimal bigOne = new BigDecimal(teacherSocialSecurity.getActualPayroll());
        BigDecimal bigTwo = new BigDecimal(0.08);
        bigOne = bigOne.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
        teacherSocialSecurity.setActualPayroll(bigOne.floatValue());
        teacherSocialSecurityRepository.save(teacherSocialSecurity);
    }
}
