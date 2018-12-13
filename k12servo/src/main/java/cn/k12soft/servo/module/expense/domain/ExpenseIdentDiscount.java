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
 * 身份类型折扣
 */
@Entity
@DynamicUpdate
public class ExpenseIdentDiscount extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  @JsonIgnore
  @ManyToOne
  private ExpenseEntry entry;
  @Enumerated(EnumType.STRING)
  private ExpenseIdentType identType;
  @Column(nullable = false)
  private BigDecimal discountRate;

  ExpenseIdentDiscount() {
  }

  public ExpenseIdentDiscount(ExpenseEntry entry,
                              ExpenseIdentType identType,
                              BigDecimal discountRate) {
    super(entry.getSchoolId());
    this.entry = entry;
    this.identType = identType;
    this.discountRate = discountRate;
  }

  public Integer getId() {
    return id;
  }

  public ExpenseEntry getEntry() {
    return entry;
  }

  public ExpenseIdentType getIdentType() {
    return identType;
  }

  public BigDecimal getDiscountRate() {
    return discountRate;
  }
}