package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.CURDType;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 新闻事件
 */
@Entity
public class NewsEvent extends SchoolEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private int newsId;

  @Enumerated(EnumType.STRING)
  private CURDType type;

  private Integer parameter;

  private Instant createdAt;

  private NewsEvent() {
  }

  public NewsEvent(News news,
                   CURDType type,
                   Integer parameter,
                   Instant createdAt) {
    super(news.getSchoolId());
    this.newsId = news.getId();
    this.type = type;
    this.parameter = parameter;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public CURDType getType() {
    return type;
  }

  public Integer getParameter() {
    return parameter;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getNewsId() {
    return newsId;
  }

  public void setNewsId(int newsId) {
    this.newsId = newsId;
  }

  public void setType(CURDType type) {
    this.type = type;
  }

  public void setParameter(Integer parameter) {
    this.parameter = parameter;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
