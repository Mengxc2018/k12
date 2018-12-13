package cn.k12soft.servo.security.jwt;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
public class JWTPrincipal implements UserDetails {

  private final Actor actor;
  private final User user;
  private final Collection<GrantedAuthority> authorities;

  public JWTPrincipal(Actor actor,
                      User user,
                      Collection<GrantedAuthority> authorities) {
    this.actor = actor;
    this.user = user;
    this.authorities = authorities;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return null;
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

  public Actor getActor() {
    return actor;
  }
}
