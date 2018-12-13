package cn.k12soft.servo.module.weixin.pay;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.weixin.util.CommonUtil;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.util.Constants;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by liubing on 2018/9/4
 */

@RestController
@RequestMapping("/weixin/pay")
public class WeixinPayController {
    private WeixinPayService weixinPayService;

    public WeixinPayController(WeixinPayService weixinPayService){
        this.weixinPayService = weixinPayService;
    }

    /**
     * 下单
     * @return
     */
    @GetMapping("/unifiedOrder")
    @ApiOperation("微信支付下单")
    public Map<String, String> placeOrder(@Active Actor operator,
                                          @RequestParam(value="code", required = true) String code,
                                          @RequestParam(value = "totalFee", required = true) int totalFee,
                                          @RequestParam(value = "clientIp", required = true) String clientIp,
                                          @RequestParam(value = "body", required = true) String body) throws Exception{
        return this.weixinPayService.placeOrder(code, totalFee, clientIp, body);
    }

//    @PostMapping("/notify")
//    public String weixinNotify(@RequestBody String xmlData) throws Exception{
//        return this.weixinPayService.weixinNotify(xmlData);
//    }

    @PostMapping("/notify")
    public String weixinNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        return this.weixinPayService.weixinNotify(request, response);
    }


    @GetMapping("/getOrderList")
    @ApiOperation("查询订单")
    public List<WeixinPayOrder> getOrderList(@Active Actor operator, @RequestParam(value="from", required = true) long fromTime, @RequestParam(value="to", required = true) long toTime){
        return this.weixinPayService.queryByTime(fromTime, toTime);
    }

    @GetMapping("/getOrder")
    @ApiOperation("查询单个订单")
    public WeixinPayOrder getByOrderId(@Active Actor operator, @RequestParam(value="orderId", required = true) String orderId){
        return this.weixinPayService.queryByOrderId(orderId);
    }
}
