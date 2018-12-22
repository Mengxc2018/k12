package cn.k12soft.servo.module.employeeFlow.domain;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.ISGONE;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;

/**
 * 人员流动申请记录表
 */
@Entity
@DynamicUpdate
public class EmployeeFlow {         // 转正申请              // 离职申请
    @Id
    @GeneratedValue
    private Long id;
    private Integer actorId;
    private String userName;        // 申请人                // 申请人
    private String dutyName;        // 所在部门
    private LocalDate joinAt;       // 入职时间              // 入职时间
    private String department;      // 试用期岗位
    private String content;         // 试用期内工作总结        // 离职原因
    private LocalDate date;         // 转正起薪日期           // 最后工作日

    private ISGONE isgone;          // 是否通过
    private Instant createdAt;      // 创建时间
    private Instant updateAt;     // 审批时间

    private Integer schoolId;
    private String types;            // 类型，转正（official）、离职（leave）

    public EmployeeFlow(Integer actorId, String userName, Integer schoolId, LocalDate joinAt, String content, LocalDate date, Instant createdAt, String types) {
        this.actorId = actorId;
        this.userName = userName;
        this.schoolId = schoolId;
        this.joinAt = joinAt;
        this.content = content;
        this.date = date;
        this.createdAt = createdAt;
        this.types = types;
    }

    public EmployeeFlow(Integer actorId, String userName, String dutyName, Integer schoolId, LocalDate joinAt, String department, String content, LocalDate date, Instant createdAt, String types) {
        this.actorId = actorId;
        this.userName = userName;
        this.dutyName = dutyName;
        this.schoolId = schoolId;
        this.joinAt = joinAt;
        this.department = department;
        this.content = content;
        this.date = date;
        this.createdAt = createdAt;
        this.types = types;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public LocalDate getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(LocalDate joinAt) {
        this.joinAt = joinAt;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public ISGONE getIsgone() {
        return isgone;
    }

    public void setIsgone(ISGONE isgone) {
        this.isgone = isgone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
