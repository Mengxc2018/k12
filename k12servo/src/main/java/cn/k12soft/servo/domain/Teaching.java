package cn.k12soft.servo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Teaching extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  private Integer teacherId;
  @ManyToOne
  private Klass klass;
  @ManyToOne
  private Course course;

  Teaching() {
  }

  public Teaching(Integer teacherId,
                  Klass klass,
                  Course course) {
    super(klass.getSchoolId());
    this.teacherId = teacherId;
    this.klass = klass.addTeaching(this);
    this.course = course.addTeaching(this);
  }

  public Integer getId() {
    return id;
  }

  public Integer getTeacherId() {
    return teacherId;
  }

  public Klass getKlass() {
    return klass;
  }

  public Course getCourse() {
    return course;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Teaching)) {
      return false;
    }
    Teaching teaching = (Teaching) o;
    if (getTeacherId() != null ? !getTeacherId().equals(teaching.getTeacherId())
      : teaching.getTeacherId() != null) {
      return false;
    }
    if (getKlass() != null ? !getKlass().equals(teaching.getKlass())
      : teaching.getKlass() != null) {
      return false;
    }
    return getCourse() != null ? getCourse().equals(teaching.getCourse())
      : teaching.getCourse() == null;
  }

  @Override
  public int hashCode() {
    int result = getTeacherId() != null ? getTeacherId().hashCode() : 0;
    result = 31 * result + (getKlass() != null ? getKlass().hashCode() : 0);
    result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
    return result;
  }
}
