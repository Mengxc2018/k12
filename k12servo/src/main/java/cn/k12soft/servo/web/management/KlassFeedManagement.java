package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_FEED_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_FEED_GET;
import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_FEED_POST;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.KlassFeedService;
import cn.k12soft.servo.service.dto.KlassFeedDTO;
import cn.k12soft.servo.web.form.KlassFeedForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@RestController
@RequestMapping("/management/klass-feeds")
public class KlassFeedManagement {

  private final KlassFeedService klassFeedService;

  @Autowired
  public KlassFeedManagement(KlassFeedService klassFeedService) {
    this.klassFeedService = klassFeedService;
  }

  @ApiOperation("创建班级事件feed")
  @PostMapping
  @PermissionRequired(KLASS_FEED_POST)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public KlassFeedDTO create(@RequestBody @Valid KlassFeedForm form,
                             @Active Actor actor) {
    return klassFeedService.create(actor, form);
  }

  @ApiOperation("删除班级事件")
  @DeleteMapping("/{feedId:\\d+}")
  @PermissionRequired(KLASS_FEED_DELETE)
  @Timed
  public void delete(@PathVariable Long feedId) {
    klassFeedService.delete(feedId);
  }

  @ApiOperation("查询班级动态，按月查询")
  @GetMapping("/findFeed")
  @PermissionRequired(KLASS_FEED_GET)
  @Timed
  public List<KlassFeedDTO> find(@Active Actor actor,
                              @RequestParam @Valid Integer klassId,
                              @RequestParam @Valid LocalDate localDate){
    return klassFeedService.findAllByMonth(actor, klassId, localDate);
  }

}
