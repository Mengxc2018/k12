package cn.k12soft.servo.third.alipay;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.BillingAliPayInfo;
import cn.k12soft.servo.domain.SchoolAliPayInfo;
import cn.k12soft.servo.domain.enumeration.Permission;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.web.form.AliPayBillingForm;
import cn.k12soft.servo.web.form.AliPaySchoolInfoForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by liubing on 2018/7/2
 */
@RestController
@RequestMapping("/api/alipay/")
public class AliPayController {
    private AliPayService aliPayService;

    @Autowired
    public AliPayController(AliPayService aliPayService) {
        this.aliPayService = aliPayService;
    }

    //应用授权回调 app_auth_code和开发者的app_id
    @ApiOperation("第三方授权回调")
    @PostMapping(value = "/toAuthCb")
    @PermissionRequired(Permission.ALIPAY_POST)
    @Timed
    public void aliPayAuthCallback(@RequestBody Map<String, Object> reqMap){
        this.aliPayService.aliPayAuthCallback(reqMap);
    }

    @ApiOperation("获取支付宝商户的token")
    @GetMapping(value = "/getAliPayInfo")
    @PermissionRequired(Permission.ALIPAY_VIEW)
    @Timed
    public SchoolAliPayInfo getSchoolAliPayInfo(@Active Actor actor, @RequestParam("userId") String userId){
        return this.aliPayService.getSchoolAliPayInfo(userId);
    }

    @ApiOperation("返回第三方授权信息")
    @GetMapping(value = "/getAuthInfo", params = {"app_auth_token"})
    @PermissionRequired(Permission.ALIPAY_VIEW)
    @Timed
    public Map<String, String> getAuthTokenInfo(@RequestParam("app_auth_token") String authToken){
        return this.aliPayService.getAuthInfo(authToken);
    }

    /**
     * private String schoolType; //学校的类型 1：代表托儿所； 2：代表幼儿园；3：代表小学；4：代表初中；5：代表高中。
     private String provinceCode; //省份的国家编码（国家统计局出版的行政区划代码 http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/）
     private String provinceName; //省名称
     private String cityCode; //城市的国家编码（国家统计局出版的行政区划代码 http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/）
     private String cityName;//	城市名称
     private String districtCode;    //区县的国家编码（国家统计局出版的行政区划代码 http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/）
     private String districtName;//	String	必选	64	区县名称
     private String isvName;//	String	必选	256	商家名称，每个接入教育缴费的ISV商家名称，由ISV自己提供
     private String isvNotifyUrl;//	String	必选	256	此通知地址是为了保持教育缴费平台与ISV商户支付状态一致性。用户支付成功后，支付宝会根据本isv_notify_url，通过POST请求的形式将支付结果作为参数通知到商户系统，ISV商户可以根据返回的参数更新账单状态。
     private String isvPid;//	String	必选	128	填写已经签约教育缴费的isv的支付宝PID
     private String isvPhone;//	String	必选	20	ISV联系电话,用于账单详情页面显示
     private String schoolPid;//	String	必选	128	学校用来签约支付宝教育缴费的支付宝PID
     * @param form
     * @return
     */
    @ApiOperation("发送学校信息")
    @PostMapping(value = "/upSchoolInfo")
    @PermissionRequired(Permission.ALIPAY_POST)
    @Timed
    public Map<String, String> modifySchoolInfo(@RequestBody AliPaySchoolInfoForm form){
        return this.aliPayService.modifySchoolInfo(form);
    }

    @ApiOperation("发送缴费订单")
    @PostMapping(value = "/sendBilling")
    @PermissionRequired(Permission.ALIPAY_POST)
    @Timed
    public Map<String, String> sendBilling(@Active Actor actor, @RequestBody AliPayBillingForm form){
        return this.aliPayService.sendBilling(actor.getSchoolId(), form);
    }

    @ApiOperation("缴费")
    @PostMapping(value = "/pay", params = {"orderNo"})
    @PermissionRequired(Permission.ALIPAY_POST)
    @Timed
    public Map<String, String> studentPay(@RequestParam String orderNo){
        return this.aliPayService.studentPay(orderNo);
    }

    @ApiOperation("查询订单")
    @GetMapping(value = "/queryBilling", params = {"orderNo"})
    @PermissionRequired(Permission.ALIPAY_VIEW)
    @Timed
    public Map<String, String> queryAliPayOrderStatus(@RequestParam String orderNo){
        return this.aliPayService.queryAliPayOrderStatus(orderNo);
    }

    @ApiOperation("应用网关")
    @PostMapping(value = "/toZfbCb")
    @PermissionRequired(Permission.ALIPAY_POST)
    @Timed
    public Map<String, String> zfbCb(@RequestBody Map<String,Object> reqMap){
        return this.aliPayService.zfbCb(reqMap);
    }

    /**
     * isv_notify_url，此链接是为了保持教育缴费平台与ISV
     商户支付状态一致性。用户支付成功后，会通过notify_url异步调用此链接，ISV商户根据返回的参数更新账单状态。
     * @param reqMap
     * @return
     */
    @ApiOperation("支付成功通知(支付宝服务器发过来的)")
    @PostMapping(value="/toPaySuccessNotify")
    @PermissionRequired(Permission.ALIPAY_POST)
    public Map<String, String> paySuccessNotify(@RequestBody Map<String,Object> reqMap){
        return this.aliPayService.paySuccessNotify(reqMap);
    }
}
