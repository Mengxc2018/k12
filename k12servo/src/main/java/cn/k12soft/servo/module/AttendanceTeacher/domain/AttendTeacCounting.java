package cn.k12soft.servo.module.AttendanceTeacher.domain;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.time.Instant;
import java.time.temporal.Temporal;

public class AttendTeacCounting {
    private Integer id;
    private String signAt;         // 打卡时间
    private String createdAt;      // 记录创建时间
    private Integer status;         // 打卡状态 0：正常、1：迟到、2：早退

    private String type;            // 异常打卡审核 yes:通过审核 no:待审核
    private Instant retroAt;        // 补签时间
    private VacationTeacherUtil.RETROTYPE retrotype;  // 补签类型

    public AttendTeacCounting(Integer id, String signAt, String createdAt, Integer status, String type, Instant retroAt, VacationTeacherUtil.RETROTYPE retrotype) {
        this.id = id;
        this.signAt = signAt;
        this.createdAt = createdAt;
        this.status = status;
        this.type = type;
        this.retroAt = retroAt;
        this.retrotype = retrotype;
    }


    public Integer getId() {
        return id;
    }

    public String getSignAt() {
        return signAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Instant getRetroAt() {
        return retroAt;
    }

    public VacationTeacherUtil.RETROTYPE getRetrotype() {
        return retrotype;
    }
}
