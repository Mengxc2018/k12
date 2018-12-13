package cn.k12soft.servo.module.healthCheck.service.mapper;

import cn.k12soft.servo.module.healthCheck.domain.HealthCheck;
import cn.k12soft.servo.module.healthCheck.domain.dto.HealthMorningDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class HealthMorningMapper extends EntityMapper<HealthCheck, HealthMorningDTO>{
    @Override
    protected HealthMorningDTO convert(HealthCheck h) {
        return new HealthMorningDTO(
                h.getId(),
                h.getStudent(),
                h.getStudent().getKlass().getId(),
                h.getStudent().getKlass().getName(),
                h.getStudent().getSchoolId(),
                h.getCreatedAt(),
                h.getType(),
                h.getSpirit(),
                h.getBody(),
                h.getSink(),
                h.getMouth(),
                h.getOther(),
                h.getTemperature(),
                h.getRemark()
        );
    }
}
