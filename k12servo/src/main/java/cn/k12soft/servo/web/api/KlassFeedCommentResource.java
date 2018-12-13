package cn.k12soft.servo.web.api;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.KlassFeedCommentService;
import cn.k12soft.servo.service.dto.KlassFeedCommentDTO;
import cn.k12soft.servo.web.form.KlassFeedCommentForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/27.
 */
@RestController
@RequestMapping("/api/klass-feed-comments")
public class KlassFeedCommentResource {

  private final KlassFeedCommentService service;

  @Autowired
  public KlassFeedCommentResource(KlassFeedCommentService service) {
    this.service = service;
  }

  @ApiOperation("发布评论")
  @PostMapping
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public KlassFeedCommentDTO comment(@Active Actor actor,
                                     @RequestBody @Valid KlassFeedCommentForm form) {
    return service.create(actor, form);
  }

  @ApiOperation("删除评论")
  @DeleteMapping("/{commentId:\\d+}")
  @Timed
  public void uncomment(@Active Actor actor,
                        @PathVariable Long commentId) {
    service.delete(actor, commentId);
  }
}
