package cn.k12soft.servo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Instant;

/**
 * 兴趣班学生
 */
@Entity
@DynamicUpdate
public class IKStudent extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  @Column(nullable = false)
  private Integer iklassId;
  @Column(nullable = false)
  private Integer studentId;
  @Column(length = 32, nullable = false)
  private String name; // 学生名
  @Column(nullable = false)
  private Integer remainCount; //剩余课节数
  @Column(length = 2000)
  private String note; // 备注
  @Column
  private Instant createAt;

  public IKStudent() {

  }

  public IKStudent(Integer schoolId) {
    super(schoolId);
  }

  public Integer getId() {
    return id;
  }

  public Integer getIklassId() {
    return iklassId;
  }

  public void setIklassId(Integer iklassId) {
    this.iklassId = iklassId;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getRemainCount() {
    return remainCount;
  }

  public void setRemainCount(Integer remainCount) {
    this.remainCount = remainCount;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Instant getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Instant createAt) {
    this.createAt = createAt;
  }
}
