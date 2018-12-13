package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/14.
 */
public class ManagerForm {

  @NotNull
  private String username;

  @NotNull
  private String mobile;

  public String getUsername() {
    return username;
  }

  public String getMobile() {
    return mobile;
  }
}
