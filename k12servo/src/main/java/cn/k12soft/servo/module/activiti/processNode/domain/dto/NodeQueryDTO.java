package cn.k12soft.servo.module.activiti.processNode.domain.dto;

import cn.k12soft.servo.module.duty.domain.Duty;

import java.util.List;
import java.util.Map;

public class NodeQueryDTO {

    private Long id;
    private List<NodeQueryListDTO> list;    // actorId, actorName, dutyId, dutyName
    private Integer schoolId;               // 学校id
    private Integer activitiType;           // 流程类型
    private List<MassageCC> massageCC;  // 抄送人
    private Duty discernDutyId;    // 基础职务：id, name

    public NodeQueryDTO(){};

    public NodeQueryDTO(Long id, List<NodeQueryListDTO> list, Integer schoolId, Integer activitiType, List<MassageCC> massageCC, Duty discernDutyId) {
        this.id = id;
        this.list = list;
        this.schoolId = schoolId;
        this.activitiType = activitiType;
        this.massageCC = massageCC;
        this.discernDutyId = discernDutyId;
    }

    public NodeQueryDTO(List<NodeQueryListDTO> actorList, Integer schoolId, String type, List<MassageCC> massageCCS, Duty duty) {
        this.list = list;
        this.schoolId = schoolId;
        this.activitiType = activitiType;
        this.massageCC = massageCC;
        this.discernDutyId = discernDutyId;
    }

    public Long getId() {
        return id;
    }

    public List<NodeQueryListDTO> getList() {
        return list;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public Integer getActivitiType() {
        return activitiType;
    }

    public List<MassageCC> getMassageCC() {
        return massageCC;
    }

    public Duty getDiscernDutyId() {
        return discernDutyId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setList(List<NodeQueryListDTO> list) {
        this.list = list;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public void setActivitiType(Integer activitiType) {
        this.activitiType = activitiType;
    }

    public void setMassageCC(List<MassageCC> massageCC) {
        this.massageCC = massageCC;
    }

    public void setDiscernDutyId(Duty discernDutyId) {
        this.discernDutyId = discernDutyId;
    }
}
