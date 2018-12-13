package cn.k12soft.servo.security;

import cn.k12soft.servo.domain.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

  private User user;

  public UserPrincipal(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return Collections.emptySet();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
