package cn.k12soft.servo.module.expense.domain;

import cn.k12soft.servo.domain.SchoolEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2017/12/16.
 * 收费项
 */
@Entity
@DynamicUpdate
public class ExpenseEntry extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  @Column(nullable = false)
  private String name;                                    // 收费名称
  @Column(nullable = false)
  private ExpensePeriodType periodType;                   // 周期类型
  @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
  private List<ExpensePeriodDiscount> periodDiscounts;    // 周期性折扣
  @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
  private List<ExpenseIdentDiscount> identDiscounts;      // 身份类型折扣
  @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
  private List<PaybackByDays> paybackByDays; // 按天退费
  @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
  private List<PaybackBySemester> paybackBySemesters;// 按学期退费
  @Column
  private boolean delayCharge;                            // 延迟收费
  private BigDecimal amount;
  private boolean fixation;     // 是否为固定收费项目

  ExpenseEntry() {
  }

  public ExpenseEntry(Integer id) {
    this.id = id;
  }

  public ExpenseEntry(Integer schoolId,
                      String name,
                      ExpensePeriodType periodType,
                      BigDecimal amount,
                      boolean fixation) {
    this(schoolId, name, periodType, amount, fixation, false);
  }

  public ExpenseEntry(Integer schoolId,
                      String name,
                      ExpensePeriodType periodType,
                      BigDecimal amount,
                      boolean delayCharge,
                      boolean fixation) {
    super(schoolId);
    this.name = name;
    this.periodType = periodType;
    this.amount = amount;
    this.delayCharge = delayCharge;
    this.fixation = fixation;
    this.periodDiscounts = new ArrayList<>();
    this.identDiscounts = new ArrayList<>();
    this.paybackByDays = new ArrayList<>();
    this.paybackBySemesters = new ArrayList<>();
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ExpensePeriodType getPeriodType() {
    return periodType;
  }

  public void setPeriodType(ExpensePeriodType periodType) {
    this.periodType = periodType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public List<ExpensePeriodDiscount> getPeriodDiscounts() {
    return periodDiscounts;
  }

  public List<ExpenseIdentDiscount> getIdentDiscounts() {
    return identDiscounts;
  }

  public List<PaybackByDays> getPaybackByDays() {
    return paybackByDays;
  }

  public List<PaybackBySemester> getPaybackBySemesters() {
    return paybackBySemesters;
  }

  public void addPeriodDiscount(ExpensePeriodDiscount expensePeriodDiscount) {
    this.periodDiscounts.add(expensePeriodDiscount);
  }

  public void addIdentDiscount(ExpenseIdentDiscount identDiscount) {
    this.identDiscounts.add(identDiscount);
  }

  public boolean isDelayCharge() {
    return delayCharge;
  }

  public void addPaybackByDays(PaybackByDays pd){
    this.paybackByDays.add(pd);
  }

  public void addPaybackBySemester(PaybackBySemester ps){
    this.paybackBySemesters.add(ps);
  }

    public boolean getIsFixation() {
        return fixation;
    }

    public void setFixation(boolean fixation) {
        this.fixation = fixation;
    }

  public boolean getIsDelayCharge() {
    return delayCharge;
  }
}
