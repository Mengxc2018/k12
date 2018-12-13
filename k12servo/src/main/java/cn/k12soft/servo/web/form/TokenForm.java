package cn.k12soft.servo.web.form;

import static cn.k12soft.servo.util.Patterns.MOBILE_REGEX;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TokenForm {

  @NotNull
  @Pattern(regexp = MOBILE_REGEX)
  private String mobile;

  @NotNull
  private String password;

  public String getMobile() {
    return mobile;
  }

  public String getPassword() {
    return password;
  }
}
