package cn.k12soft.servo.module.employeeFlow.domain.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

@ApiModel
public class LeaveForm {

    @ApiModelProperty(value = "申请人")
    private String userName;    // 申请人

    @ApiModelProperty(value = "入职时间（格式：2018-08-08）")
    private LocalDate joinAt;   // 入职时间

    @ApiModelProperty(value = "离职原因")
    private String content;     // 离职原因

    @ApiModelProperty(value = "最后工作日（格式：2018-08-08）")
    private LocalDate salaryDay;    // 最后工作日

    public String getUserName() {
        return userName;
    }

    public LocalDate getJoinAt() {
        return joinAt;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getSalaryDay() {
        return salaryDay;
    }
}
