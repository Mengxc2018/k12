package cn.k12soft.servo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Klass extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(length = 32, unique = true, nullable = false)
  private String name;

  @Column(length = 512)
  private String description;

  @ManyToOne
  private Grade grade;

  @JsonIgnore
  @OneToMany(mappedBy = "klass")
  private List<Student> students = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "klass")
  private List<Teaching> teachings = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "klass")
  private List<KlassPlan> plans = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "klass")
  private List<KlassFeed> klassFeeds = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "klass")
  private List<KlassFeedEvent> feedEvents = new ArrayList<>();

  private String joinOfYear;  // 哪一级，哪一年入学， 取年份，比如2018级
  private String graduateOfYear;  // 哪一届， 哪一年毕业，取年份，比如2018届
  private Instant goupTime;   // 升班时间
  private Instant graduateAt; // 毕业时间
  @Column(nullable=true)
  private boolean isGraduate; // 是否毕业，默认false


  Klass() {
  }

  public Klass(Integer id) {
    this.id = id;
  }

  public Klass(Grade grade,
               String name,
               String description) {
    super(grade.getSchoolId());
    this.grade = grade;
    this.name = name;
    this.description = description;
  }

  public Klass(Grade grade,
               String name,
               String description,
               String joinOfYear,
               String graduateOfYear,
               Instant goupTime,
               Instant graduateAt,
               boolean isGraduate) {
    super(grade.getSchoolId());
    this.grade = grade;
    this.name = name;
    this.description = description;
    this.joinOfYear = joinOfYear;
    this.graduateOfYear = graduateOfYear;
    this.goupTime = goupTime;
    this.graduateAt = graduateAt;
    this.isGraduate = isGraduate;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Klass setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Klass setDescription(String description) {
    this.description = description;
    return this;
  }

  public Grade getGrade() {
    return grade;
  }

  public Klass setGrade(Grade grade) {
    this.grade = grade.addKlass(this);
    return this;
  }

  public List<Student> getStudents() {
    return students;
  }

  public Klass addStudent(Student student) {
    this.students.add(student);
    return this;
  }

  public List<Teaching> getTeachings() {
    return teachings;
  }

  public Klass addTeaching(Teaching teaching) {
    this.teachings.add(teaching);
    return this;
  }

  public Klass removeTeaching(Teaching teaching) {
    this.teachings.remove(teaching);
    return this;
  }

  public List<KlassPlan> getPlans() {
    return plans;
  }

  public List<KlassFeed> getKlassFeeds() {
    return klassFeeds;
  }

  public List<KlassFeedEvent> getFeedEvents() {
    return feedEvents;
  }

  public Instant getGoupTime() {
    return goupTime;
  }

  public Klass setGoupTime(Instant goupTime) {
    this.goupTime = goupTime;
    return this;
  }

  public boolean isGraduate() {
    return isGraduate;
  }

  public Klass setGraduate(boolean graduate) {
    isGraduate = graduate;
    return this;
  }

  public Instant getGraduateAt() {
    return graduateAt;
  }

  public Klass setGraduateAt(Instant graduateAt) {
    this.graduateAt = graduateAt;
    return this;
  }

  public String getJoinOfYear() {
    return joinOfYear;
  }

  public Klass setJoinOfYear(String joinOfYear) {
    this.joinOfYear = joinOfYear;
    return  this;
  }

  public String getGraduateOfYear() {
    return graduateOfYear;
  }

  public Klass setGraduateOfYear(String graduateOfYear) {
    this.graduateOfYear = graduateOfYear;
    return this;
  }

  @Override
  public String toString() {
    return "Klass{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", goupTime=" + goupTime +
            ", graduateAt=" + graduateAt +
            ", isGraduate=" + isGraduate +
            '}';
  }
}
