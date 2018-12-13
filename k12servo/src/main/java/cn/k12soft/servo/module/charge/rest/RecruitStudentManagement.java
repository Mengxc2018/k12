package cn.k12soft.servo.module.charge.rest;

import static cn.k12soft.servo.domain.enumeration.Permission.STUDENT_CREATE;
import static cn.k12soft.servo.domain.enumeration.Permission.STUDENT_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.STUDENT_UPDATE;
import static cn.k12soft.servo.domain.enumeration.Permission.STUDENT_VIEW;
import static cn.k12soft.servo.domain.enumeration.StudentState.IN_SCHOOL;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.GenderType;
import cn.k12soft.servo.domain.enumeration.StudentAccountOpType;
import cn.k12soft.servo.module.account.domain.StudentAccount;
import cn.k12soft.servo.module.account.service.StudentAccountChangeRecordService;
import cn.k12soft.servo.module.account.service.StudentAccountService;
import cn.k12soft.servo.module.charge.domain.RecruitStudent;
import cn.k12soft.servo.module.charge.form.RecruitStudentForm;
import cn.k12soft.servo.module.charge.service.RecruitStudentService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.KlassService;
import cn.k12soft.servo.service.StudentService;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecruitStudentManagement {

  private final KlassService klassService;
  private StudentService studentService;
  private RecruitStudentService recruitStudentService;
  private StudentAccountService studentAccountService;
  private StudentAccountChangeRecordService studentAccountChangeRecordService;

  @Autowired
  public RecruitStudentManagement(KlassService klassService,
                                  StudentService studentService,
                                  RecruitStudentService recruitStudentService,
                                  StudentAccountService studentAccountService,
                                  StudentAccountChangeRecordService studentAccountChangeRecordService) {
    this.klassService = klassService;
    this.studentService = studentService;
    this.recruitStudentService = recruitStudentService;
    this.studentAccountService = studentAccountService;
    this.studentAccountChangeRecordService = studentAccountChangeRecordService;
  }

  @ApiOperation("报名")
  @PostMapping(value = "/recreit/create")
  @PermissionRequired(STUDENT_CREATE)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public void create(@Active Actor actor, @RequestBody RecruitStudentForm form) {
    // 只添加报名记录
    RecruitStudent recruitStudent = new RecruitStudent(actor.getSchoolId());
    recruitStudent.setName(form.getName());
    recruitStudent.setBirthday(form.getBirthday());
    recruitStudent.setKlassId(form.getKlassId());
    recruitStudent.setEnrolmentTime(form.getEnrolmentTime());
    recruitStudent.setMoney(form.getMoney());
    recruitStudent.setRemainMoney(form.getMoney());
    recruitStudent.setPayTime(form.getPayTime());
    recruitStudent.setTeacherId(form.getTeacherId());
    recruitStudent.setTeacherName(form.getTeacherName());
    recruitStudent.setCreateAt(Instant.now());
    recruitStudent.setUpdateAt(recruitStudent.getCreateAt());
    this.recruitStudentService.save(recruitStudent);
  }

  @ApiOperation("获取报名列表")
  @GetMapping(value = "/recreit/findAll")
  @PermissionRequired(STUDENT_VIEW)
  @Timed
  Page<RecruitStudent> getAll(@Active Actor actor, @RequestParam(value = "page", required = true) int page,
                              @RequestParam(value = "size") Integer size) {
    int pageSize = 10;
    if (size != null) {
      pageSize = size;
    }
    page = Math.max(0, page - 1);
    Sort sort = new Sort(Sort.Direction.DESC, "createAt");
    Pageable pageable = new PageRequest(page, pageSize, sort);
    return this.recruitStudentService.findAllSchoolId(actor.getSchoolId(), pageable);
  }

  @ApiOperation("获取班级的报名记录")
  @GetMapping(value = "/recreit/findByKlass")
  @PermissionRequired(STUDENT_VIEW)
  @Timed
  List<RecruitStudent> getByKlass(@Active Actor actor, @RequestParam(value = "klassId", required = true) Integer klassId) {
    return this.recruitStudentService.findByKlassId(klassId);
  }

  @ApiOperation("获取学生的报名记录")
  @GetMapping(value = "/recreit/findByStudent")
  @PermissionRequired(STUDENT_VIEW)
  @Timed
  RecruitStudent getByStudent(@Active Actor actor, @RequestParam(value = "studentId", required = true) Integer studentId) {
    return this.recruitStudentService.findByStudentId(studentId);
  }

  @ApiOperation("删除报名记录")
  @DeleteMapping("/recreit/delete/{id:\\d+}")
  @PermissionRequired(STUDENT_DELETE)
  @Timed
  void delete(@Active Actor actor, @PathVariable Integer id) {
    this.recruitStudentService.delete(id);
  }

  /**
   * 1、报道后创建学生信息
   * 2、根据学生信息创建学生账户
   * 3、将报道时该生交的费用转移到学生账户
   * 4、更新报名表学生id
   * @param actor
   * @param recruitStudentId
   */
  @ApiOperation("学生报到")
  @PutMapping("/recreit/enrolment")
  @PermissionRequired(STUDENT_UPDATE)
  @Timed
  void enrolment(@Active Actor actor,
                 @RequestParam(value = "id", required = true) Integer recruitStudentId) {
    RecruitStudent recruitStudent = this.recruitStudentService.get(recruitStudentId);
    if (recruitStudent == null) {
      return;
    }

    // 新建学生信息
    Klass klass = klassService.get(recruitStudent.getKlassId());
    GenderType gender = GenderType.FEMALE;
    String avatar = "";
    String portrait = "";
    Instant birthday = recruitStudent.getBirthday();
    Instant joinedAt = Instant.now();
    String cardNo = "";
    Student student = new Student(klass, recruitStudent.getName(), gender, avatar, portrait, birthday, joinedAt, cardNo);
    studentService.save(student);

    // 创建学生账户
    StudentAccount studentAccount = this.studentAccountService.findByStudentId(student.getId());
    float remainMoney = recruitStudent.getRemainMoney();
    if (remainMoney > 0) {
      if (studentAccount == null) {
        studentAccount = new StudentAccount(student, remainMoney);
      } else {
        studentAccount.setMoney(studentAccount.getMoney() + remainMoney);
      }
      this.studentAccountService.save(studentAccount);
      this.studentAccountChangeRecordService.create(student, studentAccount,student.getKlass().getId(), remainMoney, actor, StudentAccountOpType.ENROLMENT);
    }
    recruitStudent.setRemainMoney(0f);
    recruitStudent.setStudent(student);
    recruitStudent.setUpdateAt(Instant.now());
    this.recruitStudentService.save(recruitStudent);
  }
}
