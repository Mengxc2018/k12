package cn.k12soft.servo.web.api;

import static cn.k12soft.servo.domain.enumeration.Permission.ALIYUN_STS_TOKEN;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.third.aliyun.AliyunSTSService;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/tp/aliyun")
public class AliyunResource {

  private final AliyunSTSService stsService;

  @Autowired
  public AliyunResource(AliyunSTSService stsService) {
    this.stsService = stsService;
  }

  @GetMapping("/sts-token")
  @PermissionRequired(ALIYUN_STS_TOKEN)
  public AssumeRoleResponse.Credentials stsToken(@Active User user) throws ClientException {
    return stsService.applyForToken(user);
  }

    @GetMapping("/bd-token")
    @PermissionRequired(ALIYUN_STS_TOKEN)
  public Map<String, String> getK12softBdToken(@Active Actor actor) throws ClientException {
    return stsService.getK12softBdToken(actor.getUserId());
  }

}
