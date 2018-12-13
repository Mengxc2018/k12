package cn.k12soft.servo.module.duty.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.TRUE_FALSE;
import cn.k12soft.servo.module.duty.domain.Duty;
import cn.k12soft.servo.module.duty.domain.dto.DutyDTO;
import cn.k12soft.servo.module.duty.domain.form.DutyForm;
import cn.k12soft.servo.module.duty.repositpry.DutyRepository;
import cn.k12soft.servo.module.duty.service.mapper.DutyMapper;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

@Service
@Transactional
public class DutyService extends AbstractRepositoryService<Duty, Long, DutyRepository> {

    private final DutyMapper dutyMapper;

    @Autowired
    protected DutyService(DutyRepository repository, DutyMapper dutyMapper) {
        super(repository);
        this.dutyMapper = dutyMapper;
    }

    public List<DutyDTO> create(Actor actor, List<DutyForm> forms) {
        Integer schoolId = actor.getSchoolId();
        List<DutyDTO> list = new ArrayList<>();
        for (DutyForm form : forms){
            // 判断name是否为空
            if (StringUtils.isEmpty(form.getName())){
                throw new IllegalArgumentException("属性name不能为空");
            }
            // 判断name是否重复
            Duty dutyByName = getRepository().findByName(form.getName());
            if (dutyByName != null){
                throw new IllegalArgumentException("当前职务 + [" + form.getName() + "] + 已存在！");
            }
            Duty duty = new Duty(
                    form.getName(),
                    schoolId,
                    form.getIsSubstratum()
            );
            list.add(dutyMapper.toDTO(getRepository().save(duty)));
        }
        return list;
    }

    public DutyDTO update(Actor actor, DutyForm form, Integer dutyId) {
        Integer schoolId = actor.getSchoolId();
        Duty duty = new Duty();
        if (!isNullOrEmpty(form.getName())) {
            duty.setName(form.getName());
        }
        if(!isNullOrEmpty(schoolId.toString())){
            duty.setSchoolId(schoolId);
        }
        duty.setIsSubstratum(form.getIsSubstratum());
        duty.setId(new Long(dutyId));
        return dutyMapper.toDTO(getRepository().save(duty));
    }

    public Collection<DutyDTO> query(Actor actor, TRUE_FALSE isSubstratum) {
        return dutyMapper.toDTOs(getRepository().findBySchoolIdAndIsSubstratum(actor.getSchoolId(), isSubstratum));
    }

    public Collection<DutyDTO> queryUnSunstratum(Actor actor) {
        Integer schoolId = actor.getSchoolId();
        return dutyMapper.toDTOs(getRepository().findBySchoolId(schoolId));
    }
}
