package cn.k12soft.servo.module.VacationTeacher.domain;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendPeriodTeac.PeriodTeacType;

import java.time.LocalDate;
import java.util.*;

public class VacationPeriodTeac {

    private Integer actorId;   // 用户：老师
    private School school;  // 学校
    private PeriodTeacType type;    // 查询类型 周/月
    private LocalDate fromDate;   // 开始日期
    private LocalDate toDate;     // 结束日期
    private List<VacationTeacCounting> list ;

    public  VacationPeriodTeac(Integer actorId, School school, PeriodTeacType type, LocalDate fromDate, LocalDate toDate, List<VacationTeacCounting> list) {
        this.actorId = actorId;
        this.school = school;
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.list = list;
    }

    public Integer getActorId() {
        return actorId;
    }

    public School getSchool() {
        return school;
    }

    public List<VacationTeacCounting> getList() {
        return list;
    }

    public PeriodTeacType getType() {
        return type;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

}
