package cn.k12soft.servo.module.weixin.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.weixin.admin.WxPayRecordDTO;
import cn.k12soft.servo.module.weixin.service.WeixinPayRecodeService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("weixinPayRecord")
public class WeixinPayRecordManagement {

    private final WeixinPayRecodeService weixinPayRecodeService;

    public WeixinPayRecordManagement(WeixinPayRecodeService weixinPayRecodeService) {
        this.weixinPayRecodeService = weixinPayRecodeService;
    }

    @ApiOperation("查询微信支付成功的订单")
    @GetMapping("findOrder")
    public Collection<WxPayRecordDTO> findOrder(@Active Actor actor,
                                                @RequestParam LocalDate localDate){
        return weixinPayRecodeService.findOrder(actor, localDate);
    }

}
