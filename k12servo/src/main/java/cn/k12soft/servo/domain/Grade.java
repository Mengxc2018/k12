package cn.k12soft.servo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(uniqueConstraints = @UniqueConstraint(name = "school_grade", columnNames = {"schoolId", "name"}))
public class Grade extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  private String name;

  private String description;

  @JsonIgnore
  @OneToMany(mappedBy = "grade")
  private List<Klass> klasses = new ArrayList<>();

  Grade() {
  }

  public Grade(Integer schoolId,
               String name,
               String description) {
    super(schoolId);
    this.name = name;
    this.description = description;
  }

  public Grade(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Grade setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Grade setDescription(String description) {
    this.description = description;
    return this;
  }

  public List<Klass> getKlasses() {
    return klasses;
  }

  public Grade addKlass(Klass klass) {
    this.klasses.add(klass);
    return this;
  }

  @Override
  public String toString() {
    return "Grade{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", desc='" + description + '\'' +
      '}';
  }
}
