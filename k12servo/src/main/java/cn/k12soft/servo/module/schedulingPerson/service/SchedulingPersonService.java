package cn.k12soft.servo.module.schedulingPerson.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.scheduling.domain.Scheduling;
import cn.k12soft.servo.module.scheduling.domain.dto.SchedulingDTO;
import cn.k12soft.servo.module.scheduling.service.SchedulingService;
import cn.k12soft.servo.module.schedulingPerson.domian.SchedulingPerson;
import cn.k12soft.servo.module.schedulingPerson.domian.dto.SchedulingPersonDTO;
import cn.k12soft.servo.module.schedulingPerson.domian.form.SchedulingPersonForm;
import cn.k12soft.servo.module.schedulingPerson.mapper.SchedulingPersonMapper;
import cn.k12soft.servo.module.schedulingPerson.repository.SchedulingPersonRepository;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.service.UserService;
import org.springframework.stereotype.Service;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SchedulingPersonService extends AbstractRepositoryService<SchedulingPerson, Long, SchedulingPersonRepository> {
    private final UserService userSerivce;
    private final ActorRepository actorRepository;
    private final SchedulingPersonMapper mapper;
    private final SchedulingService schedulingService;

    protected SchedulingPersonService(SchedulingPersonRepository repository, SchedulingPersonMapper mapper, UserService userSerivce, ActorRepository actorRepository, SchedulingService schedulingService) {
        super(repository);
        this.mapper = mapper;
        this.userSerivce = userSerivce;
        this.actorRepository = actorRepository;
        this.schedulingService = schedulingService;
    }

    public void create(Actor actor, SchedulingPersonForm form) {
        String[] ids = form.getActorIds().split(",");
        for (String id : ids){
            Actor actor1 = actorRepository.getOne(Integer.valueOf(id));
            User user = userSerivce.get(actor1.getUserId());

            // 去重
            Integer num = getRepository().countByActorId(actor1.getId());
            if (num > 0){
                throw new IllegalArgumentException("[ " + user.getUsername() + " ] 已分配过排班,请重新安排");
            }

            Scheduling scheduling = schedulingService.get((long)form.getSchedulingId());
            SchedulingPerson schedulingPerson = new SchedulingPerson(
                    actor1.getId(),
                    actor.getSchoolId(),
                    user.getUsername(),
                    scheduling,
                    Instant.now(),
                    null
            );
            mapper.toDTO(getRepository().save(schedulingPerson));
        }
    }

    /**
     * 目前不可批量修改
     * @param form
     * @param schedulingPersonId
     * @return
     */
    public List<SchedulingPersonDTO> updated(SchedulingPersonForm form, Integer schedulingPersonId) {
        List<SchedulingPersonDTO> list = new ArrayList<SchedulingPersonDTO>();
        String[] ids = form.getActorIds().split(",");
        for(String id : ids){
            Actor actor = actorRepository.getOne(Integer.valueOf(id));
            User user = userSerivce.get(actor.getUserId());
            Scheduling scheduling = schedulingService.get((long)form.getSchedulingId());
            SchedulingPerson schedulingPerson = getRepository().getOne((long) schedulingPersonId);
            if (!isNullOrEmpty(String.valueOf(form.getSchedulingId()))){
                schedulingPerson.setScheduling(scheduling);
            }
            if(!isNullOrEmpty(user.getUsername())){
                schedulingPerson.setUserName(user.getUsername());
            }
            if(!isNullOrEmpty(String.valueOf(Integer.valueOf(actor.getId())))){
                schedulingPerson.setActorId(actor.getId());
            }
            schedulingPerson.setUpdatedAt(Instant.now());

            list.add(mapper.toDTO(getRepository().save(schedulingPerson)));
        }
        return list;
    }

    public SchedulingPerson getByUserId(Integer actorId) {
        return getRepository().findByActorId(actorId);
    }

    public Collection<SchedulingPersonDTO> getBySchoolId(Actor actor) {
        Integer schoolId = actor.getSchoolId();
        return mapper.toDTOs(getRepository().findBySchoolId(schoolId));
    }

    public Collection<SchedulingPersonDTO> getAllByActorIdAndSchoolId(Actor actor) {
        Integer schoolId = actor.getSchoolId();
        Integer actorId = actor.getId();
        return mapper.toDTOs(getRepository().findBySchoolIdAndActorId(schoolId, actorId));
    }
}

