package cn.k12soft.servo.module.charge.domain;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.SchoolEntity;
import cn.k12soft.servo.domain.enumeration.KlassType;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.ExpenseIdentDiscount;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodDiscount;
import cn.k12soft.servo.module.expense.domain.ExpensePeriodType;
import cn.k12soft.servo.util.Times;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * 学生的收费计划，周期性的，每个月会定时(MonthlySettlementTask)扣除费种的费用
 */
@Entity
@DynamicUpdate
public class StudentCharge extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private Integer planId;

  @Column(nullable = false)
  private Integer periodDate;// 周期时间yyyyMMdd,标记是那一期的收费计划

  @Column(nullable = false)
  private Integer studentId;

  @Column(nullable = false)
  private String studentName;

  @Column(nullable = false)
  private Integer klassId;

  @Enumerated(EnumType.STRING)
  private KlassType klassType;

  @OneToOne
  private ExpenseEntry expenseEntry; // 费种

  @OneToOne
  private ExpensePeriodDiscount periodDiscount; // 周期

  @OneToOne
  private ExpenseIdentDiscount identDiscount; // 应用类型

  @Column(nullable = false)
  private Float money;  // 应缴费用

  @Column(nullable = false)
  private Float remainMoney; // 应该补交金额

  private Instant createAt; // 发起收费时间

  private Instant endAt; // 截止日期

  private long endMills; // 截止时间长度(多少毫秒, 自动生成收费需要)

  @Column(nullable = true)
  private Instant paymentAt; // 标识缴费时间

  @ManyToOne
  private Actor checker; // 核对人

  @Column(nullable = true)
  private Instant checkAt;// 核对时间

  private Float paybackMoney;// 上个月应退费的金额

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setPlanId(Integer planId) {
    this.planId = planId;
  }

  public Integer getPlanId() {
    return planId;
  }

  public Integer getPeriodDate() {
    return periodDate;
  }

  public void setPeriodDate(Integer periodDate) {
    this.periodDate = periodDate;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public void setKlassId(Integer klassId) {
    this.klassId = klassId;
  }

  public KlassType getKlassType() {
    return klassType;
  }

  public void setKlassType(KlassType klassType) {
    this.klassType = klassType;
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

  public Float getMoney() {
    return money;
  }

  public void setMoney(Float money) {
    this.money = money;
  }

  public Float getRemainMoney() {
    return remainMoney;
  }

  public void setRemainMoney(Float remainMoney) {
    this.remainMoney = remainMoney;
  }

  public Instant getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Instant createAt) {
    this.createAt = createAt;
  }

  public Instant getEndAt() {
    return endAt;
  }

  public void setEndAt(Instant endAt) {
    this.endAt = endAt;
  }

  public long getEndMills() {
    return endMills;
  }

  public void setEndMills(long endMills) {
    this.endMills = endMills;
  }

  public Instant getPaymentAt() {
    return paymentAt;
  }

  public void setPaymentAt(Instant paymentAt) {
    this.paymentAt = paymentAt;
  }

  public Actor getChecker() {
    return checker;
  }

  public void setChecker(Actor checker) {
    this.checker = checker;
  }

  public Instant getCheckAt() {
    return checkAt;
  }

  public void setCheckAt(Instant checkAt) {
    this.checkAt = checkAt;
  }

  public Float getPaybackMoney() {
    return paybackMoney;
  }

  public void setPaybackMoney(Float paybackMoney) {
    this.paybackMoney = paybackMoney;
  }

  StudentCharge() {}

  public StudentCharge(Integer schoolId) {
    super(schoolId);
  }

  // 需要按月扣除的周期性类型
  public boolean isPeriodDeduct() {
    ExpensePeriodType periodType = this.getPeriodDiscount().getPeriodType();
    if (periodType == ExpensePeriodType.YEAR
      || periodType == ExpensePeriodType.HALF_YEAR
      || periodType == ExpensePeriodType.QUARTER
      || periodType == ExpensePeriodType.MONTH) {
      return true;
    }
    return false;
  }

  public boolean checkAndCreateNext(long currentTime) {
    ExpensePeriodType periodType = this.getExpenseEntry().getPeriodType();
    long currPeriodDateTime = Times.yyyyMM2Date(this.periodDate);
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(currPeriodDateTime);
    if (periodType == ExpensePeriodType.YEAR) {
      calendar.add(Calendar.YEAR , 1);
    } else if (periodType == ExpensePeriodType.HALF_YEAR) {
      calendar.add(Calendar.MONTH, 6);
//      tmpPeriodDate = Times.getHalfYear(currentTime);
    } else if (periodType == ExpensePeriodType.QUARTER) {
      calendar.add(Calendar.MONTH, 3);
//      tmpPeriodDate = Times.getSeason(currentTime);
    } else if (periodType == ExpensePeriodType.MONTH) {
      calendar.add(Calendar.MONTH, 1);
//      tmpPeriodDate = Times.time2yyyyMM(currentTime);
    }

    long nextPeriodDateTime = calendar.getTimeInMillis();
    int nextPeriodDate = Times.getYear(nextPeriodDateTime);
    int currYyyyMM = Times.time2yyyyMM(currentTime);
    if(currYyyyMM>nextPeriodDate){
        return false;
    }
    this.periodDate = nextPeriodDate;
    this.createAt = Instant.now();
    long endTime = nextPeriodDateTime + this.getEndMills();
    this.endAt = Instant.ofEpochMilli(endTime);
    this.paymentAt = null;
    this.checkAt = null;
    return true;
}

    // 实际需要交的费用，打折后的
    public float calcPayMoney(){
      float payMoney = this.money;
      boolean isDisc = false;
      Float pbm = paybackMoney == null ? 0f : paybackMoney;
      if(periodDiscount != null && periodDiscount.getDiscountRate() != null && periodDiscount.getDiscountRate().floatValue()>0){
          payMoney = payMoney*(1-periodDiscount.getDiscountRate().floatValue()/100f);
          isDisc = true;
      }
      if(identDiscount != null && identDiscount.getDiscountRate() != null && identDiscount.getDiscountRate().floatValue()>0){
          payMoney = pbm * (1-identDiscount.getDiscountRate().floatValue()/100f);
          isDisc = true;
      }
      if(!isDisc){
          return payMoney;
      }
      return (float)Math.round(payMoney*100f)/100;
    }

}
