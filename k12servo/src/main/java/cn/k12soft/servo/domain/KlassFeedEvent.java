package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.FeedEventType;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/6.
 */
@Entity
public class KlassFeedEvent extends SchoolEntity {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Klass klass;

  @Enumerated(EnumType.STRING)
  private FeedEventType type;

  private Long parameter;

  private Instant createdAt;

  private KlassFeedEvent() {
  }

  public KlassFeedEvent(Klass klass,
                        FeedEventType type,
                        Long parameter,
                        Instant createdAt) {
    super(klass.getSchoolId());
    this.klass = klass;
    this.type = type;
    this.parameter = parameter;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public Klass getKlass() {
    return klass;
  }

  public FeedEventType getType() {
    return type;
  }

  public Long getParameter() {
    return parameter;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
