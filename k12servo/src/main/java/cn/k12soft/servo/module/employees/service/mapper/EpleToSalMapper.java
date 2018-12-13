package cn.k12soft.servo.module.employees.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.empFlowRate.domain.FolwEnum;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.domain.dto.EpleToSalDTO;
import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;
import cn.k12soft.servo.module.revenue.repository.TeacherSocialSecurityRepository;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class EpleToSalMapper extends EntityMapper<Employee, EpleToSalDTO>{

    private final UserRepository userRepository;
    private final ActorRepository actorRepository;
    private final TeacherSocialSecurityRepository teacherSocialSecurityRepository;

    public EpleToSalMapper(UserRepository userRepository,
                           ActorRepository actorRepository,
                           TeacherSocialSecurityRepository teacherSocialSecurityRepository) {
        this.userRepository = userRepository;
        this.actorRepository = actorRepository;
        this.teacherSocialSecurityRepository = teacherSocialSecurityRepository;
    }

    @Override
    protected EpleToSalDTO convert(Employee employee) {
        FolwEnum type = FolwEnum.JOINBY;
        Actor actor = actorRepository.findOne(employee.getActorId());
        User user = userRepository.findOne(actor.getUserId());
        TeacherSocialSecurity teacherSocialSecurity = teacherSocialSecurityRepository.findByActorId(actor.getId().toString());
        if (teacherSocialSecurity == null){
            teacherSocialSecurity = new TeacherSocialSecurity(
                    actor.getId().toString(),
                    employee,
                    user.getUsername(),
                    type
            );
            teacherSocialSecurity.setId(0);
            teacherSocialSecurity.setSchoolId(0);
        }
        return new EpleToSalDTO()
                .setId(employee.getId())
                .setName(user.getUsername())
                .setTeacherSocialSecurity(teacherSocialSecurity);
    }
}
