package cn.k12soft.servo.module.finance.domain.receipt;

import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import cn.k12soft.servo.module.finance.enumeration.FeeScopeType;

public class ReceiptFee {

  private Integer feeItemId;
  private String feeName;
  private FeeScopeType scope;
  private FeePeriodType period;
  private Long amount;

  private ReceiptFee() {
  }

  public ReceiptFee(Integer feeItemId, String feeName, FeeScopeType scope, FeePeriodType period, Long amount) {
    this.feeItemId = feeItemId;
    this.feeName = feeName;
    this.scope = scope;
    this.period = period;
    this.amount = amount;
  }

  public Integer getFeeItemId() {
    return feeItemId;
  }

  public String getFeeName() {
    return feeName;
  }

  public FeeScopeType getScope() {
    return scope;
  }

  public FeePeriodType getPeriod() {
    return period;
  }

  public Long getAmount() {
    return amount;
  }
}
