package cn.k12soft.servo.module.weeklyRemark.domain.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class WeeklyForm {

    private Integer studentId;

    @ApiModelProperty(value = "教师评语")
    private String tcontext;

    @ApiModelProperty(value = "家长评语")
    private String pcontext;

    @ApiModelProperty(value = "情绪")
    private Integer emotion;

    @ApiModelProperty(value = "进餐")
    private Integer dine;

    @ApiModelProperty(value = "睡眠")
    private Integer seleep;

    @ApiModelProperty(value = "适应环境")
    private Integer environment;

    @ApiModelProperty(value = "伙伴相处")
    private Integer partner;

    @ApiModelProperty(value = "环境卫生")
    private Integer sanitation;

    @ApiModelProperty(value = "身体状况")
    private Integer health;

    @ApiModelProperty(value = "自理能力")
    private Integer self;

    private Integer klassId;

    public Integer getStudentId() {
        return studentId;
    }

    public String getTcontext() {
        return tcontext;
    }

    public String getPcontext() {
        return pcontext;
    }

    public Integer getEmotion() {
        return emotion;
    }

    public Integer getDine() {
        return dine;
    }

    public Integer getSeleep() {
        return seleep;
    }

    public Integer getEnvironment() {
        return environment;
    }

    public Integer getPartner() {
        return partner;
    }

    public Integer getSanitation() {
        return sanitation;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getSelf() {
        return self;
    }

    public Integer getKlassId() {
        return klassId;
    }
}
