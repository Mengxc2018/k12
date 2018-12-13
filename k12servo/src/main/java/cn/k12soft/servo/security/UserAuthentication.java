package cn.k12soft.servo.security;

import cn.k12soft.servo.domain.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
public class UserAuthentication implements Authentication {

  private final UserPrincipal user;

  public UserAuthentication(User user) {
    this.user = new UserPrincipal(user);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptySet();
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  public String getName() {
    return user.getUsername();
  }

  public boolean isAuthenticated() {
    return true;
  }

  public void setAuthenticated(boolean authenticated) {
  }

  public Object getDetails() {
    return user;
  }

  @Override
  public Object getPrincipal() {
    return user;
  }
}
