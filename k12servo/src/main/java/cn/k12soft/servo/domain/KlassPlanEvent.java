package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.PlanEventType;
import cn.k12soft.servo.domain.enumeration.PlanType;
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
public class KlassPlanEvent extends SchoolEntity {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Klass klass;

  @Enumerated(EnumType.STRING)
  private PlanEventType type;

  private PlanType planType;

  private Integer parameter;

  private Instant createdAt;

  private KlassPlanEvent() {
  }

  public KlassPlanEvent(Klass klass,
                        PlanEventType type,
                        PlanType planType,
                        Integer parameter,
                        Instant createdAt) {
    super(klass.getSchoolId());
    this.klass = klass;
    this.type = type;
    this.planType = planType;
    this.parameter = parameter;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public Klass getKlass() {
    return klass;
  }

  public PlanEventType getType() {
    return type;
  }

  public PlanType getPlanType() {
    return planType;
  }

  public Integer getParameter() {
    return parameter;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
