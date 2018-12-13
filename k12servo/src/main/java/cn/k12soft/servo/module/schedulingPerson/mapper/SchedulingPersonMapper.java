package cn.k12soft.servo.module.schedulingPerson.mapper;

import cn.k12soft.servo.module.schedulingPerson.domian.SchedulingPerson;
import cn.k12soft.servo.module.schedulingPerson.domian.dto.SchedulingPersonDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class SchedulingPersonMapper extends EntityMapper<SchedulingPerson, SchedulingPersonDTO> {


    @Override
    protected SchedulingPersonDTO convert(SchedulingPerson schedulingPerson) {

        return new SchedulingPersonDTO(
                schedulingPerson.getId(),
                schedulingPerson.getActorId(),
                schedulingPerson.getUserName(),
                schedulingPerson.getScheduling(),
                schedulingPerson.getCreatedAt(),
                schedulingPerson.getUpdatedAt()
        );
    }
}
