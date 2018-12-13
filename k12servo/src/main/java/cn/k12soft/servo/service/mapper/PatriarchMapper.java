package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.service.dto.PatriarchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatriarchMapper extends EntityMapper<Actor, PatriarchDTO> {

  private final ActorMapper actorMapper;
  private final StudentGuardianMapper studentGuardianMapper;

  @Autowired
  public PatriarchMapper(ActorMapper actorMapper, StudentGuardianMapper studentGuardianMapper) {
    this.actorMapper = actorMapper;
    this.studentGuardianMapper = studentGuardianMapper;
  }

  @Override
  protected PatriarchDTO convert(Actor actor) {
    Integer patriarchId = actor.getId();
    return new PatriarchDTO()
      .setId(patriarchId)
      .setActor(actorMapper.toDTO(actor))
      .setGuardians(studentGuardianMapper.fromPatriarchId(patriarchId));
  }

  public PatriarchDTO fromPatriarchId(Integer patriarchId) {
    return new PatriarchDTO().setId(patriarchId)
      .setActor(actorMapper.fromActorId(patriarchId))
      .setGuardians(studentGuardianMapper.fromPatriarchId(patriarchId));
  }
}
