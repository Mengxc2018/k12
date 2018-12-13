package cn.k12soft.servo.web.form;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/3.
 */
public class KlassFeedCommentForm {

  private Long feedId;
  private String content;
  private Long replyTo;

  public Long getFeedId() {
    return feedId;
  }

  public String getContent() {
    return content;
  }

  public Long getReplyTo() {
    return replyTo;
  }
}
