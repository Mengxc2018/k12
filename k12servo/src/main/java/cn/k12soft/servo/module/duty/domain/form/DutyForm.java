package cn.k12soft.servo.module.duty.domain.form;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;

public class DutyForm {
    private String name;    // 职务名称
    private VacationTeacherUtil.TRUE_FALSE isSubstratum;

    public DutyForm(){}

    public String getName() {
        return name;
    }

    public VacationTeacherUtil.TRUE_FALSE getIsSubstratum() {
        return isSubstratum;
    }
}
