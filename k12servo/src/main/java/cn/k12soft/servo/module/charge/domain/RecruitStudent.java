package cn.k12soft.servo.module.charge.domain;

import cn.k12soft.servo.domain.SchoolEntity;
import cn.k12soft.servo.domain.Student;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class RecruitStudent extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  @OneToOne
  private Student student;
  @Column(nullable = false)
  private String name;
  @Column
  private Instant birthday;
  @Column(nullable = false)
  private Instant payTime; //交费时间
  @Column(nullable = false)
  private Float money; //预缴费用
  @Column(nullable = false)
  private Float remainMoney;
  @Column(nullable = false)
  private Integer klassId;
  @Column(nullable = false)
  private Instant enrolmentTime; //入园时间
  @Column
  private Integer teacherId;
  @Column
  private String teacherName;
  @Column
  private Instant createAt;
  @Column
  private Instant updateAt;


  public RecruitStudent() {
  }

  public RecruitStudent(Integer schoolId) {
    super(schoolId);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }

  public Instant getPayTime() {
    return payTime;
  }

  public void setPayTime(Instant payTime) {
    this.payTime = payTime;
  }

  public Float getMoney() {
    return money;
  }

  public void setMoney(Float money) {
    this.money = money;
  }

  public Float getRemainMoney() {
    return remainMoney;
  }

  public void setRemainMoney(Float remainMoney) {
    this.remainMoney = remainMoney;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public void setKlassId(Integer klassId) {
    this.klassId = klassId;
  }

  public Instant getEnrolmentTime() {
    return enrolmentTime;
  }

  public void setEnrolmentTime(Instant enrolmentTime) {
    this.enrolmentTime = enrolmentTime;
  }

  public Integer getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(Integer teacherId) {
    this.teacherId = teacherId;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public Instant getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Instant createAt) {
    this.createAt = createAt;
  }

  public Instant getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Instant updateAt) {
    this.updateAt = updateAt;
  }
}
