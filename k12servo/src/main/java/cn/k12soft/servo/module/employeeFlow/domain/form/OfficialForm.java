package cn.k12soft.servo.module.employeeFlow.domain.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

@ApiModel
public class OfficialForm {

    @ApiModelProperty(value = "申请人")
    private String userName;    // 申请人

    @ApiModelProperty(value = "所在部门")
    private String dutyName;    // 所在部门

    @ApiModelProperty(value = "入职时间（格式：2018-08-08）")
    private LocalDate joinAt;   // 入职时间

    @ApiModelProperty(value = "试用期岗位")
    private String department;  // 试用期岗位

    @ApiModelProperty(value = "试用期内工作总结")
    private String content;     // 试用期内工作总结

    @ApiModelProperty(value = "转正起薪日期（格式：2018-08-08）")
    private LocalDate salaryDay;    // 转正起薪日期

    public String getUserName() {
        return userName;
    }

    public String getDutyName() {
        return dutyName;
    }

    public LocalDate getJoinAt() {
        return joinAt;
    }

    public String getDepartment() {
        return department;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getSalaryDay() {
        return salaryDay;
    }
}
