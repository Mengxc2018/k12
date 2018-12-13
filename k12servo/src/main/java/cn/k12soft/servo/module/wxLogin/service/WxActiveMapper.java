package cn.k12soft.servo.module.wxLogin.service;

import cn.k12soft.servo.module.wxLogin.domain.WxActive;
import cn.k12soft.servo.module.wxLogin.domain.WxActiveDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class WxActiveMapper extends EntityMapper<WxActive, WxActiveDTO>{
    @Override
    protected WxActiveDTO convert(WxActive wxActive) {
        return new WxActiveDTO(
                wxActive.getUserName(),
                0
        );
    }
}
