package cn.k12soft.servo.web.form;

import java.time.LocalDate;

public class PaybackForm {

  private Integer studentId;
  private LocalDate fromDate;
  private LocalDate toDate;

  public Integer getStudentId() {
    return studentId;
  }

  public LocalDate getFromDate() {
    return fromDate;
  }

  public LocalDate getToDate() {
    return toDate;
  }
}
