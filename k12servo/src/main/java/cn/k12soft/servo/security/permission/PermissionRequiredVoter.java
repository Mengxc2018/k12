package cn.k12soft.servo.security.permission;

import cn.k12soft.servo.domain.enumeration.Permission;
import java.util.Collection;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class PermissionRequiredVoter implements AccessDecisionVoter<MethodInvocation> {

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return attribute instanceof PermissionRequiredAttribute;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public int vote(Authentication authentication, MethodInvocation object,
                  Collection<ConfigAttribute> attributes) {
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    if (authorities.contains(Permission.ALL)) {
      return ACCESS_GRANTED;
    }
    boolean found = false;
    for (ConfigAttribute attribute : attributes) {
      if (supports(attribute)) {
        found = true;
        Permission permission = PermissionRequiredAttribute.class.cast(attribute).permission();
        if (authorities.contains(permission)) {
          return ACCESS_GRANTED;
        }
      }
    }
    return found ? ACCESS_DENIED : ACCESS_ABSTAIN;
  }
}
