package cn.k12soft.servo.service;

import static com.google.common.base.Strings.isNullOrEmpty;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.domain.enumeration.PlanEventType;
import cn.k12soft.servo.domain.enumeration.PlanType;
import cn.k12soft.servo.module.wxLogin.service.WxService;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.KlassPlanRepository;
import cn.k12soft.servo.service.dto.ActorDTO;
import cn.k12soft.servo.service.mapper.ActorMapper;
import cn.k12soft.servo.third.aliyun.AliyunPushService;
import cn.k12soft.servo.web.form.KlassPlanForm;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KlassPlanService {

  private final KlassPlanRepository repository;
  private final KlassService klassService;
  private final KlassPlanEventService planEventService;
  private AliyunPushService aliyunPushService;
  private StudentService studentService;
  private GuardianService guardianService;
  private ActorMapper actorMapper;

  @Autowired
  public KlassPlanService(KlassPlanRepository repository,
                          KlassService klassService,
                          KlassPlanEventService planEventService,
                          AliyunPushService aliyunPushService,
                          StudentService studentService,
                          GuardianService guardianService,
                          ActorMapper actorMapper) {
    this.repository = repository;
    this.klassService = klassService;
    this.planEventService = planEventService;
    this.aliyunPushService = aliyunPushService;
    this.studentService = studentService;
    this.guardianService = guardianService;
    this.actorMapper = actorMapper;
  }

  public KlassPlan create(Actor createdBy, KlassPlanForm form) {
    Klass klass = klassService.get(form.getKlassId());
    PlanType planType = form.getType();
    KlassPlan klassPlan = new KlassPlan(klass, planType, form.getTitle(), form.getContent(), createdBy);
    klassPlan = repository.save(klassPlan);
    KlassPlanEvent event = new KlassPlanEvent(klass, PlanEventType.CREATED, planType, klassPlan.getId(), Instant.now());
    planEventService.save(event);

    Set<Guardian> guardians = null;
    ActorDTO actorDTO = null;
    int klassId = event.getKlass().getId();
    Map<String, String> queryMap = new HashMap<>();
    queryMap.put("klassId", String.valueOf(klassId));
    List<Student> studentList = this.studentService.query(queryMap);
    String mobiles = "";
    for(Student student : studentList){
      guardians = guardianService.getAllByStudent(student.getId());
      for(Guardian guardian : guardians){
        actorDTO = actorMapper.fromActorId(guardian.getPatriarchId());
        mobiles += actorDTO.getUser().getMobile()+",";
      }
    }

    if(mobiles.length() > 0){
      aliyunPushService.sendKlassPlan(createdBy, klassPlan, mobiles.substring(0,mobiles.length()-1),klassId,planType);    // 阿里云推送消息
    }

    return klassPlan;
  }

  public KlassPlan update(Integer planId,
                          KlassPlanForm form) {
    KlassPlan klassPlan = repository.findOne(planId);
    if (!isNullOrEmpty(form.getTitle())) {
      klassPlan.title(form.getTitle());
    }
    if (!isNullOrEmpty(form.getContent())) {
      klassPlan.content(form.getContent());
    }
    KlassPlanEvent event = new KlassPlanEvent(klassPlan.getKlass(), PlanEventType.UPDATED,
      klassPlan.getPlanType(),
      klassPlan.getId(), Instant.now());
    planEventService.save(event);
    return repository.save(klassPlan);
  }

  @Transactional(readOnly = true)
  public KlassPlan getKlassPlan(Integer id) {
    return repository.findOne(id);
  }

  @Transactional(readOnly = true)
  public List<KlassPlan> findAllByKlassAndType(Integer klassId, PlanType type) {
    Klass klass = new Klass(klassId);
    return repository.findAllByKlassAndPlanType(klass, type);
  }

  @Transactional(readOnly = true)
  public List<KlassPlan> findAllByKlassAndTypeAndFrom(Integer klassId,
                                                      PlanType planType,
                                                      Instant fromDate) {
    Klass klass = new Klass(klassId);
    return repository.findAllByKlassAndPlanTypeAndUpdatedAtAfter(klass, planType, fromDate);
  }

  public void delete(Integer planId) {
    KlassPlan klassPlan = getKlassPlan(planId);
    KlassPlanEvent event = new KlassPlanEvent(klassPlan.getKlass(), PlanEventType.DELETED,
      klassPlan.getPlanType(),
      klassPlan.getId(), Instant.now());
    planEventService.save(event);
    repository.delete(planId);
  }
}
