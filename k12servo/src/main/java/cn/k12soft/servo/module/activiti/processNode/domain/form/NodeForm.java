package cn.k12soft.servo.module.activiti.processNode.domain.form;

import java.util.List;

public class NodeForm {
    private String processName;    // 审批流程名称
    private Integer activitiType;   // 流程类型
    private List<NodeActorIds> actor;    // 审批人actorId
    private String massageCC;   // 抄送人
    private Integer discernDutyId;   // 识别基层职务的id


    public String getProcessName() {
        return processName;
    }

    public Integer getActivitiType() {
        return activitiType;
    }

    public List<NodeActorIds> getActor() {
        return actor;
    }

    public Integer getDiscernDutyId() {
        return discernDutyId;
    }

    public String getMassageCC() {
        return massageCC;
    }
}
