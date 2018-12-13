package cn.k12soft.servo.module.employeeFlow.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.time.Instant;
import java.time.LocalDate;

public class LeaveDTO {
    private Integer id;
    private Integer actorId;
    private String userName;    // 申请人
    private LocalDate joinAt;   // 入职时间
    private String content;     // 离职原因
    private LocalDate salaryDay;    // 最后工作日
    private VacationTeacherUtil.ISGONE isgone;          // 是否通过
    private Instant createdAt;      // 创建时间
    private Instant updateAt;     // 审批时间

    public LeaveDTO(Integer id, Integer actorId, String userName, LocalDate joinAt, String content, LocalDate salaryDay, VacationTeacherUtil.ISGONE isgone, Instant createdAt, Instant updateAt) {
        this.id = id;
        this.actorId = actorId;
        this.userName = userName;
        this.joinAt = joinAt;
        this.content = content;
        this.salaryDay = salaryDay;
        this.isgone = isgone;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDate getJoinAt() {
        return joinAt;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getSalaryDay() {
        return salaryDay;
    }
}
