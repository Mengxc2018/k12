package cn.k12soft.servo.module.activiti.processNode.domain.dto;

public class NodeDTO {

    private Long id;
    private String processName;    // 审批流程名称
    private String actorIds;    // 审批人actorId
    private String dutyIds;     // 职务ID
    private String PROCESSKEY; // 流程的KEY
    private Integer schoolId;   // 学校id
    private Integer processType;    // 流程类型
    private String massageCC;  // 抄送人
    private Integer discernDutyId;// 基础职务

    public NodeDTO(Long id, String processName, String actorIds, String dutyIds, String PROCESSKEY, Integer schoolId, Integer processType, String massageCC, Integer discernDutyId) {
        this.id = id;
        this.processName = processName;
        this.actorIds = actorIds;
        this.dutyIds = dutyIds;
        this.PROCESSKEY = PROCESSKEY;
        this.schoolId = schoolId;
        this.processType = processType;
        this.massageCC = massageCC;
        this.discernDutyId = discernDutyId;
    }

    public Long getId() {
        return id;
    }

    public String getProcessName() {
        return processName;
    }

    public String getActorIds() {
        return actorIds;
    }

    public String getDutyIds() {
        return dutyIds;
    }

    public String getPROCESSKEY() {
        return PROCESSKEY;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public String getMassageCC() {
        return massageCC;
    }

    public Integer getDiscernDutyId() {
        return discernDutyId;
    }
}
