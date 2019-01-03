package cn.k12soft.servo.service;

import static com.google.common.base.Strings.isNullOrEmpty;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Grade;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.iclock.IclockDevice;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.TRUE_FALSE;
import cn.k12soft.servo.module.department.domain.Dept;
import cn.k12soft.servo.module.department.repository.DeptRepository;
import cn.k12soft.servo.module.duty.domain.Duty;
import cn.k12soft.servo.module.duty.repositpry.DutyRepository;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.repository.GradeRepository;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.repository.iclock.IclockDeviceRepository;
import cn.k12soft.servo.service.dto.SchoolDTO;
import cn.k12soft.servo.web.form.SchoolForm;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolService extends AbstractEntityService<School, Integer> {

  private final InvitationService invitationService;
  private final IclockDeviceRepository deviceRepository;
  private final GradeRepository gradeRepository;
  private final DutyRepository dutyRepository;
  private final EmployeeRepository employeeRepository;
  private final DeptRepository deptRepository;

  @Autowired
  public SchoolService(SchoolRepository repository,
                       InvitationService invitationService,
                       IclockDeviceRepository deviceRepository,
                       GradeRepository gradeRepository, DutyRepository dutyRepository, EmployeeRepository employeeRepository, DeptRepository deptRepository) {
    super(repository);
    this.invitationService = invitationService;
    this.deviceRepository = deviceRepository;
    this.gradeRepository = gradeRepository;
    this.dutyRepository = dutyRepository;
    this.employeeRepository = employeeRepository;
      this.deptRepository = deptRepository;
  }

  public School create(SchoolForm form) {
    School school = new School(form.getName(),
            form.getDescription(),
            form.getFormDate(),
            form.getToDate(),
            form.getAnnual(),
            form.getSick(),
            form.getBarth(),
            form.getBarthWith(),
            form.getMarry(),
            form.getFuneral());
    School saved = getEntityRepository().save(school);
    List<String> attDeviceIds = form.getAttDeviceIds();
    if (attDeviceIds != null && !attDeviceIds.isEmpty()) {
      List<IclockDevice> list = attDeviceIds.stream()
        .map(sn -> new IclockDevice(sn, saved))
        .collect(Collectors.toList());
      deviceRepository.save(list);
    }
    Integer schoolId = school.getId();
    invitationService.inviteManager(schoolId, form);

    Grade gradeTuo = new Grade(schoolId, "托班", "tuo");
    Grade gradeSmall = new Grade(schoolId, "小班", "xiao");
    Grade gradeMid = new Grade(schoolId, "中班", "zhong");
    Grade gradeBig = new Grade(schoolId, "大班", "da");
    gradeRepository.save(Lists.newArrayList(gradeTuo, gradeSmall, gradeMid, gradeBig));

    // 是否基层员工
    boolean isSubstratum = true;

    // 添加基本职务信息
    List<Duty> list = new ArrayList<>();
    // 基层职务
    String[] substratumDutys = {"教师", "保育员"
                              , "保安", "消毒员", "勤杂工", "食品采购员", "炊事员"
                              , "会计", "出纳"};
    // 非基层职务
    String[] dutys = {"园长", "业务副院长", "保教主任", "教研主任", "年级组长",
                      "后勤副院长", "保健主任", "保健 医", "炊事班长", "财务主任"};

    for (String duty : dutys){
      Duty d = new Duty(
              duty,
              schoolId,
              TRUE_FALSE.FALSE
      );
      dutyRepository.save(d);
    }
    for (String duty : substratumDutys){
      Duty d = new Duty(
              duty,
              schoolId,
              TRUE_FALSE.TRUE
      );
      dutyRepository.save(d);
    }

    return saved;
  }

  public School update(Integer schoolId, SchoolForm form) {

      School school = get(schoolId);

      Integer annual = 0;
      Integer sick = 0;
      Integer barth = 0;
      Integer barthWith = 0;
      Integer marry = 0;
      Integer funeral = 0;

    // 计算所有假期是否有改动，然后更新到每个员工上
    annual = Integer.valueOf(form.getAnnual()) - Integer.valueOf(school.getAnnual());
    sick = Integer.valueOf(form.getSick()) - Integer.valueOf(school.getSick());
    barth = Integer.valueOf(form.getBarth()) - Integer.valueOf(school.getBarth());
    barthWith = Integer.valueOf(form.getBarthWith()) - Integer.valueOf(school.getBarthWith());
    marry = Integer.valueOf(form.getMarry()) - Integer.valueOf(school.getMarry());
    funeral = Integer.valueOf(form.getFuneral()) - Integer.valueOf(school.getFuneral());

    Collection<Employee> employeeList = employeeRepository.findBySchoolId(schoolId);
    for (Employee employee : employeeList){
        Integer Eannual = Integer.valueOf(employee.getAnnual() == null ? "0" : employee.getAnnual());
        Integer Esick = Integer.valueOf(employee.getSick() == null ? "0" : employee.getSick());
        Integer Ebarth = Integer.valueOf(employee.getBarth() == null ? "0" : employee.getBarth());
        Integer EbarthWith = Integer.valueOf(employee.getBarthWith() == null ? "0" : employee.getBarthWith());
        Integer Emarry = Integer.valueOf(employee.getMarry() == null ? "0" : employee.getMarry());
        Integer Efuneral = Integer.valueOf(employee.getFuneral() == null ? "0" : employee.getFuneral());

        Integer Sannual = Eannual + annual;
        Integer Ssick = Esick + sick;
        Integer Sbarth = Ebarth + barth;
        Integer SbarthWith = EbarthWith + barthWith;
        Integer Smarry = Emarry + marry;
        Integer Sfuneral = Efuneral + funeral;

        employee.setAnnual(String.valueOf(Sannual));
        employee.setSick(String.valueOf(Ssick));
        employee.setBarth(String.valueOf(Sbarth));
        employee.setBarthWith(String.valueOf(SbarthWith));
        employee.setMarry(String.valueOf(Smarry));
        employee.setFuneral(String.valueOf(Sfuneral));

        employeeRepository.save(employee);
    }

    if (!isNullOrEmpty(form.getName())) {
      school.name(form.getName());
    }
    if (!isNullOrEmpty(form.getDescription())) {
      school.desc(form.getDescription());
    }
    if (!isNullOrEmpty(form.getDescription())) {
      school.desc(form.getDescription());
    }
    if (!isNullOrEmpty(form.getAnnual())) {
      school.setAnnual(form.getAnnual());
    }
    if (!isNullOrEmpty(form.getSick())) {
      school.setSick(form.getSick());
    }
    if (!isNullOrEmpty(form.getBarth())) {
      school.setBarth(form.getBarth());
    }
    if (!isNullOrEmpty(form.getBarthWith())) {
      school.setBarthWith(form.getBarthWith());
    }
    if (!isNullOrEmpty(form.getMarry())) {
      school.setMarry(form.getMarry());
    }
    if (!isNullOrEmpty(form.getFuneral())) {
      school.setFuneral(form.getFuneral());
    }

    school.setFormDate(form.getFormDate());
    school.setToDate(form.getToDate());

    return getEntityRepository().save(school);
  }

    public List<School> findBY(School school) {

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("desc", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("lastCalcTeacherPayoutTime")
                .withIgnoreCase(true);

        Example example = Example.of(school, matcher);

        return this.getEntityRepository().findAll(example);
    }

    public School addDept(Actor actor, Integer id, String deptIds) {
      String[] ida = deptIds.split(",");
      School school = this.getEntityRepository().findOne(id);
      for (String deptId : ida){
          Dept dept = this.deptRepository.findOne(Long.parseLong(deptId));
          school.getDepartment().add(dept);
      }
        return this.getEntityRepository().save(school);
    }

    public void deleteBy(Actor actor, String deptIds) {
        Integer schoolId = actor.getSchoolId();
        String[] ida = deptIds.split(",");
        Set<Dept> depts = new HashSet<>();
        School school = this.getEntityRepository().findOne(schoolId);
        for (String deptId : ida){
            depts.add(this.deptRepository.findOne(Long.parseLong(deptId)));
        }
        school.getDepartment().removeAll(depts);
        this.getEntityRepository().save(school);
    }
}
