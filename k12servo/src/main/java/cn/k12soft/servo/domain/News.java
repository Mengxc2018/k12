package cn.k12soft.servo.domain;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 新闻
 */
@Entity
@DynamicUpdate
public class News extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @ManyToOne
  private Actor createdBy;

  @Column(nullable = false)
  private Instant createdAt;

  @ManyToOne
  private Actor updatedBy;

  @Column(nullable = false)
  private Instant updatedAt;

  private News() {
  }

  public News(School school, String title, String content, Actor createdBy) {
    super(school.getId());
    this.title = title;
    this.content = content;
    this.updatedBy = this.createdBy = createdBy;
    this.createdAt = this.updatedAt = Instant.now();
  }

  public Integer getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Actor getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Actor createdBy) {
    this.createdBy = createdBy;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
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
