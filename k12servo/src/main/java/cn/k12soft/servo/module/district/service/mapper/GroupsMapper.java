package cn.k12soft.servo.module.district.service.mapper;

import cn.k12soft.servo.module.district.form.dto.GroupsDTO;
import cn.k12soft.servo.module.zone.domain.Groups;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupsMapper extends EntityMapper<Groups, GroupsDTO>{
    @Override
    protected GroupsDTO convert(Groups groups) {
        return new GroupsDTO(
                groups.getId(),
                groups.getName(),
                groups.getCode()
        );
    }
}
