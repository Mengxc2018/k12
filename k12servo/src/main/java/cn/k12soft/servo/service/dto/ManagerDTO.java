package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Role;
import java.util.Set;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
public class ManagerDTO {

  private Integer id;
  private Actor actor;
  private Set<Role> roles;

  public ManagerDTO(Actor actor) {
    this.id = actor.getId();
    this.actor = actor;
    this.roles = actor.getRoles();
  }

  public Integer getId() {
    return id;
  }

  public Actor getActor() {
    return actor;
  }

  public Set<Role> getRoles() {
    return roles;
  }
}
