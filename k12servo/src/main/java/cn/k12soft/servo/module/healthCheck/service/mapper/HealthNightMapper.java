package cn.k12soft.servo.module.healthCheck.service.mapper;

import cn.k12soft.servo.module.healthCheck.domain.HealthCheck;
import cn.k12soft.servo.module.healthCheck.domain.dto.HealthNightDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class HealthNightMapper extends EntityMapper<HealthCheck, HealthNightDTO>{
    @Override
    protected HealthNightDTO convert(HealthCheck h) {
        return new HealthNightDTO(
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
                h.getAddfood(),
                h.getExcrete(),
                h.getRemark()
        );
    }
}
