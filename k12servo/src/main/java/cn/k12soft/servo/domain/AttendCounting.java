package cn.k12soft.servo.domain;


public class AttendCounting {

  private Integer studentId;
  private String name;
  private long attendCount;

  public AttendCounting(Integer studentId, String name, long attendCount) {
    this.studentId = studentId;
    this.name = name;
    this.attendCount = attendCount;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public String getName() {
    return name;
  }

  public long getAttendCount() {
    return attendCount;
  }
}