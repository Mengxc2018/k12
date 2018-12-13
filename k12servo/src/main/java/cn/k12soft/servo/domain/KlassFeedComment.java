package cn.k12soft.servo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class KlassFeedComment extends SchoolEntity {

  @Id
  @GeneratedValue
  private Long id;

  @JsonIgnore
  @ManyToOne
  private KlassFeed feed;

  private String content;

  @ManyToOne
  private Actor createdBy;

  private Instant createdAt;

  @ManyToOne
  private Actor replyTo;

  private KlassFeedComment() {
  }

  public KlassFeedComment(KlassFeed feed,
                          String content,
                          Actor createdBy) {
    super(feed.getSchoolId());
    this.feed = feed;
    this.content = content;
    this.createdBy = createdBy;
    this.createdAt = Instant.now();
  }

  public Long getId() {
    return id;
  }

  public KlassFeed getFeed() {
    return feed;
  }

  public String getContent() {
    return content;
  }

  public Actor getCreatedBy() {
    return createdBy;
  }

  public Actor getReplyTo() {
    return replyTo;
  }

  public KlassFeedComment setReplyTo(Actor replyTo) {
    this.replyTo = replyTo;
    return this;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
