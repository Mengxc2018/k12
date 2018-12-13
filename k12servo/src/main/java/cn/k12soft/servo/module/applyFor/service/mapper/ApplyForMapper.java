package cn.k12soft.servo.module.applyFor.service.mapper;

import cn.k12soft.servo.module.VacationTeacher.domain.VacationTeacher;
import cn.k12soft.servo.module.VacationTeacher.repository.VacationTeacherRepository;
import cn.k12soft.servo.module.applyFor.domain.ApplyFor;
import cn.k12soft.servo.module.applyFor.domain.dto.ApplyForDTO;
import cn.k12soft.servo.module.applyFor.domain.dto.ApplyForNowDTO;
import cn.k12soft.servo.module.applyFor.repository.ApplyForRepository;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplyForMapper extends EntityMapper<ApplyFor, ApplyForDTO>{

    private final ApplyForRepository applyForRepository;
    private final ApplyForNowMapper applyForNowMapper;
    private final VacationTeacherRepository vacationTeacherRepository;
    private final MassageMapper massageMapper;

    @Autowired
    public ApplyForMapper(ApplyForRepository applyForRepository, ApplyForNowMapper applyForNowMapper, VacationTeacherRepository vacationTeacherRepository, MassageMapper massageMapper) {
        this.applyForRepository = applyForRepository;
        this.applyForNowMapper = applyForNowMapper;
        this.vacationTeacherRepository = vacationTeacherRepository;
        this.massageMapper = massageMapper;
    }

    @Override
    protected ApplyForDTO convert(ApplyFor applyFor) {

        ApplyForNowDTO applyForNowDTO = new ApplyForNowDTO();

        // 查询当前未审批的节点
        ApplyFor applyForNow = applyForRepository.findByProcessInstanceIdAndActorIdAndMassageIdAndProcessTypeAndAuditResultIsNull(applyFor.getProcessInstanceId(), applyFor.getActorId(), applyFor.getMassageId(), applyFor.getProcessType());

        /// 如果为空，则说明已经审批完，只显示最后一个审批者
        if (applyForNow == null){
            applyForNow = applyForRepository.findMaxCreateTimeByProcessInstanceIdAndActorIdAndProcessType(applyFor.getProcessInstanceId(), applyFor.getActorId(), applyFor.getProcessType());
        }

        // 请假详细信息映射
        VacationTeacher vacationTeacher = new VacationTeacher();
        if (applyFor.getMasterId() != null){
            vacationTeacher = vacationTeacherRepository.findOne(new Long(applyFor.getMassageId()));
        }else{
            vacationTeacher = new VacationTeacher();
        }

        return new ApplyForDTO(
            Integer.valueOf(applyFor.getId().toString()),
            applyFor.getMasterId(),
            applyFor.getMasterName(),
            applyFor.getMasterPortrait(),
            applyFor.getProcessInstanceId(),
            applyFor.getTaskId(),
            applyFor.getActorId(),
            applyFor.getUserName(),
            applyFor.getPortrait(),
            massageMapper.toDTO(vacationTeacher),
            applyFor.getProcessType(),
            applyFor.getAuditResult(),
            applyFor.getComment(),
            applyFor.getCreateTime(),
            applyFor.getUpdateTime(),
            applyForNowMapper.toDTO(applyForNow)
        );
    }
}
