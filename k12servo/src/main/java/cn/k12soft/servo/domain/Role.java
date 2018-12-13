package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.Permission;
import com.google.common.base.Objects;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Role extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  private String name;

  @Column(length = 512)
  private String description;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<Permission> permissions = new HashSet<>();

  Role() {
  }

  public Role(Integer schoolId,
              String name,
              String description) {
    super(schoolId);
    this.name = name;
    this.description = description;
    this.permissions = new HashSet<>();
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Role setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Role setDescription(String description) {
    this.description = description;
    return this;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Role)) {
      return false;
    }
    Role role = (Role) o;
    return Objects.equal(getName(), role.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getName());
  }

  @Override
  public String toString() {
    return "Role{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", desc='" + description + '\'' +
      '}';
  }
}
