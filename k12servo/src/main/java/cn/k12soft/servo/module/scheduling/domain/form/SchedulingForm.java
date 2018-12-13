package cn.k12soft.servo.module.scheduling.domain.form;

public class SchedulingForm {

    private String name;            // 排班名

    private String amStartTime;    // 规定上午上班时间
    private String amEndTime;      // 规定上午下班时间
    private String pmStartTime;    // 规定下午上班时间
    private String pmEndTime;      // 规定下午下班时间

    private String schedulingType;

    public String getName() {
        return name;
    }

    public String getAmStartTime() {
        return amStartTime;
    }

    public String getAmEndTime() {
        return amEndTime;
    }

    public String getPmStartTime() {
        return pmStartTime;
    }

    public String getPmEndTime() {
        return pmEndTime;
    }

    public String getSchedulingType() {
        return schedulingType;
    }
}
