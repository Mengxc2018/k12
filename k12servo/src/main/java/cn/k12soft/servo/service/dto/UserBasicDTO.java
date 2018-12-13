package cn.k12soft.servo.service.dto;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/3.
 */
public class UserBasicDTO {

  private Integer userId;
  private String avatar;
  private String username;

  public Integer getUserId() {
    return userId;
  }

  public UserBasicDTO setUserId(Integer userId) {
    this.userId = userId;
    return this;
  }

  public String getAvatar() {
    return avatar;
  }

  public UserBasicDTO setAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserBasicDTO setUsername(String username) {
    this.username = username;
    return this;
  }
}
