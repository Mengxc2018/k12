package cn.k12soft.servo.security.permission;

import cn.k12soft.servo.domain.enumeration.Permission;
import org.springframework.security.access.SecurityConfig;

public class PermissionRequiredAttribute extends SecurityConfig {

  private final Permission permission;

  public PermissionRequiredAttribute(Permission permission) {
    super(permission.getAuthority());
    this.permission = permission;
  }

  public Permission permission() {
    return permission;
  }
}
