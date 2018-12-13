package cn.k12soft.servo.module.feeDetails.domain.form;

import cn.k12soft.servo.domain.enumeration.FeeKindType;
import cn.k12soft.servo.domain.enumeration.FeeType;
import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

@ApiModel
public class FeeDetailsForm {

    @ApiModelProperty("用户手机号")
    private String mobile;    // 客户手机号
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("学生id")
    private Integer studentId;
    @ApiModelProperty("学生姓名")
    private String stuName;
    @ApiModelProperty("收费金额")
    private Float money;
    @ApiModelProperty("收费范围、种类")
    private FeeKindType scopt;   // 收费范围
    @ApiModelProperty("收费方式")
    private FeeType feeType;    // 收费方式
    @ApiModelProperty("收费周期")
    private FeePeriodType feePeriodtype;    // 收费周期
    @ApiModelProperty("微信订单编号")
    private String wxOrder;


    public String getMobile() {
        return mobile;
    }

    public String getUsername() {
        return username;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getStuName() {
        return stuName;
    }

    public Float getMoney() {
        return money;
    }

    public FeeKindType getScopt() {
        return scopt;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public FeePeriodType getFeePeriodtype() {
        return feePeriodtype;
    }

    public String getWxOrder() {
        return wxOrder;
    }
}
