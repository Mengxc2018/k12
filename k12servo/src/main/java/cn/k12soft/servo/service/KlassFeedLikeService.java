package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.KlassFeed;
import cn.k12soft.servo.domain.KlassFeedLike;
import cn.k12soft.servo.domain.enumeration.FeedEventType;
import cn.k12soft.servo.repository.KlassFeedLikeRepository;
import cn.k12soft.servo.service.dto.KlassFeedLikeDTO;
import cn.k12soft.servo.service.mapper.KlassFeedLikeMapper;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/27.
 */
@Service
@Transactional
public class KlassFeedLikeService extends AbstractEntityService<KlassFeedLike, Long> {

  private final KlassFeedService klassFeedService;
  private final KlassFeedEventService eventService;
  private final KlassFeedLikeMapper mapper;

  @Autowired
  public KlassFeedLikeService(KlassFeedLikeRepository entityRepository,
                              KlassFeedService klassFeedService,
                              KlassFeedEventService eventService,
                              KlassFeedLikeMapper mapper) {
    super(entityRepository);
    this.klassFeedService = klassFeedService;
    this.eventService = eventService;
    this.mapper = mapper;
  }

  public KlassFeedLikeDTO like(Actor actor, Long feedId) {
    KlassFeed feed = klassFeedService.get(feedId);
    KlassFeedLike like = new KlassFeedLike(feed, actor);
    like = getEntityRepository().save(like);
    eventService.create(feed.getKlass(), FeedEventType.LIKED, like.getId());
    return mapper.toDTO(like);
  }

  public void unlike(Actor actor, Long likeId) {
    find(likeId).ifPresent(like -> {
      if (!Objects.equals(actor, like.getUser())) {
        throw new IllegalArgumentException("No liked by myself");
      }
      getEntityRepository().delete(like);
      Klass klass = like.getFeed().getKlass();
      eventService.create(klass, FeedEventType.UNLIKED, likeId);
    });
  }

  public Optional<KlassFeedLikeDTO> findAsDTO(Long id) {
    return find(id).map(mapper::toDTO);
  }
}
