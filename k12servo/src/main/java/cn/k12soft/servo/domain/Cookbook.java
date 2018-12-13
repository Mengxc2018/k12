package cn.k12soft.servo.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Basic;
import javax.persistence.Lob;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/7.
 */
@Entity
@DynamicUpdate
public class Cookbook extends SchoolEntity {

  @Id
  @GeneratedValue
  private Integer id;

  private String title;
  
  @Basic
  @Lob
  private String content;

  @ManyToOne
  private Actor createdBy;

  private Instant createdAt;

  @ManyToOne
  private Actor updatedBy;

  private Instant updatedAt;

  private Cookbook() {
  }

  public Cookbook(School school,
                  String title,
                  String content,
                  Actor createdBy) {
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

  public Cookbook title(String title) {
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

  public Cookbook content(String content) {
    setContent(content);
    setUpdatedAt(Instant.now());
    return this;
  }

  public void setContent(String content) {
    this.content = content;
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
