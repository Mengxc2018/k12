package cn.k12soft.servo.web.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static cn.k12soft.servo.util.Patterns.MOBILE_REGEX;

import java.time.Instant;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
public class TeacherForm {

  @Pattern(regexp = MOBILE_REGEX)
  private String mobile;

  @NotNull
  private String username;

  @ApiModelProperty(value = "入职时间")
  @NotNull
  private Instant joinTime;

  public String getMobile() {
    return mobile;
  }

  public String getUsername() {
    return username;
  }

  public Instant getJoinTime() {
    return joinTime;
  }
}
