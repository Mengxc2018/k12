package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Attendance;
import cn.k12soft.servo.service.dto.AttendanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@Component
public class AttendanceMapper extends EntityMapper<Attendance, AttendanceDTO> {

  private final StudentWithGuardiansMapper studentMapper;

  @Autowired
  public AttendanceMapper(StudentWithGuardiansMapper studentMapper) {
    this.studentMapper = studentMapper;
  }

  @Override
  protected AttendanceDTO convert(Attendance attendance) {
    return new AttendanceDTO(
      attendance.getId(),
      studentMapper.toDTO(attendance.getStudentId()),
      attendance.getPortrait(),
      attendance.getTemperature(),
      attendance.getSignAt(),
      attendance.getAttendanceType(),
      attendance.getCreatedAt(),
      attendance.getRemarks()
    );
  }
}
