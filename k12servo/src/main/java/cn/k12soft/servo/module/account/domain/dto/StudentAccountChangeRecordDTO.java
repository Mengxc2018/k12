package cn.k12soft.servo.module.account.domain.dto;

import cn.k12soft.servo.domain.Actor;

import java.time.Instant;

public class StudentAccountChangeRecordDTO {
    private Integer id;
    private Integer klassId;
    private String klassName;   // 班
    private Integer studentAccountId = 0; // 账号id
    private Integer studentId; // 学生
    private String studentName; // 学生姓名
    private Float changeMoney;// 变化的费用
    private Float remainMoney;// 剩余费用
    private Actor createdBy;// 操作人
    private Instant createAt;
    private Integer opType;// 来源

    public StudentAccountChangeRecordDTO() {
    }

    public StudentAccountChangeRecordDTO(Integer id, Integer klassId, String klassName, Integer studentAccountId, Integer studentId, String studentName, Float changeMoney, Float remainMoney, Actor createdBy, Instant createAt, Integer opType) {
        this.id = id;
        this.klassId = klassId;
        this.klassName = klassName;
        this.studentAccountId = studentAccountId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.changeMoney = changeMoney;
        this.remainMoney = remainMoney;
        this.createdBy = createdBy;
        this.createAt = createAt;
        this.opType = opType;
    }

    public Integer getId() {
        return id;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public String getKlassName() {
        return klassName;
    }

    public Integer getStudentAccountId() {
        return studentAccountId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Float getChangeMoney() {
        return changeMoney;
    }

    public Float getRemainMoney() {
        return remainMoney;
    }

    public Actor getCreatedBy() {
        return createdBy;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Integer getOpType() {
        return opType;
    }
}
