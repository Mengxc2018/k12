package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Guardian;
import cn.k12soft.servo.service.dto.GuardianDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/21.
 */
@Component
public class GuardianMapper extends EntityMapper<Guardian, GuardianDTO> {

  private final KlassMapper klassMapper;
  private final PatriarchMapper patriarchMapper;

  @Autowired
  public GuardianMapper(KlassMapper klassMapper,
                        PatriarchMapper patriarchMapper) {
    this.klassMapper = klassMapper;
    this.patriarchMapper = patriarchMapper;
  }

  @Override
  protected GuardianDTO convert(Guardian guardian) {
    return new GuardianDTO()
      .setId(guardian.getId())
      .setPatriarch(patriarchMapper.fromPatriarchId(guardian.getPatriarchId()))
      .setRelationType(guardian.getRelationType())
      .setStudentId(guardian.getStudent().getId())
      .setKlass(klassMapper.toDTO(guardian.getStudent().getKlass()));
  }
}
