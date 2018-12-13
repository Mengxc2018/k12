package cn.k12soft.servo.module.holidaysWeek.domain.form;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.time.Instant;

public class HolidaysWeekForm{

    private Instant date;
    private String name;
    private VacationTeacherUtil.YES_NO isGone;

    public Instant getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public VacationTeacherUtil.YES_NO getIsGone() {
        return isGone;
    }
}
