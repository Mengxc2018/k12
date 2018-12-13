package cn.k12soft.servo.service.dto;

import java.time.Instant;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/3.
 */
public class KlassFeedCommentDTO {

  private Long id;

  private Long feedId;

  private String content;

  private UserBasicDTO createdBy;

  private Instant createdAt;

  private UserBasicDTO replyTo;

  public Long getId() {
    return id;
  }

  public KlassFeedCommentDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getFeedId() {
    return feedId;
  }

  public KlassFeedCommentDTO setFeedId(Long feedId) {
    this.feedId = feedId;
    return this;
  }

  public String getContent() {
    return content;
  }

  public KlassFeedCommentDTO setContent(String content) {
    this.content = content;
    return this;
  }

  public UserBasicDTO getCreatedBy() {
    return createdBy;
  }

  public KlassFeedCommentDTO setCreatedBy(UserBasicDTO createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  public UserBasicDTO getReplyTo() {
    return replyTo;
  }

  public KlassFeedCommentDTO setReplyTo(UserBasicDTO replyTo) {
    this.replyTo = replyTo;
    return this;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public KlassFeedCommentDTO setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
    return this;
  }
}
