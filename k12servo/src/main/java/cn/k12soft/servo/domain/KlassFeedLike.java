package cn.k12soft.servo.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/20.
 */
@Entity
public class KlassFeedLike extends SchoolEntity {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private KlassFeed feed;

  @ManyToOne
  private Actor user;

  private Instant createdAt;

  public KlassFeedLike() {

  }

  public KlassFeedLike(KlassFeed feed, Actor user) {
    super(feed.getSchoolId());
    this.feed = feed;
    this.user = user;
    this.createdAt = Instant.now();
  }

  public Long getId() {
    return id;
  }

  public KlassFeed getFeed() {
    return feed;
  }

  public Actor getUser() {
    return user;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
