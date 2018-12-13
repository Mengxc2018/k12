package cn.k12soft.servo.module.charge.domain;

import cn.k12soft.servo.module.expense.domain.ExpenseEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by liubing on 2018/4/23
 */
public class ChargeInfo {
    private Integer schoolId;
    private Integer klassId;
    private Integer studentId;
    private String name;
    private Integer leaveDays;
    private Float paybackMoney;
    List<StudentCharge> studentChargeList = new LinkedList<>();

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getKlassId() {
        return klassId;
    }

    public void setKlassId(Integer klassId) {
        this.klassId = klassId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Integer leaveDays) {
        this.leaveDays = leaveDays;
    }

    public Float getPaybackMoney() {
        return paybackMoney;
    }

    public void setPaybackMoney(Float paybackMoney) {
        this.paybackMoney = paybackMoney;
    }

    public List<StudentCharge> getStudentChargeList() {
        return studentChargeList;
    }
}
