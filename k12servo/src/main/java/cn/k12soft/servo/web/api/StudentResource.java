package cn.k12soft.servo.web.api;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.StudentService;
import cn.k12soft.servo.web.form.StudentForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/students")
public class StudentResource {

  private final StudentService studentService;

  @Autowired
  public StudentResource(StudentService studentService) {
    this.studentService = studentService;
  }

  @ApiOperation("当前登录用户获取学生列表")
  @GetMapping(params = {"klassId"})
  @Timed
  public List<Student> query(@Active Actor actor,
                             @RequestParam Map<String, String> params) {
    return Collections.emptyList();
  }

  @ApiOperation("当前登录老师新增学生信息")
  @PostMapping
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public Student create(@Active Actor actor,
                        @RequestBody @Valid StudentForm form) {
    return studentService.create(form);
  }

  @ApiOperation("当前登录老师更新学生信息")
  @PutMapping("/{studentId:\\d+}")
  @Timed
  public ResponseEntity<Student> update(@Active Actor actor,
                                        @PathVariable Integer studentId,
                                        @RequestBody StudentForm form) {
    Student student = studentService.update(studentId, form);
    return ResponseEntity.ok(student);
  }

  @ApiOperation("当前登录老师更新学生信息")
  @DeleteMapping("/{studentId:\\d+}")
  @Timed
  public void delete(@Active Actor actor,
                     @PathVariable Integer studentId) {
    studentService.delete(studentId);
  }
}
