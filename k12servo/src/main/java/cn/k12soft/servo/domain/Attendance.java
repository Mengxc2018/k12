package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.AttendanceType;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@Entity
public class Attendance extends SchoolEntity {

  @Id
  @GeneratedValue
  private Long id;
  private Integer studentId;    // 学生
  private Integer klassId;      // 班级ID
  private String name;
  private String portrait;      // 签到时半身像
  private float temperature;    //体温
  private Instant signAt;       // 打卡时间
  private Instant createdAt;    // 记录创建时间
  private AttendanceType attendanceType;  // 打卡方式
  private String remarks;       // 备注

  protected Attendance() {
  }

  public Attendance(Integer schoolId, Integer studentId, String name, Integer klassId, String portrait, float temperature, Instant signAt) {
    super(schoolId);
    this.studentId = studentId;
    this.name = name;
    this.klassId = klassId;
    this.portrait = portrait;
    this.temperature = temperature;
    this.signAt = signAt;
    this.createdAt = signAt;
  }

  public Attendance(Integer schoolId, Integer studentId, String name, Integer klassId, String portrait, AttendanceType type, float temperature) {
    super(schoolId);
    this.studentId = studentId;
    this.name = name;
    this.klassId = klassId;
    this.portrait = portrait;
    this.temperature = temperature;
    this.attendanceType = type;
    this.signAt = this.createdAt = Instant.now();
  }

  public Long getId() {
    return id;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public String getName() {
    return name;
  }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKlassId() {
    return klassId;
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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public AttendanceType getAttendanceType() {
    return attendanceType;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
