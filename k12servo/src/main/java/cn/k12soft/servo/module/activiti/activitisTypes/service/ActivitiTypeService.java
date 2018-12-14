package cn.k12soft.servo.module.activiti.activitisTypes.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.activiti.activitisTypes.domain.ActivitisTypes;
import cn.k12soft.servo.module.activiti.activitisTypes.domain.dto.ActivitiTypeDTO;
import cn.k12soft.servo.module.activiti.activitisTypes.domain.form.ActivitiTypeForm;
import cn.k12soft.servo.module.activiti.activitisTypes.repository.ActivitiTypeRepository;
import cn.k12soft.servo.module.activiti.activitisTypes.service.mapper.ActivitiTypeMapper;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Service
public class ActivitiTypeService extends AbstractRepositoryService<ActivitisTypes, Long, ActivitiTypeRepository>{

    private final ActivitiTypeMapper activitiTypeMapper;
    @Autowired
    public ActivitiTypeService(ActivitiTypeRepository repository, ActivitiTypeMapper activitiTypeMapper) {
        super(repository);
        this.activitiTypeMapper = activitiTypeMapper;
    }

    public ActivitiTypeDTO create(ActivitiTypeForm form, Actor actor) {

        Integer schoolId = actor.getSchoolId();

        Long no = getRepository().findMaxByTypeNo();

        if (no == null){
            no = new Long(0);
        }
        no = no + 1;

        ActivitisTypes activitiType = new ActivitisTypes(
                form.getName(),
                no,
                schoolId,
                Instant.now()
        );
        return activitiTypeMapper.toDTO(getRepository().save(activitiType));
    }

    public Collection<ActivitiTypeDTO> query(Integer school) {
        Integer schoolId = school;
        return activitiTypeMapper.toDTOs(getRepository().findBySchoolId(schoolId));
    }
}
