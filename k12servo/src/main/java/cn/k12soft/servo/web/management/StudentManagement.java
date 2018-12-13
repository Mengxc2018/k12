package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.STUDENT_CREATE;
import static cn.k12soft.servo.domain.enumeration.Permission.STUDENT_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.STUDENT_UPDATE;
import static cn.k12soft.servo.domain.enumeration.Permission.STUDENT_VIEW;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.module.account.domain.StudentAccount;
import cn.k12soft.servo.module.account.service.StudentAccountService;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.StudentService;
import cn.k12soft.servo.service.dto.StudentWithGuardiansDTO;
import cn.k12soft.servo.service.mapper.StudentWithGuardiansMapper;
import cn.k12soft.servo.web.form.StudentForm;
import cn.k12soft.servo.web.form.StudentKlNmForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@RestController
@RequestMapping("/management/students")
public class StudentManagement {

  private final StudentService studentService;
  private final StudentAccountService studentAccountService;
  private final StudentWithGuardiansMapper studentMapper;
  private final KlassRepository klassRepository;


  @Autowired
  public StudentManagement(StudentService studentService, StudentAccountService studentAccountService, StudentWithGuardiansMapper studentMapper, KlassRepository klassRepository) {
    this.studentService = studentService;
    this.studentAccountService = studentAccountService;
    this.studentMapper = studentMapper;
    this.klassRepository = klassRepository;
  }

  @ApiOperation("按照条件查询学生信息")
  @PermissionRequired(STUDENT_VIEW)
  @GetMapping(params = {"klassId"})
  @Timed
  public Collection<StudentWithGuardiansDTO> getAll(@RequestParam Map<String, String> params) {
    return studentMapper.toDTOs(studentService.query(params));
  }

  @ApiOperation("查询有改动的学生信息,查询完成后重置为未改动")
  @PermissionRequired(STUDENT_VIEW)
  @GetMapping(params = {"klassId"}, value = ("/getAllUpdate"))
  @Timed
  public Collection<StudentWithGuardiansDTO> getAllUpdate(@RequestParam Map<String, String> params) {
    return studentMapper.toDTOs(studentService.getAllUpdate(params));
  }

  @ApiOperation("查询学生家长所有有修改的信息，以学校为单位,查询完成后重置为未改动")
  @PermissionRequired(STUDENT_VIEW)
  @GetMapping(value = ("/getAllUpdateBySchool"))
  @Timed
  public Map<String, Object> getAllUpdateBySchool(@RequestParam @Valid Integer schoolId) {
    return (studentService.getAllUpdateBySchool(schoolId));
  }

  @ApiOperation("新增学生信息")
  @PostMapping
  @PermissionRequired(STUDENT_CREATE)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public Student create(@RequestBody @Valid StudentForm form) {
    Student student = studentService.create(form);
    StudentAccount studentAccount = new StudentAccount(student, 0f);
    studentAccountService.save(studentAccount);
    return student;
  }

  @ApiOperation("批量：新增学生信息")
  @PostMapping("/addMany")
  @PermissionRequired(STUDENT_CREATE)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public List<Student> addMany(@Active Actor actor,
                               @RequestBody @Valid List<StudentKlNmForm> forms) {
    List<Student> list = new ArrayList<>();
    Integer schoolId = actor.getSchoolId();
    for (StudentKlNmForm form : forms){
      String klassName = form.getKlassName().replace(" ", "");
      Klass klass = klassRepository.findByNameAndSchoolId(klassName, schoolId);
      StudentForm studentForm = new StudentForm(
              klass.getId(),
              form.getName(),
              form.getGender(),
              form.getAvatar(),
              form.getPortrait(),
              form.getBirthday(),
              form.getJoinedAt(),
              form.getCardNo()
      );
      Student student = studentService.create(studentForm);
      StudentAccount studentAccount = new StudentAccount(student, 0f);
      studentAccountService.save(studentAccount);
      list.add(student);
    }
    return list;
  }

  @ApiOperation("更新学生信息")
  @PutMapping("/{studentId:\\d+}")
  @PermissionRequired(STUDENT_UPDATE)
  @Timed
  public Student update(@PathVariable Integer studentId,
                        @RequestBody StudentForm form) {
    return studentService.update(studentId, form);
  }

  @ApiOperation("物理删除学生信息，删除后学生就没有了")
  @DeleteMapping("/{studentId:\\d+}")
  @PermissionRequired(STUDENT_DELETE)
  @Timed
  public void delete(@PathVariable Integer studentId) {
    studentAccountService.deleteByStudent(studentId);
    studentService.delete(studentId);
  }

  @ApiOperation("逻辑删除学生信息，只是隐藏该学生")
  @DeleteMapping("/deleteBy/{studentId:\\d+}")
  @PermissionRequired(STUDENT_DELETE)
  @Timed
  public void deleteBy(@PathVariable Integer studentId) {
//    studentAccountService.deleteByStudent(studentId);
    studentService.deleteBy(studentId);
  }
}
