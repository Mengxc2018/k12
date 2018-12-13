package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Vacation;
import cn.k12soft.servo.service.dto.VacationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@Component
public class VacationMapper extends EntityMapper<Vacation, VacationDTO> {

  private final StudentWithGuardiansMapper studentMapper;

  @Autowired
  public VacationMapper(StudentWithGuardiansMapper studentMapper) {
    this.studentMapper = studentMapper;
  }

  @Override
  protected VacationDTO convert(Vacation vacation) {
    return new VacationDTO(
      vacation.getId(),
      studentMapper.toDTO(vacation.getStudentId()),
      vacation.getReason(),
      vacation.getDesc(),
      vacation.getFromDate(),
      vacation.getToDate(),
      vacation.getCreatedAt()
    );
  }
}
