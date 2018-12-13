package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.GenderType;
import cn.k12soft.servo.domain.enumeration.UserState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class User {

  @Id
  @GeneratedValue
  private Integer id;

  private String mobile;

  @JsonIgnore
  private String password;

  private String username;

  private String avatar;

  private String portrait;

  @Enumerated(EnumType.STRING)
  private GenderType gender;

  private Instant createdAt;

  private Instant birthday;

  @Enumerated(EnumType.STRING)
  private UserState userState;

  private Integer resetCode;

  private Instant resetActivatedAt;

  @JsonIgnore
  @OneToMany
  private Set<Actor> actors = new HashSet<>();

  private boolean isUpdate; // 是否有更新（true更新、false未更新）

  private boolean isOneSelf;  // 是否自己注册(true是自己注册、false不是)

  public User() {
  }

  public User(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public String getMobile() {
    return mobile;
  }

  public User mobile(String mobile) {
    setMobile(mobile);
    return this;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getUsername() {
    return username;
  }

  public User username(String username) {
    setUsername(username);
    return this;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public User password(String password) {
    setPassword(password);
    return this;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAvatar() {
    return avatar;
  }

  public User avatar(String avatar) {
    setAvatar(avatar);
    return this;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getPortrait() {
    return this.portrait;
  }

  public User portrait(String portrait) {
    setPortrait(portrait);
    return this;
  }

  public void setPortrait(String portrait) {
    this.portrait = portrait;
  }

  public GenderType getGender() {
    return gender;
  }

  public User gender(GenderType gender) {
    setGender(gender);
    return this;
  }

  public void setGender(GenderType gender) {
    this.gender = gender;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public User createdAt(Instant now) {
    setCreatedAt(now);
    return this;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public User setUserState(UserState userState) {
    this.userState = userState;
    return this;
  }

  public UserState getUserState() {
    return userState;
  }

  public Set<Actor> getActors() {
    return actors;
  }

  public User addActor(Actor actor) {
    this.actors.add(actor);
    return this;
  }

  public Integer getResetCode() {
    return resetCode;
  }

  public Instant getResetActivatedAt() {
    return resetActivatedAt;
  }

  public User resetCode(Integer resetCode) {
    this.resetCode = resetCode;
    this.resetActivatedAt = Instant.now();
    return this;
  }

  public void clearReset() {
    this.resetCode = null;
    this.resetActivatedAt = null;
  }

  public void setUpdate(boolean update) {
    isUpdate = update;
  }

  public User setOneSelf(boolean oneSelf) {
    this.isOneSelf = oneSelf;
    return this;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return getMobile() != null ? getMobile().equals(user.getMobile()) : user.getMobile() == null;
  }

  @Override
  public final int hashCode() {
    return getMobile() != null ? getMobile().hashCode() : 0;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", mobile='" + mobile + '\'' +
      ", username='" + username + '\'' +
      ", avatar='" + avatar + '\'' +
      ", gender=" + gender +
      ", createdAt=" + createdAt +
      '}';
  }

  public Instant getBirthday() {
    return birthday;
  }

  public void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }
}