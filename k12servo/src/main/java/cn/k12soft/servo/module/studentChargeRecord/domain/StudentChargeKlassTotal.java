package cn.k12soft.servo.module.studentChargeRecord.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@DynamicUpdate
public class StudentChargeKlassTotal {


    @Id
    @GeneratedValue
    private Long id;
    private Integer klassId;
    private String klassName;

    @ApiModelProperty("固定有伙食费")
    private Float feeFood = 0f;

    @ApiModelProperty("固定有保教费或者保育费或者保教保育费")
    private Float feeEducation = 0f;

    @ApiModelProperty("其他费用，以json格式存放，属性要有feeName、feeMoney，格式为{'feeName','FeeMoney'},{...}")
    private String feeOther;

    @ApiModelProperty("伙食费跟保教费统计")
    private Float feeTotal;

    @ApiModelProperty("所有费用的统计")
    private Float feeAllTotal;

    private Integer schoolId;

    private Instant createAt;

    public StudentChargeKlassTotal(){

    }

    public StudentChargeKlassTotal(Integer klassId, String klassName, Integer schoolId, Instant createAt) {
        this.klassId = klassId;
        this.klassName = klassName;
        this.schoolId = schoolId;
        this.createAt = createAt;

        this.feeFood = 0f;
        this.feeEducation = 0f;
        this.feeTotal = 0f;
        this.feeAllTotal = 0f;

    }

    public StudentChargeKlassTotal(Long id, Integer klassId, String klassName, Float feeFood, Float feeEducation, String feeOther, Float feeTotal, Float feeAllTotal, Integer schoolId, Instant createAt) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public String getKlassName() {
        return klassName;
    }

    public void setKlassName(String klassName) {
        this.klassName = klassName;
    }

    public Float getFeeFood() {
        return feeFood;
    }

    public void setFeeFood(Float feeFood) {
        this.feeFood = feeFood;
    }

    public Float getFeeEducation() {
        return feeEducation;
    }

    public void setFeeEducation(Float feeEducation) {
        this.feeEducation = feeEducation;
    }

    public String getFeeOther() {
        return feeOther;
    }

    public void setFeeOther(String feeOther) {
        this.feeOther = feeOther;
    }

    public Float getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(Float feeTotal) {
        this.feeTotal = feeTotal;
    }

    public Float getFeeAllTotal() {
        return feeAllTotal;
    }

    public void setFeeAllTotal(Float feeAllTotal) {
        this.feeAllTotal = feeAllTotal;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }
}
