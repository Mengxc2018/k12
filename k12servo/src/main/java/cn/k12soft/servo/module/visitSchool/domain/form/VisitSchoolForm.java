package cn.k12soft.servo.module.visitSchool.domain.form;

import javax.validation.constraints.NotNull;

public class VisitSchoolForm {

    @NotNull
    private String parentName;  // 家长姓名
    @NotNull
    private String mobile;      // 家长手机号
    @NotNull
    private String babyName;    // 宝宝姓名
    @NotNull
    private String babyAge;     // 宝宝年龄
    @NotNull
    private Integer schoolId;

    public String getParentName() {
        return parentName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getBabyName() {
        return babyName;
    }

    public String getBabyAge() {
        return babyAge;
    }

    public Integer getSchoolId() {
        return schoolId;
    }
}
