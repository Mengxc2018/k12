package cn.k12soft.servo.module.healthCheck.domain.form;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.Physical;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

@ApiModel
public class HealthNightForm {
    private Integer studentId;

    @ApiModelProperty(value = " 创建时间，单位：时间戳")
    private Instant createdAt;

    @ApiModelProperty(value = " @ApiModelProperty(value = \" 晨检、午检、晚检 \") ")
    private Physical.TYPE type;  // 早中晚类型

    @ApiModelProperty(value = " 精神 ")
    private Physical.SPIRIT spirit;  // 精神

    @ApiModelProperty(value = " 身体 ")
    private Physical.BODY body;      // 身体

    @ApiModelProperty(value = " 皮肤 ")
    private Physical.SINK sink;      // 皮肤

    @ApiModelProperty(value = " 加餐 ")
    private Physical.ADDFOOD addfood;    // 加餐

    @ApiModelProperty(value = " 大小便排泄 ")
    private Physical.EXCRETE excrete;    // 大小便排泄

    @ApiModelProperty(value = " 备注 ")
    private String remark;      // 备注

    public Integer getStudentId() {
        return studentId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Physical.TYPE getType() {
        return type;
    }

    public Physical.SPIRIT getSpirit() {
        return spirit;
    }

    public Physical.BODY getBody() {
        return body;
    }

    public Physical.SINK getSink() {
        return sink;
    }

    public Physical.ADDFOOD getAddfood() {
        return addfood;
    }

    public Physical.EXCRETE getExcrete() {
        return excrete;
    }

    public String getRemark() {
        return remark;
    }
}
