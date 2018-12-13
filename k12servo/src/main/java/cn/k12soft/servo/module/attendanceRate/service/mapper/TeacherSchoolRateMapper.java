package cn.k12soft.servo.module.attendanceRate.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.attendanceRate.domain.TeacherSchoolRate;
import cn.k12soft.servo.module.attendanceRate.domain.dto.TeacherDTO;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class TeacherSchoolRateMapper extends EntityMapper<TeacherSchoolRate, TeacherDTO>{

    private final ActorRepository actorRepository;
    private final UserRepository userRepository;

    public TeacherSchoolRateMapper(ActorRepository actorRepository, UserRepository userRepository) {
        this.actorRepository = actorRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected TeacherDTO convert(TeacherSchoolRate attendanceRate) {
        Actor actor = actorRepository.findOne(attendanceRate.getActorId());
        User user = userRepository.findOne(actor.getUserId());
        return new TeacherDTO(
                attendanceRate.getId(),
                attendanceRate.getSchool(),
                attendanceRate.getActorId(),
                user.getUsername(),
                attendanceRate.getJanuary(),
                attendanceRate.getFebruary(),
                attendanceRate.getMarch(),
                attendanceRate.getApril(),
                attendanceRate.getMay(),
                attendanceRate.getJune(),
                attendanceRate.getJuly(),
                attendanceRate.getAuguest(),
                attendanceRate.getSeptember(),
                attendanceRate.getOctober(),
                attendanceRate.getNovember(),
                attendanceRate.getDecember(),
                attendanceRate.getCreatedAt()
        );
    }
}
