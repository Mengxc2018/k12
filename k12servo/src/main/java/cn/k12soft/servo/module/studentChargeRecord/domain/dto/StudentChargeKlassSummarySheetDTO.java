package cn.k12soft.servo.module.studentChargeRecord.domain.dto;


import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

public class StudentChargeKlassSummarySheetDTO {

//    private Long id;
    private Integer klassId;
    private String klassName;

    @ApiModelProperty("在册人数")
    private Integer listed;

    @ApiModelProperty("伙食费跟保教费统计")
    private Float feeTotal;

    @ApiModelProperty("所有费用的统计")
    private Float feeAllTotal;

    private Integer schoolId;
    private Instant createAt;
    private String remark;


    public StudentChargeKlassSummarySheetDTO() {
    }

    public StudentChargeKlassSummarySheetDTO(Integer klassId, String klassName, Integer listed, Float feeTotal, Float feeAllTotal, Integer schoolId, Instant createAt, String remark) {
//        this.id = id;
        this.klassId = klassId;
        this.klassName = klassName;
        this.listed = listed;
        this.feeTotal = feeTotal;
        this.feeAllTotal = feeAllTotal;
        this.schoolId = schoolId;
        this.createAt = createAt;
        this.remark = remark;
    }


    public Integer getKlassId() {
        return klassId;
    }

    public String getKlassName() {
        return klassName;
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

    public Integer getListed() {
        return listed;
    }

    public String getRemark() {
        return remark;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public void setKlassName(String klassName) {
        this.klassName = klassName;
    }

    public void setListed(Integer listed) {
        this.listed = listed;
    }

    public void setFeeTotal(Float feeTotal) {
        this.feeTotal = feeTotal;
    }

    public void setFeeAllTotal(Float feeAllTotal) {
        this.feeAllTotal = feeAllTotal;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
