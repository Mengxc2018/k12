package cn.k12soft.servo.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/11/1.
 */
public class DailyAttendStat {

  private Integer klassId;
  private Integer studentId;
  private String name;
  private String portrait;
  private String date;
  private Instant earliest;
  private Instant latest;

  public DailyAttendStat(Object[] columns) {
    this.klassId = (Integer) columns[0];
    this.studentId = (Integer) columns[1];
    this.name = (String) columns[2];
    this.portrait = (String) columns[3];
    this.date = ((Date) columns[4]).toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
    this.earliest = ((Timestamp) columns[5]).toInstant();
    this.latest = ((Timestamp) columns[6]).toInstant();
  }

  public Integer getStudentId() {
    return studentId;
  }

  public String getName() {
    return name;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public String getDate() {
    return date;
  }

  public Instant getEarliest() {
    return earliest;
  }

  public Instant getLatest() {
    return latest;
  }

  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }
}
