package cn.k12soft.servo.module.weixin.service;

import cn.k12soft.servo.module.weixin.admin.WeixinPayRecord;
import cn.k12soft.servo.module.weixin.admin.WxPayRecordDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class WxPayRecordMapper extends EntityMapper<WeixinPayRecord, WxPayRecordDTO> {
    @Override
    protected WxPayRecordDTO convert(WeixinPayRecord wx) {
        return new WxPayRecordDTO(
                wx.getFeeType(),
                wx.getTotalFee(),
                wx.getOpenId(),
                wx.getOutTradeNo(),
                wx.getTransactionId(),
                wx.getTimeEnd(),
                wx.getCreateAt()
        );
    }
}
