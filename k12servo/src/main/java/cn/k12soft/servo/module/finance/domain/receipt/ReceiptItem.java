package cn.k12soft.servo.module.finance.domain.receipt;

import java.util.List;
import javax.annotation.concurrent.Immutable;

/**
 * 收据项目,保留收费时的所有细节内容,不可变
 */
@Immutable
public class ReceiptItem {

  private ReceiptFee fee;
  private List<ReceiptDiscount> discounts;
  private Long receiptedAmount;

  private ReceiptItem() {}

  public ReceiptItem(ReceiptFee fee,
                     List<ReceiptDiscount> discounts,
                     Long receiptedAmount) {
    this.fee = fee;
    this.discounts = discounts;
    this.receiptedAmount = receiptedAmount;
  }

  public ReceiptFee getFee() {
    return fee;
  }

  public List<ReceiptDiscount> getDiscounts() {
    return discounts;
  }

  public Long getReceiptedAmount() {
    return receiptedAmount;
  }
}