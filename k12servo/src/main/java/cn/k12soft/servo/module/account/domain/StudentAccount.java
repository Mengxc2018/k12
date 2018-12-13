package cn.k12soft.servo.module.account.domain;

import cn.k12soft.servo.domain.Student;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 学生账户,奖金
 */
@Entity
@DynamicUpdate
public class StudentAccount {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private Student student;
    @Column(nullable = false)
    private Float money;
    @Column(nullable = false)
    private Integer leaveDays;
    @Column
    private Float paybackMoney;

    public StudentAccount() {
    }

    public StudentAccount(Student student, Float money) {
        this.student = student;
        this.money = money;
        this.leaveDays = 0;
        this.paybackMoney = 0f;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
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
}
