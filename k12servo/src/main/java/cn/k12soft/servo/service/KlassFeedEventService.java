package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.domain.MsgRecordStat.MsgType;
import cn.k12soft.servo.domain.enumeration.FeedEventType;
import cn.k12soft.servo.repository.KlassFeedEventRepository;
import cn.k12soft.servo.service.dto.ActorDTO;
import cn.k12soft.servo.service.dto.KlassFeedListDTO;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import cn.k12soft.servo.service.mapper.ActorMapper;
import cn.k12soft.servo.third.aliyun.AliyunPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/27.
 */
@Service
@Transactional
public class KlassFeedEventService extends AbstractEntityService<KlassFeedEvent, Long> {

  private final KlassFeedService feedService;
  private final KlassFeedCommentService feedCommentService;
  private final KlassFeedLikeService feedLikeService;
  private final MsgRecordStatService msgRecordStatService;
  private int feedLimit = 30;
  private AliyunPushService aliyunPushService;
  private StudentService studentService;
  private GuardianService guardianService;
  private ActorMapper actorMapper;

  @Lazy
  @Autowired
  public KlassFeedEventService(KlassFeedEventRepository repository,
                               KlassFeedService feedService,
                               KlassFeedCommentService feedCommentService,
                               KlassFeedLikeService feedLikeService,
                               MsgRecordStatService msgRecordStatService,
                               AliyunPushService aliyunPushService,
                               StudentService studentService,
                               GuardianService guardianService,
                               ActorMapper actorMapper) {
    super(repository);
    this.feedService = feedService;
    this.feedCommentService = feedCommentService;
    this.feedLikeService = feedLikeService;
    this.msgRecordStatService = msgRecordStatService;
    this.aliyunPushService = aliyunPushService;
    this.studentService = studentService;
    this.guardianService = guardianService;
    this.actorMapper = actorMapper;
  }

  public void create(Klass klass, FeedEventType type, Long id) {
    KlassFeedEvent klassFeedEvent = new KlassFeedEvent(
      klass, type, id, Instant.now());
    getEntityRepository().save(klassFeedEvent);

    Map<String, String> queryMap = new HashMap<>();
    queryMap.put("klassId", klass.getId().toString());
    List<Student> studentList = this.studentService.query(queryMap);
    Set<Guardian> guardians = null;
    ActorDTO actorDTO = null;
    String mobiles = "";
    for(Student student : studentList){
        guardians = guardianService.getAllByStudent(student.getId());
        for(Guardian guardian : guardians){
            actorDTO = actorMapper.fromActorId(guardian.getPatriarchId());
            mobiles += actorDTO.getUser().getMobile()+",";
        }
    }

    if(mobiles.length() > 0){
        aliyunPushService.sendKlassFeedNotification(mobiles.substring(0,mobiles.length()-1),klass.getId());

    }

  }

  @Transactional(readOnly = true)
  public KlassFeedListDTO aggregate(Actor actor, long fromEventId) {
    KlassFeedEventRepository entityRepository = getEntityRepository();
    return aggregate(pageable ->
        entityRepository.findAllBySchoolIdAndIdGreaterThanOrderByIdDesc(actor.getSchoolId(), fromEventId, pageable)
      , new PageRequest(0, feedLimit), actor);
  }

  @Transactional(readOnly = true)
  public KlassFeedListDTO aggregate(Actor actor, Set<Klass> klasses, long fromEventId) {
    KlassFeedEventRepository entityRepository = getEntityRepository();
    return aggregate(pageable ->
        entityRepository.findAllByKlassInAndIdGreaterThanOrderByIdDesc(klasses, fromEventId, pageable)
      , new PageRequest(0, feedLimit), actor);
  }

  private KlassFeedListDTO aggregate(Function<Pageable, Page<KlassFeedEvent>> retriever,
                                     Pageable start,
                                     Actor actor) {
    KlassFeedListDTO listDTO = new KlassFeedListDTO();
    for (Page<KlassFeedEvent> page = retriever.apply(start);
      page.hasContent();
      page = retriever.apply(page.nextPageable())) {
      if (!page.hasPrevious()) {
        listDTO.setEventId(page.getContent().get(0).getId());
      }
      for (KlassFeedEvent event : page) {
        aggregate(actor.getSchoolId(), actor.getId(), listDTO, event);
        if (listDTO.getFeeds().size() >= feedLimit) {
          listDTO.setMore(true);
          return listDTO;
        }
      }
      if (!page.hasNext()) {
        return listDTO;
      }
    }
    return listDTO;
  }


  @Transactional
  protected void aggregate(Integer schoolId,
                           Integer userId,
                           KlassFeedListDTO listDTO,
                           KlassFeedEvent event) {
    switch (event.getType()) {
      case CREATED: {
        feedService.findAsDTO(event.getParameter()).ifPresent(listDTO::addFeed);
        msgRecordStatService.recordAndCount(schoolId, MsgType.FEED, event.getKlass().getId(), event.getParameter(), userId);
        return;
      }
      case DELETED: {
        listDTO.getDeletedFeeds().add(event.getParameter());
        return;
      }
      case COMMENTED: {
        feedCommentService.findAsDTO(event.getParameter()).ifPresent(listDTO::addComment);
        return;
      }
      case UNCOMMENTED: {
        listDTO.getDeletedComments().add(event.getParameter());
        return;
      }
      case LIKED: {
        feedLikeService.findAsDTO(event.getParameter()).ifPresent(listDTO::addLike);
        return;
      }
      case UNLIKED: {
        listDTO.getDeletedLikes().add(event.getParameter());
        return;
      }
      default:
    }
  }

  @Override
  protected KlassFeedEventRepository getEntityRepository() {
    return (KlassFeedEventRepository) super.getEntityRepository();
  }
}
