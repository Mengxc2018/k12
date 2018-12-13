package cn.k12soft.servo.module.activiti.processNode.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class Node {

    @Id
    @GeneratedValue
    private Long id;
    private String processName;    // 审批流程名称
    private String actorIds;    // 审批人actorId
    private String dutyIds;     // 职务ids
    private String num;         // 请假天数或者财务审批金额数量
    private String PROCESSKEY;  // 流程的KEY
    private Integer schoolId;   // 学校id
    private Integer processType;    // 流程类型
    private Integer discernDutyId;   // 识别基层职务的id
    @JsonIgnore
    private Integer deploymentId;     // 流程部署Id
    private Integer createBy;   // 创建人
    private Instant createTime; // 创建时间
    private String massageCC;   // 抄送人

    public Node(){}

    public Node(String processName, String actorIds, String dutyIds, String num, String PROCESSKEY, Integer schoolId, Integer processType, Integer discernDutyId, Integer createBy, Instant createTime, String massageCC) {
        this.processName = processName;
        this.actorIds = actorIds;
        this.dutyIds = dutyIds;
        this.num = num;
        this.PROCESSKEY = PROCESSKEY;
        this.schoolId = schoolId;
        this.processType = processType;
        this.discernDutyId = discernDutyId;
        this.createBy = createBy;
        this.createTime = createTime;
        this.massageCC = massageCC;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public void setActorIds(String actorIds) {
        this.actorIds = actorIds;
    }

    public void setPROCESSKEY(String PROCESSKEY) {
        this.PROCESSKEY = PROCESSKEY;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setDutyIds(String dutyIds) {
        this.dutyIds = dutyIds;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Integer getDiscernDutyId() {
        return discernDutyId;
    }

    public void setDiscernDutyId(Integer discernDutyId) {
        this.discernDutyId = discernDutyId;
    }

    public String getMassageCC() {
        return massageCC;
    }

    public void setMassageCC(String massageCC) {
        this.massageCC = massageCC;
    }

    public Integer getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(Integer deploymentId) {
        this.deploymentId = deploymentId;
    }
}
