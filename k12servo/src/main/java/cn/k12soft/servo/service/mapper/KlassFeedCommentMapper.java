package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.KlassFeedComment;
import cn.k12soft.servo.service.dto.KlassFeedCommentDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/3.
 */
@Component
public class KlassFeedCommentMapper {

  @Autowired
  private UserBasicMapper userBasicMapper;

  public KlassFeedCommentDTO toDTO(KlassFeedComment comment) {
    return new KlassFeedCommentDTO()
      .setId(comment.getId())
      .setFeedId(comment.getFeed().getId())
      .setContent(comment.getContent())
      .setCreatedBy(userBasicMapper.toDTO(comment.getCreatedBy()))
      .setReplyTo(userBasicMapper.toDTO(comment.getReplyTo()))
      .setCreatedAt(comment.getCreatedAt());
  }

  public List<KlassFeedCommentDTO> toDTO(List<KlassFeedComment> comments) {
    return comments.stream().map(this::toDTO).collect(Collectors.toList());
  }
}
