package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.ActorType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "school_user", columnNames = {"schoolId", "userId"}))
public class Actor extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  private int parentId; // 上一级用户id, 集团，大区, 省

  private Integer userId;

  private Instant joinedAt;

  private String cityId;    // 市
  private String provinceId;// 省
  private String regionId;  // 大区
  private String groupId;   // 集团

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<ActorType> types = new HashSet<>();

  @JsonIgnore
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
  private Set<Role> roles = new HashSet<>();

  public Actor() {
  }


  public Actor(Integer id) {
    this.id = id;
  }

  public Actor(Integer schoolId, Integer userId) {
    super(schoolId);
    this.userId = userId;
    this.joinedAt = Instant.now();
  }

  public Actor(Integer schoolId, int parentId, Integer userId, Instant joinedAt, String city, String province, String region, String group, Set<ActorType> types, Set<Role> roles) {
    super(schoolId);
    this.parentId = parentId;
    this.userId = userId;
    this.joinedAt = joinedAt;
    this.cityId = city;
    this.provinceId = province;
    this.regionId = region;
    this.groupId = group;
    this.types = types;
    this.roles = roles;
  }

  public Integer getId() {
    return id;
  }

  public int getParentId() {
    return parentId;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }

  public Integer getUserId() {
    return userId;
  }

  public Instant getJoinedAt() {
    return joinedAt;
  }

  public void setJoinedAt(Instant joinedAt) {
    this.joinedAt = joinedAt;
  }

  public Set<ActorType> getTypes() {
    return types;
  }

  public boolean addType(ActorType actorType) {
    return this.types.add(actorType);
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public Actor addRole(Role role) {
    this.roles.add(role);
    return this;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public String getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId;
  }

  public String getRegionId() {
    return regionId;
  }

  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Actor)) {
      return false;
    }
    Actor actor = (Actor) o;
    return getId() != null ? getId().equals(actor.getId()) : actor.getId() == null;
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }
}
