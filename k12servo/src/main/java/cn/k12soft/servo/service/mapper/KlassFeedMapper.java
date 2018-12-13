package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.KlassFeed;
import cn.k12soft.servo.service.dto.KlassFeedDTO;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/3.
 */
@Component
public class KlassFeedMapper {

  private final UserBasicMapper userBasicMapper;

  @Autowired
  public KlassFeedMapper(UserBasicMapper userBasicMapper) {
    this.userBasicMapper = userBasicMapper;
  }

  public KlassFeedDTO toDTO(KlassFeed klassFeed) {
    if (klassFeed == null) {
      return null;
    }
    return new KlassFeedDTO()
      .setId(klassFeed.getId())
      .setKlassId(klassFeed.getKlass().getId())
      .setContent(klassFeed.getContent())
      .setPictures(klassFeed.getPictures())
      .setCreatedBy(userBasicMapper.toDTO(klassFeed.getCreatedBy()))
      .setCreatedAt(klassFeed.getCreatedAt())
      .setKlassName(klassFeed.getKlass().getName());
  }

  public List<KlassFeedDTO> toDTOs(List<KlassFeed> klassFeeds) {
    return klassFeeds.stream().map(this::toDTO)
      .filter(Objects::nonNull)
      .collect(Collectors.toCollection(LinkedList::new));
  }
}
