package cn.k12soft.servo.service.dto;

import java.util.Collection;
import java.util.List;

public class PatriarchDTO {

  private Integer id;
  private ActorDTO actor;
  private Collection<StudentGuardianDTO> guardians;

  public Integer getId() {
    return id;
  }

  public PatriarchDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public ActorDTO getActor() {
    return actor;
  }

  public PatriarchDTO setActor(ActorDTO actor) {
    this.actor = actor;
    return this;
  }

  public Collection<StudentGuardianDTO> getGuardians() {
    return guardians;
  }

  public PatriarchDTO setGuardians(Collection<StudentGuardianDTO> guardians) {
    this.guardians = guardians;
    return this;
  }

  @Override
  public String toString() {
    return "PatriarchDTO{" +
      "id=" + id +
      ", actor=" + actor +
      '}';
  }
}
