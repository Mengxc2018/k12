package cn.k12soft.servo.module.healthCheck.service.mapper;

import cn.k12soft.servo.module.healthCheck.domain.HealthCheck;
import cn.k12soft.servo.module.healthCheck.domain.dto.HealthNoonDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class HealthNoonMapper extends EntityMapper<HealthCheck, HealthNoonDTO>{
    @Override
    protected HealthNoonDTO convert(HealthCheck h) {
        return new HealthNoonDTO(
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
                h.getDinner(),
                h.getAfternap(),
                h.getRemark()
        );
    }
}
