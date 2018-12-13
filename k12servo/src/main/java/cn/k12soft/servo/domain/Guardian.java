package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.RelationType;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Guardian extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  private Integer patriarchId;

  @ManyToOne(fetch = FetchType.EAGER)
  private Student student;

  @Enumerated(EnumType.STRING)
  private RelationType relationType;

  Guardian() {
  }

  public Guardian(Integer patriarchId,
                  Student student,
                  RelationType relationType) {
    super(student.getSchoolId());
    this.patriarchId = patriarchId;
    this.student = student;
    this.relationType = relationType;
  }

  public Integer getId() {
    return id;
  }

  public Integer getPatriarchId() {
    return patriarchId;
  }

  public Student getStudent() {
    return student;
  }

  public RelationType getRelationType() {
    return relationType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Guardian)) {
      return false;
    }
    Guardian guardian = (Guardian) o;
    return Objects.equals(getId(), guardian.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public String toString() {
    return "Guardian{" +
      "id=" + id +
      ", patriarchId=" + patriarchId +
      ", student=" + student +
      ", relationType=" + relationType +
      '}';
  }
}
