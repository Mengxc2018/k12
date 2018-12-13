package cn.k12soft.servo.module.healthCheck.domain.form;

import cn.k12soft.servo.domain.enumeration.Physical.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
@ApiModel
public class HealthMorningForm {

    private Integer studentId;

    @ApiModelProperty(value = " 晨检、午检、晚检 ")
    private TYPE type;  // 早中晚类型

    @ApiModelProperty(value = " 精神 ")
    private SPIRIT spirit;  // 精神

    @ApiModelProperty(value = " 身体 ")
    private BODY body;      // 身体

    @ApiModelProperty(value = " 皮肤 ")
    private SINK sink;      // 皮肤

    @ApiModelProperty(value = " 口腔 ")
    private MOUTH mouth;        // 口腔

    @ApiModelProperty(value = " 其他 ")
    private OTHER other;

    @ApiModelProperty(value = " 体温 ")
    private float temperature;  // 体温

    @ApiModelProperty(value = " 备注 ")
    private String remark;      // 备注

    public Integer getStudentId() {
        return studentId;
    }

    public TYPE getType() {
        return type;
    }

    public SPIRIT getSpirit() {
        return spirit;
    }

    public BODY getBody() {
        return body;
    }

    public SINK getSink() {
        return sink;
    }

    public MOUTH getMouth() {
        return mouth;
    }

    public OTHER getOther() {
        return other;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getRemark() {
        return remark;
    }
}
