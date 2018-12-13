package cn.k12soft.servo.module.AttendanceTeacher.domain.form;

import java.time.Instant;

public class RetorForm {
    private Integer attendanceTeacherId;
    private String content;
    private Instant retorTime;

    public Integer getAttendanceTeacherId() {
        return attendanceTeacherId;
    }

    public String getContent() {
        return content;
    }

    public Instant getRetorTime() {
        return retorTime;
    }
}
