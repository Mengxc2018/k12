package cn.k12soft.servo.web.api;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.KlassPlan;
import cn.k12soft.servo.domain.MsgRecordStat.MsgType;
import cn.k12soft.servo.domain.enumeration.Permission;
import cn.k12soft.servo.domain.enumeration.PlanType;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.KlassPlanEventService;
import cn.k12soft.servo.service.KlassPlanService;
import cn.k12soft.servo.service.MsgRecordStatService;
import cn.k12soft.servo.service.dto.KlassPlanListDTO;
import cn.k12soft.servo.web.form.KlassPlanForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */

@RequestMapping
@RestController
public class KlassPlanResource {

  private final KlassPlanService klassPlanService;
  private final KlassPlanEventService eventService;
  private final MsgRecordStatService msgRecordStatService;

  @Autowired
  KlassPlanResource(KlassPlanService klassPlanService,
                    KlassPlanEventService eventService,
                    MsgRecordStatService msgRecordStatService) {
    this.klassPlanService = klassPlanService;
    this.eventService = eventService;
    this.msgRecordStatService = msgRecordStatService;
  }

  // ================================== API ==================================

  @ApiOperation("查看当前用户所在班级的班级计划")
  @GetMapping(value = "/api/klass-plans", params = {"klassId", "planType", "fromDate"})
  @PreAuthorize("hasRole('TEACHER')")
  @Timed
  @Transactional
  public ResponseEntity<KlassPlanListDTO> getKlassPlans(@Active Actor user,
                                                        @RequestParam("klassId") Integer klassId,
                                                        @RequestParam("planType") PlanType planType,
                                                        @RequestParam(value = "fromDate", required = false) Long eventId) {
    return ResponseEntity.ok(eventService.aggregate(user, klassId, planType, eventId));
  }

  @ApiOperation("查看指定班级的未读计划数量")
  @GetMapping(value = "/api/klass-plans/unread", params = {"klassId", "fromId"})
  @PreAuthorize("hasRole('TEACHER')")
  @Timed
  public Map<PlanType, Integer> getKlassPlansUnread(@RequestParam("klassId") Integer klassId,
                                                    @RequestParam(value = "fromId", required = false, defaultValue = "0") Long eventId) {
    return eventService.countUnread(klassId, eventId);
  }

  @ApiOperation("获取Plan阅读量统计")
  @GetMapping(value = "/api/klass-plans/readstats")
  @Timed
  @Transactional(readOnly = true)
  public Map<Long, Integer> queryStat(@RequestBody List<Long> planIds) {
    return msgRecordStatService.queryStats(MsgType.PLAN, planIds);
  }

  @ApiOperation("当前用户（老师）新增班级计划或者通知")
  @PostMapping("/api/klass-plans")
  @PreAuthorize("hasRole('TEACHER')")
  @PermissionRequired(Permission.KLASS_PLAN_POST)
  @Timed
  public ResponseEntity<KlassPlan> create(@Active Actor actor,
                                          @RequestBody @Valid KlassPlanForm form) {
    KlassPlan klassPlan = klassPlanService.create(actor, form);
    return ResponseEntity.ok(klassPlan);
  }

  @ApiOperation("当前用户（老师）更新班级计划或者通知")
  @PutMapping("/api/klass-plans/{planId:\\d+}")
  @PreAuthorize("hasRole('TEACHER')")
  @PermissionRequired(Permission.KLASS_PLAN_PUT)
  @Timed
  public ResponseEntity<KlassPlan> update(@Active Actor actor,
                                          @PathVariable Integer planId,
                                          @RequestBody KlassPlanForm form) {
    KlassPlan klassPlan = klassPlanService.update(planId, form);
    return ResponseEntity.ok(klassPlan);
  }
}
