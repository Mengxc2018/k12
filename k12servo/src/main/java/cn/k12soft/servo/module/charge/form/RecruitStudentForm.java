package cn.k12soft.servo.module.charge.form;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class RecruitStudentForm {
    private Integer studentId;
    @NotNull
    private String name;
    @NotNull
    private Instant birthday;
    private Instant payTime; //交费时间
    private Float money; //预缴费用
    @NotNull
    private Integer klassId;
    @NotNull
    private Instant enrolmentTime; //入园时间
    private Integer teacherId;
    private String teacherName;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Instant getPayTime() {
        return payTime;
    }

    public void setPayTime(Instant payTime) {
        this.payTime = payTime;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public Instant getEnrolmentTime() {
        return enrolmentTime;
    }

    public void setEnrolmentTime(Instant enrolmentTime) {
        this.enrolmentTime = enrolmentTime;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
