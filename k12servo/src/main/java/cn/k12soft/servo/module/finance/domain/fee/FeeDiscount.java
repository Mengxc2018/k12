package cn.k12soft.servo.module.finance.domain.fee;

import cn.k12soft.servo.domain.SchoolEntity;
import cn.k12soft.servo.module.finance.enumeration.FeeDiscountMode;
import cn.k12soft.servo.module.finance.enumeration.FeeDiscountType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FeeDiscount extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  /**
   * 收费项目
   */
  private Integer feeItemId;

  /**
   * 优惠类型
   */
  @Enumerated(EnumType.STRING)
  private FeeDiscountType type;

  /**
   * 优惠类型目标
   */
  private String target;

  /**
   * 优惠模式
   */
  @Enumerated(EnumType.STRING)
  private FeeDiscountMode mode;

  /**
   * 优惠具体数值
   */
  private Long amount;

  private FeeDiscount() {}

  public FeeDiscount(Integer schoolId, Integer feeItemId, FeeDiscountType type, String target,
                     FeeDiscountMode mode, Long amount) {
    super(schoolId);
    this.feeItemId = feeItemId;
    this.type = type;
    this.target = target;
    this.mode = mode;
    this.amount = amount;
  }

  public Integer getId() {
    return id;
  }

  public Integer getFeeItemId() {
    return feeItemId;
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