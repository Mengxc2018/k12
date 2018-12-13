package cn.k12soft.servo.web.form;

import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.util.List;
import javax.validation.constraints.NotNull;

public class SchoolForm extends ManagerForm {

  @NotNull
  private String name;

  private String description;

  private Integer formDate; // 考核周期开始日期
  private Integer toDate;   // 考核周期结束时间

  private String annual;      // 年假时间
  private String sick;        // 病假时间
  private String barth;       // 产假
  private String barthWith;   // 陪产假
  private String marry;       // 婚假
  private String funeral;     // 丧假



  @ApiModelProperty("考勤机Id列表(支持关联多个)")
  private List<String> attDeviceIds;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getAttDeviceIds() {
    return attDeviceIds;
  }

  public Integer getFormDate() {
    return formDate;
  }

  public Integer getToDate() {
    return toDate;
  }

  public String getAnnual() {
    return annual;
  }

  public String getSick() {
    return sick;
  }

  public String getBarth() {
    return barth;
  }

  public String getBarthWith() {
    return barthWith;
  }

  public String getMarry() {
    return marry;
  }

  public String getFuneral() {
    return funeral;
  }
}
