package cn.k12soft.servo.configuration;

import cn.k12soft.servo.domain.enumeration.GenderType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;

@ConfigurationProperties(prefix = "application")
public class K12Properties {

  private String appName = "k12servo";

  private Security security = new Security();

  private Metrics metrics = new Metrics();

  private SuperUser superUser = new SuperUser();

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public Security getSecurity() {
    return security;
  }

  public void setSecurity(Security security) {
    this.security = security;
  }

  public Metrics getMetrics() {
    return metrics;
  }

  public void setMetrics(Metrics metrics) {
    this.metrics = metrics;
  }

  public SuperUser getSuperUser() {
    return superUser;
  }

  public void setSuperUser(SuperUser superUser) {
    this.superUser = superUser;
  }

  public static class Security {

    private AccessRule[] accessRules = new AccessRule[0];
    private Jwt jwt = new Jwt();

    public AccessRule[] getAccessRules() {
      return accessRules;
    }

    public void setAccessRules(AccessRule[] accessRules) {
      this.accessRules = accessRules;
    }

    public Jwt getJwt() {
      return jwt;
    }

    public void setJwt(Jwt jwt) {
      this.jwt = jwt;
    }

    public static class Jwt {

      private String secret;

      private long tokenInSeconds = 1800;

      private long rememberMeTokenInSeconds = 2592000;

      public String getSecret() {
        return secret;
      }

      public void setSecret(String secret) {
        this.secret = secret;
      }

      public long getTokenInSeconds() {
        return tokenInSeconds;
      }

      public void setTokenInSeconds(long tokenInSeconds) {
        this.tokenInSeconds = tokenInSeconds;
      }

      public long getRememberMeTokenInSeconds() {
        return rememberMeTokenInSeconds;
      }

      public void setRememberMeTokenInSeconds(long rememberMeTokenInSeconds) {
        this.rememberMeTokenInSeconds = rememberMeTokenInSeconds;
      }
    }

    public static class AccessRule {

      private AntMatcher matcher;
      private String access;

      public AntMatcher getMatcher() {
        return matcher;
      }

      public void setMatcher(AntMatcher matcher) {
        this.matcher = matcher;
      }

      public String getAccess() {
        return access;
      }

      public void setAccess(String access) {
        this.access = access;
      }
    }

    public static class AntMatcher {

      private HttpMethod method;
      private String[] antPatterns;

      public HttpMethod getMethod() {
        return method;
      }

      public void setMethod(HttpMethod method) {
        this.method = method;
      }

      public String[] getAntPatterns() {
        return antPatterns;
      }

      public void setAntPatterns(String[] antPatterns) {
        this.antPatterns = antPatterns;
      }
    }
  }

  public static class SuperUser {

    private String mobile;
    private String username;
    private String password;
    private GenderType gender;

    public String getMobile() {
      return mobile;
    }

    public void setMobile(String mobile) {
      this.mobile = mobile;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public GenderType getGender() {
      return gender;
    }

    public void setGender(GenderType gender) {
      this.gender = gender;
    }
  }

  public static class Metrics {

    private final Jmx jmx = new Jmx();

    private final Logs logs = new Logs();

    public Jmx getJmx() {
      return jmx;
    }

    public Logs getLogs() {
      return logs;
    }

    public static class Jmx {

      private boolean enabled = true;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }
    }

    public static class Logs {

      private boolean enabled = false;

      private long reportFrequency = 60;

      public long getReportFrequency() {
        return reportFrequency;
      }

      public void setReportFrequency(int reportFrequency) {
        this.reportFrequency = reportFrequency;
      }

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }
    }
  }
}
