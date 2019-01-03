package cn.k12soft.servo.third.alipay;

import cn.k12soft.servo.configuration.ChinaAreaConfigManager;
import cn.k12soft.servo.domain.BillingAliPayInfo;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.SchoolAliPayInfo;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.repository.BillingAliPayInfoRepository;
import cn.k12soft.servo.repository.SchoolAliPayInfoRepository;
import cn.k12soft.servo.service.SchoolService;
import cn.k12soft.servo.service.StudentService;
import cn.k12soft.servo.util.CommonUtils;
import cn.k12soft.servo.util.Times;
import cn.k12soft.servo.web.form.AliPayBillingForm;
import cn.k12soft.servo.web.form.AliPaySchoolInfoForm;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝缴费 https://docs.open.alipay.com/440/107334/
 * Created by liubing on 2018/6/29
 */
@Service
public class AliPayService {
    private static Logger logger = LoggerFactory.getLogger(AliPayService.class);

    private final ApplicationContext context;
    private SchoolService schoolService;
    private StudentService studentService;
    private AlipayClient alipayClient;

    private int BILLING_STATE_0 = 0; // 订单生成
    private int BILLING_STATE_1 = 1; // 缴费成功
    private int BILLING_STATE_2 = 2; //2:关闭账单
    private int BILLING_STATE_3 = 3; // 退费

    private static final String PAY_SUCCESS_NOTIFY_URL = "https://k12.vwico.com/api/alipay/toPaySuccessNotify";

    private String RESPONSE_STATUS_SUCCESS = "Y";

    private final static String PRIVATE_KEY = "jANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhTAnzTqOHilC3uCswdS74FwauQez6pfsPzNjvaKNTgQv/kHHDvU/2iiDEGzzmOeWhvj5gB1YemWoDKoyyo/6H8XGu/+/hqeRyT9XvcRAs9K3ZWn+Dq"; // 应用私钥

    private final static String URL = "https://openapi.alipaydev.com/gateway.do"; // 支付宝网关(沙箱环境)
    private final static String APP_ID = "2016091800543568"; //开发者appId (沙箱环境)

    // private final static String URL = "https://openapi.alipay.com/gateway.do"; //支付宝网关（固定）
    // private final static String APP_ID = "2018070960550728";//开发者appId

    private final static String APP_PRIVATE_KEY = "aliqwio90*@#ER1qP90"; // 应用私钥
    private final static String FORMAT = "json"; //参数返回格式，只支持json
    private final static String CHARSET = "UTF-8";
    private final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhTAnzTqOHilC3uCswdS74FwauQez6pfsPzNjvaKNTgQv/kHHDvU/2iiDEGzzmOBCqLu2InKztrtpj03pZosWbqXEGtcbh6BKXr+sZCJ4TIOT4z6bKPQ+/tVMNUlcZtmc653sv4IIRQlruD1qHMkZ2E89syeWhvj5gR0uRcX74HjvWG0cqu6s0FJxa7wmpb0tV84UXnv0Noo0ECVThVIBiazBhb1B4xyIkvZRpD528ZYTXuZXKR4xGSC1Ddnr6zSk86Iu8mcSwVUXdWMvsJT3Bi5rm6B1YemWoDKoyyo/6H8XGu/+/hqeRyT9XvcRAs9K3ZWnx1gT6lY6y1Es+DqySQIDAQAB"; //支付宝公钥，由支付宝生成
    private final static String SIGN_TYPE = "RSA2"; //商户生成签名字符串所使用的签名算法类型
    private final static String VERSION = "1.0";
    private final static String SCHOOL_TYPE = "2";// 幼儿园

    private final static String GET_APP_AUTH_TOKEN_URL = "alipay.open.auth.token.app";
    private final static String QUERY_APP_AUTH_TOKEN_INFO = "alipay.open.auth.token.app.query";
    private final static String GET_APP_AUTH_TOKEN_GRANT_TYPE_NEW = "authorization_code";
    private final static String GET_APP_AUTH_TOKEN_GRANT_TYPE_REFRESH = "refresh_token";

