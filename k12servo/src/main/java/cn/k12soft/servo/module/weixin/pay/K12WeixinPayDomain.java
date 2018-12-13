package cn.k12soft.servo.module.weixin.pay;

import cn.k12soft.servo.module.weixin.pay.sdk.IWXPayDomain;
import cn.k12soft.servo.module.weixin.pay.sdk.WXPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liubing on 2018/9/14
 */
public class K12WeixinPayDomain implements IWXPayDomain {
    private static Logger logger = LoggerFactory.getLogger(K12WeixinPayDomain.class);
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {
        if(ex != null){
            logger.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        DomainInfo domainInfo = new DomainInfo(K12WXPayConfig.WEIXIN_PAY_DOMAIN, true);
        return domainInfo;
    }
}
