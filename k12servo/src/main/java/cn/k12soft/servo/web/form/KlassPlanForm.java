package cn.k12soft.servo.web.form;

import cn.k12soft.servo.domain.enumeration.PlanType;
import javax.validation.constraints.NotNull;

public class KlassPlanForm {

  @NotNull
  private Integer klassId;
  @NotNull
  private PlanType type;
  @NotNull
  private String title;
  @NotNull
  private String content;

  public Integer getKlassId() {
    return klassId;
  }

  public PlanType getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }
}
