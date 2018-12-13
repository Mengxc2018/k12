package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_PLAN_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_PLAN_GET;
import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_PLAN_POST;
import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_PLAN_PUT;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.KlassPlan;
import cn.k12soft.servo.domain.enumeration.PlanType;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.KlassPlanService;
import cn.k12soft.servo.web.form.KlassPlanForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@RestController
@RequestMapping("/management/klass-plans")
public class KlassPlanManagement {

  private final KlassPlanService klassPlanService;

  @Autowired
  public KlassPlanManagement(KlassPlanService klassPlanService) {
    this.klassPlanService = klassPlanService;
  }

  @ApiOperation("创建班级计划")
  @PostMapping
  @PermissionRequired(KLASS_PLAN_POST)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public KlassPlan create(@Active Actor actor, @RequestBody @Valid KlassPlanForm form) {
    return klassPlanService.create(actor, form);
  }

  @ApiOperation("更新班级计划")
  @PutMapping("/{planId:\\d+}")
  @PermissionRequired(KLASS_PLAN_PUT)
  @Timed
  public KlassPlan update(@PathVariable Integer planId,
                          @RequestBody @Valid KlassPlanForm form) {
    return klassPlanService.update(planId, form);
  }

//  @ApiOperation("查看指定班级的班级计划")
//  @GetMapping(params = {"klassId", "planType", "fromDate"})
//  @PermissionRequired(KLASS_PLAN_GET)
//  @Timed
//  public List<KlassPlan> query(@Active Actor actor,
//                               @RequestParam Map<String, String> params) {
//    throw new UnsupportedOperationException("KlassPlanManagement.query");
//  }


  @Timed
  public ResponseEntity<KlassPlan> getKlassPlan(@PathVariable Integer planId) {
    return ResponseEntity.ok(klassPlanService.getKlassPlan(planId));
  }

  @ApiOperation("查看班级计划")
  @GetMapping(value = "", params = {"klassId", "planType"})
  @PermissionRequired(KLASS_PLAN_GET)
  @Timed
  public ResponseEntity<List<KlassPlan>> getAllKlassPlans(
    @RequestParam("klassId") Integer klassId,
    @RequestParam("planType") PlanType planType,
    @RequestParam(value = "fromDate", required = false) Instant fromDate) {
    List<KlassPlan> klassPlans;
    if (fromDate == null) {
      klassPlans = klassPlanService.findAllByKlassAndType(klassId, planType);
    } else {
      klassPlans = klassPlanService.findAllByKlassAndTypeAndFrom(klassId, planType, fromDate);
    }
    return ResponseEntity.ok(klassPlans);
  }

  @ApiOperation("删除班级计划")
  @PermissionRequired(KLASS_PLAN_DELETE)
  @DeleteMapping("/{planId:\\d+}")
  @Timed
  public ResponseEntity<Void> deleteKlassPlan(@PathVariable Integer planId) {
    klassPlanService.delete(planId);
    return ResponseEntity.ok().build();
  }
}
