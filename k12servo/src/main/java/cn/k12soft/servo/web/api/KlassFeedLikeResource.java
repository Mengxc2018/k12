package cn.k12soft.servo.web.api;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.KlassFeedLikeService;
import cn.k12soft.servo.service.dto.KlassFeedLikeDTO;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/27.
 */
@RestController
@RequestMapping
public class KlassFeedLikeResource {

  private final KlassFeedLikeService feedLikeService;

  @Autowired
  public KlassFeedLikeResource(KlassFeedLikeService feedLikeService) {
    this.feedLikeService = feedLikeService;
  }

  @ApiOperation("班级活动点赞")
  @PostMapping("/api/klass-feed/{feedId:\\d+}/likes")
  @Timed
  public KlassFeedLikeDTO like(@Active Actor actor,
                               @PathVariable Long feedId) {
    return feedLikeService.like(actor, feedId);
  }

  @ApiOperation("取消点赞")
  @DeleteMapping("/api/klass-feed-likes/{likeId:\\d+}")
  @Timed
  public void unlike(@Active Actor actor,
                     @PathVariable Long likeId) {
    feedLikeService.unlike(actor, likeId);
  }
}
