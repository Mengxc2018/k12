package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;

public class TeachingForm {

  @NotNull
  private Integer teacherId;
  @NotNull
  private Integer klassId;
  @NotNull
  private Integer courseId;

  public Integer getTeacherId() {
    return teacherId;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public Integer getCourseId() {
    return courseId;
  }
}
