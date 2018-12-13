package cn.k12soft.servo.third.aliyun;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.third.aliyun")
public class AliyunProperties {

  private String region;
  private String accessKeyId;
  private String accessKeySecret;
  private STS sts = new STS();
  private SMS sms = new SMS();
  private PUSH push = new PUSH();

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getAccessKeyId() {
    return accessKeyId;
  }

  public void setAccessKeyId(String accessKeyId) {
    this.accessKeyId = accessKeyId;
  }

  public String getAccessKeySecret() {
    return accessKeySecret;
  }

  public void setAccessKeySecret(String accessKeySecret) {
    this.accessKeySecret = accessKeySecret;
  }

  public STS getSts() {
    return sts;
  }

  public void setSts(STS sts) {
    this.sts = sts;
  }

  public SMS getSms() {
    return sms;
  }

  public void setSms(SMS sms) {
    this.sms = sms;
  }

  public PUSH getPush() {
    return push;
  }

  public void setPush(PUSH push) {
    this.push = push;
  }

  public static class STS {

    private String arn;
    private Map<String, String> policyMap;
    private Long tokenDuration;

    public String getArn() {
      return arn;
    }

    public void setArn(String arn) {
      this.arn = arn;
    }

    public Map<String, String> getPolicyMap() {
      return policyMap;
    }

    public void setPolicyMap(Map<String, String> policyMap) {
      this.policyMap = policyMap;
    }

    public Long getTokenDuration() {
      return tokenDuration;
    }

    public void setTokenDuration(Long tokenDuration) {
      this.tokenDuration = tokenDuration;
    }
  }

  public static class SMS {
    private String product = "Dysmsapi";
    private String domain = "dysmsapi.aliyuncs.com";
    private String endpoint;
    private String topicName;
    private String signName;
    private String smsMessageBody;
    private Map<AliyunSMSTemplate, String> templateCodes = new HashMap<>();

    public String getProduct() {
      return product;
    }

    public String getDomain() {
      return domain;
    }

    public String getEndpoint() {
      return endpoint;
    }

    public void setEndpoint(String endpoint) {
      this.endpoint = endpoint;
    }

    public String getTopicName() {
      return topicName;
    }

    public void setTopicName(String topicName) {
      this.topicName = topicName;
    }

    public String getSignName() {
      return signName;
    }

    public void setSignName(String signName) {
      this.signName = signName;
    }

    public String getSmsMessageBody() {
      return smsMessageBody;
    }

    public void setSmsMessageBody(String smsMessageBody) {
      this.smsMessageBody = smsMessageBody;
    }

    public Map<AliyunSMSTemplate, String> getTemplateCodes() {
      return templateCodes;
    }

    public void setTemplateCode(Map<AliyunSMSTemplate, String> templateCodes) {
      this.templateCodes = templateCodes;
    }

    public void setTemplateCode(AliyunSMSTemplate templateCode, String value) {
      this.templateCodes.put(templateCode, value);
    }
  }
  public static class PUSH {

    private String pushKeyId;
    private String pushKeySecret;
    private String pushIosKeyId;

    public String getPushKeyId() {
      return pushKeyId;
    }

    public void setPushKeyId(String pushKeyId) {
      this.pushKeyId = pushKeyId;
    }

    public String getPushKeySecret() {
      return pushKeySecret;
    }

    public void setPushKeySecret(String pushKeySecret) {
      this.pushKeySecret = pushKeySecret;
    }

    public String getPushIosKeyId() {
      return pushIosKeyId;
    }

    public void setPushIosKeyId(String pushIosKeyId) {
      this.pushIosKeyId = pushIosKeyId;
    }
  }
}
