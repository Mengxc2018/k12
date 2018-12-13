package cn.k12soft.servo.module.VacationTeacher.mapper;

import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.VacationTeacher.domain.dto.VacationTeacherDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class VacationTeacherMapper extends EntityMapper<VacationTeacher, VacationTeacherDTO>{

    @Override
    protected VacationTeacherDTO convert(VacationTeacher vacationTeacher) {
        return new VacationTeacherDTO(
                vacationTeacher.getId(),
                vacationTeacher.getActorId(),
                vacationTeacher.getUserName(),
                vacationTeacher.getSchoolId(),
                vacationTeacher.getReason(),
                vacationTeacher.getDesc(),
                vacationTeacher.getFormDate(),
                vacationTeacher.getToDate(),
                vacationTeacher.getPortrait(),
                vacationTeacher.getCreatedAt(),
                vacationTeacher.getVacationTime(),
                vacationTeacher.getIsGone(),
                vacationTeacher.getUpdateTime()
        );
    }
}
