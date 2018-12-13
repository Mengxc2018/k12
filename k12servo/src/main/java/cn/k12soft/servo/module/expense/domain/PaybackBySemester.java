package cn.k12soft.servo.module.expense.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 退费规则-按学期退费
 * Created by liubing on 2018/4/9
 */
@Entity
@DynamicUpdate
public class PaybackBySemester {
    @Id
    @GeneratedValue
    private Integer id;
    @JsonIgnore
    @ManyToOne
    private ExpenseEntry entry; // 对应的费种
    @Column(nullable = false)
    private Integer semesterType; // 年度请假=1，学期请假=2
    @Column(nullable = false)
    private Integer vacationDays;// 能请多少天假
    @Column(nullable = false)
    private Integer daysCompareType;// > = <
    @Column(nullable = false)
    private Integer vacationCount; // 能请多少次假
    @Column(nullable = false)
    private Integer countCompareType;// > = <
    @Column(nullable = false)
    private Integer pType; // 金额=1，比例=2
    @Column(nullable = false)
    private Float pValue; // 金额或者比例

    public PaybackBySemester(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ExpenseEntry getEntry() {
        return entry;
    }

    public void setEntry(ExpenseEntry entry) {
        this.entry = entry;
    }

    public Integer getSemesterType() {
        return semesterType;
    }

    public void setSemesterType(Integer semesterType) {
        this.semesterType = semesterType;
    }

    public Integer getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
    }

    public Integer getDaysCompareType() {
        return daysCompareType;
    }

    public void setDaysCompareType(Integer daysCompareType) {
        this.daysCompareType = daysCompareType;
    }

    public Integer getVacationCount() {
        return vacationCount;
    }

    public void setVacationCount(Integer vacationCount) {
        this.vacationCount = vacationCount;
    }

    public Integer getCountCompareType() {
        return countCompareType;
    }

    public void setCountCompareType(Integer countCompareType) {
        this.countCompareType = countCompareType;
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public Float getpValue() {
        return pValue;
    }

    public void setpValue(Float pValue) {
        this.pValue = pValue;
    }

    public boolean match(int[] termArr, int[] yearArr) {
        int checkCount; // 请假次数
        int checkDays;
        if(this.semesterType == 1){
            checkCount = yearArr[0];
            checkDays = yearArr[1];
        }else{
            checkCount = termArr[0];
            checkDays = termArr[1];
        }

        // 先比较请假次数，次数不满足条件，返回false
        if(countCompareType == 1){
            //<
            if(!(this.vacationCount<checkCount)){
                return false;
            }
        }else if(countCompareType == 2){
            //=
            if(this.vacationCount != checkCount){
                return false;
            }
        }else{
            //>
            if(!(this.vacationCount>checkCount)){
                return false;
            }
        }

        // 比较请假天数，天数不满足条件，返回false
        if(daysCompareType == 1){
            // <
            if(!(this.vacationDays<checkDays)){
                return false;
            }
        }else if(daysCompareType == 2){
            // =
            if(this.vacationDays != checkDays){
                return false;
            }
        }else{
            // >
            if(!(this.vacationDays > checkDays)){
                return false;
            }
        }

        return true;
    }

    public boolean pTypeIsPerc() {
        return this.pType == 2; // 百分比类型
    }
}
