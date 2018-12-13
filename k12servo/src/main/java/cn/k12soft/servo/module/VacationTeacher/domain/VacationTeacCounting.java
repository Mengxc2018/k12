package cn.k12soft.servo.module.VacationTeacher.domain;

public class VacationTeacCounting {
    private String createdAt;
    private String fromDate;
    private String toDate;
    private String reason;   // 补签或请假类型
    private String isGone;      // 是否准假

    public VacationTeacCounting(){}

    public VacationTeacCounting(String createdAt, String fromDate, String toDate, String isGone, String reason) {
        this.createdAt = createdAt;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.isGone = isGone;
        this.reason = reason;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getReason() {
        return reason;
    }

    public String getIsGone() {
        return isGone;
    }
}
