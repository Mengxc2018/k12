package cn.k12soft.servo.web.form;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
public class AttendanceForm {

  @NotNull
  private Integer studentId; // 学生Id

  private String portrait;   // 半身像URL

  @NotNull
  private Float temperature; // 体温

  @JsonIgnore
  private String remarks;

  public Integer getStudentId() {
    return studentId;
  }

  public String getPortrait() {
    return portrait;
  }

  public float getTemperature() {
    return temperature;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
