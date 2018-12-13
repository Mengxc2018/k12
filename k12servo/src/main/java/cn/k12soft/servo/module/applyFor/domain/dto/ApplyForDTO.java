package cn.k12soft.servo.module.applyFor.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.time.Instant;

public class ApplyForDTO {
    private Integer id;
    private Integer masterId;           // 审批人id
    private String masterName;          // 审批人姓名
    private String masterPortrait;      // 审批人头像
    private String processInstanceId;   // activiti 流程实例ID
    private String taskId;              // activiti 任务节点ID
    private Integer actorId;            // 申请用户ID
    private String userName;            // 申请人姓名
    private String portrait;            // 申请人头像
    private MassageDTO massageId;          // 申请信息id
    private Integer processType;        // 申请类型
    private VacationTeacherUtil.ISGONE auditResult;     // 申请结果
    private String comment;             // 审批意见
    private Instant createTime;         // 创建时间
    private Instant updateTime;         // 审核时间
    private ApplyForNowDTO applyForNow;          // 当前审批人或者最后审批人

    private ApplyForDTO(){ }

    public ApplyForDTO(Integer id, Integer masterId, String masterName, String masterPortrait, String processInstanceId, String taskId, Integer actorId, String userName, String portrait, MassageDTO massageId, Integer processType, VacationTeacherUtil.ISGONE auditResult, String comment, Instant createTime, Instant updateTime, ApplyForNowDTO applyForNow) {
        this.id = id;
        this.masterId = masterId;
        this.masterName = masterName;
        this.masterPortrait = masterPortrait;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.actorId = actorId;
        this.userName = userName;
        this.portrait = portrait;
        this.massageId = massageId;
        this.processType = processType;
        this.auditResult = auditResult;
        this.comment = comment;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.applyForNow = applyForNow;
    }

    public ApplyForDTO(Integer masterId, String masterName, String masterPortrait, String auditResult) {
        this.masterId = masterId;
        this.masterName = masterName;
        this.masterPortrait = masterPortrait;
        auditResult = auditResult;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getMasterName() {
        return masterName;
    }

    public Integer getActorId() {
        return actorId;
    }

    public String getUserName() {
        return userName;
    }

    public MassageDTO getMassageId() {
        return massageId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public VacationTeacherUtil.ISGONE getAuditResult() {
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

    public String getMasterPortrait() {
        return masterPortrait;
    }

    public String getPortrait() {
        return portrait;
    }

    public ApplyForNowDTO getApplyForNow() {
        return applyForNow;
    }
}
