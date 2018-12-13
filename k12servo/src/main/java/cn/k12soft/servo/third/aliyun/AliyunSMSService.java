package cn.k12soft.servo.third.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliyunSMSService {

  private final ObjectMapper objectMapper;
  private final AliyunProperties properties;
  private final IAcsClient acsClient;

  @Autowired
  AliyunSMSService(ObjectMapper objectMapper, AliyunProperties properties) throws ClientException {
    this.objectMapper = objectMapper;
    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", properties.getAccessKeyId(), properties.getAccessKeySecret());
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", properties.getSms().getProduct(), properties.getSms().getDomain());
    this.acsClient = new DefaultAcsClient(profile);
    this.properties = properties;
  }

  public SendSmsResponse sendInvitationCode(String phoneNumber,
                                            int secretCode) {
    AliyunProperties.SMS sms = properties.getSms();
    Map<String, Object> params = new HashMap<>();
    params.put("code", secretCode);
    String tempCode = sms.getTemplateCodes().get(AliyunSMSTemplate.INVITATION);
    try {
      return send(phoneNumber, tempCode, params);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public SendSmsResponse sendPasswordResetCode(String phoneNumber,
                                               int secretCode) {
    AliyunProperties.SMS sms = properties.getSms();
    Map<String, Object> params = new HashMap<>();
    params.put("code", secretCode);
    String tempCode = sms.getTemplateCodes().get(AliyunSMSTemplate.RESET_PASSWORD);
    try {
      return send(phoneNumber, tempCode, params);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public SendSmsResponse sendVerificationCode(String phoneNumber, int verificationCode){
    AliyunProperties.SMS sms = properties.getSms();
    Map<String, Object> map = new HashMap<>();
    map.put("code", verificationCode);
    String tempCode = sms.getTemplateCodes().get(AliyunSMSTemplate.INVITATION);
    try {
      return send(phoneNumber, tempCode, map);
    }catch (Exception e){
      throw new IllegalArgumentException(e);
    }
  }

  private SendSmsResponse send(String phoneNumber, String tempCode, Map<String, Object> params) throws Exception {
    SendSmsRequest request = new SendSmsRequest();
    request.setPhoneNumbers(phoneNumber);
    request.setSignName(properties.getSms().getSignName());
    request.setTemplateCode(tempCode);
    request.setTemplateParam(objectMapper.writeValueAsString(params));
    return acsClient.getAcsResponse(request);
  }
}
