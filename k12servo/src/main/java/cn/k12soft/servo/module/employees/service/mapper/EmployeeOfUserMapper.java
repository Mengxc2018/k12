package cn.k12soft.servo.module.employees.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.employees.domain.dto.EmployeeDTO;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * 不在员工表内的员工映射
 */
@Component
public class EmployeeOfUserMapper extends EntityMapper<Actor, EmployeeDTO>{
    private final UserRepository userRepository;

    public EmployeeOfUserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected EmployeeDTO convert(Actor actor) {
        User user = userRepository.findOne(actor.getUserId());
        return new EmployeeDTO(
                actor.getId(),
                user.getUsername(),
                actor.getSchoolId(),
                user.getMobile()
        );
    }
}
