package cn.k12soft.servo.module.wxLogin.domain.form;

import cn.k12soft.servo.domain.Actor;
import io.swagger.annotations.ApiModel;

import javax.persistence.OneToOne;
import java.time.Instant;

@ApiModel
public class WxMsgBoardForm {
    private String massage;

    public WxMsgBoardForm() {
    }

    public WxMsgBoardForm(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }
}
