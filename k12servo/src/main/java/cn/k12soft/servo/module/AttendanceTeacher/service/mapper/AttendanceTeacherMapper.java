package cn.k12soft.servo.module.AttendanceTeacher.service.mapper;

import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendanceTeacher;
import cn.k12soft.servo.module.AttendanceTeacher.domain.dto.AttendanceTeacherDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Component
public class AttendanceTeacherMapper extends EntityMapper<AttendanceTeacher, AttendanceTeacherDTO> {

    @Override
    protected AttendanceTeacherDTO convert(AttendanceTeacher attendanceTeacher) {
        LocalDate d = LocalDate.parse(attendanceTeacher.getCreatedAt().toString(), DateTimeFormatter.BASIC_ISO_DATE);
        Instant date = LocalDateTime.of(d, LocalTime.now()).toInstant(ZoneOffset.UTC).plusMillis(-28800000);
        return new AttendanceTeacherDTO(
                attendanceTeacher.getId(),
                attendanceTeacher.getTemperature(),
                attendanceTeacher.getPortrait(),
                date,
                date
        );
    }
}
