package cn.k12soft.servo.web.form;

import cn.k12soft.servo.domain.enumeration.Permission;
import java.util.Set;
import javax.validation.constraints.NotNull;

public class RoleForm {

  @NotNull
  private String name;

  @NotNull
  private String description;

  @NotNull
  private Set<Permission> permissions;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }
}
