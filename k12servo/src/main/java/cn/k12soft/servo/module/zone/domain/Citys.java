package cn.k12soft.servo.module.zone.domain;

import cn.k12soft.servo.module.department.domain.Dept;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

/**
 * Created by liubing on 2018/7/24
 */
@Entity
@DynamicUpdate
public class Citys {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private int provinceId;
    @Column
    private String provinceName;
    @Column
    private Integer parentId;
    @Column
    private String atteTeacher; // 教师出勤率
    @Column
    private String atteStudent; // 学生出勤率
    @Column
    private String income; // 每月收入
    @Column
    private String expenditure; // 每月支出
    private String atteRecruit; // 招生率
    @Column
    private Instant monthInst; // 月份
    @Column
    private Instant createAt;
    @Column
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Dept> department;

    public Citys(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getAtteTeacher() {
        return atteTeacher;
    }

    public void setAtteTeacher(String atteTeacher) {
        this.atteTeacher = atteTeacher;
    }

    public String getAtteStudent() {
        return atteStudent;
    }

    public void setAtteStudent(String atteStudent) {
        this.atteStudent = atteStudent;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(String expenditure) {
        this.expenditure = expenditure;
    }

    public String getAtteRecruit() {
        return atteRecruit;
    }

    public void setAtteRecruit(String atteRecruit) {
        this.atteRecruit = atteRecruit;
    }

    public Instant getMonthInst() {
        return monthInst;
    }

    public void setMonthInst(Instant monthInst) {
        this.monthInst = monthInst;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Set<Dept> getDepartment() {
        return department;
    }

    public void setDepartment(Set<Dept> department) {
        this.department = department;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}