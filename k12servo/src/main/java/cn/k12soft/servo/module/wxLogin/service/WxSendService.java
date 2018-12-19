package cn.k12soft.servo.module.wxLogin.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.WxSendType;
import cn.k12soft.servo.module.weixin.pay.K12WXPayConfig;
import cn.k12soft.servo.module.weixin.pojo.Token;
import cn.k12soft.servo.module.weixin.util.CommonUtil;
import cn.k12soft.servo.module.wxLogin.domain.WxPushMsg;
import cn.k12soft.servo.module.wxLogin.domain.WxUsers;
import cn.k12soft.servo.module.wxLogin.domain.form.TemplateMessage;
import cn.k12soft.servo.module.wxLogin.domain.pojo.PushCode;
import cn.k12soft.servo.module.wxLogin.domain.pojo.TemplateMessagePojo;
import cn.k12soft.servo.module.wxLogin.repository.PushCodeRepository;
import cn.k12soft.servo.module.wxLogin.repository.WxPushMsgRepository;
import cn.k12soft.servo.module.wxLogin.repository.WxUserRepository;
import cn.k12soft.servo.repository.UserRepository;
import com.google.common.base.Strings;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class WxSendService {

    public final static String SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";      // 发送模板消息
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"; // 获取小程序 access_token
    public final Logger log = LoggerFactory.getLogger(WxSendService.class);

    private final UserRepository userRepository;
    private final WxUserRepository wxUserRepository;
    private final PushCodeRepository pushCodeRepository;
    private final WxPushMsgRepository wxPushMsgRepository;

    @Autowired
    public WxSendService(UserRepository userResource, WxUserRepository wxUserRepository, PushCodeRepository pushCodeRepository, WxPushMsgRepository wxPushMsgRepository) {
        this.userRepository = userResource;
        this.wxUserRepository = wxUserRepository;
        this.pushCodeRepository = pushCodeRepository;
        this.wxPushMsgRepository = wxPushMsgRepository;
    }


    public ResponseEntity<Token> getToken(String url) {
        Token token = null;

        String requestUrl = url.replace("APPID", K12WXPayConfig.WEIXIN_PAY_APPID).replace("APPSECRET", K12WXPayConfig.WEIXIN_PAY_APPSECRET);

        // 发起GET请求获取凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                return new ResponseEntity("errcode:" + jsonObject.getInt("errcode") + " errmsg:" + jsonObject.getString("errmsg"), HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok().body(token);
    }

    /**
     * 发送微信消息
     *
     * @param actor
     * @param form
     * @return
     */
    public Map<String, Object> sendTemplateMessage(Actor actor, TemplateMessage form, String formid) {
        Map<String, Object> map = new HashMap<>();

        // 判断formid是否为空，为空，则是交互消息，用上传的formid；不为空，则是系统消息
        if (Strings.isNullOrEmpty(formid)) {
            formid = form.getFormId();
        }

        try {
            // 获取access_token
            Token token = getToken(token_url).getBody();

            // 组装数据
            JSONObject date = new JSONObject();
            date.put("touser", getOpenid(actor));
//          date.put("touser", "oNEVZ5G-qXO74HkgB75aWlMzmmMA");   // 测试用
            date.put("template_id", form.getTemplateId());
            date.put("page", form.getPage());
            date.put("form_id", formid);
            date.put("data", form.getData());
            date.put("emphasis_keyword", form.getEmphasisKeyword());

            // 推送消息
            boolean sendCodeTrue = true;
            while (sendCodeTrue){
                // 发送消息
                JSONObject jsonObject = CommonUtil.httpsRequest(SEND_URL.replace("ACCESS_TOKEN", token.getAccessToken()), "POST", date.toString());
                map = jsonObject;
                map.put("actorId", actor.getId());
                log.info("errcode：{}, actorId：{}", jsonObject.get("errcode"), actor.getId());
                // 判断发送是否成功 41028:form_id不正确，或者过期 41029:form_id已被使用。如果返回41028、41029，则删掉旧的formid重新获取
                if (!jsonObject.get("errcode").toString().equals("0")){
                    if (jsonObject.get("errcode").toString().equals("41029") || jsonObject.get("errcode").toString().equals("41028")) {
                        sendCodeTrue = true;
                        log.info("old formid:" + formid);
                        pushCodeRepository.deleteByFormId(formid);
                        List<PushCode> pushCodes =  pushCodeRepository.findAllByActorIdAndCreatedAtMax(actor.getId());
                        if (pushCodes.size() == 0){
                            log.error("actorId：{}没有足够的formid", actor.getId());
                            sendCodeTrue = false;
                            continue;
                        }
                        formid = pushCodes.get(0).getFormId();
                        date.put("form_id", formid);
                        log.info("new formid:"+formid);
                        log.error(map.toString());
                    }
                }else{
                    sendCodeTrue = false;
                }
            }

        } catch (Exception e) {
            log.error(e.toString());
        }

        return map;
    }

    public String getOpenid(Actor actor) {
        User user = userRepository.findOne(actor.getUserId());
        WxUsers wxUsers = wxUserRepository.findByMobile(user.getMobile());
        return wxUsers.getOpenid();
    }

    public ResponseEntity sendMessage(List<TemplateMessagePojo> messagesPojo, Integer klassId, WxSendType WX_SEND){
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            for (TemplateMessagePojo message : messagesPojo) {
                Actor sendActor = message.getActors();
                String formid = message.getTemplateMessage().getFormId();

                CompletableFuture completableFuture = CompletableFuture.supplyAsync(()->{
                    sendTemplateMessage(message.getActors(), message.getTemplateMessage(), formid);
                    return null;
                });

                // 保存数据
                WxPushMsg wxPushMsg = new WxPushMsg(
                        message.getJsonObject().toString(),
                        sendActor.getId(),
                        klassId,
                        WX_SEND,
                        sendActor.getSchoolId(),
                        Instant.now()
                );
                wxPushMsgRepository.save(wxPushMsg);
                log.info("保存推送消息：" + wxPushMsg.toString());
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * 通过actorID获取推送码的formId
     *
     * @param actorId
     * @return
     */
    public String getFormId(Integer actorId) {
        String formid = "";
        try {
            List<PushCode> pushCodeList = pushCodeRepository.findAllByActorIdAndCreatedAtMax(actorId);
            formid = pushCodeList.get(0).getFormId();
            System.out.println(formid);
            pushCodeRepository.delete(pushCodeList.get(0));
        } catch (Exception e) {
            System.out.println(e);
            log.error("actor: {} 没有足够的formid", actorId);
        }
        return formid;
    }


}
