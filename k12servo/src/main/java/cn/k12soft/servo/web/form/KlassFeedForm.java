package cn.k12soft.servo.web.form;

import java.util.List;
import javax.validation.constraints.NotNull;

public class KlassFeedForm {

  @NotNull
  private String content;

  private List<String> pictures;

  @NotNull
  private Integer klassId;

  public String getContent() {
    return content;
  }

  public List<String> getPictures() {
    return pictures;
  }

  public Integer getKlassId() {
    return klassId;
  }
}
