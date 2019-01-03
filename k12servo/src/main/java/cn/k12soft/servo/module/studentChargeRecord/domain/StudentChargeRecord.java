package cn.k12soft.servo.module.studentChargeRecord.domain;

import cn.k12soft.servo.domain.enumeration.FeeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

/**
 * 学生缴费记录
 * 所有余额不与其他任何数据计算，仅做展示
 */
@Entity
@DynamicUpdate
public class StudentChargeRecord {
    @Id
    @GeneratedValue
    private Long id;
    private Integer studentId;
    private Integer klassId;
    private Integer schoolId;
    private Instant createAt;

    @JsonIgnore
    private String StudentChargeIds;    // 当月学生的缴费id

    private String lastMonthIds;    // 统计上个月的收费计划的id，id用英文逗号隔开

    @ApiModelProperty("入园时间")
    private Instant joinAt;

//    收费标准:保教费、伙食费、收费合计
    @ApiModelProperty("保育教育费")
    private Float feeEducation;

    @ApiModelProperty("伙食费")
    private Float feeFood;

    @ApiModelProperty("其他费用")
    private Float feeOther;

    @ApiModelProperty("支付方式")
    @Enumerated(EnumType.STRING)
    private FeeType feeType;


    @ApiModelProperty("收费合计")
    private Float feeTotal;

    @ApiModelProperty("本月出勤天数")
    private Integer daysAttendance;

    @ApiModelProperty("本月缺勤天数")
    private Integer daysLost;


//    扣除费用
    @ApiModelProperty("余额")
    private Float balance;

    @ApiModelProperty("保教缺勤退费额")
    private Float deductLost;

    @ApiModelProperty("保教缺勤退费额")
    private Float deductFood;

    @ApiModelProperty("其他退费")
    private Float deductOther;

    @ApiModelProperty("退园退费")
    private Float deductLeave;

    @ApiModelProperty("退费合计")
    private Float deductTotal;


    @ApiModelProperty("实收金额")
    private Float actual;

    @ApiModelProperty("园长")
    private Integer managerBy;

    @ApiModelProperty("收费经办人")
    private String chargeBy;

    public StudentChargeRecord() {
    }

    public StudentChargeRecord(Integer studentId, Integer klassId, Integer schoolId, Instant joinAt, Float feeEducation, Float feeFood, Float feeOther, FeeType feeType, Float feeTotal, Integer daysAttendance, Integer daysLost, Float balance, Float deductLost, Float deductFood, Float deductOther, Float deductLeave, Float deductTotal, Float actual, Integer managerBy, String chargeBy, Instant createAt) {
        this.studentId = studentId;
        this.klassId = klassId;
        this.schoolId = schoolId;
        this.joinAt = joinAt;
        this.feeEducation = feeEducation;
        this.feeFood = feeFood;
        this.feeOther = feeOther;
        this.feeType = feeType;
        this.feeTotal = feeTotal;
        this.daysAttendance = daysAttendance;
        this.daysLost = daysLost;
        this.balance = balance;
        this.deductLost = deductLost;
        this.deductFood = deductFood;
        this.deductOther = deductOther;
        this.deductLeave = deductLeave;
        this.deductTotal = deductTotal;
        this.actual = actual;
        this.managerBy = managerBy;
        this.chargeBy = chargeBy;
        this.createAt = createAt;
    }

    public StudentChargeRecord(Integer studentId, Integer klassId, Instant joinAt, Integer schoolId, Instant createAt) {
        this.studentId = studentId;
        this.klassId = klassId;
        this.joinAt = joinAt;
        this.schoolId = schoolId;
        this.createAt = createAt;
    }

    public Instant getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Instant joinAt) {
        this.joinAt = joinAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Float getFeeEducation() {
        return feeEducation;
    }

    public void setFeeEducation(Float feeEducation) {
        this.feeEducation = feeEducation;
    }

    public Float getFeeFood() {
        return feeFood;
    }

    public void setFeeFood(Float feeFood) {
        this.feeFood = feeFood;
    }

    public Float getFeeOther() {
        return feeOther;
    }

    public void setFeeOther(Float feeOther) {
        this.feeOther = feeOther;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public Float getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(Float feeTotal) {
        this.feeTotal = feeTotal;
    }

    public Integer getDaysAttendance() {
        return daysAttendance;
    }

    public void setDaysAttendance(Integer daysAttendance) {
        this.daysAttendance = daysAttendance;
    }

    public Integer getDaysLost() {
        return daysLost;
    }

    public void setDaysLost(Integer daysLost) {
        this.daysLost = daysLost;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Float getDeductLost() {
        return deductLost;
    }

    public void setDeductLost(Float deductLost) {
        this.deductLost = deductLost;
    }

    public Float getDeductFood() {
        return deductFood;
    }

    public void setDeductFood(Float deductFood) {
        this.deductFood = deductFood;
    }

    public Float getDeductOther() {
        return deductOther;
    }

    public void setDeductOther(Float deductOther) {
        this.deductOther = deductOther;
    }

    public Float getDeductLeave() {
        return deductLeave;
    }

    public void setDeductLeave(Float deductLeave) {
        this.deductLeave = deductLeave;
    }

    public Float getDeductTotal() {
        return deductTotal;
    }

    public void setDeductTotal(Float deductTotal) {
        this.deductTotal = deductTotal;
    }

    public Float getActual() {
        return actual;
    }

    public void setActual(Float actual) {
        this.actual = actual;
    }

    public Integer getManagerBy() {
        return managerBy;
    }

    public void setManagerBy(Integer managerBy) {
        this.managerBy = managerBy;
    }

    public String getChargeBy() {
        return chargeBy;
    }

    public void setChargeBy(String chargeBy) {
        this.chargeBy = chargeBy;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public String getStudentChargeIds() {
        return StudentChargeIds;
    }

    public void setStudentChargeIds(String studentChargeIds) {
        StudentChargeIds = studentChargeIds;
    }

    public String getLastMonthIds() {
        return lastMonthIds;
    }

    public void setLastMonthIds(String lastMonthIds) {
        this.lastMonthIds = lastMonthIds;
    }
}
