package cn.k12soft.servo.third.aliyun;

import static java.util.Objects.requireNonNull;

import cn.k12soft.servo.domain.User;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AliyunSTSService {
    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    private static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    // 当前 STS API 版本
    private static final String STS_API_VERSION = "2015-04-01";
    public static final String[] K12SOFT_BD_ACCESSKEY_TOKEN = new String[]{"LTAIllDotklimOoa", "3LYwtfcm8CDtuECMMp5VED3o08BKpA", "acs:ram::1276178519321483:role/k12softsts"};

  private final AliyunProperties configuration;
  private final IAcsClient client;

  @Autowired
  public AliyunSTSService(AliyunProperties configuration) {
    this.configuration = configuration;
    this.client = new DefaultAcsClient(
      DefaultProfile.getProfile(configuration.getRegion(),
        configuration.getAccessKeyId(),
        configuration.getAccessKeySecret())
    );
  }

  public AssumeRoleResponse.Credentials applyForToken(User user) throws ClientException {
    String applyForToken = "applyForToken";
    AliyunProperties.STS sts = configuration.getSts();
    String policy = requireNonNull(sts.getPolicyMap().get(applyForToken), "applyForToken policy");
    AssumeRoleRequest request = new AssumeRoleRequest();
    request.setRoleArn(sts.getArn());
    request.setRoleSessionName("user_" + user.getId());
    request.setPolicy(policy);
    request.setDurationSeconds(configuration.getSts().getTokenDuration());
    return client.getAcsResponse(request).getCredentials();
  }

    public Map<String,String> getK12softBdToken(Integer userId) throws ClientException {
        Map<String, String> map = new HashMap<>();

        String accessKeyId = K12SOFT_BD_ACCESSKEY_TOKEN[0];
        String accessKeySecret = K12SOFT_BD_ACCESSKEY_TOKEN[1];
        String roleArn = K12SOFT_BD_ACCESSKEY_TOKEN[2];
        String roleSessionName = "bd_"+userId;
        String policy = null;

        ProtocolType protocolType = ProtocolType.HTTPS;
        final AssumeRoleResponse response = assumeRole(accessKeyId, accessKeySecret,
                    roleArn, roleSessionName, policy, protocolType);
        map.put("accessKeyId",response.getCredentials().getAccessKeyId());
        map.put("accessKeySecret", response.getCredentials().getAccessKeySecret());
        map.put("token", response.getCredentials().getSecurityToken());
        map.put("expiration", response.getCredentials().getExpiration());
        map.put("arn", response.getAssumedRoleUser().getArn());
        map.put("roleId", response.getAssumedRoleUser().getAssumedRoleId());
        return map;
    }

    private static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
                                         String roleArn, String roleSessionName, String policy,
                                         ProtocolType protocolType) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);
            return response;
        } catch (ClientException e) {
            throw e;
        }
    }
}
