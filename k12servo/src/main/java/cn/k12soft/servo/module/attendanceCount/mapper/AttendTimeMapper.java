package cn.k12soft.servo.module.attendanceCount.mapper;

import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendanceTeacher;
import cn.k12soft.servo.module.attendanceCount.domain.dto.AttendTimeDTO;
import cn.k12soft.servo.module.attendanceCount.domain.dto.EntityMapperToList;
import org.springframework.stereotype.Component;

@Component
public class AttendTimeMapper extends EntityMapperToList<AttendanceTeacher, AttendTimeDTO> {
    @Override
    protected AttendTimeDTO convert(AttendanceTeacher attendanceTeacher) {
        Long createdAt = Long.parseLong(attendanceTeacher.getCreatedAt().toString().replace("-", ""));
        return new AttendTimeDTO(
                attendanceTeacher.getId().intValue(),
                attendanceTeacher.getActorId(),
                attendanceTeacher.getAmStartTime(),
                attendanceTeacher.getAmEndTime(),
                attendanceTeacher.getPmStartTime(),
                attendanceTeacher.getPmEndTime(),
                createdAt,
                null,
                null

        );
    }
}
