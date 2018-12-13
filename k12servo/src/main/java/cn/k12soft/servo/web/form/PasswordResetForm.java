package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
public class PasswordResetForm {

  @NotNull
  private String old;

  @NotNull
  private String now;

  public String getOld() {
    return old;
  }

  public String getNow() {
    return now;
  }
}
