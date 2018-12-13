package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.Klass;

import java.time.Instant;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/21.
 */
public class KlassDTO {

  private Integer id;
  private String name;
  private String description;
  private Integer gradeId;
  private String joinOfYear;  // 哪一级，哪一年入学， 取年份，比如2018级
  private String graduateOfYear;  // 哪一届， 哪一年毕业，取年份，比如2018届
  private Instant goupTime;   // 升班时间
  private Instant graduateAt; // 毕业时间
  private boolean isGraduate = false; // 是否毕业，默认false

  public Integer getId() {
    return id;
  }

  public KlassDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public KlassDTO setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public KlassDTO setDescription(String description) {
    this.description = description;
    return this;
  }

  public Integer getGradeId() {
    return gradeId;
  }

  public KlassDTO setGradeId(Integer gradeId) {
    this.gradeId = gradeId;
    return this;
  }

  public Instant getGoupTime() {
    return goupTime;
  }

  public KlassDTO setGoupTime(Instant goupTime) {
    this.goupTime = goupTime;
    return this;
  }

  public boolean isGraduate() {
    return isGraduate;
  }

  public KlassDTO setGraduate(boolean graduate) {
    isGraduate = graduate;
    return this;
  }

  public Instant getGraduateAt() {
    return graduateAt;
  }

  public KlassDTO setGraduateAt(Instant graduateAt) {
    this.graduateAt = graduateAt;
    return this;
  }

  public String getJoinOfYear() {
    return joinOfYear;
  }

  public KlassDTO setJoinOfYear(String joinOfYear) {
    this.joinOfYear = joinOfYear;
    return this;
  }

  public String getGraduateOfYear() {
    return graduateOfYear;
  }

  public KlassDTO setGraduateOfYear(String graduateOfYear) {
    this.graduateOfYear = graduateOfYear;
    return this;
  }
}
