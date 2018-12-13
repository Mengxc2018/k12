package cn.k12soft.servo.web.form;

import static cn.k12soft.servo.util.Patterns.MOBILE_REGEX;

import cn.k12soft.servo.domain.enumeration.GenderType;
import java.time.Instant;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserForm {

  @Pattern(regexp = MOBILE_REGEX)
  private String mobile;

  @NotNull
  private String username;

  @NotNull
  private String password;

  private String avatar;

  private String portrait;

  private Instant birthday;

  @NotNull
  private GenderType gender;

  @NotNull
  private Integer secretCode;

  public String getMobile() {
    return mobile;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getAvatar() {
    return avatar;
  }

  public String getPortrait() {
    return portrait;
  }

  public GenderType getGender() {
    return gender;
  }

  public Integer getSecretCode() {
    return secretCode;
  }

  public Instant getBirthday() {
    return birthday;
  }

}
