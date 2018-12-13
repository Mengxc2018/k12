package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.KlassFeedLike;
import cn.k12soft.servo.service.dto.KlassFeedLikeDTO;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/20.
 */
@Component
public class KlassFeedLikeMapper {

  private final UserBasicMapper userBasicMapper;

  @Autowired
  public KlassFeedLikeMapper(UserBasicMapper userBasicMapper) {
    this.userBasicMapper = userBasicMapper;
  }

  public KlassFeedLikeDTO toDTO(KlassFeedLike like) {
    return new KlassFeedLikeDTO()
      .setId(like.getId())
      .setFeedId(like.getFeed().getId())
      .setLikedBy(userBasicMapper.toDTO(like.getUser()))
      .setLikedAt(like.getCreatedAt());
  }

  public List<KlassFeedLikeDTO> toDTO(Collection<KlassFeedLike> likes) {
    return likes.stream().map(this::toDTO).collect(Collectors.toCollection(LinkedList::new));
  }
}
