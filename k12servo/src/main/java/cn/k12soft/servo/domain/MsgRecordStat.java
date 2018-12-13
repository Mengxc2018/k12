package cn.k12soft.servo.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"type", "msgId", "userId"}))
public class MsgRecordStat extends SchoolEntity {

  public enum MsgType {
    PLAN,
    FEED
  }

  @Id
  @GeneratedValue
  private Long id;
  @Enumerated(EnumType.STRING)
  private MsgType type;
  private Integer klassId;
  private Long msgId;
  private Integer userId;
  private Instant readAt;

  private MsgRecordStat() {}

  public MsgRecordStat(Integer schoolId,
                       MsgType type,
                       Integer klassId,
                       Long msgId,
                       Integer userId,
                       Instant readAt) {
    super(schoolId);
    this.type = type;
    this.klassId = klassId;
    this.msgId = msgId;
    this.userId = userId;
    this.readAt = readAt;
  }

  public Long getId() {
    return id;
  }

  public MsgType getType() {
    return type;
  }

  public Integer getKlassId() {
    return klassId;
  }

  public Long getMsgId() {
    return msgId;
  }

  public Integer getUserId() {
    return userId;
  }

  public Instant getReadAt() {
    return readAt;
  }
}
