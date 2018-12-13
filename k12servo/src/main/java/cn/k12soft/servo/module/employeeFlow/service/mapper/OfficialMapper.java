package cn.k12soft.servo.module.employeeFlow.service.mapper;

import cn.k12soft.servo.module.employeeFlow.domain.EmployeeFlow;
import cn.k12soft.servo.module.employeeFlow.domain.dto.OfficialDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class OfficialMapper extends EntityMapper<EmployeeFlow, OfficialDTO>{
    @Override
    protected OfficialDTO convert(EmployeeFlow employeeFlow) {
        return new OfficialDTO(
                Integer.valueOf(employeeFlow.getId().toString()),
                employeeFlow.getUserName(),
                employeeFlow.getDutyName(),
                employeeFlow.getJoinAt(),
                employeeFlow.getDepartment(),
                employeeFlow.getContent(),
                employeeFlow.getDate(),
                employeeFlow.getIsgone(),
                employeeFlow.getCreatedAt(),
                employeeFlow.getUpdateAt()
        );
    }
}
