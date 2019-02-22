package cn.k12soft.servo.module.employees.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.duty.domain.Duty;
import cn.k12soft.servo.module.duty.repositpry.DutyRepository;
import cn.k12soft.servo.module.employeeFlow.repositpry.EmployeeFlowRepository;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.domain.EmployeeBasic;
import cn.k12soft.servo.module.employees.domain.dto.EmployeeDTO;
import cn.k12soft.servo.module.employees.domain.dto.EpleToSalDTO;
import cn.k12soft.servo.module.employees.domain.form.EmployeeForm;
import cn.k12soft.servo.module.employees.repository.EmployeeBasicRepository;
import cn.k12soft.servo.module.employees.service.mapper.EmployeeMapper;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.employees.service.mapper.EmployeeOfUserMapper;
import cn.k12soft.servo.module.employees.service.mapper.EpleToSalMapper;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.util.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.time.*;
import java.util.*;

@Transactional
@Service
public class EmployeeService extends AbstractRepositoryService<Employee, Long, EmployeeRepository>{

    private final EmployeeMapper employeeMapper;
    private final DutyRepository dutyRepository;
    private final UserRepository userRepository;
    private final ActorRepository actorRepository;
    private final EpleToSalMapper epleToSalMapper;
    private final SchoolRepository schoolRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeOfUserMapper employeeOfUserMapper;
    private final EmployeeFlowRepository employeeFlowRepository;
    private final EmployeeBasicRepository employeeBasicRepository;
    @Autowired
    protected EmployeeService(EmployeeRepository repository,
                              EmployeeMapper employeeMapper,
                              DutyRepository dutyRepository,
                              UserRepository userRepository,
                              ActorRepository actorRepository,
                              EpleToSalMapper epleToSalMapper,
                              SchoolRepository schoolRepository,
                              EmployeeRepository employeeRepository,
                              EmployeeOfUserMapper employeeOfUserMapper,
                              EmployeeFlowRepository employeeFlowRepository, EmployeeBasicRepository employeeBasicRepository) {
        super(repository);
        this.employeeMapper = employeeMapper;
        this.dutyRepository = dutyRepository;
        this.userRepository = userRepository;
        this.actorRepository = actorRepository;
        this.epleToSalMapper = epleToSalMapper;
        this.schoolRepository = schoolRepository;
        this.employeeRepository = employeeRepository;
        this.employeeOfUserMapper = employeeOfUserMapper;
        this.employeeFlowRepository = employeeFlowRepository;
        this.employeeBasicRepository = employeeBasicRepository;
    }

    public List<EmployeeDTO> create(Actor actor, EmployeeForm form) {
        List<EmployeeDTO> list = new ArrayList<>();
        Integer schoolId = actor.getSchoolId();
        String[] actorIds = form.getActorId().split(",");

        // 员工工号生成规则：学校ID + 四位数
        StringBuffer numMix = new StringBuffer(String.valueOf(schoolId));
        StringBuffer numMax = new StringBuffer(String.valueOf(schoolId));
        numMix.append("0000");
        numMax.append("9999");

        // 某校员工编号最大值
        Long num = employeeRepository.findMaxActorNumAndActorNumBetween(numMix.toString(),numMax.toString());
        if (num == null){
            num = new Long(schoolId+"0000");
        }

        for (String actorIdx : actorIds){
            Employee employee = getRepository().findByActorId(Integer.valueOf(actorIdx));
            EmployeeBasic employeeBasic = new EmployeeBasic();
            Actor actorx = actorRepository.findOne(Integer.valueOf(actorIdx));
            // 如果是空的，新增一条,加工号
            if (employee == null){
                num++;
                employee = new Employee();
                employee.setActorNum(Integer.valueOf(num.toString()));
                employee.setId(Long.parseLong(actorIdx));
            }
            // 如果不是空的，职务不是空的，返回员工已分配的错误
            if (employee != null && employee.getDuty() != null){
                throw new IllegalArgumentException("actorId为 " + actorIdx + " 的员工已分配职位，不能重复分配");
            }

            Duty duty = new Duty();
            // 如果不是空的，但是职务是空的，更新一下职务
            if (employee != null){
                employeeBasic = employee.getEmployeeBasic();
                if (employee.getDuty() == null){
                    duty = dutyRepository.findOne(Long.parseLong(form.getDutyId().toString()));
                }
            }

            // 产假陪产假判断
            String barth = "0";
            String birthWith = "0";
            User user = userRepository.getOne(actor.getUserId());
            // 如果没有性别，默认女性
            if (user.getGender() == null) {
                barth = employee.getBarth();
                birthWith = "0";
            } else {
                // 如果是男人，则产假为0， 只有陪产假；如果为女人，有产假，没有陪产假
                if (user.getGender().toString().equals("FEMALE".toString())) {
                    barth = employee.getBarth();
                    birthWith = "0";
                }
                if (user.getGender().toString().equals("MALE".toString())) {
                    barth = "0";
                    birthWith = employee.getBarthWith();
                }
            }

            // 添加基本信息
            employeeBasic = new EmployeeBasic(
                    form.getJoinAt(),
                    form.isOfficial(),
                    form.isGraduate(),
                    form.isHasDiploma(),
                    schoolId
            );

            Long id = employee.getId();
            School school = schoolRepository.findOne(actorx.getSchoolId());
            employee = new Employee(
                    id,
                    duty,
                    Integer.valueOf(actorIdx),
                    Integer.valueOf(num.toString()),
                    null,
                    actor.getSchoolId(),
                    true,
                    employeeBasic,
                    "0",    // 加班时间，新建时默认为0
                    "0",    // 调休时间，新建时默认为0
                    school.getAnnual(),
                    school.getSick(),
                    barth,
                    birthWith,
                    school.getMarry(),
                    school.getFuneral(),
                    Instant.now()
            );
            employeeBasicRepository.save(employeeBasic);
            list.add(employeeMapper.toDTO(getRepository().save(employee)));
        }
        return list;
    }

