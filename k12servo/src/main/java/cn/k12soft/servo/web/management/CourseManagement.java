package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.COURSE_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.COURSE_GET;
import static cn.k12soft.servo.domain.enumeration.Permission.COURSE_POST;
import static cn.k12soft.servo.domain.enumeration.Permission.COURSE_PUT;

import cn.k12soft.servo.domain.Course;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.CourseService;
import cn.k12soft.servo.web.form.CourseForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/2.
 */
@RestController
@RequestMapping("/management/courses")
public class CourseManagement {

  private final CourseService courseService;

  public CourseManagement(CourseService courseService) {
    this.courseService = courseService;
  }

  @ApiOperation("获取所有课程")
  @GetMapping
  @PermissionRequired(COURSE_GET)
  @Timed
  public List<Course> getAll() {
    return courseService.getAll();
  }

  @ApiOperation("创建课程")
  @PostMapping
  @PermissionRequired(COURSE_POST)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Course> create(@RequestBody @Valid CourseForm form) {
    Course course = courseService.create(form);
    return ResponseEntity.ok(course);
  }

  @ApiOperation("更新课程信息")
  @PutMapping("/{courseId:\\d+}")
  @PermissionRequired(COURSE_PUT)
  @Timed
  public Course update(@PathVariable Integer courseId,
                       @RequestBody CourseForm form) {
    return courseService.update(courseId, form);
  }

  @ApiOperation("删除课程")
  @DeleteMapping("/{courseId:\\d+}")
  @PermissionRequired(COURSE_DELETE)
  @Timed
  public void delete(@PathVariable Integer courseId) {
    courseService.delete(courseId);
  }
}
