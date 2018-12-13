package cn.k12soft.servo.module.applyFor.service.mapper;

import cn.k12soft.servo.module.applyFor.domain.dto.ApplyForNowDTO;
import cn.k12soft.servo.module.applyFor.domain.ApplyFor;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ApplyForNowMapper extends EntityMapper<ApplyFor, ApplyForNowDTO>{
    @Override
    protected ApplyForNowDTO convert(ApplyFor applyFor) {
        return new ApplyForNowDTO(
                applyFor.getMasterId(),
                applyFor.getMasterName(),
                applyFor.getPortrait()
        );
    }
}
