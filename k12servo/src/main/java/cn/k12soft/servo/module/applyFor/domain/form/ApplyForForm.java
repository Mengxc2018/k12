package cn.k12soft.servo.module.applyFor.domain.form;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.time.Instant;

public class ApplyForForm {

    private Long id;
    private Integer messageId;  // 申请信息id
    private Integer type;       // 申请类型，大类型
    private VacationTeacherUtil.VACATIONTYPE applyType; // 申请类型，小类型
    private VacationTeacherUtil.ISGONE auditResult;     // 申请结果
    private String comment;     // 审批意见

    public Long getId() {
        return id;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public Integer getType() {
        return type;
    }

    public VacationTeacherUtil.VACATIONTYPE getApplyType() {
        return applyType;
    }

    public VacationTeacherUtil.ISGONE getAuditResult() {
        return auditResult;
    }

    public String getComment() {
        return comment;
    }

}
