package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.domain.MsgRecordStat.MsgType;
import cn.k12soft.servo.domain.enumeration.PlanEventType;
import cn.k12soft.servo.domain.enumeration.PlanType;
import cn.k12soft.servo.repository.KlassPlanEventRepository;
import cn.k12soft.servo.repository.KlassPlanRepository;
import cn.k12soft.servo.service.dto.ActorDTO;
import cn.k12soft.servo.service.dto.KlassPlanListDTO;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.k12soft.servo.service.mapper.ActorMapper;
import cn.k12soft.servo.third.aliyun.AliyunPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/10/17.
 */
@Service
public class KlassPlanEventService extends AbstractEntityService<KlassPlanEvent, Integer> {

  private final KlassPlanRepository planRepository;
  private final MsgRecordStatService msgRecordStatService;

  @Autowired
  public KlassPlanEventService(KlassPlanEventRepository entityRepository,
                               KlassPlanRepository planRepository,
                               MsgRecordStatService msgRecordStatService) {
    super(entityRepository);
    this.planRepository = planRepository;
    this.msgRecordStatService = msgRecordStatService;
  }

  @Transactional(readOnly = true)
  public KlassPlanListDTO aggregate(Actor user, Long fromEventId) {
    List<KlassPlanEvent> events = getEntityRepository()
      .findAllBySchoolIdAndIdGreaterThan(user.getSchoolId(), fromEventId);
    return aggregate(user, events);
  }

  public KlassPlanListDTO aggregate(Actor user, Integer klassId, PlanType planType, Long eventId) {
    List<KlassPlanEvent> events = getEntityRepository()
      .findAllByKlass_IdAndPlanTypeAndIdGreaterThan(klassId, planType, eventId);
    return aggregate(user, events);
  }

  @Transactional(readOnly = true)
  public KlassPlanListDTO aggregate(Actor user, Set<Klass> klasses, Long fromEventId) {
    List<KlassPlanEvent> events = getEntityRepository()
      .findAllByKlassInAndIdGreaterThan(klasses, fromEventId);
    return aggregate(user, events);
  }

  private KlassPlanListDTO aggregate(Actor user, List<KlassPlanEvent> events) {
    KlassPlanListDTO listDTO = new KlassPlanListDTO();
    events.forEach(event -> aggregate(user.getSchoolId(), user.getId(), listDTO, event));
    return listDTO;
  }

  @Transactional
  protected void aggregate(Integer schoolId,
                         Integer userId,
                         KlassPlanListDTO listDTO,
                         KlassPlanEvent event) {
    listDTO.setEventId(event.getId());
    switch (event.getType()) {
      case CREATED: {
        listDTO.addCreated(planRepository.findOne(event.getParameter()));
        msgRecordStatService.recordAndCount(schoolId, MsgType.PLAN, event.getKlass().getId(), event.getParameter().longValue(), userId);

        return;
      }
      case DELETED: {
        listDTO.addDeleted(event.getParameter());
        return;
      }
      case UPDATED: {
        listDTO.addUpdated(planRepository.findOne(event.getParameter()));
        return;
      }
      default:
    }
  }

  public Map<PlanType, Integer> countUnread(Integer klassId, Long eventId) {
    KlassPlanEventRepository entityRepository = getEntityRepository();
    return Arrays.stream(PlanType.values())
      .collect(Collectors.toMap(Function.identity(), type ->
        entityRepository.countAllByKlass_IdAndTypeAndPlanTypeAndIdGreaterThan(klassId, PlanEventType.CREATED, type, eventId)
      ));
  }

  @Override
  protected KlassPlanEventRepository getEntityRepository() {
    return (KlassPlanEventRepository) super.getEntityRepository();
  }
}
