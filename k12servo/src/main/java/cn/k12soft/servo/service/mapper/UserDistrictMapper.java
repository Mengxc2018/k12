package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.module.district.repository.CitysRepository;
import cn.k12soft.servo.module.district.repository.GroupsRepository;
import cn.k12soft.servo.module.district.repository.ProvincesRepository;
import cn.k12soft.servo.module.district.repository.RegionsRepository;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.module.zone.domain.Groups;
import cn.k12soft.servo.module.zone.domain.Provinces;
import cn.k12soft.servo.module.zone.domain.Regions;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.dto.UserDistrictDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.k12soft.servo.domain.enumeration.ActorType.*;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@Component
@Transactional(readOnly = true)
public class UserDistrictMapper {

  private final UserRepository repository;
  private final SchoolMapper schoolMapper;
  private final ActorRepository actorRepository;
  private final CitysRepository citysRepository;
  private final ProvincesRepository provincesRepository;
  private final RegionsRepository regionsRepository;
  private final GroupsRepository groupsRepository;

  @Autowired
  public UserDistrictMapper(UserRepository repository, SchoolMapper schoolMapper, ActorRepository actorRepository, CitysRepository citysRepository, ProvincesRepository provincesRepository, RegionsRepository regionsRepository, GroupsRepository groupsRepository) {
    this.repository = repository;
    this.schoolMapper = schoolMapper;
    this.actorRepository = actorRepository;
      this.citysRepository = citysRepository;
      this.provincesRepository = provincesRepository;
      this.regionsRepository = regionsRepository;
      this.groupsRepository = groupsRepository;
  }

  public UserDistrictDTO toDTO(User user) {
    // 获取该用户的最高角色权限类型
    List<Actor> actors = actorRepository.findByUserId(user.getId());
    Set<ActorType> actorTypes = null;
    Actor actor = new Actor();
      int index = 0;
      for (int i = 0; i < actors.size(); i++){

        // 获取actor的角色
        Set<ActorType> types = actors.get(i).getTypes();
        ActorType actorType = types.iterator().next();
        int j = actorType.ordinal();
        if (j >= index){
            index = j;
            actorTypes = actors.get(i).getTypes();
            actor = actors.get(i);
        }
    }
      ActorType actorType = null;
    for (ActorType actorType1 : actorTypes){
        actorType = actorType1;
    }

    String codeStr = "";
          switch (actorType) {
              case CITY:
                  Citys citys = citysRepository.findById(Integer.valueOf(actor.getCityId()));
                  codeStr = citys.getCode();
                  break;
              case PROVINCE:
                  Provinces provinces = provincesRepository.findById(Integer.valueOf(actor.getProvinceId()));
                  codeStr = provinces.getCode();
                  break;
              case REGION:
                  Regions regions = regionsRepository.findById(Integer.valueOf(actor.getRegionId()));
                  codeStr = regions.getCode();
                  break;
              case GROUP:
                  Groups groups = groupsRepository.findById(Integer.valueOf(actor.getGroupId()));
                  codeStr = groups.getCode();
                  break;
              default:
                  //do nothing
          }

      return new UserDistrictDTO()
      .setId(user.getId())
      .setMobile(user.getMobile())
      .setUsername(user.getUsername())
      .setAvatar(user.getAvatar())
      .setPortrait(user.getPortrait())
      .setCreatedAt(user.getCreatedAt())
      .setGender(user.getGender())
      .setActorType(actorTypes)
      .setCode(codeStr)
      .setSchools(schoolMapper.fromSchoolIds(user.getActors().stream().map(Actor::getSchoolId).collect(Collectors.toList())));
  }

  public UserDistrictDTO toDTO(User user, String token) {
    return toDTO(user).setToken(token);
  }

  public UserDistrictDTO fromUserId(Integer userId) {
    return toDTO(repository.getOne(userId));
  }
}
