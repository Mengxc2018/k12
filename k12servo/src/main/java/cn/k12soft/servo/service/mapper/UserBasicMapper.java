package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.dto.UserBasicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/3.
 */
@Component
public class UserBasicMapper {

  private final UserRepository userRepository;

  @Autowired
  public UserBasicMapper(UserRepository userRepository) {this.userRepository = userRepository;}

  public UserBasicDTO toDTO(Actor actor) {
    if (actor == null) {
      return null;
    }
    User user = userRepository.getOne(actor.getUserId());
    return new UserBasicDTO()
      .setUserId(user.getId())
      .setAvatar(user.getAvatar())
      .setUsername(user.getUsername());
  }
}
