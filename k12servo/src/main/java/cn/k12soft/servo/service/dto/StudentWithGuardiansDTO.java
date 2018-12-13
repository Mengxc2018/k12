package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.enumeration.GenderType;
import java.time.Instant;
import java.util.List;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/21.
 */
public class StudentWithGuardiansDTO {

  private Integer id;
  private String name;
  private GenderType gender;
  private String avatar;
  private String portrait;
  private Instant birthday;
  private Instant joinedAt;
  private String cardNo;
  private Integer klassId;
  private List<GuardianDTO> guardians;

  public Integer getId() {
    return id;
  }

  public StudentWithGuardiansDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public StudentWithGuardiansDTO setName(String name) {
    this.name = name;
    return this;
  }

  public GenderType getGender() {
    return gender;
  }

  public StudentWithGuardiansDTO setGender(GenderType gender) {
    this.gender = gender;
    return this;
  }

  public String getAvatar() {
    return avatar;
  }

  public StudentWithGuardiansDTO setAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public String getPortrait() {
    return portrait;
  }

  public StudentWithGuardiansDTO setPortrait(String portrait) {
    this.portrait = portrait;
    return this;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public StudentWithGuardiansDTO setBirthday(Instant birthday) {
    this.birthday = birthday;
    return this;
  }

  public Instant getJoinedAt() {
    return joinedAt;
  }

  public StudentWithGuardiansDTO setJoinedAt(Instant joinedAt) {
    this.joinedAt = joinedAt;
    return this;
  }

  public String getCardNo() {
    return cardNo;
  }

  public StudentWithGuardiansDTO setCardNo(String cardNo) {
    this.cardNo = cardNo;
    return this;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public StudentWithGuardiansDTO setKlassId(Integer klassId) {
    this.klassId = klassId;
    return this;
  }

  public List<GuardianDTO> getGuardians() {
    return guardians;
  }

  public StudentWithGuardiansDTO setGuardians(List<GuardianDTO> guardians) {
    this.guardians = guardians;
    return this;
  }
}
