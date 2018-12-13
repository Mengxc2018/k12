package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.service.StudentService;
import cn.k12soft.servo.service.dto.StudentWithGuardiansDTO;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/21.
 */
@Component
public class StudentWithGuardiansMapper extends EntityMapper<Student, StudentWithGuardiansDTO> {

  private final StudentService studentService;

  public StudentWithGuardiansMapper(StudentService studentService) {
    this.studentService = studentService;
  }

  public StudentWithGuardiansDTO toDTO(Integer studentId) {
    return toDTO(studentService.get(studentId));
  }

  @Override
  protected StudentWithGuardiansDTO convert(Student student) {
    return new StudentWithGuardiansDTO()
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
