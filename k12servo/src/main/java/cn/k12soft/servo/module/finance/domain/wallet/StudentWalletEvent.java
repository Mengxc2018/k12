package cn.k12soft.servo.module.finance.domain.wallet;

import cn.k12soft.servo.domain.SchoolEntity;
import cn.k12soft.servo.module.finance.enumeration.WalletEventType;
import java.time.Instant;
import javax.annotation.concurrent.Immutable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 学生钱包操作事件,不可变,提供审计明细
 */
@Immutable
@Entity
public class StudentWalletEvent extends SchoolEntity {

  @Id
  @GeneratedValue
  private Long id;
  private Integer walletId;
  @Enumerated(EnumType.STRING)
  private WalletEventType type;
  /**
   * 根据不同的type有不同的 JSON 内容格式
   */
  private String detail;
  private Long walletBeforeAmount;
  private Long amount;
  private Long walletAfterAmount;
  private Instant time;

  private StudentWalletEvent() {}

  public StudentWalletEvent(Integer schoolId,
                            Integer walletId,
                            WalletEventType type,
                            String detail,
                            Long walletBeforeAmount,
                            Long amount,
                            Long walletAfterAmount) {
    super(schoolId);
    this.walletId = walletId;
    this.type = type;
    this.detail = detail;
    this.amount = amount;
    this.walletBeforeAmount = walletBeforeAmount;
    this.walletAfterAmount = walletAfterAmount;
    this.time = Instant.now();
  }

  public Long getId() {
    return id;
  }

  public Integer getWalletId() {
    return walletId;
  }

  public WalletEventType getType() {
    return type;
  }

  public String getDetail() {
    return detail;
  }

  public Long getWalletBeforeAmount() {
    return walletBeforeAmount;
  }

  public Long getAmount() {
    return amount;
  }

  public Long getWalletAfterAmount() {
    return walletAfterAmount;
  }

  public Instant getTime() {
    return time;
  }
}
