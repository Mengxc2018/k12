package cn.k12soft.servo.web.form;

import cn.k12soft.servo.domain.enumeration.GenderType;
import java.time.Instant;
import javax.validation.constraints.NotNull;

public class StudentForm {

  @NotNull
  private Integer klassId;

  @NotNull
  private String name;

  @NotNull
  private GenderType gender;

  private String avatar;

  private String portrait;

  @NotNull
  private Instant birthday;

  @NotNull
  private Instant joinedAt;

  private String cardNo;

  public  StudentForm(){}

  public StudentForm(Integer klassId, String name, GenderType gender, String avatar, String portrait, Instant birthday, Instant joinedAt, String cardNo) {
    this.klassId = klassId;
    this.name = name;
    this.gender = gender;
    this.avatar = avatar;
    this.portrait = portrait;
    this.birthday = birthday;
    this.joinedAt = joinedAt;
    this.cardNo = cardNo;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public String getName() {
    return name;
  }

  public GenderType getGender() {
    return gender;
  }

  public String getAvatar() {
    return avatar;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public Instant getJoinedAt() {
    return joinedAt;
  }

  public String getCardNo() {
    return cardNo;
  }

  @Override
  public String toString() {
    return "KidForm{" +
      ", name='" + name + '\'' +
      ", gender=" + gender +
      ", avatar='" + avatar + '\'' +
      ", portrait='" + portrait + '\'' +
      ", birthday=" + birthday +
      ", joinedAt=" + joinedAt +
      '}';
  }

  public String getPortrait() {
    return portrait;
  }
}
