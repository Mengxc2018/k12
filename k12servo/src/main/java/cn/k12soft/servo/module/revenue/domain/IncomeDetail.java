package cn.k12soft.servo.module.revenue.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

/**
 * Created by liubing on 2018/8/23
 */
@Entity
@DynamicUpdate
public class IncomeDetail {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private int expenseId; // Income对应的费种id
    private int studentId;
    private String studentName;
    private float money;
    private float refundMoney;
    private int theYearMonth;
    private Instant createAt;

    public IncomeDetail(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public int getTheYearMonth() {
        return theYearMonth;
    }

    public void setTheYearMonth(int theYearMonth) {
        this.theYearMonth = theYearMonth;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }
}
