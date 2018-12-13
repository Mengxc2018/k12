package cn.k12soft.servo.module.weixin.pay;

import cn.k12soft.servo.module.weixin.pay.sdk.IWXPayDomain;
import cn.k12soft.servo.module.weixin.pay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * Created by liubing on 2018/9/5
 */
public class K12WXPayConfig extends WXPayConfig {
    public static final int WX_PAY_STATE_0 = 0;//生成订单
    public static final int WX_PAY_STATE_1 = 1;//统一下单成功
    public static final int WX_PAY_STATE_2 = 2;//支付成功

    //微信支付
    public static final String WEIXIN_PAY_CHECK_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session?";
    public static final String WEIXIN_PAY_APPID = "wx6df6913b21e771ed";
    public static final String WEIXIN_PAY_APPSECRET = "03dfc8a010ca9db45e60cb020dfc3050";
    public static final String WEIXIN_PAY_GRANT_TYPE = "authorization_code";
    public static final String WEIXIN_PAY_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String WEIXIN_PAY_MCH_ID = "1493696232";
//    public static final String WEIXIN_PAY_SIGNATURE = "JUIE788#@q)o_!PP";
    public static final String WEIXIN_PAY_SIGNATURE = "24D85E56E7F13AFEB649938AF2433CA1";
    public static final String WEIXIN_PAY_NOTIFY_URL = "https://k12.vwico.com/weixin/pay/notify";
    public static final String WEIXIN_PAY_DOMAIN = "api.mch.weixin.qq.com";
    public static final String WEIXIN_PAY_UNIFIEDORDER_URLSUFFIX = "/pay/unifiedorder";

    @Override
    public String getAppID() {
        return WEIXIN_PAY_APPID;
    }

    @Override
    public String getMchID() {
        return WEIXIN_PAY_MCH_ID;
    }

    @Override
    public String getKey() {
        return WEIXIN_PAY_SIGNATURE;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    protected IWXPayDomain getWXPayDomain() {
        return new K12WeixinPayDomain();
    }
}
