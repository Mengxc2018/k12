package cn.k12soft.servo.module.attendanceCount.mapper;

import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.attendanceCount.domain.dto.VacaTimeDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * VacationTeacher TO VacaTimeDate
 */
@Component
public class VacationToVacaTimeDTO extends EntityMapper<VacationTeacher, VacaTimeDTO>{

    @Override
    protected VacaTimeDTO convert(VacationTeacher vacationTeacher) {
        return new VacaTimeDTO(
                vacationTeacher.getFormDate(),
                vacationTeacher.getToDate(),
                vacationTeacher.getVacationTime(),
                vacationTeacher.getCreatedAt(),
                vacationTeacher.getIsGone()
        );
    }
}
