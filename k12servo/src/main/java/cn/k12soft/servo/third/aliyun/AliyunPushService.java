package cn.k12soft.servo.third.aliyun;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.KlassPlan;
import cn.k12soft.servo.domain.enumeration.PlanType;
import cn.k12soft.servo.module.wxLogin.service.WxService;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.aliyuncs.utils.ParameterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by xfnjlove on 2018/5/3.
 */
@Service
public class AliyunPushService {

    private Long pushAppKey;
    private Long pushIosAppKey;
    private DefaultAcsClient client;
    private final WxService wxService;

    public static final int  EVENT_TYPE_CLASS_FEED = 1001;
    public static final int  EVENT_TYPE_CLASS_PLAN_NOTICE = 1002;
    public static final int  EVENT_TYPE_CLASS_PLAN_WEEK = 1003;
    public static final int  EVENT_TYPE_CLASS_PLAN_MONTH = 1004;
    public static final int  EVENT_TYPE_CLASS_PLAN_HOMEWORK = 1005;
    public static final int  EVENT_TYPE_TEA_APPLY = 1006;
    public static final int  EVENT_TYPE_TEA_APPLY_PASS = 1007;
    public static final int  EVENT_TYPE_TEA_APPLY_REJECT = 1008;


    public final static String WX_SEND_PAGE_NOTICE = "notice";
    public final static String WX_SEND_PAGE_DYAMIC = "dyamic";


    @Autowired
    AliyunPushService(AliyunProperties properties, WxService wxService){

        pushAppKey = Long.parseLong(properties.getPush().getPushKeyId());
        pushIosAppKey = Long.parseLong(properties.getPush().getPushIosKeyId());
        this.wxService = wxService;
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",properties.getAccessKeyId(),properties.getAccessKeySecret());
        client = new DefaultAcsClient(profile);
    }


    public void sendTeaApplyNotification(String accounts){

        this.sendNotification("审批消息","您有新的申请审批,快来看看吧","ACCOUNT",accounts,
                "ACTIVITY","com.kdev.app.main.activity.SplashActivity",
                "com.kdev.app.widget.push.ThirdPushPopupActivity","审批消息",
                "您有新的申请审批,快来看看吧",
                EVENT_TYPE_TEA_APPLY,0);


    }

    public void sendTeaApplyPassNotification(String accounts){

        this.sendNotification("审批消息","您的申请审批已经通过,快来看看吧","ACCOUNT",accounts,
                "ACTIVITY","com.kdev.app.main.activity.SplashActivity",
                "com.kdev.app.widget.push.ThirdPushPopupActivity","审批消息",
                "您有新的申请审批,快来看看吧",
                EVENT_TYPE_TEA_APPLY_PASS,0);


    }

    public void sendTeaApplyRejectNotification(String accounts){

        this.sendNotification("审批消息","您的申请审批已被拒绝,速去修改吧","ACCOUNT",accounts,
                "ACTIVITY","com.kdev.app.main.activity.SplashActivity",
                "com.kdev.app.widget.push.ThirdPushPopupActivity","审批消息",
                "您的申请审批已被拒绝,,速去修改吧",
                EVENT_TYPE_TEA_APPLY_REJECT,0);

    }

    public void sendKlassFeedNotification(String accounts, int klassId){

        this.sendNotification("班级消息","您家宝宝有新的班级动态,快来看看吧","ACCOUNT",accounts,
                                "ACTIVITY","com.kdev.app.main.activity.SplashActivity",
                                "com.kdev.app.widget.push.ThirdPushPopupActivity","班级动态通知",
                                "您家宝宝有新的班级动态,快来看看吧",
                                EVENT_TYPE_CLASS_FEED,klassId);
    }

