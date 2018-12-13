package cn.k12soft.servo.module.employeeFlow.domain.form;

import cn.k12soft.servo.module.empFlowRate.domain.FolwEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.time.Instant;

/**
 * 园长审批转正离职
 */
@ApiModel(value = "转正、离职通用")
public class Applyform {
    @ApiModelProperty(value = "员工id")
    private String id;

    @ApiModelProperty(value = "是否转正，是否离职,true、false")
    private boolean isTransform; // 是否转正

    @ApiModelProperty(value = "转正时间、离职时间，时间戳")
    private Instant date; // 转正时间

    @ApiModelProperty(value = "有无社保（true、false）")
    private boolean hasSocial;  // 有无社保

    @ApiModelProperty(value = "如果为离职，不需填写")
    private boolean isGraduate; // 是否毕业

    @ApiModelProperty(value = "如果为离职，不需填写")
    private boolean hasDoploma; // 是否有毕业证

    @ApiModelProperty(value = "如果为离职，选LEAVEBY、转正选JOINBY")
    private FolwEnum folw;      // 离职、转正

    public String getId() {
        return id;
    }

    public Instant getDate() {
        return date;
    }

    public boolean getIsHasSocial() {
        return hasSocial;
    }

    public boolean getIsTransform() {

        return isTransform;
    }

    public boolean getIsGraduate() {
        return isGraduate;
    }

    public boolean getIsHasDoploma() {
        return hasDoploma;
    }

    public FolwEnum getFolw() {
        return folw;
    }
}
