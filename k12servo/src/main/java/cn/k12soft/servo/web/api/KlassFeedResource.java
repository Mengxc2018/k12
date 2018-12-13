package cn.k12soft.servo.web.api;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.MsgRecordStat.MsgType;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.domain.enumeration.Permission;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.KlassFeedEventService;
import cn.k12soft.servo.service.KlassFeedService;
import cn.k12soft.servo.service.KlassService;
import cn.k12soft.servo.service.MsgRecordStatService;
import cn.k12soft.servo.service.dto.KlassFeedListDTO;
import cn.k12soft.servo.service.dto.KlassFeedReadDTO;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@RestController
@RequestMapping
public class KlassFeedResource {

  private final KlassFeedEventService feedEventService;
  private final KlassFeedService klassFeedService;
  private final KlassService klassService;
  private final MsgRecordStatService msgRecordStatService;

  @Autowired
  public KlassFeedResource(KlassFeedEventService feedEventService,
                           KlassFeedService klassFeedService,
                           KlassService klassService,
                           MsgRecordStatService msgRecordStatService) {
    this.feedEventService = feedEventService;
    this.klassFeedService = klassFeedService;
    this.klassService = klassService;
    this.msgRecordStatService = msgRecordStatService;
  }

  @ApiOperation("获取与当前用户相关的所有班级feeds")
  @GetMapping(value = "/api/klass-feeds", params = {"fromId"})
  @Timed
  @Transactional
  public KlassFeedListDTO query(@Active Actor actor,
                                @RequestParam Long fromId) {
    if (actor.getTypes().contains(ActorType.MANAGER)) {
      return feedEventService.aggregate(actor, fromId);
    }
    Set<Klass> klasses = klassService.getKlasses(actor);
    return feedEventService.aggregate(actor, klasses, fromId);
  }

  @ApiOperation("获取Feed阅读量统计")
  @GetMapping(value = "/api/klass-feeds/readstats")
  @Timed
  @Transactional(readOnly = true)
  public Map<Long, Integer> queryStat(@RequestBody List<Long> feedIds) {
    return msgRecordStatService.queryStats(MsgType.FEED, feedIds);
  }

  @ApiOperation("查看指定班级的未读Feed数量")
  @GetMapping(value = "/api/klass-feeds/unread", params = {"klassId", "fromId"})
  @PreAuthorize("hasRole('TEACHER')")
  @Timed
  public KlassFeedReadDTO getKlassPlansUnread(@RequestParam("klassId") Integer klassId,
                                 @RequestParam(value = "fromId", required = false, defaultValue = "0") Long eventId) {

    KlassFeedReadDTO readDTO = new KlassFeedReadDTO();
    readDTO.setCount(klassFeedService.countUnread(klassId, eventId));
    return readDTO;
  }


  @ApiOperation("老师删除Feed")
  @DeleteMapping("/api/klass-feeds/{feedId:\\d+}")
  @PreAuthorize("hasRole('TEACHER')")
  @PermissionRequired(Permission.KLASS_FEED_DELETE)
  @Timed
  public void delete(@Active Actor actor,
                     @PathVariable Long feedId) {
    klassFeedService.userDelete(actor, feedId);
  }
}
