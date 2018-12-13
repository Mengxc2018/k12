package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.GRADE_GET;
import static cn.k12soft.servo.domain.enumeration.Permission.GRADE_POST;
import static cn.k12soft.servo.domain.enumeration.Permission.GRADE_PUT;

import cn.k12soft.servo.domain.Grade;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.GradeService;
import cn.k12soft.servo.web.form.GradeForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/management/grades")
public class GradeManagement {

  private final GradeService gradeService;

  @Autowired
  public GradeManagement(GradeService gradeService) {
    this.gradeService = gradeService;
  }

  @ApiOperation("获取年级列表")
  @GetMapping(params = "schoolId")
  @PermissionRequired(GRADE_GET)
  @Timed
  public List<Grade> query(@RequestParam("schoolId") Integer schoolId) {
    return gradeService.findAllBySchool(schoolId);
  }

  @ApiOperation("创建新的年级")
  @PostMapping
  @PermissionRequired(GRADE_POST)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public Grade create(@RequestBody @Valid GradeForm form) {
    return gradeService.create(form);
  }

  @ApiOperation("更新年级信息")
  @PutMapping("/{gradeId:\\d+}")
  @PermissionRequired(GRADE_PUT)
  @Timed
  public Grade updateGrade(@PathVariable Integer gradeId,
                           @RequestBody GradeForm form) {
    return gradeService.update(gradeId, form);
  }
}
