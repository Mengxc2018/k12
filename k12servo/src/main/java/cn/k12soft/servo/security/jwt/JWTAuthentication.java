package cn.k12soft.servo.security.jwt;

import java.security.Principal;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTAuthentication implements Authentication {

  private final JWTPrincipal principal;

  JWTAuthentication(JWTPrincipal principal) {
    this.principal = principal;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return principal.getAuthorities();
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public String getName() {
    if (this.getPrincipal() instanceof UserDetails) {
      return ((UserDetails) this.getPrincipal()).getUsername();
    }
    if (getPrincipal() instanceof Principal) {
      return ((Principal) getPrincipal()).getName();
    }

    return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
  }

  @Override
  public Object getDetails() {
    return principal.getActor();
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }
}
