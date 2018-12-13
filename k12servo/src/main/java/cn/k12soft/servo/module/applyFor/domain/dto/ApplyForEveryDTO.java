package cn.k12soft.servo.module.applyFor.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.applyFor.domain.ApplyForEntity;

import java.time.Instant;

public class ApplyForEveryDTO extends ApplyForEntity{

    public ApplyForEveryDTO(Integer masterId, String masterName, String masterPortrait, Integer actorId, String userName, String portrait) {
        super(masterId, masterName, masterPortrait, actorId, userName, portrait);
    }
    private Integer id;
    private String processInstanceId;   // activiti 流程实例ID
    private String taskId;              // activiti 任务节点ID
    private Integer massageId;          // 申请信息id
    private Integer processType;        // 申请类型
    private VacationTeacherUtil.YES_NO auditResult;     // 申请结果
    private String comment;             // 审批意见
    private Instant createTime;         // 创建时间
    private Instant updateTime;         // 审核时间

    public ApplyForEveryDTO(Integer masterId, String masterName, String masterPortrait, Integer actorId, String userName, String portrait, Integer id, String processInstanceId, String taskId, Integer massageId, Integer processType, VacationTeacherUtil.YES_NO auditResult, String comment, Instant createTime, Instant updateTime) {
        super(masterId, masterName, masterPortrait, actorId, userName, portrait);
        this.id = id;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.massageId = massageId;
        this.processType = processType;
        this.auditResult = auditResult;
        this.comment = comment;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public Integer getMassageId() {
        return massageId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public VacationTeacherUtil.YES_NO getAuditResult() {
        return auditResult;
    }

    public String getComment() {
        return comment;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }
}
