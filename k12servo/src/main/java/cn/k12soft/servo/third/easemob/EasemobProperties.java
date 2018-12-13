package cn.k12soft.servo.third.easemob;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2017/8/2.
 */
@ConfigurationProperties(prefix = "application.third.easemob")
public class EasemobProperties {

  private String appKey;
  private String clientId;
  private String clientSecret;

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }
}
