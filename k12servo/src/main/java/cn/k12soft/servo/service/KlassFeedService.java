package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.KlassFeed;
import cn.k12soft.servo.domain.enumeration.FeedEventType;
import cn.k12soft.servo.module.wxLogin.service.WxService;
import cn.k12soft.servo.repository.KlassFeedRepository;
import cn.k12soft.servo.service.dto.KlassFeedDTO;
import cn.k12soft.servo.service.mapper.KlassFeedMapper;
import cn.k12soft.servo.util.Times;
import cn.k12soft.servo.web.form.KlassFeedForm;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KlassFeedService extends AbstractEntityService<KlassFeed, Long> {

  private final KlassService klassService;
  private final KlassFeedEventService feedEventService;
  private final KlassFeedMapper klassFeedMapper;
  private final TeachingService teachingService;
  private final WxService wxService;

  @Autowired
  public KlassFeedService(KlassFeedRepository feedRepository,
                          KlassService klassService,
                          KlassFeedEventService feedEventService,
                          KlassFeedMapper klassFeedMapper,
                          TeachingService teachingService,
                          WxService wxService) {
    super(feedRepository);
    this.klassService = klassService;
    this.feedEventService = feedEventService;
    this.klassFeedMapper = klassFeedMapper;
    this.teachingService = teachingService;
      this.wxService = wxService;
  }

  private static final Logger log = LoggerFactory.getLogger(KlassFeedService.class);

  public KlassFeedDTO create(Actor actor, KlassFeedForm form) {
    Klass klass = klassService.get(form.getKlassId());
    KlassFeed klassFeed = new KlassFeed(klass, form.getContent(), form.getPictures(), actor);
    klassFeed = getEntityRepository().save(klassFeed);
    feedEventService.create(klass, FeedEventType.CREATED, klassFeed.getId());

    Map<String, String> map = new HashMap<>();
    map.put("msg", "您家宝宝有新的班级动态,快来看看吧");
    map.put("type", "dynamic");
    // 为了缩短时间，采用异步
    ExecutorService executor = Executors.newCachedThreadPool();
    Future<Double> future =executor.submit(new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        wxService.sendWxKlassPlan(actor, null, klass.getId(), map);
        return null;
      }
    });

    return klassFeedMapper.toDTO(klassFeed);
  }

  @Transactional(readOnly = true)
  public int countUnread(Integer klassId, Long eventId) {
    return getEntityRepository().countAllByKlass_IdAndIdGreaterThan(klassId, eventId);
  }

  @Override
  public void delete(Long id) {
    KlassFeed klassFeed = getEntityRepository().findOne(id);
    if (klassFeed != null) {
      delete(klassFeed);
    }
  }

  private void delete(KlassFeed klassFeed) {
    getEntityRepository().delete(klassFeed);
    Klass klass = klassFeed.getKlass();
    feedEventService.create(klass, FeedEventType.DELETED, klassFeed.getId());
  }

  public void userDelete(Actor user, Long feedId) {
    find(feedId).ifPresent(feed -> {
      if (teachingService.isUserTeaching(user, feed.getKlass())) {
        delete(feed);
      }
    });
  }

  @Override
  protected KlassFeedRepository getEntityRepository() {
     return (KlassFeedRepository) super.getEntityRepository();
  }

  public Optional<KlassFeedDTO> findAsDTO(Long id) {
    return find(id).map(klassFeedMapper::toDTO);
  }

  public List<KlassFeedDTO> findAllByMonth(Actor actor, Integer klassId, LocalDate localDate) {
    Integer schoolId = actor.getSchoolId();
    Instant first = Times.getFirstAndSecond(localDate).getFirst();
    Instant second = Times.getFirstAndSecond(localDate).getSecond();
    Klass klass = this.klassService.find(klassId).get();
    return klassFeedMapper.toDTOs(this.getEntityRepository().findAllBySchoolIdAndKlassAndCreatedAtBetween(schoolId, klass, first, second));
  }
}
