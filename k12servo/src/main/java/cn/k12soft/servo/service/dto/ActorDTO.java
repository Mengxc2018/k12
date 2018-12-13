package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.enumeration.ActorType;
import java.time.Instant;
import java.util.Set;

public class ActorDTO {

  private Integer id;
  private UserDTO user;
  private Set<ActorType> types;
  private Instant joinedAt;

  public Integer getId() {
    return id;
  }

  public ActorDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public UserDTO getUser() {
    return user;
  }

  public ActorDTO setUser(UserDTO user) {
    this.user = user;
    return this;
  }

  public Set<ActorType> getTypes() {
    return types;
  }

  public ActorDTO setTypes(Set<ActorType> types) {
    this.types = types;
    return this;
  }

  public Instant getJoinedAt() {
    return joinedAt;
  }

  public ActorDTO setJoinedAt(Instant joinedAt) {
    this.joinedAt = joinedAt;
    return this;
  }

  @Override
  public String toString() {
    return "ActorDTO{" +
      "id=" + id +
      ", user=" + user +
      ", types=" + types +
      ", joinedAt=" + joinedAt +
      '}';
  }
}
