package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.module.expense.domain.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2018/1/2.
 */
public class ExpenseEntryDTO {

  private Integer id;
  private String name;                                    // 收费名称
  private ExpensePeriodType periodType;                   // 周期类型
  private List<ExpensePeriodDiscount> periodDiscounts;    // 周期性折扣
  private List<ExpenseIdentDiscount> identDiscounts;      // 身份类型折扣
  private List<PaybackByDays> paybackByDays;
  private List<PaybackBySemester> paybackBySemesters;
  private boolean delayCharge;                            // 延迟收费
  private BigDecimal amount;

  public ExpenseEntryDTO(ExpenseEntry entry,
                         List<ExpenseIdentDiscount> identDiscounts,
                         List<ExpensePeriodDiscount> periodDiscounts,
                         List<PaybackByDays> paybackByDays,
                         List<PaybackBySemester> paybackBySemesters) {
    this.id = entry.getId();
    this.name = entry.getName();
    this.periodType = entry.getPeriodType();
    this.periodDiscounts = periodDiscounts;
    this.identDiscounts = identDiscounts;
    this.delayCharge = entry.isDelayCharge();
    this.amount = entry.getAmount();
    this.paybackByDays = paybackByDays;
    this.paybackBySemesters = paybackBySemesters;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ExpensePeriodType getPeriodType() {
    return periodType;
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

  public boolean isDelayCharge() {
    return delayCharge;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
