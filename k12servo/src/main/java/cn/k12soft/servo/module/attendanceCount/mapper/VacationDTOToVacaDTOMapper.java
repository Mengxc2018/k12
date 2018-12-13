package cn.k12soft.servo.module.attendanceCount.mapper;

import cn.k12soft.servo.module.VacationTeacher.domain.dto.VacationTeacherDTO;
import cn.k12soft.servo.module.attendanceCount.domain.dto.VacaTimeDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class VacationDTOToVacaDTOMapper extends EntityMapper<VacationTeacherDTO, VacaTimeDTO>{


    @Override
    protected VacaTimeDTO convert(VacationTeacherDTO vacationTeacherDTO) {
        return new VacaTimeDTO(
                vacationTeacherDTO.getFromDate(),
                vacationTeacherDTO.getToDate(),
                vacationTeacherDTO.getVacationTime(),
                vacationTeacherDTO.getCreatedAt(),
                vacationTeacherDTO.getIsGone()
        );
    }
}