    private final static String MODIFY_SCHOOL_INFO_METHOD = "alipay.eco.edu.kt.schoolinfo.modify";
    private final static String SEND_BILLING_METHOD = "alipay.eco.edu.kt.billing.send";
    private final static String MODIFY_BILLING_METHOD  = "alipay.eco.edu.kt.billing.modify";
    private final static String QEURY_BILLING_STATUS_METHOD = "alipay.eco.edu.kt.billing.query";

    private final static String OPENAPI_ALIPAY_GATEWAY_URL = "https://openapi.alipay.com/gateway.do";

    public AliPayService(ApplicationContext context, SchoolService schoolService, StudentService studentService){
        this.context = context;
        this.schoolService = schoolService;
        this.studentService = studentService;
        initAlipayClient();
    }

    private void initAlipayClient() {
        alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }

    //第三方授权回调
    public void aliPayAuthCallback(Map<String, Object> reqMap) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", GET_APP_AUTH_TOKEN_GRANT_TYPE_NEW);
        paramMap.put("code", String.valueOf(reqMap.get("app_auth_code")));
        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent(CommonUtils.map2String(paramMap));
        try {
            AlipayOpenAuthTokenAppResponse response = alipayClient.execute(request);
            String authAppId = response.getAuthAppId();//授权商户的AppId 幼儿园的
            String appAuthToken = response.getAppAuthToken(); //商户授权令牌
            String userId = response.getUserId(); // 授权商户id,幼儿园的
            String appRefreshToken = response.getAppRefreshToken();//刷新令牌时使用
            SchoolAliPayInfo schoolAliPayInfo = getSchoolAliPayInfoEntityRepository().findByUserId(userId);
            if(schoolAliPayInfo == null){
                schoolAliPayInfo = new SchoolAliPayInfo();
                schoolAliPayInfo.setUserId(userId);
                schoolAliPayInfo.setUserAppId(authAppId);
                schoolAliPayInfo.setAppAuthToken(appAuthToken);
                schoolAliPayInfo.setAppRefreshToken(appRefreshToken);
            }else{
                schoolAliPayInfo.setAppAuthToken(appAuthToken);
                schoolAliPayInfo.setAppRefreshToken(appRefreshToken);
            }
            getSchoolAliPayInfoEntityRepository().save(schoolAliPayInfo);
        } catch (AlipayApiException e) {
            logger.error("[AliPayService] ", e);
        }
    }

    // 获取第三方授权信息
    public Map<String, String> getAuthInfo(String appAuthToken) {
        Map<String, String> retMap = new HashMap<>();
        try {
            AlipayOpenAuthTokenAppQueryRequest request = new AlipayOpenAuthTokenAppQueryRequest();
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("app_auth_token", appAuthToken);
            request.setBizContent(CommonUtils.map2String(paramMap));
            AlipayOpenAuthTokenAppQueryResponse response = alipayClient.execute(request);
            retMap.put("user_id", response.getUserId());
            retMap.put("auth_app_id", response.getAuthAppId());
            retMap.put("status", response.getStatus());
        } catch (AlipayApiException e) {
            retMap.clear();
            retMap.put("error", "1001");
            e.printStackTrace();
        }
        return retMap;
    }

    // 发送学校信息
    public Map<String, String> modifySchoolInfo(AliPaySchoolInfoForm form) {
        Map<String, String> retMap = new HashMap<>();
        try {
            ChinaAreaConfigManager chinaAreaConfigManager = getChinaAreaConfigManager();
            String provinceCode = chinaAreaConfigManager.getCode(form.getProvinceName());
            String cityCode = chinaAreaConfigManager.getCode(form.getCityName());
            String districtCode = chinaAreaConfigManager.getCode(form.getDistrictName());
            String isvName = "博顿教育"; //ISV公司名称 ， 会在账单详情 页面展示给用户
            String isvNotifyUrl = PAY_SUCCESS_NOTIFY_URL;
            String isvPid = form.getIsvPid(); //"2088666966699"; //填写已经签约教育缴费的isv支付宝的pid
            String isvPhone = form.getIsvPhone(); //"13699182012";//ISV的联系方式 ， 会在账单详 情页面展示给用户，用户有问 题可以直接联系此电话获取帮助
            String schoolPid = form.getSchoolPid();//学校签约支付宝教育缴费支付宝pid

            if(StringUtils.isEmpty(provinceCode)){
                retMap.put("error", "1001");
                retMap.put("msg", "provinceCode is null");
                return retMap;
            }

            if(StringUtils.isEmpty(cityCode)){
                retMap.put("error", "1001");
                retMap.put("msg", "cityCode is null");
                return retMap;
            }

            if(StringUtils.isEmpty(districtCode)){
                retMap.put("error", "1001");
                retMap.put("msg", "districtCode is null");
                logger.error("modifyschoolinfo districtcode is null {}, {}, {}", form.getProvinceName(), form.getCityName(), form.getDistrictName());
                return retMap;
            }

            School school = this.schoolService.get(form.getSchollId());
            // 请求参数
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("school_name", school.getName());
            contentMap.put("school_type", SCHOOL_TYPE);
            contentMap.put("province_code", provinceCode);
            contentMap.put("province_name", form.getProvinceName());
            contentMap.put("city_code", cityCode);
            contentMap.put("city_name", form.getCityName());
            contentMap.put("district_code", districtCode);
            contentMap.put("district_name", form.getDistrictName());
            contentMap.put("isv_name", isvName);
            contentMap.put("isv_notify_url", isvNotifyUrl);
            contentMap.put("isv_pid", isvPid);
            contentMap.put("isv_phone", isvPhone);
            contentMap.put("school_pid", schoolPid);

            String reqContent = CommonUtils.map2String(contentMap);
            String sign = AlipaySignature.rsaSign(reqContent, PRIVATE_KEY, CHARSET, SIGN_TYPE);

            Map<String, String> paramMap = _baseParamMap(sign, MODIFY_SCHOOL_INFO_METHOD);
            paramMap.put("biz_content", reqContent);

            AlipayEcoEduKtSchoolinfoModifyRequest request = new AlipayEcoEduKtSchoolinfoModifyRequest();
            request.setBizContent(CommonUtils.map2String(paramMap));
            AlipayEcoEduKtSchoolinfoModifyResponse response = alipayClient.execute(request);
            if(response.isSuccess() && response.getStatus().equals("Y")){
                String schoolNo = response.getSchoolNo();
                retMap.put("schoolId", String.valueOf(school.getId()));
                retMap.put("schoolNo", schoolNo);
            } else {
                retMap.put("error", "1001");
                retMap.put("code", response.getCode());
                retMap.put("msg", response.getMsg());
                logger.error("modifyschoolinfo error {} {} {} {}", response.getCode(), response.getMsg(), response.getSubCode(), response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            retMap.clear();
            retMap.put("error", "1001");
            logger.error("error ", e);
        }
        return retMap;
    }

    //发送缴费账单
    public Map<String,String> sendBilling(int schoolId, AliPayBillingForm form) {
        Map<String, String> retMap = new HashMap<>();
        try {
            SchoolAliPayInfo schoolAliPayInfo = getSchoolAliPayInfoEntityRepository().findOne(schoolId);
            Student student = this.studentService.get(form.getStudentId());
            // 请求参数
            String schoolPid = ""; //学校支付宝pid
            String outTradeNo = form.getOutTradeNo();//ISV端的缴费账单编号
            String chargeBillTitle = form.getChargeBillTitle();//缴费账单名称
            String partnerId = "";//Isv支付宝pid, 支付宝签约后，返回给ISV编号
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("users", form.getUserDetails());
            contentMap.put("school_pid", schoolPid);
            contentMap.put("school_no", schoolAliPayInfo.getSchoolNo());
            contentMap.put("child_name", student.getName());
            contentMap.put("class_in", student.getClass().getName());
            contentMap.put("student_code", String.valueOf(student.getId()));
            contentMap.put("out_trade_no", outTradeNo);
            contentMap.put("charge_bill_title", chargeBillTitle);
            contentMap.put("gmt_end", Times.fromTimeToStandardStr(form.getGmtEnd()));
            contentMap.put("end_enable", form.getEndEnable());
            contentMap.put("partner_id", partnerId);

            String reqContent = CommonUtils.map2String(contentMap);
            String sign = AlipaySignature.rsaSign(reqContent, PRIVATE_KEY, CHARSET, SIGN_TYPE);

            Map<String, String> paramMap = _baseParamMap(sign, SEND_BILLING_METHOD);
            paramMap.put("biz_content", reqContent);

            AlipayEcoEduKtBillingSendRequest request = new AlipayEcoEduKtBillingSendRequest();
            request.setBizContent(CommonUtils.map2String(paramMap));
            AlipayEcoEduKtBillingSendResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                String orderNo = response.getOrderNo();
                BillingAliPayInfo billingAliPayInfo = getBillingAliPayInfoRepository().findByOrderNo(orderNo);
                if(billingAliPayInfo == null) {
                    billingAliPayInfo = new BillingAliPayInfo();
                    billingAliPayInfo.setStudentId(student.getId());
                    billingAliPayInfo.setBillingNo(String.valueOf(student.getId())+String.valueOf(System.currentTimeMillis()));
                    billingAliPayInfo.setOrderNo(orderNo);
                    billingAliPayInfo.setBillingTitle(chargeBillTitle);
                    billingAliPayInfo.setState(BILLING_STATE_0);
                    billingAliPayInfo.setCreatAt(Instant.now());
                    getBillingAliPayInfoRepository().save(billingAliPayInfo);
                }

            } else {
                logger.error("sendBilling error {}, {}, {}", response.getCode(), response.getMsg(), response.getSubMsg());
                retMap.put("error", "1001");
                retMap.put("code", response.getCode());
                retMap.put("msg", response.getMsg());
            }
        }catch (Exception e){
            logger.error("error ", e);
            retMap.clear();
            retMap.put("error", "1001");
        }
        return retMap;
    }

    private Map<String, String> _baseParamMap(String sign, String method){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("app_id", APP_ID);
        paramMap.put("method", method);
        paramMap.put("charset", CHARSET);
        paramMap.put("sign_type", SIGN_TYPE);
        paramMap.put("sign", sign);
        paramMap.put("timestamp", Times.fromTimeToStandardStr(System.currentTimeMillis()));
        paramMap.put("version", VERSION);
        return paramMap;
    }

    //支付流程: https://docs.open.alipay.com/204/105297
    public Map<String,String> studentPay(String orderNo) {
        Map<String, String> retMap = new HashMap<>();
        try {
            BillingAliPayInfo billingAliPayInfo = getBillingAliPayInfoRepository().findByOrderNo(orderNo);
            if(billingAliPayInfo != null && billingAliPayInfo.getState() == BILLING_STATE_0){
                String rsaContent = AlipaySignature.rsaSign(billingAliPayInfo.getBillingNo(), PRIVATE_KEY, CHARSET, SIGN_TYPE);

            }
//            if (billingAliPayInfo != null && billingAliPayInfo.getState() != BILLING_STATE_0) {
//                Map<String, String> contentMap = new HashMap<>();
//                contentMap.put("status", String.valueOf(BILLING_STATE_1));//1:缴费成功，2:关闭账单，3、退费
//                String reqContent = CommonUtils.map2String(contentMap);
//                String sign = AlipaySignature.rsaSign(reqContent, PRIVATE_KEY, CHARSET, SIGN_TYPE);
//
//                Map<String, String> paramMap = _baseParamMap(sign, MODIFY_BILLING_METHOD);
//                paramMap.put("biz_content", reqContent);
//
//                AlipayEcoEduKtBillingModifyRequest request = new AlipayEcoEduKtBillingModifyRequest();
//                request.setBizContent(CommonUtils.map2String(paramMap));
//                AlipayEcoEduKtBillingModifyResponse response = alipayClient.execute(request);
//                if(response.isSuccess()){
//                    boolean checkRet = AlipaySignature.rsaCheckV1(response.getParams(), ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
//                    if(checkRet) {
//                        // 支付宝支付成功
//                        billingAliPayInfo.setState(BILLING_STATE_1);
//                        billingAliPayInfo.setPayAt(Instant.now());
//                        getBillingAliPayInfoRepository().save(billingAliPayInfo);
//                        retMap.put("state", String.valueOf(billingAliPayInfo.getState()));
//                    }else{
//                        retMap.put("error", "1001");
//                        retMap.put("msg", "check failed");
//                        logger.error("check failed billingno={}, billingtitle={}...", billingAliPayInfo.getBillingNo(), billingAliPayInfo.getBillingTitle());
//                    }
//                } else {
//                    retMap.put("error", "1001");
//                    retMap.put("status", response.getStatus());
//                    retMap.put("code",response.getCode());
//                    retMap.put("msg", response.getMsg());
//                    logger.error("syn billing state error cod={},msg={},subcode={},submsg={},status={}", response.getCode(), response.getMsg(), response.getSubCode(), response.getSubMsg(), response.getStatus());
//                }
//            }
        }catch (Exception e){
            logger.error("error", e);
            retMap.clear();
            retMap.put("error", "1001");
        }
        return retMap;
    }

    public Map<String, String> queryAliPayOrderStatus(String orderNo){
        Map<String, String> retMap = new HashMap<>();
        try {
            String isvPid = "";
            String schoolPid = "";
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("isv_pid", isvPid);
            contentMap.put("school_pid", schoolPid);
            contentMap.put("out_trade_no", orderNo);

            String reqContent = CommonUtils.map2String(contentMap);
            String sign = AlipaySignature.rsaSign(reqContent, PRIVATE_KEY, CHARSET, SIGN_TYPE);
            Map<String, String> paramMap = _baseParamMap(sign, QEURY_BILLING_STATUS_METHOD);
            paramMap.put("biz_content", reqContent);

            AlipayEcoEduKtBillingQueryRequest request = new AlipayEcoEduKtBillingQueryRequest();
            request.setBizContent(CommonUtils.map2String(paramMap));
            AlipayEcoEduKtBillingQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                retMap.put("orderNo", response.getOutTradeNo());
               retMap.put("orderStatus", response.getOrderStatus());
            } else {
                retMap.put("error", "1001");
                retMap.put("code",response.getCode());
                retMap.put("msg", response.getMsg());
                logger.error("query billing state error cod={},msg={},subcode={},submsg={}", response.getCode(), response.getMsg(), response.getSubCode(), response.getSubMsg());
            }
        }catch (Exception e){
            logger.error("error", e);
            retMap.clear();
            retMap.put("error", "1001");
        }
        return retMap;
    }

    // 支付宝回调(更改订单状态, 缴费成功，退费成功....);
    // 1.向支付宝发送退款信息，返回参数param
    // 2.向教育平台发送退款信息(alipay)
    public Map<String, String> zfbCb(Map<String,Object> reqMap){
        Map<String, String> retMap = new HashMap<>();
        try {
            BillingAliPayInfo billingAliPayInfo = null;//getBillingAliPayInfoRepository().findByOrderNo(orderNo);
            if (billingAliPayInfo == null) {
                retMap.put("error", "1001");
                retMap.put("msg", "billingAliPayInfo is null");
                return retMap;
            }

            String outTradeNo = billingAliPayInfo.getBillingNo(); //订单支付时传入的商户订单号
            String refundAmount = "";//需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
            String refundReason = "";//退款的原因说明

            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("status", String.valueOf(BILLING_STATE_3));
            contentMap.put("out_trade_no", outTradeNo);
            contentMap.put("refund_amount", refundAmount);
            contentMap.put("refund_reason", refundReason);

            String reqContent = CommonUtils.map2String(contentMap);
            String sign = AlipaySignature.rsaSign(reqContent, PRIVATE_KEY, CHARSET, SIGN_TYPE);
            Map<String, String> paramMap = _baseParamMap(sign, OPENAPI_ALIPAY_GATEWAY_URL);
            paramMap.put("biz_content", reqContent);

            // 向支付包发送退款请求
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            request.setBizContent(CommonUtils.map2String(paramMap));
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                // 向教育平台发送退款请求
                _refundToAliPay(billingAliPayInfo, response, refundAmount);
                retMap.put("billingNo", billingAliPayInfo.getBillingNo());
                retMap.put("status", String.valueOf(billingAliPayInfo.getState()));
            } else {
                retMap.put("code",response.getCode());
                retMap.put("msg", response.getMsg());
                logger.error("syn billing state error cod={},msg={},subcode={},submsg={}", response.getCode(), response.getMsg(), response.getSubCode(), response.getSubMsg());
            }
        }catch (Exception e){
            logger.error("error", e);
            retMap.clear();
            retMap.put("error", "1001");
        }
        return retMap;
    }

    private void _refundToAliPay(BillingAliPayInfo billingAliPayInfo, AlipayTradeRefundResponse reFundResponse, String refundAmount) throws Exception{
        if (billingAliPayInfo != null && billingAliPayInfo.getState() != BILLING_STATE_1) {
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("status", String.valueOf(BILLING_STATE_3));//1:缴费成功，2:关闭账单，3、退费
            contentMap.put("fund_change", reFundResponse.getFundChange());
            contentMap.put("refund_amount", reFundResponse.getRefundFee());
            contentMap.put("buyer_user_id", reFundResponse.getBuyerUserId());
            contentMap.put("buyer_logon_id", reFundResponse.getBuyerLogonId());

            String reqContent = CommonUtils.map2String(contentMap);
            String sign = AlipaySignature.rsaSign(reqContent, PRIVATE_KEY, CHARSET, SIGN_TYPE);

            Map<String, String> paramMap = _baseParamMap(sign, MODIFY_BILLING_METHOD);
            paramMap.put("biz_content", reqContent);

            AlipayEcoEduKtBillingModifyRequest request = new AlipayEcoEduKtBillingModifyRequest();
            request.setBizContent(CommonUtils.map2String(paramMap));
            AlipayEcoEduKtBillingModifyResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                if(RESPONSE_STATUS_SUCCESS.equals(response.getStatus())){
                    billingAliPayInfo.setState(BILLING_STATE_3);
                    getBillingAliPayInfoRepository().save(billingAliPayInfo);
                }
            }
        }
    }

    public SchoolAliPayInfo getSchoolAliPayInfo(String userId) {
        return getSchoolAliPayInfoEntityRepository().findByUserId(userId);
    }

    public Map<String,String> paySuccessNotify(Map<String, Object> reqMap) {
        return new HashMap<>();
    }

    private ChinaAreaConfigManager getChinaAreaConfigManager(){
        return this.context.getBean(ChinaAreaConfigManager.class);
    }

    protected SchoolAliPayInfoRepository getSchoolAliPayInfoEntityRepository() {
        return this.context.getBean(SchoolAliPayInfoRepository.class);
    }

    private BillingAliPayInfoRepository getBillingAliPayInfoRepository(){
        return this.context.getBean(BillingAliPayInfoRepository.class);
    }

}
