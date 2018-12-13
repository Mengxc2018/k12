package cn.k12soft.servo.web.form;

import cn.k12soft.servo.domain.enumeration.VacationReason;
import java.time.Instant;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
public class VacationForm {

  @NotNull
  private Integer studentId;
  @NotNull
  private VacationReason reason;

  private String desc;
  @NotNull
  private Instant fromDate;
  @NotNull
  private Instant toDate;

  public Integer getStudentId() {
    return studentId;
  }

  public VacationReason getReason() {
    return reason;
  }

  public String getDesc() {
    return desc;
  }

  public Instant getFromDate() {
    return fromDate;
  }

  public Instant getToDate() {
    return toDate;
  }
}
