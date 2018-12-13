package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.service.dto.StudentDTO;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
@Component
public class StudentMapper extends EntityMapper<Student, StudentDTO> {

  @Override
  protected StudentDTO convert(Student student) {
    return new StudentDTO()
      .setId(student.getId())
      .setName(student.getName())
      .setAvatar(student.getAvatar())
      .setPortrait(student.getPortrait())
      .setGender(student.getGender())
      .setJoinedAt(student.getJoinedAt())
      .setBirthday(student.getBirthday())
      .setCardNo(student.getCardNo())
      .setKlassId(student.getKlass().getId());
  }
}
