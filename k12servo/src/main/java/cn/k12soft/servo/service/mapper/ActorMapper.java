package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.service.dto.ActorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ActorMapper extends EntityMapper<Actor, ActorDTO> {

  private final ActorRepository repository;
  private final UserMapper userMapper;

  @Autowired
  public ActorMapper(ActorRepository repository, UserMapper userMapper) {
    this.repository = repository;
    this.userMapper = userMapper;
  }

  @Override
  protected ActorDTO convert(Actor actor) {
    return new ActorDTO()
      .setId(actor.getId())
      .setUser(userMapper.fromUserId(actor.getUserId()))
      .setTypes(actor.getTypes())
      .setJoinedAt(actor.getJoinedAt());
  }

  public ActorDTO fromActorId(Integer actorId) {
    return toDTO(repository.getOne(actorId));
  }
}
