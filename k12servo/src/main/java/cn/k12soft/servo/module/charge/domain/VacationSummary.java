package cn.k12soft.servo.module.charge.domain;

/**
 * Created by liubing on 2018/4/23
 */
public class VacationSummary {
    private int leaveDaysOfMonth;
    private int[] termArr;
    private int[] yearArr;

    public VacationSummary(int leaveDaysOfMonth, int[] termArr, int[] yearArr) {
        this.leaveDaysOfMonth = leaveDaysOfMonth;
        this.termArr = termArr;
        this.yearArr = yearArr;
    }

    public int getLeaveDaysOfMonth() {
        return leaveDaysOfMonth;
    }

    public int[] getTermArr() {
        return termArr;
    }

    public int[] getYearArr() {
        return yearArr;
    }
}
