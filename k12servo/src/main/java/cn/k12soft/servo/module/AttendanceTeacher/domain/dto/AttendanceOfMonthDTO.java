package cn.k12soft.servo.module.AttendanceTeacher.domain.dto;

/**
 * 按月返回出勤天数、迟到天数、请假天数、缺勤天数
 */
public class AttendanceOfMonthDTO {

    private String att;     // 出勤天数
    private String late;    // 迟到天数
    private String vaca;    // 请假天数
    private String lack;    // 缺勤天数

    public AttendanceOfMonthDTO(String att, String late, String vaca, String lack) {
        this.att = att;
        this.late = late;
        this.vaca = vaca;
        this.lack = lack;
    }

    public String getAtt() {
        return att;
    }

    public String getLate() {
        return late;
    }

    public String getVaca() {
        return vaca;
    }

    public String getLack() {
        return lack;
    }
}
