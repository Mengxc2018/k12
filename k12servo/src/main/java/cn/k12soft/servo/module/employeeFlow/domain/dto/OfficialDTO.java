package cn.k12soft.servo.module.employeeFlow.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.time.Instant;
import java.time.LocalDate;

public class OfficialDTO {
    private Integer id;
    private String userName;    // 申请人              // 申请人
    private String dutyName;    // 所在部门
    private LocalDate joinAt;   // 入职时间             // 入职时间
    private String department;  // 试用期岗位
    private String content;     // 试用期内工作总结      // 离职原因
    private LocalDate salaryDay;    // 转正起薪日期     // 最后工作日

    private VacationTeacherUtil.ISGONE isgone;          // 是否通过
    private Instant createdAt;      // 创建时间
    private Instant updateAt;     // 审批时间

    public OfficialDTO(Integer id, String userName, String dutyName, LocalDate joinAt, String department, String content, LocalDate salaryDay, VacationTeacherUtil.ISGONE isgone, Instant createdAt, Instant updateAt) {
        this.id = id;
        this.userName = userName;
        this.dutyName = dutyName;
        this.joinAt = joinAt;
        this.department = department;
        this.content = content;
        this.salaryDay = salaryDay;
        this.isgone = isgone;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public String getUserName() {
        return userName;
    }

    public String getDutyName() {
        return dutyName;
    }

    public LocalDate getJoinAt() {
        return joinAt;
    }

    public String getDepartment() {
        return department;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getSalaryDay() {
        return salaryDay;
    }
}
