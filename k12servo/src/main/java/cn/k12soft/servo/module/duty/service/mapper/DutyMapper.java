package cn.k12soft.servo.module.duty.service.mapper;

import cn.k12soft.servo.module.duty.domain.Duty;
import cn.k12soft.servo.module.duty.domain.dto.DutyDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class DutyMapper extends EntityMapper<Duty, DutyDTO>{

    @Override
    protected DutyDTO convert(Duty duty) {
        return new DutyDTO(
                Integer.valueOf(duty.getId().toString()),
                duty.getName(),
                duty.getSchoolId(),
                duty.getIsSubstratum()
        );
    }
}
