package cn.k12soft.servo.service.dto;

import java.time.Instant;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/20.
 */
public class KlassFeedLikeDTO {

  private Long id;
  private Long feedId;
  private UserBasicDTO likedBy;
  private Instant likedAt;

  public Long getId() {
    return id;
  }

  public KlassFeedLikeDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getFeedId() {
    return feedId;
  }

  public KlassFeedLikeDTO setFeedId(Long feedId) {
    this.feedId = feedId;
    return this;
  }

  public UserBasicDTO getLikedBy() {
    return likedBy;
  }

  public KlassFeedLikeDTO setLikedBy(UserBasicDTO likedBy) {
    this.likedBy = likedBy;
    return this;
  }

  public Instant getLikedAt() {
    return likedAt;
  }

  public KlassFeedLikeDTO setLikedAt(Instant likedAt) {
    this.likedAt = likedAt;
    return this;
  }
}
