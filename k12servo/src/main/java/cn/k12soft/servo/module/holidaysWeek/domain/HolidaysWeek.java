package cn.k12soft.servo.module.holidaysWeek.domain;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class HolidaysWeek {

    @Id
    @GeneratedValue
    private Integer id;
    private Instant date;
    private String name;
    private VacationTeacherUtil.YES_NO isGone;

    public HolidaysWeek(){}

    public HolidaysWeek(Instant date, String name, VacationTeacherUtil.YES_NO isGone) {
        this.date = date;
        this.name = name;
        this.isGone = isGone;
    }

    public Integer getId() {
        return id;
    }

    public Instant getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public VacationTeacherUtil.YES_NO getIsGone() {
        return isGone;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsGone(VacationTeacherUtil.YES_NO isGone) {
        this.isGone = isGone;
    }
}
