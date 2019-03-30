package cn.k12soft.servo.module.wxLogin.service;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.domain.enumeration.ActiveSourceType;
import cn.k12soft.servo.domain.enumeration.WxActiveType;
import cn.k12soft.servo.domain.enumeration.WxSendType;
import cn.k12soft.servo.module.charge.domain.StudentCharge;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodType;
import cn.k12soft.servo.module.healthCheck.domain.HealthCheck;
import cn.k12soft.servo.module.weixin.pay.K12WXPayConfig;
import cn.k12soft.servo.module.weixin.pojo.Token;
import cn.k12soft.servo.module.weixin.util.CommonUtil;
import cn.k12soft.servo.module.wxLogin.domain.WxActive;
import cn.k12soft.servo.module.wxLogin.domain.WxActiveDTO;
import cn.k12soft.servo.module.wxLogin.domain.WxPushMsg;
import cn.k12soft.servo.module.wxLogin.domain.WxUsers;
import cn.k12soft.servo.module.wxLogin.domain.form.PushCodeForm;
import cn.k12soft.servo.module.wxLogin.domain.form.TemplateMessage;
import cn.k12soft.servo.module.wxLogin.domain.pojo.PushCode;
import cn.k12soft.servo.module.wxLogin.domain.pojo.TemplateMessagePojo;
import cn.k12soft.servo.module.wxLogin.repository.PushCodeRepository;
import cn.k12soft.servo.module.wxLogin.repository.WxActiveRepository;
import cn.k12soft.servo.module.wxLogin.repository.WxPushMsgRepository;
import cn.k12soft.servo.module.wxLogin.repository.WxUserRepository;
import cn.k12soft.servo.repository.*;
import com.google.common.base.Strings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class WxService {

    private final UserRepository userRepository;
    private final ActorRepository actorRepository;
    private final KlassRepository klassRepository;
    private final WxUserRepository wxUserRepository;
    private final StudentRepository studentRepository;
    private final WxActiveRepository wxActiveRepository;
    private final GuardianRepository guardianRepository;
    private final PushCodeRepository pushCodeRepository;
    private final WxPushMsgRepository wxPushMsgRepository;
    private final WxSendService wxSendService;

    @Autowired
    public WxService(UserRepository userRepository,
                     ActorRepository actorRepository,
                     KlassRepository klassRepository,
                     WxUserRepository wxUserRepository,
                     StudentRepository studentRepository,
                     WxActiveRepository wxActiveRepository,
                     GuardianRepository guardianRepository,
                     PushCodeRepository pushCodeRepository,
                     WxPushMsgRepository wxPushMsgRepository,
                     WxSendService wxSendService) {
        this.userRepository = userRepository;
        this.actorRepository = actorRepository;
        this.klassRepository = klassRepository;
        this.wxUserRepository = wxUserRepository;
        this.studentRepository = studentRepository;
        this.wxActiveRepository = wxActiveRepository;
        this.guardianRepository = guardianRepository;
        this.pushCodeRepository = pushCodeRepository;
        this.wxPushMsgRepository = wxPushMsgRepository;
        this.wxSendService = wxSendService;
    }

    public static final String URL = "https://api.weixin.qq.com/cgi-bin/wxopen/template/list?access_token=ACCESS_TOKEN";        // 获取帐号下已存在的模板列表
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"; // 获取小程序 access_token
    public final static String TEMPLATE_ID = "Y84Nf9zM5oAQ0l_DFcyOnYpAigrFIXYscY_AD22ijqM";     // 通用消息模版ID
    public final static String WX_PAGE = "pages/information/information";
//    public final static String SEND_PLAN_PAGE = "pages/information/information";
    public final static String WX_SEND_PAGE_NOTICE = "pages/notice/notice";     // 班级通知
    public final static String WX_SEND_PAGE_DYAMIC = "pages/dyamic/dyamic";     // 班级动态
    public final static String WX_SEND_PAGE_PLAN = "pages/plan/plan";  // 班级计划
    public final static String WX_SEND_STU_CHARGE = ""; // 收费计划

    public final Logger log = LoggerFactory.getLogger(WxService.class);

    public Map<String, Object> getTemplateList(Actor actor) {

        Map<String, Object> map = new HashMap<>();
        CommonUtil.httpsRequest(token_url.replace("APPID", K12WXPayConfig.WEIXIN_PAY_APPID).replace("APPSECRET", K12WXPayConfig.WEIXIN_PAY_APPSECRET), "GET", null);
        try {

            // 发送请求获取access_token
            Token token = wxSendService.getToken(token_url).getBody();

            // 存放jsonDate数据
            Map<String, String> maplist = new HashMap<>();
            maplist.put("offset", "0");
            maplist.put("count", "20");
            JSONObject json = JSONObject.fromObject(maplist);

            // 获取帐号下已存在的模版列表
            JSONObject jsonObject_token = CommonUtil.httpsRequest(URL.replace("ACCESS_TOKEN", token.getAccessToken()), "POST", json.toString());
            if (jsonObject_token.getString("list") != null) {
                map.put("list", jsonObject_token.getString("list"));

                // 存储list模版数据，并组装map
                List<JSONObject> strArr = JSONArray.fromObject(jsonObject_token.getString("list"));
                map.put("list", strArr);
            }
            map.put("errcode", jsonObject_token.getString("errcode"));
            map.put("errmsg", jsonObject_token.getString("errmsg"));

        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return map;
    }


    /**
     * 系统发送消息
     *
     * @param actor
     * @param healthCheckList
     * @param klassId
     */
    public ResponseEntity sendSysMassage(Actor actor, List<HealthCheck> healthCheckList, Integer klassId) {

        List<TemplateMessagePojo> messagesPojo = new ArrayList<>();
        WxSendType WX_SEND = WxSendType.HEALTH;

        for (HealthCheck healthCheck : healthCheckList) {
            // 通过学生找到家长，可能有多个
            List<Actor> actorList = new ArrayList<>();
            Set<Guardian> allByStudent_id = guardianRepository.findAllByStudent_Id(healthCheck.getStudent().getId());
            Iterator<Guardian> guardinsIter = allByStudent_id.iterator();
            while (guardinsIter.hasNext()) {
                Guardian guardian = guardinsIter.next();
                Actor actorByGuardian = actorRepository.findOne(guardian.getPatriarchId());
                actorList.add(actorByGuardian);

                // 要放大的字
                String times = "";
                switch (healthCheck.getType()) {
                    case MORNING:
                        times = "晨检";
                        break;
                    case NOON:
                        times = "午检";
                        break;
                    case NIGHT:
                        times = "晚检";
                        break;
                }

                // date数据组装
                Object healthCheckObj = (Object) healthCheck;
                String typeSrt = healthCheck.getType().toString();
                String name = healthCheck.getStudent().getName();

                JSONObject jsonSend = new JSONObject();
                JSONObject jsonDate = new JSONObject();
                JSONObject jsonValue1 = new JSONObject();        // keyword中的value
                JSONObject jsonValue2 = new JSONObject();        // keyword中的value
                StringBuffer valueStr = new StringBuffer();   //  微信要发送的value详情
                JSONObject json = getGeneralObjectValue(healthCheckObj);
                JSONObject jsonSendVal = JSONObject.fromObject(json.get("sendValStr"));
                Iterator keys = jsonSendVal.keys();

                // 组装数据,要推送微信消息
                while (keys.hasNext()) {

                    String key = keys.next().toString();
                    String value = jsonSendVal.getString(key);
                    String val = key + value;
                    valueStr.append(val);         // value
                }

                String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                // value值
                String cname = "幼儿姓名：" + name + "\n" + valueStr + "详情请点击该通知";
                jsonValue1.put("value", cname);            // value幼儿姓名
                jsonValue2.put("value", createdAt);        // value发送时间

                // 把value放进keyword
                jsonSend.put("keyword1", jsonValue1);
                jsonSend.put("keyword2", jsonValue2);

                jsonSend.put("data", jsonSend);
                log.info("组装后的微信推送消息" + jsonSend);

                // 组装数据,要保存的推送消息
                jsonDate.put("name", name);
                jsonDate.put("type", typeSrt);
                jsonDate.put("data", JSONObject.fromObject(json.get("jsonDateSave")));

                // 得到该角色的推送消息的formId
                String formid = wxSendService.getFormId(actorByGuardian.getId());

                // 微信推送消息数据
                TemplateMessage templateMessage = new TemplateMessage(
                        null,   // 超时时间
                        TEMPLATE_ID,    // 模板ID
                        WX_PAGE,    // 要显示的页数
                        formid,     // form_id，推送消息时角色的识别码
                        jsonSend,   // DATA数据
                        times   // 要放大的字
                );

                TemplateMessagePojo pojo = new TemplateMessagePojo(
                        templateMessage,
                        jsonDate,
                        actorByGuardian
                );
                messagesPojo.add(pojo);
            }
        }

        return wxSendService.sendMessage(messagesPojo, klassId, WX_SEND);
    }


    /**
     * 特定模版
     *
     * @param healthCheckObj
     * @return
     */
//    private JSONObject getObjectValue(Object healthCheckObj) {
//        String valueStr = null;
//        JSONObject json = new JSONObject();
//        JSONObject jsonSendMsg = new JSONObject();
//        JSONObject jsonDateSave = new JSONObject();
//        String temperature = null;
//        Field[] field = healthCheckObj.getClass().getDeclaredFields();
//        try {
//            for (int j = 0; j < field.length; j++) { // 遍历所有属性
//                String name = field[j].getName(); // 获取属性的名字
//                if (name == "issue") {
//                    continue;
//                }
//                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
//                Method m = healthCheckObj.getClass().getMethod("get" + name);
//                String value = String.valueOf(m.invoke(healthCheckObj)); // 调用getter方法获取属性值
//
//                // 要保存进数据库里的体检信息
//                String saveKey = name.substring(0, 1).toLowerCase() + name.substring(1);
//                String saveValue = value;
//                boolean isSave = true;  // 只保存体检项目，其他不需要保存，因此用此字段做个开关，当进到default时，开关关闭
//
//                // 晨检体温单独处理
//                if (name.equals("Temperature")) {
//                    temperature = value;
//                    jsonDateSave.put(name, temperature);
//                }
//
//                // .....处理开始........
//                // 执行处理方法
//                if (value != null || !value.equals("")) {
//                    String objValueStr = "";
//
//                    switch (value) {
//
//                        case "WELL":
//                            value = "正常";
//                            break;
//
//                        // 精神
//                        case "DOLDRUMS":
//                            value = "精神不振";
//                            break;
//                        case "SAG":
//                            value = "萎靡";
//                            break;
//                        case "TOOEXCITED":
//                            value = "过于兴奋";
//                            break;
//                        case "TOOAGITATED":
//                            value = "过于烦躁";
//                            break;
//
//                        // 身体
//                        case "COUGH":
//                            value = "咳嗽";
//                            break;
//                        case "RHINORRHEA":
//                            value = "流鼻涕";
//                            break;
//                        case "FEVERHIGH":
//                            value = "高烧";
//                            break;
//                        case "EVERLOWF":
//                            value = "低烧";
//                            break;
//                        case "DIARRHEA":
//                            value = "腹泻";
//                            break;
//
//                        // 皮肤
//                        case "RASH":
//                            value = "皮疹";
//                            break;
//                        case "ALLERGY":
//                            value = "过敏";
//                            break;
//                        case "BODYHURT":
//                            value = "身体外伤";
//                            break;
//                        case "FACEHURT":
//                            value = "脸部外伤";
//                            break;
//                        case "MOSQUITOHURT":
//                            value = "蚊虫叮咬";
//                            break;
//
//                        // 正餐
//                        case "LESSEATTING":
//                            value = "饭量少";
//                            break;
//                        case "LESSVEGETABLE":
//                            value = "蔬菜少";
//                            break;
//                        case "LESSMEAT":
//                            value = "肉类少";
//                            break;
//                        case "TEACHERHELP":
//                            value = "老师喂";
//                            break;
//
//                        // 午睡
//                        case "LESS":
//                            value = "少";
//                            break;
//                        case "NOSELEEP":
//                            value = "没睡";
//                            break;
//
//                        // 加餐
//                        case "LESSDRINK":
//                            value = "饮品少";
//                            break;
//                        case "LESSFRUIT":
//                            value = "水果少";
//                            break;
//
//                        // 大小便，排泄
//                        case "YELLO":
//                            value = "小便黄";
//                            break;
//                        // 小便少
//                        case "CONSTIPATION":
//                            value = "便秘";
//                            break;
//
//                        // 口腔
//                        case "HERPAS":
//                            value = "疱疹";
//                            break;
//                        case "ULCERATION":
//                            value = "溃疡";
//                            break;
//                        case "THROATRED":
//                            value = "咽部发红";
//                            break;
//
//                        // 其他
//                        case "MEDICINE":
//                            value = "携带药物";
//                            break;
//                        case "DANGEROUS":
//                            value = "携带危险物品";
//                            break;
//                        case "NOTHING":
//                            value = "无";
//                            break;
//                        default:
//                            value = "";
//                            isSave = false;
//                    }
//
//                    if (isSave) {
//                        jsonDateSave.put(saveKey, saveValue);
//                    }
//
//                    name = name.substring(0, 1).toLowerCase() + name.substring(1);
//                    switch (name) {
//                        case "spirit":
//                            if (!value.equals("")) {
//                                valueStr = value + (" \n");
//                                jsonSendMsg.put("精神状况", value);
//                            }
//                            break;
//                        case "sink":
//                            if (!value.equals("")) {
//                                valueStr = value + (" \n");
//                                jsonSendMsg.put("皮肤状况：", value);
//                            }
//                            break;
//                        case "mouth":
//                            if (!value.equals("")) {
//                                valueStr = value + (" \n");
//                                jsonSendMsg.put("口腔状况：", value);
//                            }
//                            break;
//                        default:
//                            valueStr = null;
//                    }
//                }
//                // .....处理结束........
//
//                json.put("jsonDateSave", jsonDateSave);
//                json.put("jsonSendMsg", jsonSendMsg);
//
//            }
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return json;
//    }


    /**
     * 通用模板
     * @param healthCheckObj
     * @return
     */
    private JSONObject getGeneralObjectValue(Object healthCheckObj) {
        String valueStr = null;
        JSONObject json = new JSONObject();
        JSONObject jsonDateSave = new JSONObject();
        JSONObject jsonSend = new JSONObject();
        String temperature = null;
        Field[] field = healthCheckObj.getClass().getDeclaredFields();
        try {
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                if (name == "issue" || name.equals("healthCondition")) {
                    continue;
                }
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                Method m = healthCheckObj.getClass().getMethod("get" + name);
                String value = String.valueOf(m.invoke(healthCheckObj)); // 调用getter方法获取属性值

                // 要保存进数据库里的体检信息
                String saveKey = name.substring(0, 1).toLowerCase() + name.substring(1);
                String saveValue = value;
                boolean isSave = true;  // 只保存体检项目，其他不需要保存，因此用此字段做个开关，当进到default时，开关关闭

                // 晨检体温单独处理
                if (name.equals("Temperature")) {
                    temperature = value;
                    jsonDateSave.put(name, temperature);
                }

                // .....处理开始........
                // 执行处理方法
                if (value != null || !value.equals("")) {
                    String objValueStr = "";

                    switch (value) {

                        case "WELL":
                            value = "正常";
                            break;

                        // 精神
                        case "DOLDRUMS":
                            value = "精神不振";
                            break;
                        case "SAG":
                            value = "萎靡";
                            break;
                        case "TOOEXCITED":
                            value = "过于兴奋";
                            break;
                        case "TOOAGITATED":
                            value = "过于烦躁";
                            break;

                        // 身体
                        case "COUGH":
                            value = "咳嗽";
                            break;
                        case "RHINORRHEA":
                            value = "流鼻涕";
                            break;
                        case "FEVERHIGH":
                            value = "高烧";
                            break;
                        case "EVERLOWF":
                            value = "低烧";
                            break;
                        case "DIARRHEA":
                            value = "腹泻";
                            break;

                        // 皮肤
                        case "RASH":
                            value = "皮疹";
                            break;
                        case "ALLERGY":
                            value = "过敏";
                            break;
                        case "BODYHURT":
                            value = "身体外伤";
                            break;
                        case "FACEHURT":
                            value = "脸部外伤";
                            break;
                        case "MOSQUITOHURT":
                            value = "蚊虫叮咬";
                            break;

                        // 正餐
                        case "LESSEATTING":
                            value = "饭量少";
                            break;
                        case "LESSVEGETABLE":
                            value = "蔬菜少";
                            break;
                        case "LESSMEAT":
                            value = "肉类少";
                            break;
                        case "TEACHERHELP":
                            value = "老师喂";
                            break;

                        // 午睡
                        case "LESS":
                            value = "少";
                            break;
                        case "NOSELEEP":
                            value = "没睡";
                            break;

                        // 加餐
                        case "LESSDRINK":
                            value = "饮品少";
                            break;
                        case "LESSFRUIT":
                            value = "水果少";
                            break;

                        // 大小便，排泄
                        case "YELLO":
                            value = "小便黄";
                            break;
                        // 小便少
                        case "CONSTIPATION":
                            value = "便秘";
                            break;

                        // 口腔
                        case "HERPAS":
                            value = "疱疹";
                            break;
                        case "ULCERATION":
                            value = "溃疡";
                            break;
                        case "THROATRED":
                            value = "咽部发红";
                            break;

                        // 其他
                        case "MEDICINE":
                            value = "携带药物";
                            break;
                        case "DANGEROUS":
                            value = "携带危险物品";
                            break;
                        case "NOTHING":
                            value = "无";
                            break;
                        default:
                            value = value;
                            isSave = false;
                    }

                    // 要保存推送消息的组装
                    if (isSave) {
                        jsonDateSave.put(saveKey, saveValue);
                    }

                    // 要推送的消息组装，只组装三个，避免超过30个字符
                    name = name.substring(0, 1).toLowerCase() + name.substring(1);
                    valueStr = value + (" \n");
                    String model = null;
                    switch (name) {
                        case "spirit":
                            if (!value.equals("")) {
                                model = "精神状况：";
                            }
                            break;
                        case "body":
                            if (!value.equals("")) {
                                model = "身体状况：";
                            }
                            break;
                        case "sink":
                            if (!value.equals("")) {
                                model = "皮肤状况：";
                            }
                            break;
                        default:
                            valueStr = null;
                    }
                    if (!Strings.isNullOrEmpty(model)) {
                        jsonSend.put(model, valueStr);
                    }
                }
                // .....处理结束........

                json.put("jsonDateSave", jsonDateSave);
                json.put("sendValStr", jsonSend);

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return json;
    }



    public void getPushCode(Actor actor, List<PushCodeForm> forms) {
        User user = userRepository.findOne(actor.getUserId());
        WxUsers wxUsers = wxUserRepository.findByMobile(user.getMobile());
        for (PushCodeForm form : forms) {
            PushCode pushCode = new PushCode(
                    wxUsers,
                    actor.getId(),
                    user.getId(),
                    form.getFormId(),
                    form.getTimeOut(),
                    Instant.now()
            );
            pushCodeRepository.save(pushCode);
        }

        // 删除过期推送码
        deleteTimeout(actor);

    }

    /**
     * 清除过期的推送码（formId）
     */
    public void deleteTimeout(Actor actor) {
        Collection<PushCode> pushCodes = pushCodeRepository.findAllByTimeOut7Days(actor.getId());
        pushCodeRepository.delete(pushCodes);
    }

    public Collection<WxPushMsg> findWxPushMassage(Actor actor, LocalDate date) {
        Instant first = null;
        Instant second = null;
        WxSendType wxSend = WxSendType.HEALTH;
        if (date != null) {
            first = date.atStartOfDay().toInstant(ZoneOffset.UTC);
            second = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        } else {
            TemporalField temporalField = WeekFields.of(Locale.getDefault()).dayOfWeek();
            date = LocalDate.now();
            first = date.with(temporalField, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
            second = date.with(temporalField, 7).atStartOfDay().toInstant(ZoneOffset.UTC);
        }
        return wxPushMsgRepository.findByActorIdAndWxSendTypeAndCreateAtBetween(actor.getId(), wxSend, first, second);
    }

    public void countActive(Actor actor, WxActiveType wxSendType, ActiveSourceType activeSourceType) {
        User user = userRepository.findOne(actor.getUserId());
        WxActive wxActive = new WxActive(
                actor.getId(),
                user.getUsername(),
                Instant.now(),
                actor.getSchoolId(),
                wxSendType,
                activeSourceType
        );
        wxActiveRepository.save(wxActive);
    }

    public Collection<WxActiveDTO> findActive(Actor actor, AttendPeriodStat.PeriodType type, Integer actorId, LocalDate localDate) {
        Integer schoolId = actor.getSchoolId();
        Pair<LocalDate, LocalDate> pair = type.toPeriodRange(localDate);
        Instant first = pair.getFirst().atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = pair.getSecond().atStartOfDay().toInstant(ZoneOffset.UTC);


        Collection<Object[]> wxServices = actorId == null
                ? wxActiveRepository.findAllBySchoolIdAndCreatedAtBetween(schoolId, first, second)
                : wxActiveRepository.findAllBySchoolIdAndActorIdAndCreatedAtBetween(schoolId, actorId, first, second);

        Collection<WxActiveDTO> wxActiveDTOS = wxServices.stream().map(
                wx -> new WxActiveDTO(((String) wx[1]).toString(), ((Number) wx[0]).intValue())
        ).collect(Collectors.toList());

        return wxActiveDTOS;
    }

    public void sendWxKlassPlan(Actor actor, KlassPlan klassPlan, Integer klassId, Map<String, String> getMap){

        String notifyDesc = getMap.get("msg");
        String msg = getMap.get("type");

        // 组装微信消息推送
        JSONObject json = new JSONObject(); //
        JSONObject value1 = new JSONObject(); // value1值
        JSONObject value2 = new JSONObject(); // value2值

        User user = new User();
        if (klassPlan != null){
            user = userRepository.findOne(klassPlan.getCreatedBy().getUserId());
        }else if (klassPlan == null){
            user = userRepository.findOne(actor.getUserId());
        }

        Klass klass = null;
        if (klassPlan != null){
            klass = klassPlan.getKlass();
        }else{
            klass = klassRepository.findOne(klassId);
        }
        String valueStr1 = notifyDesc
                    + " \n班级：" + klass.getName()
//                    + " \n标题：" + klassPlan.getTitle()
//                    + " \n内容：" + klassPlan.getContent().substring(0,15) + "..."
                    + " \n发送人：" + user.getUsername();
        String valueStr2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        value1.put("value", valueStr1);
        value2.put("value", valueStr2);

        json.put("keyword1", value1);
        json.put("keyword2", value2);

        // 判断往哪个页面跳转
        String page = null;
        if (msg.equals("dynamic")){
            page = WX_SEND_PAGE_DYAMIC;
        }else if (msg.equals("notice")){
            page = WX_SEND_PAGE_NOTICE;
        }else if (msg.equals("plan")){
            page = WX_SEND_PAGE_PLAN;
        }
        TemplateMessage sendMessage = new TemplateMessage(
                null,
                TEMPLATE_ID,
                page,
                "",
                json,
                ""
        );

        List<Student> students = studentRepository.findByKlassAndIsShow(klass, true);
        List<Map<String, Object>> lists = new ArrayList<>();
        for (Student student : students){
            Integer studentId = student.getId();
            Set<Guardian> guardians = guardianRepository.findAllByStudent_Id(studentId);
            Iterator<Guardian> guardianIterator = guardians.iterator();
            Map<String, Object> map = new LinkedHashMap<>();
            while(guardianIterator.hasNext()){
                Guardian guardian = guardianIterator.next();
                Integer actorId = guardian.getPatriarchId();
                Actor actorSend = actorRepository.findOne(actorId);
                String formid = wxSendService.getFormId(actorId);
                map.put("actorSend", actorSend);
                map.put("sendMessage", sendMessage);
                map.put("formid", formid);
                lists.add(map);
            }
        }
        for (Map<String, Object> map : lists){
            Actor sendActor = (Actor)map.get("actorSend");
            TemplateMessage sendMsg = (TemplateMessage)map.get("sendMessage");
            String formidSrt = map.get("formid").toString();

            CompletableFuture completableFuture  = CompletableFuture.supplyAsync(()->{
                        sendTemplateMessage(sendActor, sendMsg, formidSrt);
                        return null;
                    });
        }
    }

    /**
     *
     * @param studentId
     * @param map
     * @param emphasisKeyword
     */
    public void sendMedicine(Integer studentId, Map<String, Object> map, String emphasisKeyword){

        try {

            Collection<Guardian> guardians = this.guardianRepository.findAllByStudent_Id(studentId);
            for (Guardian guardian : guardians) {
                Integer sendActorId = guardian.getPatriarchId();
                Actor sendActor = actorRepository.findOne(sendActorId);

                int i = 1;
                JSONObject keyword = new JSONObject();
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    JSONObject value = new JSONObject();
                    value.put("value", m.getValue());
                    keyword.put("keyword" + i, value);
                    i++;
                }

                String formId = wxSendService.getFormId(sendActorId);

                TemplateMessage templateMessage = new TemplateMessage(
                        null,
                        TEMPLATE_ID,
                        WX_PAGE,
                        formId,
                        keyword,
                        emphasisKeyword
                );

                CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> {
                    sendTemplateMessage(sendActor, templateMessage, formId);
                    return null;
                });
                // 初始化
                i = 1;
            }
        }catch (Exception e){
            log.error(e.toString());
        }
    }

    public void sendStudentPlan(StudentCharge studentCharge, String msg){

        JSONObject keywordJson = new JSONObject();
        JSONObject valueJson = new JSONObject();

        // 学生姓名
        Integer stuId = studentCharge.getStudentId();
        String stuName = studentRepository.findOne(stuId).getName();

        // 缴费金额
        Float money = studentCharge.getMoney();

        // 收费期数
        String entityType = "";
        ExpensePeriodType entry = studentCharge.getExpensePeriodType();
        switch (entry){
            case YEAR:
                entityType = "年";
                break;
            case HALF_YEAR:
                entityType = "学期";
                break;
            case QUARTER:
                entityType = "季度";
                break;
            case MONTH:
                entityType = "月";
                break;
            case WEEK:
                entityType = "周";
                break;
            case DAY:
                entityType = "日";
                break;
            case ONCE:
                entityType = "一次性";
                break;
        }

        // 收费名称
        String chargeName = studentCharge.getExpenseEntry().getName();

        // 周期开始时间
        Instant createAt = studentCharge.getCreateAt();
        String createAtStr = LocalDateTime.ofInstant(createAt, ZoneOffset.UTC).toLocalDate().toString();

        // 周期结束时间
        Instant endAt = studentCharge.getEndAt();
        String endAtStr = LocalDateTime.ofInstant(endAt, ZoneOffset.UTC).toLocalDate().toString();

        msg = msg + "\n幼儿姓名：" + stuName
                  + "\n缴费名称：" + chargeName
                  + "\n缴费金额：" + money + "元"
                  + "\n缴费周期：" + entityType
                  + "\n周期开始时间：" + createAtStr
                  + "\n周期结束时间：" + endAtStr;

        valueJson.put("value", msg);
        keywordJson.put("keyword1", valueJson);

        valueJson.put("value", LocalDate.now().toString());
        keywordJson.put("keyword2", valueJson);
//
//        valueJson.put("value", "缴费名称：" + chargeName);
//        keywordJson.put("keyword3", valueJson);
//
//        valueJson.put("value", "缴费金额：" + money);
//        keywordJson.put("keyword4", valueJson);
//
//        valueJson.put("value", "缴费周期类型：" + entityType);
//        keywordJson.put("keyword5", valueJson);
//
//        valueJson.put("value", "缴费周期开始时间：" + createAtStr);
//        keywordJson.put("keyword6", valueJson);
//
//        valueJson.put("value", "缴费周期结束时间：" + endAtStr);
//        keywordJson.put("keyword7", valueJson);

        // 根据学生找到家长的actor
        Set<Guardian> guardians = guardianRepository.findAllByStudent_Id(stuId);
        Iterator<Guardian> guardianIterator = guardians.iterator();
        while(guardianIterator.hasNext()){
            TemplateMessage templateMessage = new TemplateMessage(
                    "0",
                    TEMPLATE_ID,
                    WX_SEND_STU_CHARGE,
                    null,
                    keywordJson,
                    null
            );
            Actor actor = actorRepository.findOne(guardianIterator.next().getPatriarchId());
            sendTemplateMessage(actor, templateMessage, "00");
        }
    }

    public Map<String, Object> sendTemplateMessage(Actor actor, TemplateMessage form, String msg) {
        return wxSendService.sendTemplateMessage(actor, form, msg);
    }
}
