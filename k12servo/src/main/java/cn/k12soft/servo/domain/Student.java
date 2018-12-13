package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.GenderType;
import cn.k12soft.servo.domain.enumeration.StudentState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Student extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  private String name;

  @Enumerated(EnumType.STRING)
  private GenderType gender;

  private String avatar;

  private String portrait;

  private Instant birthday;

  private Instant joinedAt;

  private String cardNo;

  private StudentState state;

  @JsonIgnore
  private Boolean isShow = true;   // 是否展示学生 默认true：展示 false:不展示

  @JsonIgnore
  @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
  private List<Guardian> guardians = new ArrayList<>();

  @ManyToOne
  private Klass klass;

  private boolean isUpdate;   // 是否有更新（true更新、false未更新）

  @JsonIgnore
  @ManyToMany
  private List<InterestKlass> interestKlassList = new ArrayList<>();

  public Student() {
  }

  public Student(Integer id) {
    this.id = id;
  }

  public Student(Klass klass,
                 String name,
                 GenderType gender,
                 String avatar,
                 String portrait,
                 Instant birthday,
                 Instant joinedAt,
                 String cardNo) {
    super(klass.getSchoolId());
    this.klass = klass.addStudent(this);
    this.name = name;
    this.gender = gender;
    this.avatar = avatar;
    this.portrait = portrait;
    this.birthday = birthday;
    this.joinedAt = joinedAt;
    this.cardNo = cardNo;
    this.state = StudentState.IN_SCHOOL;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Student name(String name) {
    this.name = name;
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GenderType getGender() {
    return gender;
  }

  public Student gender(GenderType gender) {
    this.gender = gender;
    return this;
  }

  public boolean getIsShow() {
    return isShow;
  }

  public void setShow(boolean show) {
    isShow = show;
  }

  public void setGender(GenderType gender) {
    this.gender = gender;
  }

  public String getAvatar() {
    return avatar;
  }

  public Student avatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public Student birthday(Instant birthday) {
    this.birthday = birthday;
    return this;
  }

  public void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }

  public Instant getJoinedAt() {
    return joinedAt;
  }

  public Student joinTime(Instant joinTime) {
    this.joinedAt = joinTime;
    return this;
  }

  public void setJoinedAt(Instant joinedAt) {
    this.joinedAt = joinedAt;
  }

  public String getCardNo() {
    return cardNo;
  }

  public Student cardNo(String cardNo) {
    setCardNo(cardNo);
    return this;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }


  public String getPortrait() {
    return portrait;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }


  public List<Guardian> getGuardians() {
    return guardians;
  }

  public Student guardians(List<Guardian> guardians) {
    this.guardians = guardians;
    return this;
  }

  public Student addGuardians(Guardian guardian) {
    this.guardians.add(guardian);
    return this;
  }

  public Student removeGuardians(Guardian guardian) {
    this.guardians.remove(guardian);
    return this;
  }

  public void setGuardians(List<Guardian> guardians) {
    this.guardians = guardians;
  }

  public Klass getKlass() {
    return klass;
  }

  public Student klass(Klass klass) {
    this.klass = klass;
    return this;
  }

  public void setKlass(Klass klass) {
    this.klass = klass;
  }

  public List<InterestKlass> getInterestKlassList() {
    return interestKlassList;
  }

  public void setInterestKlassList(List<InterestKlass> interestKlassList) {
    this.interestKlassList = interestKlassList;
  }

  public void addInterestKlass(InterestKlass interestKlass) {
    this.interestKlassList.add(interestKlass);
  }

  public StudentState getState() {
    return state;
  }

  public void setState(StudentState state) {
    this.state = state;
  }

  public void setUpdate(boolean update) {
    isUpdate = update;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Student student = (Student) o;
    if (student.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), student.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "Student{" +
      "id=" + getId() +
      ", name='" + getName() + "'" +
      ", gender='" + getGender() + "'" +
      ", avatar='" + getAvatar() + "'" +
      ", portrait='" + getPortrait() + "'" +
      ", birthday='" + getBirthday() + "'" +
      ", joinedAt='" + getJoinedAt() + "'" +
      "}";
  }
}
