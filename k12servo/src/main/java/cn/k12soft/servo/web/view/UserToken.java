package cn.k12soft.servo.web.view;

import cn.k12soft.servo.service.dto.UserDTO;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
public class UserToken {

  private String token;
  private UserDTO user;

  public UserToken(String token, UserDTO user) {
    this.token = token;
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public UserDTO getUser() {
    return user;
  }
}