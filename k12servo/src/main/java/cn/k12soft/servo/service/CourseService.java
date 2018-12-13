package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Course;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.repository.CourseRepository;
import cn.k12soft.servo.web.form.CourseForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/2.
 */
@Service
@Transactional
public class CourseService extends AbstractEntityService<Course, Integer> {

  private final SchoolService schoolService;

  public CourseService(CourseRepository courseRepository,
                       SchoolService schoolService) {
    super(courseRepository);
    this.schoolService = schoolService;
  }

  public Course create(CourseForm form) {
    School school = schoolService.get(form.getSchoolId());
    Course course = new Course(school.getId(), form.getName(), form.getDescription());
    return getEntityRepository().save(course);
  }

  public Course update(Integer courseId, CourseForm form) {
    return null;
  }
}
