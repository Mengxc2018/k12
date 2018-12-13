package cn.k12soft.servo.web.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;

import static cn.k12soft.servo.util.Patterns.MOBILE_REGEX;

@ApiModel
public class TeacherTestForm {

    @Pattern(regexp = MOBILE_REGEX)
    private String mobile;

    @NotNull
    private String username;

    @ApiModelProperty(value = "入职时间(毫秒时间戳)")
    @NotNull
    private Instant joinTime;

    @ApiModelProperty(value = "有无社保（true、false）")
    private boolean hasSocial;  // 有无社保

    @ApiModelProperty(value = "是否转正（true/false）")
    private boolean isOfficial; // 是否转正

    @ApiModelProperty(value = "是否毕业（true/false）")
    @NotNull
    private boolean isGraduate; // 是否毕业

    @ApiModelProperty(value = "是否有毕业证（true/false）")
    private boolean hasDiploma; // 是否有毕业证

    @ApiModelProperty(value = "工资,没有则填0")
    private String salary = "0";      // 工资

    @ApiModelProperty(value = "试用期，月为单位，只传数字，没有试用期则填0")
    private String probation;   // 试用期

    public boolean getIsHasSocial() {
        return hasSocial;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUsername() {
        return username;
    }

    public Instant getJoinTime() {
        return joinTime;
    }

    public boolean getIsOfficial() {
        return isOfficial;
    }

    public boolean getIsGraduate() {
        return isGraduate;
    }

    public boolean getIsHasDiploma() {
        return hasDiploma;
    }

    public String getSalary() {
        return salary;
    }

    public String getProbation() {
        return probation;
    }
}
