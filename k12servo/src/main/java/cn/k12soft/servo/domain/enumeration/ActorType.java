package cn.k12soft.servo.domain.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum ActorType implements GrantedAuthority {
  PATRIARCH
  ,TEACHER
  ,MANAGER
  ,CITY //3 市级
  ,PROVINCE //4 省级
  ,REGION //5 大区
  ,GROUP //6 集团
  ;

  private final String roleAuthority = "ROLE_" + name();

  @Override
  public String getAuthority() {
    return roleAuthority;
  }
}
