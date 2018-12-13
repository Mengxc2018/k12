package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.KlassFeed;
import cn.k12soft.servo.domain.KlassFeedComment;
import cn.k12soft.servo.domain.enumeration.FeedEventType;
import cn.k12soft.servo.repository.KlassFeedCommentRepository;
import cn.k12soft.servo.service.dto.KlassFeedCommentDTO;
import cn.k12soft.servo.service.mapper.KlassFeedCommentMapper;
import cn.k12soft.servo.web.form.KlassFeedCommentForm;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KlassFeedCommentService extends AbstractEntityService<KlassFeedComment, Long> {

  private final KlassFeedService klassFeedService;
  private final KlassFeedEventService eventService;
  private final KlassFeedCommentMapper mapper;

  @Autowired
  public KlassFeedCommentService(KlassFeedService klassFeedService,
                                 KlassFeedCommentRepository commentRepository,
                                 KlassFeedEventService eventService,
                                 KlassFeedCommentMapper mapper) {
    super(commentRepository);
    this.klassFeedService = klassFeedService;
    this.eventService = eventService;
    this.mapper = mapper;
  }

  public KlassFeedCommentDTO create(Actor createdBy,
                                    KlassFeedCommentForm form) {
    KlassFeed feed = klassFeedService.get(form.getFeedId());
    KlassFeedComment comment = new KlassFeedComment(feed, form.getContent(), createdBy);
    if (form.getReplyTo() != null) {
      KlassFeedComment replyTo = get(form.getReplyTo());
      comment.setReplyTo(replyTo.getCreatedBy());
    }
    comment = getEntityRepository().save(comment);
    eventService.create(feed.getKlass(), FeedEventType.COMMENTED, comment.getId());
    return mapper.toDTO(comment);
  }

  public void delete(Actor actor, Long id) {
    find(id).ifPresent(comment -> {
      if (!Objects.equals(actor, comment.getCreatedBy())) {
        throw new IllegalArgumentException("Not created myself");
      }
      getEntityRepository().delete(comment);
      Klass klass = comment.getFeed().getKlass();
      eventService.create(klass, FeedEventType.UNCOMMENTED, id);
    });
  }

  public Optional<KlassFeedCommentDTO> findAsDTO(Long id) {
    return find(id).map(mapper::toDTO);
  }
}
