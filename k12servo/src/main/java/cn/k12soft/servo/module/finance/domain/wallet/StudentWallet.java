package cn.k12soft.servo.module.finance.domain.wallet;

import cn.k12soft.servo.domain.SchoolEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 学生钱包,用于预交费,所有费用扣除走此账户进行相关的操作,同时需要生成对应的钱包操作事件作为审计用
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"schoolId", "studentId"}))
public class StudentWallet extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;
  private Integer studentId;
  /**
   * 余额(精确到分)
   */
  private Long amount;

  private StudentWallet() {}

  public StudentWallet(Integer schoolId, Integer studentId, Long amount) {
    super(schoolId);
    this.studentId = studentId;
    this.amount = amount;
  }

  public Integer getId() {
    return id;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public Long getAmount() {
    return amount;
  }
}
