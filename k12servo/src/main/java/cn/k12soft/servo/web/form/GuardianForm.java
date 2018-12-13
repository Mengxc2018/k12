package cn.k12soft.servo.web.form;

import static cn.k12soft.servo.util.Patterns.MOBILE_REGEX;

import cn.k12soft.servo.domain.enumeration.RelationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/1.
 */
public class GuardianForm {

  @Pattern(regexp = MOBILE_REGEX)
  private String mobile;

  @NotNull
  private String username;

  @NotNull
  private Integer kidId;

  @NotNull
  private RelationType relationType;

  public String getMobile() {
    return mobile;
  }

  public String getUsername() {
    return username;
  }

  public Integer getKidId() {
    return kidId;
  }

  public RelationType getRelationType() {
    return relationType;
  }
}
