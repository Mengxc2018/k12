package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.enumeration.RelationType;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/21.
 */
public class GuardianDTO {

  private Integer id;
  private PatriarchDTO patriarch;
  private Integer studentId;
  private RelationType relationType;
  private KlassDTO klass;

  public Integer getId() {
    return id;
  }

  public GuardianDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public PatriarchDTO getPatriarch() {
    return patriarch;
  }

  public GuardianDTO setPatriarch(PatriarchDTO patriarch) {
    this.patriarch = patriarch;
    return this;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public GuardianDTO setStudentId(Integer studentId) {
    this.studentId = studentId;
    return this;
  }

  public RelationType getRelationType() {
    return relationType;
  }

  public GuardianDTO setRelationType(RelationType relationType) {
    this.relationType = relationType;
    return this;
  }

  public KlassDTO getKlass() {
    return klass;
  }

  public GuardianDTO setKlass(KlassDTO klass) {
    this.klass = klass;
    return this;
  }

  @Override
  public String toString() {
    return "GuardianDTO{" +
      "id=" + id +
      ", patriarch=" + patriarch +
      ", studentId=" + studentId +
      ", relationType=" + relationType +
      '}';
  }
}
