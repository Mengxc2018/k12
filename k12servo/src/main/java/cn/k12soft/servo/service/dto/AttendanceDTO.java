package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.enumeration.AttendanceType;

import java.time.Instant;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
public class AttendanceDTO {

  private Long id;
  private StudentWithGuardiansDTO student; // 学生
  private String portrait;
  private float temperature; //体温
  private Instant signAt; // 打卡时间
  private Instant createdAt; // 记录创建时间
  private AttendanceType attendanceType;  // 打卡方式
  private String remarks;       // 备注

  public AttendanceDTO(Long id,
                       StudentWithGuardiansDTO student,
                       String portrait,
                       float temperature,
                       Instant signAt,
                       AttendanceType attendanceType,
                       Instant createdAt,
                       String remarks) {
    this.id = id;
    this.student = student;
    this.portrait = portrait;
    this.temperature = temperature;
    this.signAt = signAt;
    this.attendanceType = attendanceType;
    this.createdAt = createdAt;
    this.remarks = remarks;
  }

  public Long getId() {
    return id;
  }

  public StudentWithGuardiansDTO getStudent() {
    return student;
  }

  public String getPortrait() {
    return portrait;
  }

  public float getTemperature() {
    return temperature;
  }

  public Instant getSignAt() {
    return signAt;
  }

  public AttendanceType getAttendanceType() {
    return attendanceType;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public String getRemarks() {
    return remarks;
  }
}
