package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.Course;
import cn.k12soft.servo.domain.Klass;

public class TeachingDTO {

  private Integer id;
  private TeacherDTO teacher;
  private Klass klass;
  private Course course;

  public TeachingDTO(Integer id,
                     TeacherDTO teacher,
                     Klass klass,
                     Course course) {
    this.id = id;
    this.teacher = teacher;
    this.klass = klass;
    this.course = course;
  }

  public Integer getId() {
    return id;
  }

  public TeacherDTO getTeacher() {
    return teacher;
  }

  public Klass getKlass() {
    return klass;
  }

  public Course getCourse() {
    return course;
  }
}
