package cn.k12soft.servo.module.studentChargeRecord.domain.dto;


import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

import java.time.Instant;

public class StudentChargeKlassTotalDTO {


    private Long id;
    private Integer klassId;
    private String klassName;

    @ApiModelProperty("固定有伙食费")
    private Float feeFood = 0f;

    @ApiModelProperty("固定有保教费或者保育费或者保教保育费")
    private Float feeEducation = 0f;

    @ApiModelProperty("其他费用，以json格式存放，属性要有feeName、feeMoney，格式为{'feeName','FeeMoney'},{...}")
    private JSONObject feeOther;

    @ApiModelProperty("伙食费跟保教费统计")
    private Float feeTotal;

    @ApiModelProperty("所有费用的统计")
    private Float feeAllTotal;

    private Integer schoolId;

    private Instant createAt;

    public StudentChargeKlassTotalDTO() {
    }

    public StudentChargeKlassTotalDTO(Long id, Integer klassId, String klassName, Float feeFood, Float feeEducation, JSONObject feeOther, Float feeTotal, Float feeAllTotal, Integer schoolId, Instant createAt) {
        this.id = id;
        this.klassId = klassId;
        this.klassName = klassName;
        this.feeFood = feeFood;
        this.feeEducation = feeEducation;
        this.feeOther = feeOther;
        this.feeTotal = feeTotal;
        this.feeAllTotal = feeAllTotal;
        this.schoolId = schoolId;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public String getKlassName() {
        return klassName;
    }

    public Float getFeeFood() {
        return feeFood;
    }

    public Float getFeeEducation() {
        return feeEducation;
    }

    public JSONObject getFeeOther() {
        return feeOther;
    }

    public Float getFeeTotal() {
        return feeTotal;
    }

    public Float getFeeAllTotal() {
        return feeAllTotal;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public Instant getCreateAt() {
        return createAt;
    }
}
