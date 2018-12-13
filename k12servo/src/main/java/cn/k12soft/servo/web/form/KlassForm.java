package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class KlassForm {

  @NotNull
  private Integer gradeId;

  @NotNull
  @Size(min = 2, max = 32)
  private String name;

  private String description;

  private String joinOfYear;

  private boolean isGraduate;

  public Integer getGradeId() {
    return gradeId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getJoinOfYear() {
    return joinOfYear;
  }


  public boolean getIsGraduate() {
    return isGraduate;
  }


}
