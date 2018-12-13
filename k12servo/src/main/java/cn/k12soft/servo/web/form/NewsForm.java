package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;

public class NewsForm {
  @NotNull
  private Integer schoolId;
  @NotNull
  private String title;
  @NotNull
  private String content;

  public Integer getSchoolId() {
    return schoolId;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }
}
