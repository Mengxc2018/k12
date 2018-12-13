package cn.k12soft.servo.module.applyFor.domain;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import java.time.Instant;

/**
 * 请假审批表
 */
@Entity
public class ApplyFor {

    @Id
    @GeneratedValue
    private Long id;
    private Integer masterId;           // 审批人id
    private String masterName;          // 审批人姓名
    private String masterPortrait;      // 审批人头像
    private String processInstanceId;   // activiti 流程实例ID
    private String taskId;              // activiti 任务节点ID
    private Integer actorId;            // 申请用户ID
    private String userName;            // 申请人姓名
    private String portrait;            // 申请人头像
    private Integer massageId;          // 申请信息id
    private Integer processType;        // 申请类型，大类型
    private VacationTeacherUtil.VACATIONTYPE vacationType;  // 小类型，考核申请类型
    private VacationTeacherUtil.ISGONE isGone;  // 整个流程审批状态
    private VacationTeacherUtil.ISGONE auditResult;     // 当前审批者审批状态
    private String comment;             // 审批意见
    @OrderBy("DESC")
    private Instant createTime;         // 创建时间
    private Instant updateTime;         // 审核时间

    public ApplyFor(){  }

    public ApplyFor(Long id,
                    Integer masterId,
                    String masterName,
                    String masterPortrait,
                    String processInstanceId,
                    Integer processType,
                    VacationTeacherUtil.VACATIONTYPE vacationType,
                    VacationTeacherUtil.ISGONE isGone,
                    String taskId,
                    Integer actorId,
                    String userName,
                    String portrait,
                    Integer massageId,
                    VacationTeacherUtil.ISGONE auditResult,
                    String comment,
                    Instant createTime, Instant updateTime) {
        this.id = id;
        this.masterId = masterId;
        this.masterName = masterName;
        this.masterPortrait = masterPortrait;
        this.processInstanceId = processInstanceId;
        this.vacationType = vacationType;
        this.processType = processType;
        this.isGone = isGone;
        this.taskId = taskId;
        this.actorId = actorId;
        this.userName = userName;
        this.portrait = portrait;
        this.massageId = massageId;
        this.auditResult = auditResult;
        this.comment = comment;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ApplyFor(Integer masterId, String masterName, String masterPortrait, String processInstanceId,Integer processType , VacationTeacherUtil.VACATIONTYPE vacationType, VacationTeacherUtil.ISGONE isGone, Integer massageId, String taskId, Integer actorId, String userName, String portrait, Instant createTime) {
        this.masterId = masterId;
        this.masterName = masterName;
        this.masterPortrait = masterPortrait;
        this.processInstanceId = processInstanceId;
        this.processType = processType;
        this.vacationType = vacationType;
        this.isGone = isGone;
        this.massageId = massageId;
        this.taskId = taskId;
        this.actorId = actorId;
        this.userName = userName;
        this.portrait = portrait;
        this.createTime = createTime;
    }


    public Long getId() {
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

    public Integer getMassageId() {
        return massageId;
    }

    public void setMassageId(Integer massageId) {
        this.massageId = massageId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAuditResult(VacationTeacherUtil.ISGONE auditResult) {
        this.auditResult = auditResult;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getMasterPortrait() {
        return masterPortrait;
    }

    public void setMasterPortrait(String masterPortrait) {
        this.masterPortrait = masterPortrait;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public VacationTeacherUtil.ISGONE getIsGone() {
        return isGone;
    }

    public void setIsGone(VacationTeacherUtil.ISGONE isGone) {
        this.isGone = isGone;
    }

    public VacationTeacherUtil.VACATIONTYPE getVacationType() {
        return vacationType;
    }

    public void setVacationType(VacationTeacherUtil.VACATIONTYPE vacationType) {
        this.vacationType = vacationType;
    }
}
