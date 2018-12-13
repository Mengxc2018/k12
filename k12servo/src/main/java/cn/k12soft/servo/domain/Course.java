package cn.k12soft.servo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Course extends SchoolEntity implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private String name;

  private String description;

  @JsonIgnore
  @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
  private Set<Teaching> teachings = new HashSet<>();

  private Course() {
  }

  public Course(Integer id) {
    this.id = id;
  }

  public Course(Integer schoolId,
                String name,
                String description) {
    super(schoolId);
    this.name = name;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Teaching> getTeachings() {
    return teachings;
  }

  public Course addTeaching(Teaching teaching) {
    this.teachings.add(teaching);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Course course = (Course) o;
    return getId() != null ? getId().equals(course.getId()) : course.getId() == null;
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }
}