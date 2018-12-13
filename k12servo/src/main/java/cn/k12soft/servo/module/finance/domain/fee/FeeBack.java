package cn.k12soft.servo.module.finance.domain.fee;

import cn.k12soft.servo.domain.SchoolEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FeeBack extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  /**
   * 关联收费项目
   */
  private Integer feeItemId;

  /**
   * 描述
   */
  private String feeBackdesc;

  /**
   * 退还公式
   */
  private String expr;

  private FeeBack() {}

  public FeeBack(Integer schoolId, Integer feeItemId, String feeBackdesc, String expr) {
    super(schoolId);
    this.feeItemId = feeItemId;
    this.feeBackdesc = feeBackdesc;
    this.expr = expr;
  }

  public Integer getId() {
    return id;
  }

  public Integer getFeeItemId() {
    return feeItemId;
  }

  public String getFeeBackdesc() {
    return feeBackdesc;
  }

  public String getExpr() {
    return expr;
  }
}
