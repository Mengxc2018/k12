package cn.k12soft.servo.module.finance.domain.receipt;

import cn.k12soft.servo.module.finance.enumeration.FeeDiscountMode;
import cn.k12soft.servo.module.finance.enumeration.FeeDiscountType;

public class ReceiptDiscount {

  private FeeDiscountType type;
  private String target;
  private FeeDiscountMode mode;
  private Long amount;

  private ReceiptDiscount() {}

  public ReceiptDiscount(FeeDiscountType type, String target, FeeDiscountMode mode, Long amount) {
    this.type = type;
    this.target = target;
    this.mode = mode;
    this.amount = amount;
  }

  public FeeDiscountType getType() {
    return type;
  }

  public String getTarget() {
    return target;
  }

  public FeeDiscountMode getMode() {
    return mode;
  }

  public Long getAmount() {
    return amount;
  }
}
