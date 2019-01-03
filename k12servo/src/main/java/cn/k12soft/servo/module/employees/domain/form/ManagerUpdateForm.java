package cn.k12soft.servo.module.employees.domain.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class ManagerUpdateForm {
    @NotNull
    @ApiModelProperty("数据id")
    private Integer id;

    private Integer actorId;

    @ApiModelProperty("试用时长、单位：月")
    private Integer probation;

    @ApiModelProperty("试用工资")
    private Float salaryProbationer;


    @ApiModelProperty("转正工资")
    private Float salary;

    @ApiModelProperty("入职日期")
    private Instant dateJoinAt;

    @ApiModelProperty("转正日期")
    private Instant dateOfficialAt;

    @ApiModelProperty("登记日期")
    private Instant dateRegisterAt;

    @ApiModelProperty("用工形式")
    private String useForm;

    @ApiModelProperty("是否签订劳动合同")
    private boolean isContract;

    @ApiModelProperty("合同起始日期")
    private Instant contractDateStart;

    @ApiModelProperty("合同截止日期")
    private Instant contractDateEnd;

    @ApiModelProperty("劳动合同续签提醒")
    private Instant renewRemind;

    @ApiModelProperty("续签情况")
    private boolean isRenew;

    @ApiModelProperty("续签起始日期")
    private Instant renewDateStart;

    @ApiModelProperty("续签截止日期")
    private Instant renewDateEnd;

    @ApiModelProperty("是否转正")
    private boolean isOfficial;

    @ApiModelProperty("有无社保")
    private boolean hasSocial;  // 有无社保

    @ApiModelProperty("加班时间")
    private String overtime;    // 加班时间
    @ApiModelProperty("调休时间")
    private String rest;        // 调休时间
    @ApiModelProperty("年假时间")
    private String annual;      // 年假时间
    @ApiModelProperty("病假时间")
    private String sick;        // 病假时间
    @ApiModelProperty("产假")
    private String barth;       // 产假
    @ApiModelProperty("陪产假")
    private String barthWith;   // 陪产假
    @ApiModelProperty("婚假")
    private String marry;       // 婚假
    @ApiModelProperty("丧假")
    private String funeral;     // 丧假

    public Integer getId() {
        return id;
    }

    public Integer getActorId() {
        return actorId;
    }

    public Integer getProbation() {
        return probation;
    }

    public Float getSalaryProbationer() {
        return salaryProbationer;
    }

    public Float getSalary() {
        return salary;
    }

    public Instant getDateJoinAt() {
        return dateJoinAt;
    }

    public Instant getDateOfficialAt() {
        return dateOfficialAt;
    }

    public Instant getDateRegisterAt() {
        return dateRegisterAt;
    }

    public String getUseForm() {
        return useForm;
    }

    public boolean getIsContract() {
        return isContract;
    }

    public Instant getContractDateStart() {
        return contractDateStart;
    }

    public Instant getContractDateEnd() {
        return contractDateEnd;
    }

    public Instant getRenewRemind() {
        return renewRemind;
    }

    public boolean getIsRenew() {
        return isRenew;
    }

    public Instant getRenewDateStart() {
        return renewDateStart;
    }

    public Instant getRenewDateEnd() {
        return renewDateEnd;
    }

    public boolean getIsOfficial() {
        return isOfficial;
    }

    public boolean getIsHasSocial() {
        return hasSocial;
    }

    public String getOvertime() {
        return overtime;
    }

    public String getRest() {
        return rest;
    }

    public String getAnnual() {
        return annual;
    }

    public String getSick() {
        return sick;
    }

    public String getBarth() {
        return barth;
    }

    public String getBarthWith() {
        return barthWith;
    }

    public String getMarry() {
        return marry;
    }

    public String getFuneral() {
        return funeral;
    }
}