    public EmployeeDTO update(EmployeeForm form) {
        Employee employee = getRepository().findByActorId(Integer.valueOf(form.getActorId()));

        Duty duty = dutyRepository.findOne(Long.parseLong(form.getDutyId().toString()));

        if (!isNullOrEmpty(form.getActorId().toString())){
            employee.setActorId(Integer.valueOf(form.getActorId()));
        }

        if (!isNullOrEmpty(form.getDutyId().toString())){
            employee.setDuty(duty);
        }

        return employeeMapper.toDTO(getRepository().save(employee));
    }

    public Collection<EmployeeDTO> query(Actor actor, Integer actorId) {
        Integer schoolId = actor.getSchoolId();
        Collection<EmployeeDTO> list = actorId != null ? employeeMapper.toDTOs(getRepository().findBySchoolIdAndActorId(schoolId, actorId))
                : employeeMapper.toDTOs(getRepository().findBySchoolId(schoolId));
        return list;
    }

    public List<Map<String, Object>> findAssigned(Actor actor) {
        Integer schoolId = actor.getSchoolId();
        getRepository().findBySchoolId(schoolId);

        List<Object[]> objs = getRepository().findAssigneds(schoolId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object[] obj : objs){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("actorId", obj[0]);     // 用户actorId
            map.put("userName", obj[1]);    // 用户名
            map.put("dutyId", obj[2]);      // 职务ID
            map.put("dutyName", obj[3]);    // 职务名
            map.put("mobile", obj[4]);      // 手机号
            list.add(map);
        }
        return list;
    }

    public List<Map<String, Object>> findUnAssigned(Actor actor) {
        Integer schoolId = actor.getSchoolId();
        List<Object[]> objs = getRepository().findUnAssigneds(schoolId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object[] obj : objs){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("actorId", obj[0]);     // 用户actorId
            map.put("userName", obj[1]);    // 用户名
            map.put("dutyId", obj[2]);      // 职务ID
            map.put("dutyName", obj[3]);    // 职务名
            map.put("mobile", obj[4]);      // 手机号
            list.add(map);
        }
        return list;
    }

    public EmployeeDTO addParend(Actor actor, Integer actorId, Integer parentActorId) {
        Employee employee = getRepository().findByActorId(actorId);
        employee.setParentActorId(parentActorId);
        return employeeMapper.toDTO(getRepository().save(employee));
    }

    public void deleteByActorId(Integer actorId) {
        Employee employee = getRepository().findByActorId(actorId);
        employee.setDuty(null);
        employee.setShow(false);
        getRepository().save(employee);
    }

    public Collection<EmployeeDTO> queryAll(Actor actor) {
        Integer schoolId = actor.getSchoolId();
        // 查询未分配的员工
        Collection<EmployeeDTO> employeeDTOS = employeeOfUserMapper.toDTOs(actorRepository.findByNoPATRIARCH(schoolId));
        // 查询已分配的员工
        Collection<EmployeeDTO> employeeDTOSList = employeeMapper.toDTOs(employeeRepository.findBySchoolId(schoolId));
        // 重复过滤
        for (EmployeeDTO employeeDTO : employeeDTOSList){
            for (EmployeeDTO e : employeeDTOS){
                if (employeeDTO.getActorId() == e.getActorId() ||employeeDTO.getActorId().equals(e.getActorId())){
                    employeeDTOS.remove(e);
                    break;
                }
            }
        }
        // 整合，有员工数据的在前，没有的在后
        employeeDTOSList.addAll(employeeDTOS);
        return employeeDTOSList;
    }

    public EmployeeDTO queryOne(Actor actor) {
        Integer actorId = actor.getId();
        Integer schoolId = actor.getSchoolId();
        Employee employee = getRepository().queryBySchoolIdAndActorId(schoolId, actorId);
        if (employee == null){
            return null;
        }else{
            return employeeMapper.toDTO(employee);
        }
    }

    public Collection<EpleToSalDTO> findSalart(Actor actor) {
        return epleToSalMapper.toDTOs(getRepository().findBySchoolId(actor.getSchoolId()));
    }

    public List<EmployeeDTO> findLeave(Actor actor, LocalDate localDate) {
        Pair pair = Times.getFirstAndSecond(localDate);
        Instant first = (Instant) pair.getFirst();
        Instant second = (Instant) pair.getSecond();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        Collection<EmployeeBasic> employeeBasics = employeeBasicRepository.findBySchoolIdAndIsLeaveAndLeaveAtBetween(actor.getSchoolId(), true, first, second);

        for (EmployeeBasic employeeBasic : employeeBasics){
            EmployeeDTO employee = employeeMapper.toDTO(employeeRepository.findByEmployeeBasic(employeeBasic));
            employeeDTOS.add(employee);
        }
        return employeeDTOS;
    }

}
