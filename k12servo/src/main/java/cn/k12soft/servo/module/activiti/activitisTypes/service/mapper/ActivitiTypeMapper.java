package cn.k12soft.servo.module.activiti.activitisTypes.service.mapper;

import cn.k12soft.servo.module.activiti.activitisTypes.domain.ActivitisTypes;
import cn.k12soft.servo.module.activiti.activitisTypes.domain.dto.ActivitiTypeDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ActivitiTypeMapper extends EntityMapper<ActivitisTypes, ActivitiTypeDTO>{
    @Override
    protected ActivitiTypeDTO convert(ActivitisTypes activitiType) {
        return new ActivitiTypeDTO(
                activitiType.getName(),
                activitiType.getTypeNo(),
                activitiType.getSchoolId()
        );
    }
}
