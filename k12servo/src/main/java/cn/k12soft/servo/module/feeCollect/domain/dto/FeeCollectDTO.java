package cn.k12soft.servo.module.feeCollect.domain.dto;

import cn.k12soft.servo.domain.enumeration.FeeKindType;
import cn.k12soft.servo.domain.enumeration.FeeType;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

public class FeeCollectDTO{

    private String name;

    private Float money;

    @ApiModelProperty("收费范围，比如视频收费、图书收费等")
    private FeeKindType scopt;   // 收费范围、收费种类

    @ApiModelProperty("收费方式")
    private FeeType feeType;    // 收费方式

    @ApiModelProperty("收费明细id")
    private Integer msgId;      // 收费详情id

    private Instant createdAt;

    private String jsonObject;

    public FeeCollectDTO(){}

    public FeeCollectDTO(String name, Float money, FeeKindType scopt, FeeType feeType, Integer msgId, Instant createdAt, String jsonObject) {
        this.name = name;
        this.money = money;
        this.scopt = scopt;
        this.feeType = feeType;
        this.msgId = msgId;
        this.createdAt = createdAt;
        this.jsonObject = jsonObject;
    }
}
