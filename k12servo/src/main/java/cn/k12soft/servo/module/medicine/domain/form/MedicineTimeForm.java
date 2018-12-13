package cn.k12soft.servo.module.medicine.domain.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MedicineTimeForm {

    @ApiModelProperty("吃药时间，字符串、格式例：12：30")
    private String time;       // 吃药时间

    public MedicineTimeForm() {
    }

    public MedicineTimeForm(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}
