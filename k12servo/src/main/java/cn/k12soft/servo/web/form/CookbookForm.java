package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
public class CookbookForm {

  @NotNull
  private Integer schoolId;
  @NotNull
  private String title;
  @NotNull
  private String content;


  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public Integer getSchoolId() {
    return schoolId;
  }
}
