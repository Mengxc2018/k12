package cn.k12soft.servo.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SchoolEntity {

//  @Column(nullable = false)
  private Integer schoolId;

  protected SchoolEntity() {
  }

  protected SchoolEntity(Integer schoolId) {
    this.schoolId = schoolId;
  }

  public Integer getSchoolId() {
    return schoolId;
  }

  public void setSchoolId(Integer schoolId) {
    this.schoolId = schoolId;
  }
}