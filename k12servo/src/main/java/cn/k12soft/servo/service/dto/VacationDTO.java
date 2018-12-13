package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.enumeration.VacationReason;
import java.time.Instant;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
public class VacationDTO {

  private Long id;
  private StudentWithGuardiansDTO student;
  private VacationReason reason;
  private String desc;
  private Instant fromDate;
  private Instant toDate;
  private Instant createdAt;

  public VacationDTO(Long id,
                     StudentWithGuardiansDTO student,
                     VacationReason reason,
                     String desc,
                     Instant fromDate,
                     Instant toDate,
                     Instant createdAt) {
    this.id = id;
    this.student = student;
    this.reason = reason;
    this.desc = desc;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public StudentWithGuardiansDTO getStudent() {
    return student;
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

  public Instant getCreatedAt() {
    return createdAt;
  }
}
