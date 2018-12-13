package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.dto.UserDTO;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@Component
@Transactional(readOnly = true)
public class UserMapper{

  private final UserRepository repository;
  private final SchoolMapper schoolMapper;
  private final ActorRepository actorRepository;

  @Autowired
  public UserMapper(UserRepository repository, SchoolMapper schoolMapper, ActorRepository actorRepository) {
    this.repository = repository;
    this.schoolMapper = schoolMapper;
    this.actorRepository = actorRepository;
  }

  private static ActorType PATRIARCH = ActorType.PATRIARCH; // 0 家长
  private static ActorType TEACHER = ActorType.TEACHER;     // 1 教师
  private static ActorType MANAGER = ActorType.MANAGER;     // 2 学校
  private static ActorType CITY = ActorType.CITY;           // 3 市级
  private static ActorType PROVINCE = ActorType.PROVINCE;   // 4 省级
  private static ActorType REGION = ActorType.REGION;       // 5 大区
  private static ActorType GROUP = ActorType.GROUP;         // 6 集团

  public UserDTO toDTO(User user) {
    // 获取该用户的最高角色权限类型
    List<Actor> actors = actorRepository.findByUserId(user.getId());
    Collection<ActorType> actorTypeColl = new ArrayList<ActorType>();
    int index = 0;
    for (int i = 0; i < actors.size(); i++){
      // 获取actor的角色
        Set<ActorType> types = actors.get(i).getTypes();
        Iterator<ActorType> ati = types.iterator();

        while (ati.hasNext()){
            ActorType actorType = ati.next();
            int j = actorType.ordinal();
            if (j >= index){
                index = j;
                actorTypeColl.removeAll(actorTypeColl);
                actorTypeColl.add(actorType);
            }
        }
    }
      return new UserDTO()
      .setId(user.getId())
      .setMobile(user.getMobile())
      .setUsername(user.getUsername())
      .setAvatar(user.getAvatar())
      .setPortrait(user.getPortrait())
      .setCreatedAt(user.getCreatedAt())
      .setGender(user.getGender())
      .setActorType(actorTypeColl)
      .setSchools(schoolMapper.fromSchoolIds(user.getActors().stream().map(Actor::getSchoolId).collect(Collectors.toList())));
  }

    public UserDTO toDTO(User user, String token) {
    return toDTO(user).setToken(token);
  }

    public Collection<UserDTO> toDTOs(Collection<User> entities) {
        return entities.stream()
                .map(this::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedList::new));
    }

  public UserDTO fromUserId(Integer userId) {
    return toDTO(repository.getOne(userId));
  }
}
