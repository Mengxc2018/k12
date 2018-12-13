package cn.k12soft.servo.module.scheduling.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.scheduling.repository.SchedulingRepository;
import cn.k12soft.servo.module.scheduling.domain.Scheduling;
import cn.k12soft.servo.module.scheduling.domain.form.SchedulingForm;
import cn.k12soft.servo.module.scheduling.domain.dto.SchedulingDTO;
import cn.k12soft.servo.module.scheduling.service.mapper.SchedulingMapper;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class SchedulingService extends AbstractRepositoryService<Scheduling, Long, SchedulingRepository> {

    private final SchedulingMapper mapper;

    @Autowired
    public SchedulingService(SchedulingMapper mapper, SchedulingRepository repository) {
        super(repository);
        this.mapper = mapper;
    }

    public SchedulingDTO create(Actor actor, SchedulingForm form) {
        Integer schoolId = actor.getSchoolId();

        Scheduling scheduling = new Scheduling(
                schoolId,
                form.getName(),
                LocalTime.parse(form.getAmStartTime(), DateTimeFormatter.ISO_LOCAL_TIME),
                LocalTime.parse(form.getAmEndTime(), DateTimeFormatter.ISO_LOCAL_TIME),
                LocalTime.parse(form.getPmStartTime(), DateTimeFormatter.ISO_LOCAL_TIME),
                LocalTime.parse(form.getPmEndTime(), DateTimeFormatter.ISO_LOCAL_TIME),
                Instant.now(),
                null,
                form.getSchedulingType()
        );
        return mapper.toDTO(getRepository().save(scheduling));
    }

    public SchedulingDTO update(Actor actor,SchedulingForm form, long id) {
        Scheduling scheduling = get(id);
        String type = scheduling.getSchedulingType();
        if (type.equals("1")){
            if (!isNullOrEmpty(form.getAmEndTime())) {
                scheduling.setAmEndTime(LocalTime.parse(form.getAmEndTime()));
            }
            if (!isNullOrEmpty(form.getPmStartTime())) {
                scheduling.setPmStartTime(LocalTime.parse(form.getPmStartTime()));
            }
        }else if (type.equals("2")){
            if (!isNullOrEmpty(form.getAmEndTime())) {
                scheduling.setAmEndTime(null);
            }
            if (!isNullOrEmpty(form.getPmStartTime())) {
                scheduling.setPmStartTime(null);
            }
        }
        if (!isNullOrEmpty(form.getName())) {
            scheduling.setName(form.getName());
        }
        if (!isNullOrEmpty(form.getAmStartTime())) {
            scheduling.setAmStartTime(LocalTime.parse(form.getAmStartTime()));
        }
        if (!isNullOrEmpty(form.getPmEndTime())) {
            scheduling.setPmEndTime(LocalTime.parse(form.getPmEndTime()));
        }
        scheduling.setUpdatedDate(Instant.now());
        scheduling = getRepository().save(scheduling);
        return mapper.toDTO(scheduling);
    }

    /**
     * 按学校查询所有排班的时间
     * @param actor
     * @return
     */
    public Collection<SchedulingDTO> getAllBySchoolId(Actor actor) {
        Integer schoolId = actor.getSchoolId();
        SchedulingRepository repository = getRepository();
        Collection<SchedulingDTO> list = mapper.toDTOs(repository.findBySchoolId(schoolId));
        return list;
    }

    public Collection<SchedulingDTO> queryByType(Actor actor, String schedulingType) {
        Integer schoolId = actor.getSchoolId();
        return mapper.toDTOs(getRepository().findBySchedulingTypeAndSchoolId(schedulingType, schoolId));
    }


    public void deleteOne(Long id) {
        Scheduling scheduling = getRepository().findOne(id);
        if (scheduling == null){
            throw new IllegalArgumentException("要删除的内容不存在");
        }
        getRepository().delete(scheduling.getId());
    }
}