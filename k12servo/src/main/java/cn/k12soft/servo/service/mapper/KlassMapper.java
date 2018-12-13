package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.repository.GradeRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.service.dto.KlassDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
@Component
public class KlassMapper extends EntityMapper<Klass, KlassDTO> {

  private final KlassRepository repository;

  @Autowired
  public KlassMapper(KlassRepository repository) {
    this.repository = repository;
  }

  @Override
  protected KlassDTO convert(Klass klass) {
    Klass kls = repository.getOne(klass.getId());
    return new KlassDTO()
      .setId(kls.getId())
      .setName(kls.getName())
      .setDescription(kls.getDescription())
      .setGradeId(kls.getGrade().getId())
            .setGoupTime(klass.getGoupTime())
            .setJoinOfYear(klass.getJoinOfYear())
            .setGraduateOfYear(klass.getGraduateOfYear())
            .setGraduate(klass.isGraduate())
            .setGraduateAt(klass.getGraduateAt());
  }

  public KlassDTO fromKlassId(Integer klassId) {
    return toDTO(repository.getOne(klassId));
  }
}
