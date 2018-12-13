package cn.k12soft.servo.module.expense.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 *
 * 退费规则-按天退费
 * Created by liubing on 2018/4/9
 */
@Entity
@DynamicUpdate
public class PaybackByDays {
    @Id
    @GeneratedValue
    private Integer id;
    @JsonIgnore
    @ManyToOne
    private ExpenseEntry entry; // 对应的费种
    @Column(nullable = false)
    private Integer compareType;// > = <
    @Column(nullable = false)
    private Integer pDay; // 天数
    @Column(nullable = false)
    private Integer pType; // 金额=1，比例=2
    @Column(nullable = false)
    private Float pValue; // 金额或者比例

    public PaybackByDays(){}

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

    public Integer getCompareType() {
        return compareType;
    }

    public void setCompareType(Integer compareType) {
        this.compareType = compareType;
    }

    public Integer getpDay() {
        return pDay;
    }

    public void setpDay(Integer pDay) {
        this.pDay = pDay;
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

    public boolean match(int leftDays) {
        if(compareType == 1){
            // <
            if(!(leftDays<this.pDay)){
                return false;
            }
        }else if(compareType == 2){
            // =
            if(leftDays != this.pDay){
                return false;
            }
        }else{
            // >
            if(!(leftDays>pDay)){
                return false;
            }
        }
        return true;
    }

    public boolean pTypeIsPerc() {
        return this.pType == 2;
    }
}
