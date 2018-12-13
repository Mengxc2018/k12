package cn.k12soft.servo.module.medicine.domain.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.util.Set;

@ApiModel
public class MedicineForm {
    private Integer stuId;
    @ApiModelProperty("服药儿童姓名")
    private String stuName;         // 服药儿童姓名
    @ApiModelProperty("执行时间")
    private Instant executeTime;    // 执行时间
    @ApiModelProperty("药品名称")
    private String medicineName;    // 药品名称
    @ApiModelProperty("服药剂量")
    private String dose;        // 服药剂量
    @ApiModelProperty("服药时间、明细")
    private Set<MedicineTimeForm> medicineTimeForms; // 服药时间
    @ApiModelProperty("吃药天数")
    private Integer days;           // 吃药天数
    @ApiModelProperty("儿童所在班级")
    private Integer klassId;
    @ApiModelProperty("备注")
    private String remark;

    public Integer getStuId() {
        return stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Instant getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Instant executeTime) {
        this.executeTime = executeTime;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDose() {
        return dose;
    }

    public Set<MedicineTimeForm> getMedicineTimeForms() {
        return medicineTimeForms;
    }

    public Integer getDays() {
        return days;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
