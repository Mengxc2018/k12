package cn.k12soft.servo.web.form;

import java.time.Instant;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
public class RetroAttendanceForm extends AttendanceForm {

  @NotNull
  private ZonedDateTime retroAt;

  public ZonedDateTime getRetroAt() {
    return retroAt;
  }
}
