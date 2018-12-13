package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.enumeration.GenderType;
import java.time.Instant;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
public class StudentDTO {
  private Integer id;
  private String name;
  private GenderType gender;
  private String avatar;
  private String portrait;
  private Instant birthday;
  private Instant joinedAt;
  private String cardNo;
  private Integer klassId;

  public Integer getId() {
    return id;
  }

  public StudentDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public StudentDTO setName(String name) {
    this.name = name;
    return this;
  }

  public GenderType getGender() {
    return gender;
  }

  public StudentDTO setGender(GenderType gender) {
    this.gender = gender;
    return this;
  }

  public String getAvatar() {
    return avatar;
  }

  public StudentDTO setAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public String getPortrait() {
    return portrait;
  }

  public StudentDTO setPortrait(String portrait) {
    this.portrait = portrait;
    return this;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public StudentDTO setBirthday(Instant birthday) {
    this.birthday = birthday;
    return this;
  }

  public Instant getJoinedAt() {
    return joinedAt;
  }

  public StudentDTO setJoinedAt(Instant joinedAt) {
    this.joinedAt = joinedAt;
    return this;
  }

  public String getCardNo() {
    return cardNo;
  }

  public StudentDTO setCardNo(String cardNo) {
    this.cardNo = cardNo;
    return this;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public StudentDTO setKlassId(Integer klassId) {
    this.klassId = klassId;
    return this;
  }
}
