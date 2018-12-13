package cn.k12soft.servo.service.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/20.
 */
public class KlassFeedListDTO {

  private List<KlassFeedDTO> feeds = new LinkedList<>();
  private List<KlassFeedCommentDTO> comments = new LinkedList<>();
  private List<KlassFeedLikeDTO> likes = new LinkedList<>();
  private List<Long> deletedFeeds = new LinkedList<>();
  private List<Long> deletedComments = new LinkedList<>();
  private List<Long> deletedLikes = new LinkedList<>();
  private Long eventId;
  private boolean more;

  public List<KlassFeedDTO> getFeeds() {
    return feeds;
  }

  public KlassFeedListDTO addFeed(KlassFeedDTO klassFeedDTO) {
    feeds.add(klassFeedDTO);
    return this;
  }

  public List<KlassFeedCommentDTO> getComments() {
    return comments;
  }

  public KlassFeedListDTO addComment(KlassFeedCommentDTO commentDTO) {
    this.comments.add(commentDTO);
    return this;
  }

  public List<KlassFeedLikeDTO> getLikes() {
    return likes;
  }

  public KlassFeedListDTO addLike(KlassFeedLikeDTO likeDTO) {
    this.likes.add(likeDTO);
    return this;
  }

  public List<Long> getDeletedFeeds() {
    return deletedFeeds;
  }

  public List<Long> getDeletedComments() {
    return deletedComments;
  }

  public List<Long> getDeletedLikes() {
    return deletedLikes;
  }

  public Long getEventId() {
    return eventId;
  }

  public KlassFeedListDTO setEventId(Long eventId) {
    this.eventId = eventId;
    return this;
  }

  public boolean isMore() {
    return more;
  }

  public KlassFeedListDTO setMore(boolean more) {
    this.more = more;
    return this;
  }
}
