package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.VacationReason;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 *
 * 请假记录
 */
@Entity
public class Vacation extends SchoolEntity {

  @Id
  @GeneratedValue
  private Long id;
  private Integer studentId;
  private Integer klassId;
  private VacationReason reason;
  @Column(name = "`desc`")
  private String desc;
  private Instant fromDate;
  private Instant toDate;
  private Instant createdAt;

  protected Vacation() {
  }

  public Vacation(Integer studentId,
                  Integer klassId,
                  VacationReason reason,
                  String desc,
                  Instant fromDate,
                  Instant toDate,
                  Instant createdAt) {
    this.studentId = studentId;
    this.klassId = klassId;
    this.reason = reason;
    this.desc = desc;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public Integer getKlassId() {
    return klassId;
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
