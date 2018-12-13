package cn.k12soft.servo.module.scheduling.service.mapper;

import cn.k12soft.servo.module.scheduling.domain.Scheduling;
import cn.k12soft.servo.module.scheduling.domain.dto.SchedulingDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class SchedulingMapper extends EntityMapper<Scheduling, SchedulingDTO>{

    @Override
    protected SchedulingDTO convert(Scheduling scheduling) {
        return new SchedulingDTO(
                scheduling.getId(),
                scheduling.getSchoolId(),
                scheduling.getName(),
                scheduling.getAmStartTime(),
                scheduling.getAmEndTime(),
                scheduling.getPmStartTime(),
                scheduling.getPmEndTime(),
                scheduling.getCreatedAt(),
                scheduling.getUpdatedDate(),
                scheduling.getSchedulingType()
        );
    }

}
