package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.service.dto.ManagerDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
@Component
public class ManagerMapper extends EntityMapper<Actor, ManagerDTO> {

  @Override
  protected ManagerDTO convert(Actor actor) {
    return new ManagerDTO(actor);
  }
}
