package cn.k12soft.servo.module.holidaysWeek.domain.dto;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

import java.sql.Timestamp;
import java.time.Instant;

public class HolidaysWeekDTO {
    private Integer id;
    private String date;    // 时间格式：月-日
    private String name;
    private VacationTeacherUtil.YES_NO isGone;

    public HolidaysWeekDTO(Integer id, String date, String name, VacationTeacherUtil.YES_NO isGone) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.isGone = isGone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VacationTeacherUtil.YES_NO getIsGone() {
        return isGone;
    }

    public void setIsGone(VacationTeacherUtil.YES_NO isGone) {
        this.isGone = isGone;
    }
}
