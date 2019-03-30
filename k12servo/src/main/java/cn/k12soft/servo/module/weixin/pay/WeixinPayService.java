package cn.k12soft.servo.module.weixin.pay;

import cn.k12soft.servo.module.weixin.admin.WeixinPayRecord;
import cn.k12soft.servo.module.weixin.pay.sdk.WXPay;
import cn.k12soft.servo.module.weixin.pay.sdk.WXPayUtil;
import cn.k12soft.servo.module.weixin.service.WeixinPayRecodeService;
import cn.k12soft.servo.module.weixin.util.CommonUtil;
import cn.k12soft.servo.util.ConcurrentLock;
import cn.k12soft.servo.util.Times;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liubing on 2018/9/4
 */
@Service
public class WeixinPayService {
    private static Logger logger = LoggerFactory.getLogger(WeixinPayService.class);
    private WeixinPayOrderRepository weixinPayOrderRepository;
    private WeixinPayRecodeService weixinPayRecodeService;

    public WeixinPayService(WeixinPayOrderRepository weixinPayOrderRepository,
                            WeixinPayRecodeService weixinPayRecodeService){
        this.weixinPayOrderRepository = weixinPayOrderRepository;
        this.weixinPayRecodeService = weixinPayRecodeService;
    }

    public Map<String, String> placeOrder(String code, int totalFee, String clientIp, String body) throws Exception {
        long currentTime = System.currentTimeMillis();
        //登录凭证校验
        Map<String, String> loginParamMap = new LinkedHashMap<>();
        loginParamMap.put("appid", K12WXPayConfig.WEIXIN_PAY_APPID);
        loginParamMap.put("secret", K12WXPayConfig.WEIXIN_PAY_APPSECRET);
        loginParamMap.put("js_code", code);
        loginParamMap.put("grant_type", K12WXPayConfig.WEIXIN_PAY_GRANT_TYPE);
        JSONObject loginRetObj = CommonUtil.doGet(K12WXPayConfig.WEIXIN_PAY_CHECK_LOGIN_URL, loginParamMap);
        String openid = loginRetObj.getString("openid");
        String sessionKey = loginRetObj.getString("session_key");

        // 生成订单
        WeixinPayOrder payOrder = new WeixinPayOrder();
        payOrder.setOpenId(openid);
        payOrder.setOrderId(CommonUtil.createOrderId());
        payOrder.setState(K12WXPayConfig.WX_PAY_STATE_0);
        payOrder.setTotalFee(totalFee);
        this.weixinPayOrderRepository.save(payOrder);



        //调用支付统一下单
        K12WXPayConfig unifiedOrderPayConfig = new K12WXPayConfig();
        WXPay unifiedOrderPay = new WXPay(unifiedOrderPayConfig, true, true);
        Map<String, String> unifiedOrderParamMap = new HashMap<>();
        unifiedOrderParamMap.put("appid", K12WXPayConfig.WEIXIN_PAY_APPID);
        unifiedOrderParamMap.put("mch_id", K12WXPayConfig.WEIXIN_PAY_MCH_ID);
        unifiedOrderParamMap.put("nonce_str", CommonUtil.getRandomString(16));
        unifiedOrderParamMap.put("body", body);
        unifiedOrderParamMap.put("out_trade_no", payOrder.getOrderId());
        unifiedOrderParamMap.put("total_fee", String.valueOf(totalFee)); // 单位是分
        unifiedOrderParamMap.put("spbill_create_ip", clientIp);
        unifiedOrderParamMap.put("time_start", Times.fromTimeToyyyyMMddHHmmSS(currentTime));
        unifiedOrderParamMap.put("notify_url", K12WXPayConfig.WEIXIN_PAY_NOTIFY_URL);
        unifiedOrderParamMap.put("trade_type", "JSAPI");
        unifiedOrderParamMap.put("openid", openid);
        unifiedOrderParamMap = unifiedOrderPay.fillRequestData(unifiedOrderParamMap);
        logger.info("weixinpay sign str="+unifiedOrderParamMap.get("sign"));
        Map<String, String>  respMap = unifiedOrderPay.requestWithoutCertJson(K12WXPayConfig.WEIXIN_PAY_UNIFIEDORDER_URLSUFFIX, unifiedOrderParamMap, 10000, 10000);
        String  retCode = respMap.get("return_code");
        String resultCode = respMap.get("result_code");
        String retMsg = respMap.get("return_msg");
        if(!retCode.equals("SUCCESS") || !resultCode.equals("SUCCESS")){
            Map<String, String> retMap = new HashMap<>();
            retMap.put("return_code", retCode);
            retMap.put("result_code", resultCode);
            retMap.put("return_msg", retMsg);
            logger.error("weixin pay underfiedorder error retcode={}, resultcode={}, msg={}, err_code={}, err_code_des={}", retCode, resultCode, retMsg, respMap.get("unifiedJSONObj"), respMap.get("err_code_des"));
            return retMap;
        }

        String prepayId = respMap.get("prepay_id");
        String appId = respMap.get("appid");
        currentTime = System.currentTimeMillis();
        String nonceStr = CommonUtil.getRandomString(20);
        String signType = "MD5";
        String packageStr = "prepay_id="+prepayId;
        StringBuilder signSb = new StringBuilder("appId=").append(appId).append("&nonceStr=").append(nonceStr).append("&package=").append(packageStr)
                .append("&signType=").append("MD5").append("&timeStamp=").append(currentTime).append("&key=").append(K12WXPayConfig.WEIXIN_PAY_SIGNATURE);
        payOrder.setPrePayId(prepayId);
        payOrder.setState(K12WXPayConfig.WX_PAY_STATE_1);
        this.weixinPayOrderRepository.save(payOrder);
        Map<String, String> retMap = new HashMap<>();
        retMap.put("appId", appId);
        retMap.put("timeStamp", String.valueOf(currentTime));
        retMap.put("nonceStr", nonceStr);
        retMap.put("package", packageStr);
        retMap.put("signType", signType);
        retMap.put("sign", unifiedOrderParamMap.get("sign"));
        retMap.put("orderId", payOrder.getOrderId());
        logger.info("weixinpay placeorder success orderId="+payOrder.getOrderId());

        String transactionId = respMap.get("transaction_id") != null ? respMap.get("transaction_id") : "";
        String timeEnd = respMap.get("time_end") != null ? respMap.get("time_end") : "";

        // 微信支付记录
        WeixinPayRecord weixinPayRecode = new WeixinPayRecord(
                totalFee/100,
                openid,
                payOrder.getOrderId(),
                transactionId,
                timeEnd,
                Instant.now()
        );
        this.weixinPayRecodeService.save(weixinPayRecode);

        return retMap;
    }

