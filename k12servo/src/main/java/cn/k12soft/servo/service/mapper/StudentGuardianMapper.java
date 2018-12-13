package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Guardian;
import cn.k12soft.servo.repository.GuardianRepository;
import cn.k12soft.servo.service.dto.StudentGuardianDTO;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
@Component
public class StudentGuardianMapper extends EntityMapper<Guardian, StudentGuardianDTO> {

  private final GuardianRepository guardianRepository;
  private final StudentMapper studentMapper;
  private final KlassMapper klassMapper;

  @Autowired
  public StudentGuardianMapper(GuardianRepository guardianRepository,
                               StudentMapper studentMapper,
                               KlassMapper klassMapper) {
    this.guardianRepository = guardianRepository;
    this.studentMapper = studentMapper;
    this.klassMapper = klassMapper;
  }

  @Override
  protected StudentGuardianDTO convert(Guardian guardian) {
    return new StudentGuardianDTO()
      .setId(guardian.getId())
      .setPatriarchId(guardian.getPatriarchId())
      .setRelationType(guardian.getRelationType())
      .setStudent(studentMapper.toDTO(guardian.getStudent()))
      .setKlass(klassMapper.toDTO(guardian.getStudent().getKlass()));
  }

  public Collection<StudentGuardianDTO> fromPatriarchId(Integer patriarchId) {
    return toDTOs(guardianRepository.findAllByPatriarchId(patriarchId));
  }
}