    public void sendKlassPlan(Actor actor, KlassPlan klassPlan, String accounts, int klassId, PlanType planType){
        String type = "";
        String notifyDesc = "";
        int planTypeEvent = EVENT_TYPE_CLASS_PLAN_NOTICE;
        switch (planType){
            case NOTICE:
                planTypeEvent = EVENT_TYPE_CLASS_PLAN_NOTICE;
                notifyDesc = "您宝宝班级有新的通知,快来看看吧";
                type = "notice";
                break;
            case MONTH:
                planTypeEvent = EVENT_TYPE_CLASS_PLAN_MONTH;
                notifyDesc = "您宝宝班级有新的月计划,快来看看吧";
                type = "plan";
                break;
            case WEEK:
                planTypeEvent = EVENT_TYPE_CLASS_PLAN_WEEK;
                notifyDesc = "您宝宝班级有新的周计划,快来看看吧";
                type = "plan";
                break;
            case HOMEWORK:
                planTypeEvent = EVENT_TYPE_CLASS_PLAN_HOMEWORK;
                notifyDesc = "您宝宝班级有新的作业提醒,快来看看吧";
                type = "notice";
                break;
        }
            this.sendNotification("班级消息",notifyDesc,"ACCOUNT",accounts,
                "ACTIVITY","com.kdev.app.main.activity.SplashActivity",
                "com.kdev.app.widget.push.ThirdPushPopupActivity","班级消息",
                notifyDesc, planTypeEvent,klassId);

        Map<String, String> map = new HashMap<>();
        map.put("msg", notifyDesc);
        map.put("type", type);
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->{
            wxService.sendWxKlassPlan(actor, klassPlan, klassId, map);
            return null;
        });

    }

    public void sendNotification(String title,String body,String target,String targetValues,
                                 String openType,String openActivity,
                                 String popupActivity,String popupTitle,String popupBody,
                                 int extTypeValue,int klassId){

       this.createAndroidPushReq("NOTICE",title,body,target,targetValues,
               openType,openActivity,
               popupActivity,popupTitle,popupBody,
               extTypeValue,klassId);

        this.createIosPushReq("NOTICE",title,body,target,targetValues,
                openType,openActivity,
                popupActivity,popupTitle,popupBody,
                extTypeValue,klassId);

    }

    public void sendMessage(String title,String body,String target,String targetValues,
                                 String openType,String openActivity,
                                 String popupActivity,String popupTitle,String popupBody,
                                 int extTypeValue,int klassId){

        this.createAndroidPushReq("MESSAGE",title,body,target,targetValues,
                openType,openActivity,
                popupActivity,popupTitle,popupBody,
                extTypeValue,klassId);

        this.createIosPushReq("MESSAGE",title,body,target,targetValues,
                openType,openActivity,
                popupActivity,popupTitle,popupBody,
                extTypeValue,klassId);
    }

    public void createAndroidPushReq(String pushType,String title,String body,String target,String targetValues,
                                 String openType,String openActivity,
                                 String popupActivity,String popupTitle,String popupBody,
                                 int extTypeValue,int klassId){

        PushRequest pushRequest = new PushRequest();
        // 推送目标
        pushRequest.setAppKey(pushAppKey);
        pushRequest.setTarget(target); //推送目标: DEVICE:按设备推送 ALIAS : 按别名推送 ACCOUNT:按帐号推送  TAG:按标签推送; ALL: 广播推送
        pushRequest.setTargetValue(targetValues); //根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
        pushRequest.setPushType(pushType); // 消息类型 MESSAGE NOTICE
        pushRequest.setDeviceType("ANDROID"); // 设备类型 ANDROID iOS ALL.
        // 推送配置
        pushRequest.setTitle(title); // 消息的标题
        pushRequest.setBody(body); // 消息的内容
        // 推送配置: Android
        pushRequest.setAndroidNotifyType("BOTH");//通知的提醒方式 "VIBRATE" : 震动 "SOUND" : 声音 "BOTH" : 声音和震动 NONE : 静音
        pushRequest.setAndroidNotificationBarType(1);//通知栏自定义样式0-100
        pushRequest.setAndroidNotificationBarPriority(1);//通知栏自定义样式0-100
        pushRequest.setAndroidOpenType(openType); //点击通知后动作 "APPLICATION" : 打开应用 "ACTIVITY" : 打开AndroidActivity "URL" : 打开URL "NONE" : 无跳转
        pushRequest.setAndroidActivity(openActivity); //"com.alibaba.push2.demo.XiaoMiPushActivity" 设定通知打开的activity，仅当AndroidOpenType="Activity"有效
        pushRequest.setAndroidMusic("default"); // Android通知音乐
        pushRequest.setAndroidPopupActivity(popupActivity);//设置该参数后启动辅助弹窗功能, 此处指定通知点击后跳转的Activity（辅助弹窗的前提条件：1. 集成第三方辅助通道；2. StoreOffline参数设为true）
        pushRequest.setAndroidPopupTitle(popupTitle);
        pushRequest.setAndroidPopupBody(popupBody);
        //设定通知的扩展属性。(注意 : 该参数要以 json map 的格式传入,否则会解析出错)
        pushRequest.setAndroidExtParameters("{\"type\":\""+extTypeValue+"\",\"attribute\":\""+klassId+"\"}");
        // 推送控制
        Date pushDate = new Date(System.currentTimeMillis() +20 * 1000) ; // 30秒之间的时间点, 也可以设置成你指定固定时间
        String pushTime = ParameterHelper.getISO8601Time(pushDate);
        pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送
        String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)); // 12小时后消息失效, 不会再发送
        pushRequest.setExpireTime(expireTime);
        pushRequest.setStoreOffline(false); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到

        try {

            PushResponse pushResponse = client.getAcsResponse(pushRequest);
            System.out.printf("RequestId: %s, MessageID: %s\n",
                    pushResponse.getRequestId(), pushResponse.getMessageId());

        }catch (ClientException e){
            System.out.printf("client exception: %s\n",
                    e.getErrCode()+":"+e.getErrMsg());
        }

    }

    public void createIosPushReq(String pushType,String title,String body,String target,String targetValues,
                              String openType,String openActivity,
                              String popupActivity,String popupTitle,String popupBody,
                              int extTypeValue,int klassId){

        PushRequest pushRequest = new PushRequest();
        // 推送目标
        pushRequest.setAppKey(pushIosAppKey);
        pushRequest.setTarget(target); //推送目标: DEVICE:按设备推送 ALIAS : 按别名推送 ACCOUNT:按帐号推送  TAG:按标签推送; ALL: 广播推送
        pushRequest.setTargetValue(targetValues); //根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
        pushRequest.setPushType(pushType); // 消息类型 MESSAGE NOTICE
        pushRequest.setDeviceType("iOS"); // 设备类型 ANDROID iOS ALL.
        // 推送配置
        pushRequest.setTitle(title); // 消息的标题
        pushRequest.setBody(body); // 消息的内容
        // 推送配置: iOS
        pushRequest.setIOSBadge(5); // iOS应用图标右上角角标
        pushRequest.setIOSMusic("default"); // iOS通知声音
        pushRequest.setIOSNotificationCategory("iOS10 Notification Category");//指定iOS10通知Category
        pushRequest.setIOSMutableContent(true);//是否允许扩展iOS通知内容
        pushRequest.setIOSApnsEnv("PRODUCT");//iOS的通知是通过APNs中心来发送的，需要填写对应的环境信息。"DEV" : 表示开发环境 "PRODUCT" : 表示生产环境
        pushRequest.setIOSRemind(true); // 消息推送时设备不在线（既与移动推送的服务端的长连接通道不通），则这条推送会做为通知，通过苹果的APNs通道送达一次。注意：离线消息转通知仅适用于生产环境
        pushRequest.setIOSRemindBody("iOSRemindBody");//iOS消息转通知时使用的iOS通知内容，仅当iOSApnsEnv=PRODUCT && iOSRemind为true时有效
         //通知的扩展属性(注意 : 该参数要以json map的格式传入,否则会解析出错)
        pushRequest.setIOSExtParameters("{\"type\":\""+extTypeValue+"\",\"attribute\":\""+klassId+"\"}");

        // 推送控制
        Date pushDate = new Date(System.currentTimeMillis() +20 * 1000) ; // 30秒之间的时间点, 也可以设置成你指定固定时间
        String pushTime = ParameterHelper.getISO8601Time(pushDate);
        pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送
        String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)); // 12小时后消息失效, 不会再发送
        pushRequest.setExpireTime(expireTime);
        pushRequest.setStoreOffline(false); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到

        try {

            PushResponse pushResponse = client.getAcsResponse(pushRequest);
            System.out.printf("RequestId: %s, MessageID: %s\n",
                    pushResponse.getRequestId(), pushResponse.getMessageId());

        }catch (ClientException e){
            System.out.printf("client exception: %s\n",
                    e.getErrCode()+":"+e.getErrMsg());
        }

    }


}
