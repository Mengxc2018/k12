package cn.k12soft.servo.module.charge.domain;

import cn.k12soft.servo.domain.SchoolEntity;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.ExpenseIdentDiscount;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodDiscount;
import cn.k12soft.servo.util.CommonUtils;
import cn.k12soft.servo.util.Times;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 */
@Entity
@DynamicUpdate
public class ChargePlan extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  @OneToOne
  private ExpenseEntry expenseEntry; // 费种
  @OneToOne
  private ExpensePeriodDiscount periodDiscount; //周期
  @OneToOne
  private ExpenseIdentDiscount identDiscount; // 应用类型
  @Column(nullable = false)
  private Integer targetType; // 应用对象类型
  @Column(nullable = false)
  private String target;// 应用对象
  @Column(nullable = false)
  private Instant endAt;// 截止日期
  @Column(nullable = false)
  private Float money;
  @Column(nullable = true)
  private String receiptId; // 收据id(yyyyMMdd+5位随机数)
  @Column
  private Instant createAt;

  ChargePlan() {}

  public ChargePlan(Integer schoolId) {
    super(schoolId);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ExpenseEntry getExpenseEntry() {
    return expenseEntry;
  }

  public void setExpenseEntry(ExpenseEntry expenseEntry) {
    this.expenseEntry = expenseEntry;
  }

  public ExpensePeriodDiscount getPeriodDiscount() {
    return periodDiscount;
  }

  public void setPeriodDiscount(ExpensePeriodDiscount periodDiscount) {
    this.periodDiscount = periodDiscount;
  }

  public ExpenseIdentDiscount getIdentDiscount() {
    return identDiscount;
  }

  public void setIdentDiscount(ExpenseIdentDiscount identDiscount) {
    this.identDiscount = identDiscount;
  }

  public Integer getTargetType() {
    return targetType;
  }

  public void setTargetType(Integer targetType) {
    this.targetType = targetType;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public Instant getEndAt() {
    return endAt;
  }

  public void setEndAt(Instant endAt) {
    this.endAt = endAt;
  }

  public Float getMoney() {
    return money;
  }

  public void setMoney(Float money) {
    this.money = money;
  }

  public String getReceiptId() {
    return receiptId;
  }

  public void setReceiptId(String receiptId) {
    this.receiptId = receiptId;
  }

  public Instant getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Instant createAt) {
    this.createAt = createAt;
  }

  public void createReceiptId() {
    int date = Times.time2yyyyMMdd(System.currentTimeMillis());
    StringBuilder sb = new StringBuilder(date);
    for (int i = 0; i < 5; i++) {
      sb.append(CommonUtils.randomGetFromZero2Ten());
    }
    this.receiptId = sb.toString();
  }
}
