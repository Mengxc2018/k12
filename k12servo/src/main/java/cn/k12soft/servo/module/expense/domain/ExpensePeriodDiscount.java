package cn.k12soft.servo.module.expense.domain;

import cn.k12soft.servo.domain.SchoolEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2017/12/16.
 * 周期性折扣
 */
@Entity
@DynamicUpdate
public class ExpensePeriodDiscount extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  @JsonIgnore
  @ManyToOne
  private ExpenseEntry entry;
  @Enumerated(EnumType.STRING)
  private ExpensePeriodType periodType;
  @Column(nullable = false)
  private BigDecimal discountRate;

  ExpensePeriodDiscount() {
  }

  public ExpensePeriodDiscount(ExpenseEntry entry,
                               ExpensePeriodType periodType,
                               BigDecimal discountRate) {
    super(entry.getSchoolId());
    this.entry = entry;
    this.periodType = periodType;
    this.discountRate = discountRate;
  }

  public Integer getId() {
    return id;
  }

  public ExpenseEntry getEntry() {
    return entry;
  }

  public ExpensePeriodType getPeriodType() {
    return periodType;
  }

  public BigDecimal getDiscountRate() {
    return discountRate;
  }
}
