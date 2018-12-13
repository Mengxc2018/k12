package cn.k12soft.servo.module.AttendanceTeacher.domain.form;

public class AttendanceTeacherForm {
    /**
     * 教师打卡Form
     */
    private Integer actorId;      // 教师id

    private float temperature;      // 体温

    private String portrait;        // 半身像URL

    public Integer getActorId() {
        return actorId;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getPortrait() {
        return portrait;
    }

}



