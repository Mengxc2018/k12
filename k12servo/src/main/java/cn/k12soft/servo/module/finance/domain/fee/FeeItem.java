package cn.k12soft.servo.module.finance.domain.fee;

import cn.k12soft.servo.domain.SchoolEntity;
import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import cn.k12soft.servo.module.finance.enumeration.FeeScopeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 收费项目
 */
@Entity
public class FeeItem extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  /**
   * 收费名称
   */
  @Column(nullable = false)
  private String name;
  /**
   * 费用对象范围
   */
  @Enumerated(EnumType.STRING)
  private FeeScopeType scope;

  /**
   * 费用周期
   */
  @Enumerated(EnumType.STRING)
  private FeePeriodType period;

  /**
   * 标准收取费用(精确到分)
   */
  private Long amount;

  /**
   * 后置结算(周期结束后结算)
   */
  private boolean delay;

  private FeeItem() {}

  public FeeItem(Integer schoolId,
                 String name,
                 FeeScopeType scope,
                 FeePeriodType period,
                 Long amount,
                 boolean delay) {
    super(schoolId);
    this.name = name;
    this.scope = scope;
    this.period = period;
    this.amount = amount;
    this.delay = delay;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
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

  public boolean isDelay() {
    return delay;
  }
}
