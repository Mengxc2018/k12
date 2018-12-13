package cn.k12soft.servo.web.form;

import javax.validation.constraints.NotNull;

/**
 * Created by xfnjlove on 2017/12/26.
 */
public class MarqueeForm {

    @NotNull
    private Integer schoolId;

    private String firstImg;

    private String centerImg;

    private String lastImg;



    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getCenterImg() {
        return centerImg;
    }

    public void setCenterImg(String centerImg) {
        this.centerImg = centerImg;
    }

    public String getLastImg() {
        return lastImg;
    }

    public void setLastImg(String lastImg) {
        this.lastImg = lastImg;
    }

    public Integer getSchoolId() {
        return schoolId;
    }
}
