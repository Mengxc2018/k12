package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.PlanType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class KlassPlan extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  private String title;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private String content;

  @Enumerated(EnumType.STRING)
  private PlanType planType;

  @ManyToOne
  private Actor createdBy;

  private Instant createdAt;

  @ManyToOne
  private Actor updatedBy;

  private Instant updatedAt;

  @JsonIgnore
  @ManyToOne
  private Klass klass;

  KlassPlan() {
  }

  public KlassPlan(Klass klass,
                   PlanType planType,
                   String title,
                   String content,
                   Actor createdBy) {
    super(klass.getSchoolId());
    this.klass = klass;
    this.title = title;
    this.content = content;
    this.planType = planType;
    this.updatedBy = this.createdBy = createdBy;
    this.createdAt = this.updatedAt = Instant.now();
  }

  public Integer getId() {
    return id;
  }

  public Klass getKlass() {
    return klass;
  }

  public String getTitle() {
    return title;
  }

  public KlassPlan title(String title) {
    setTitle(title);
    setUpdatedAt(Instant.now());
    return this;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public KlassPlan content(String content) {
    setContent(content);
    setUpdatedAt(Instant.now());
    return this;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public PlanType getPlanType() {
    return planType;
  }

  public Actor getCreatedBy() {
    return createdBy;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Actor getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(Actor updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
