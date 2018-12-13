package cn.k12soft.servo.module.applyFor.service.mapper;

import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.VacationTeacher.repository.VacationTeacherRepository;
import cn.k12soft.servo.module.applyFor.domain.dto.MassageDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class MassageMapper extends EntityMapper<VacationTeacher, MassageDTO>{
    private final VacationTeacherRepository vacationTeacherRepository;

    public MassageMapper(VacationTeacherRepository vacationTeacherRepository) {
        this.vacationTeacherRepository = vacationTeacherRepository;
    }

    @Override
    protected MassageDTO convert(VacationTeacher vacationTeacher) {
        VacationTeacher one = vacationTeacherRepository.findOne(new Long(vacationTeacher.getId()));
        if (one == null){
            return null;
        }
        return new MassageDTO(
                one.getId(),
                one.getActorId(),
                one.getSchoolId(),
                one.getUserName(),
                one.getReason(),
                one.getDesc(),
                one.getPictrue(),
                one.getFormDate(),
                one.getToDate(),
                one.getPortrait(),
                one.getCreatedAt(),
                one.getVacationTime(),
                one.getIsGone()
        );
    }
}