    public String weixinNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String inputLine;
        String notifyXml = "";
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        //微信给返回的东西
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notifyXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            logger.error("weixinnotify exception ", e);
            String errorMsg = "weixinnotify parse param  ";
            return error(errorMsg);
        }
        if (StringUtils.isEmpty(notifyXml)) {
            String errorMsg = "weixinnotify notityXml param is empty";
            return error(errorMsg);
        }
        return weixinNotify(notifyXml);
    }

    public String weixinNotify(String xmlData) throws Exception {
        Map<String, String> dataMap = WXPayUtil.xmlToMap(xmlData);
        String returnCode = dataMap.get("return_code");
        String returnMsg = dataMap.get("return_msg");

        logger.info("weixinnotify begin...."+xmlData);

        if(!returnCode.equals("SUCCESS")){
            String errorMsg = "weixinnotify payorder returncode "+ returnCode+" "+ returnMsg;
            return error(errorMsg);
        }

        String resultCode = dataMap.get("result_code");
        if(!resultCode.equals("SUCCESS")){
            String errorMsg = "weixinnotify payorder resultcode "+ resultCode + " " + resultCode;
            return error(errorMsg);
        }
        String orderId = dataMap.get("out_trade_no");
        Object lock = ConcurrentLock.getInstance().getLock(ConcurrentLock.WX_PAY_ORDER, orderId);
        synchronized (lock) {
            int totalFee = Integer.valueOf(dataMap.get("total_fee"));
            String paramSign = dataMap.get("sign");
            String localSign = WXPayUtil.generateSignature(dataMap, K12WXPayConfig.WEIXIN_PAY_SIGNATURE);
            if(!paramSign.equals(localSign)){
                String errorMsg = "weixinnotify payorder sing error "+ paramSign + " "+localSign;
                return error(errorMsg);
            }
            WeixinPayOrder payOrder = this.weixinPayOrderRepository.findByOrderId(orderId);
            if (payOrder == null) {
                String errorMsg = "weixinnotify payorder is null";
                return error(errorMsg);
            }
            if(payOrder.getState() != K12WXPayConfig.WX_PAY_STATE_1){
                String errorMsg = "weixinnotify payorder state error "+payOrder.getState();
                return error(errorMsg);
            }
            if(payOrder.getTotalFee() != totalFee){
                String errorMsg = "weixinnotify payorder state error "+payOrder.getState();
                return error(errorMsg);
            }
            payOrder.setState(K12WXPayConfig.WX_PAY_STATE_2);
            this.weixinPayOrderRepository.save(payOrder);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("return_code", "SUCCESS");
            logger.info("weixinnotify end...."+payOrder.getOrderId());
            return WXPayUtil.mapToXml(retMap);
        }
    }

    private String error(String errorMsg) throws Exception {
        Map<String, String> retMap = new HashMap<>();
        retMap.put("return_code", "FAIL");
        retMap.put("return_msg", errorMsg);
        logger.error("weixin pay error "+ errorMsg);
        return WXPayUtil.mapToXml(retMap);
    }

    public List<WeixinPayOrder> queryByTime(long fromTime, long toTime) {
        if(fromTime<=0){
            fromTime = System.currentTimeMillis();
        }
        fromTime =  Times.monthStartTime(fromTime);
        toTime = Times.monthEndTime(fromTime+24*3600*1000);

        return this.weixinPayOrderRepository.findByCreateTimeBetween(fromTime, toTime);
    }

    public WeixinPayOrder queryByOrderId(String orderId) {
        return this.weixinPayOrderRepository.findByOrderId(orderId);
    }
}
