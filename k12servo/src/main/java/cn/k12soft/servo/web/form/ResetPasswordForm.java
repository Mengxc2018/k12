package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/25.
 */
public class ResetPasswordForm {

  @NotNull
  private Integer code;

  @NotNull
  private String password;

  public Integer getCode() {
    return code;
  }

  public String getPassword() {
    return password;
  }
}
