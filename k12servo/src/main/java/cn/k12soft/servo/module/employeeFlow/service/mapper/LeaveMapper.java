package cn.k12soft.servo.module.employeeFlow.service.mapper;

import cn.k12soft.servo.module.employeeFlow.domain.EmployeeFlow;
import cn.k12soft.servo.module.employeeFlow.domain.dto.LeaveDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class LeaveMapper extends EntityMapper<EmployeeFlow, LeaveDTO>{

    @Override
    protected LeaveDTO convert(EmployeeFlow e) {
        return new LeaveDTO(
                Integer.valueOf(e.getId().toString()),
                e.getActorId(),
                e.getUserName(),
                e.getJoinAt(),
                e.getContent(),
                e.getDate(),
                e.getIsgone(),
                e.getCreatedAt(),
                e.getUpdateAt()
        );
    }
}
