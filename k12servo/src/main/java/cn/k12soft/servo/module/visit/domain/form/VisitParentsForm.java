package cn.k12soft.servo.module.visit.domain.form;

import cn.k12soft.servo.domain.enumeration.IsWill;

import javax.validation.constraints.NotNull;

public class VisitParentsForm {
    @NotNull
    private String content;           // 回访内容
    @NotNull
    private IsWill isWill;          // 是否同意入园

    public String getContent() {
        return content;
    }

    public IsWill getIsWill() {
        return isWill;
    }
}
