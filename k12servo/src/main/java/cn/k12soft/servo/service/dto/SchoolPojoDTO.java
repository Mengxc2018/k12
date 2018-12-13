package cn.k12soft.servo.service.dto;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
public class SchoolPojoDTO {

  private Integer id;
  private String label;
  private String code;

  public SchoolPojoDTO(Integer id, String label, String code) {
    this.id = id;
    this.label = label;
    this.code = code;
  }

  public Integer getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public String getCode() {
    return code;
  }
}
