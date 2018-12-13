package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.enumeration.RelationType;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
public class StudentGuardianDTO {

  private Integer id;
  private Integer patriarchId;
  private StudentDTO student;
  private RelationType relationType;
  private KlassDTO klass;

  public Integer getId() {
    return id;
  }

  public StudentGuardianDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public Integer getPatriarchId() {
    return patriarchId;
  }

  public StudentGuardianDTO setPatriarchId(Integer patriarchId) {
    this.patriarchId = patriarchId;
    return this;
  }

  public StudentDTO getStudent() {
    return student;
  }

  public StudentGuardianDTO setStudent(StudentDTO student) {
    this.student = student;
    return this;
  }

  public RelationType getRelationType() {
    return relationType;
  }

  public StudentGuardianDTO setRelationType(RelationType relationType) {
    this.relationType = relationType;
    return this;
  }

  public KlassDTO getKlass() {
    return klass;
  }

  public StudentGuardianDTO setKlass(KlassDTO klass) {
    this.klass = klass;
    return this;
  }
}
