package cn.k12soft.servo.module.finance.service.dto;

import cn.k12soft.servo.module.finance.enumeration.FeePeriodType;
import cn.k12soft.servo.module.finance.enumeration.FeeScopeType;

public class FeeItemDTO {

  private Integer id;
  private String name;
  private FeeScopeType scope;
  private FeePeriodType period;
  private Long amount;
  private boolean delay;

  public Integer getId() {
    return id;
  }

  public FeeItemDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public FeeItemDTO setName(String name) {
    this.name = name;
    return this;
  }

  public FeeScopeType getScope() {
    return scope;
  }

  public FeeItemDTO setScope(FeeScopeType scope) {
    this.scope = scope;
    return this;
  }

  public FeePeriodType getPeriod() {
    return period;
  }

  public FeeItemDTO setPeriod(FeePeriodType period) {
    this.period = period;
    return this;
  }

  public Long getAmount() {
    return amount;
  }

  public FeeItemDTO setAmount(Long amount) {
    this.amount = amount;
    return this;
  }

  public boolean isDelay() {
    return delay;
  }

  public FeeItemDTO setDelay(boolean delay) {
    this.delay = delay;
    return this;
  }
}
