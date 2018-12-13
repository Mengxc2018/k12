package cn.k12soft.servo.module.AttendanceTeacher.service.mapper;

import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendanceTeacher;
import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendTeacDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.time.*;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

@Component
public class AttendTeacMapper extends EntityMapper<AttendanceTeacher, AttendTeacDTO>{
    @Override
    protected AttendTeacDTO convert(AttendanceTeacher attendanceTeacher) {
        LocalDate createdAt = LocalDate.parse(attendanceTeacher.getCreatedAt().toString(),BASIC_ISO_DATE);
        Instant date = createdAt.atStartOfDay().toInstant(ZoneOffset.UTC);
//        Instant date = LocalDateTime.of(createdAt, LocalTime.now()).toInstant(ZoneOffset.UTC);
        return new AttendTeacDTO(
                Integer.valueOf(attendanceTeacher.getId().toString()),
                attendanceTeacher.getActorId(),
                attendanceTeacher.getSchoolId(),
                attendanceTeacher.getPortrait(),
                attendanceTeacher.getTemperature(),
                attendanceTeacher.getAmStartTime(),
                attendanceTeacher.getAmEndTime(),
                attendanceTeacher.getPmStartTime(),
                attendanceTeacher.getPmEndTime(),
                attendanceTeacher.getAst(),
                attendanceTeacher.getAet(),
                attendanceTeacher.getPst(),
                attendanceTeacher.getPet(),
                date,
                attendanceTeacher.getIsFull(),
                attendanceTeacher.getTime(),
                attendanceTeacher.getStatus(),
                null
        );
    }
}
