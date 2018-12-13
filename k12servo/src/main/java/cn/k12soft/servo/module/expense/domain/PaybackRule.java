package cn.k12soft.servo.module.expense.domain;

import cn.k12soft.servo.domain.SchoolEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 生成expr说明：
 * expr既表达式，形式类似于 a>b?a:b  使用spring el解析，所以具体使用需要参考spring el文档，
 * 上下文定义PaybackContext，get开头的方法用于描述属性，目前计划支持alias，既前端传输的表达式可以为
 * 出勤日> (常数)? ...
 * 表达式返回值必须为int值
 */
@Entity
public class PaybackRule extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  @JsonIgnore
  @ManyToOne
  private ExpenseEntry entry;
  @Column(nullable = false, name = "`desc`")
  private String desc;
  @Column(nullable = false)
  private String expr;

  private PaybackRule() {
  }

  public PaybackRule(ExpenseEntry entry, String desc, String expr) {
    super(entry.getSchoolId());
    this.entry = entry;
    this.desc = desc;
    this.expr = expr;
  }

  public Integer getId() {
    return id;
  }

  public ExpenseEntry getEntry() {
    return entry;
  }

  public String getDesc() {
    return desc;
  }

  public String getExpr() {
    return expr;
  }
}
