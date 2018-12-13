package cn.k12soft.servo.module.wxLogin.domain.form;

import java.time.Instant;

public class PushCodeForm {
    private String formId;
    private Instant timeOut;

    public PushCodeForm(){}

    public PushCodeForm(String formId, Instant timeOut) {
        this.formId = formId;
        this.timeOut = timeOut;
    }

    public String getFormId() {
        return formId;
    }

    public Instant getTimeOut() {
        return timeOut;
    }
}
