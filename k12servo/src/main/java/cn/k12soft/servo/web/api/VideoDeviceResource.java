package cn.k12soft.servo.web.api;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.VideoDevice;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.VideoDeviceService;
import cn.k12soft.servo.third.aliyun.AliyunSTSService;
import cn.k12soft.servo.util.CommonUtils;
import cn.k12soft.servo.util.HttpUtil;
import cn.k12soft.servo.web.form.VideoDeviceForm;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.validation.Valid;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/video_device")
public class VideoDeviceResource {
    private final static String VOD_PLAY_INFO_URL="http://vod.cn-shanghai.aliyuncs.com";
    private final static String VOD_PLAY_INFO_HTTP_METHOD = "GET";

  private final VideoDeviceService service;

  @Autowired
  public VideoDeviceResource(VideoDeviceService service) {
    this.service = service;
  }

  @PostMapping
  public VideoDevice create(@Active Actor actor, @RequestBody @Valid VideoDeviceForm form) {
    return service.createDevice(form.getSchoolId(), form, form.getCreatorId());
  }

  @GetMapping(path = "/creator", params = {"creatorId"})
  public List<VideoDevice> getByCreator(@Active Actor actor, @RequestParam(name = "creatorId") Integer creatorId) {
    return service.getAllOfCreatorId(actor.getSchoolId(), creatorId);
  }

  @GetMapping(path = "/klass", params = {"klassId"})
  public List<VideoDevice> getAll(@Active Actor actor, @RequestParam(name = "klassId") Integer klassId) {
    return service.getAllOfKlassId(actor.getSchoolId(), klassId);
  }

  @DeleteMapping(params = {"deviceId", "creatorId"})
  public void delete(@Active Actor actor,
                     @RequestParam(name = "deviceId") String deviceId,
                     @RequestParam(name = "creatorId") Integer creatorId) {
    service.delete(actor.getSchoolId(), deviceId, creatorId);
  }

    @GetMapping(path = "/videoPath", params = {"videoId"})
  public GetVideoPlayAuthResponse getVideoPlayAuth(@Active Actor actor, @RequestParam(name="videoId") String videoId) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", AliyunSTSService.K12SOFT_BD_ACCESSKEY_TOKEN[0], AliyunSTSService.K12SOFT_BD_ACCESSKEY_TOKEN[1]);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        return response;
  }

  @GetMapping(path = "/videoUrl", params = {"videoId"})
  public String getVideoPlayInfo(@Active Actor actor, @RequestParam(name="videoId") String videoId){
      Map<String, String> publicParams = new HashMap<>();
      Map<String, String> privateParams = new HashMap<>();

      Map<String, String> paramMap = new HashMap<>();
      paramMap.put("Action", "GetPlayInfo");//操作接口名，系统规定参数，取值： GetPlayInfo
      paramMap.put("VideoId", videoId);//视频ID
      privateParams.putAll(paramMap);
      // 公共参数
      paramMap.put("Version", "2017-03-21");//API版本号，为日期形式：YYYY-MM-DD，本版本对应为2017-03-21。
      paramMap.put("AccessKeyId", AliyunSTSService.K12SOFT_BD_ACCESSKEY_TOKEN[0]);
      paramMap.put("SignatureVersion", "1.0");//签名算法版本，目前版本是1.0
      paramMap.put("SignatureNonce", CommonUtils.generateRandom());//唯一随机数，用于防止网络重放攻击。用户在不同请求间要使用不同的随机数值。代码示例参见SignatureNonce
      paramMap.put("Timestamp", CommonUtils.generateTimestamp());//请求的时间戳
      paramMap.put("SignatureMethod", "HMAC-SHA1");//签名方式，目前支持HMAC-SHA1
      paramMap.put("Format","json");

      publicParams.putAll(paramMap);
      publicParams.remove("Action");
      publicParams.remove("VideoId");

      String URL =  CommonUtils.generateURL(VOD_PLAY_INFO_URL, VOD_PLAY_INFO_HTTP_METHOD, AliyunSTSService.K12SOFT_BD_ACCESSKEY_TOKEN[1], publicParams, privateParams);

      String result = HttpUtil.getRequest(URL, null);

      return result;
  }



}
