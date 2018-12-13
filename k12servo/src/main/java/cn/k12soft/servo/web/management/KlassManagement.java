package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_GET;
import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_POST;
import static cn.k12soft.servo.domain.enumeration.Permission.KLASS_PUT;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Grade;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.GradeService;
import cn.k12soft.servo.service.KlassService;
import cn.k12soft.servo.web.form.KlassForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@RestController
@RequestMapping("/management/klasses")
public class KlassManagement {

  private final KlassService klassService;

  @Autowired
  public KlassManagement(KlassService klassService) {
    this.klassService = klassService;
  }


  @ApiOperation("获取班级列表，不包括已经毕业的班级")
  @GetMapping
  @PermissionRequired(KLASS_GET)
  @Timed
  public List<Klass> getKlass(@Active Actor actor) {
    return klassService.getAllOfSchool(actor.getSchoolId());
  }

  @ApiOperation("获取老师所在班级列表，不包括已经毕业的班级")
  @GetMapping("/findKlassOfTeacher")
  @PermissionRequired(KLASS_GET)
  @Timed
  public List<Klass> findKlassOfTeacher(@Active Actor actor) {
    return klassService.findKlassOfTeacher(actor);
  }

  @ApiOperation("获取已经毕业班级列表")
  @GetMapping("/goup")
  @PermissionRequired(KLASS_GET)
  @Timed
  public List<Klass> getKlassGoup(@Active Actor actor) {
    return klassService.getGoupOfSchool(actor.getSchoolId());
  }

  @ApiOperation("获取指定班级信息")
  @GetMapping("/{klassId:\\d+}")
  @PermissionRequired(KLASS_GET)
  @Timed
  public Klass fetch(@PathVariable Integer klassId) {
    return klassService.get(klassId);
  }


  @ApiOperation("新增班级信息")
  @PostMapping
  @PermissionRequired(KLASS_POST)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public Klass create(@RequestBody @Valid KlassForm form) {
    return klassService.create(form);
  }

  @ApiOperation("更新班级信息")
  @PutMapping("/{klassId:\\d+}")
  @PermissionRequired(KLASS_PUT)
  @Timed
  public Klass update(@PathVariable Integer klassId,
                      @RequestBody KlassForm form) {
    return klassService.update(klassId, form);
  }

  @ApiOperation("删除班级")
  @DeleteMapping("/{klassId:\\d+}")
  @PermissionRequired(KLASS_DELETE)
  @Timed
  public void delete(@PathVariable(name = "klassId") Integer id) {
    klassService.delete(id);
  }

  @ApiOperation("升班管理")
  @PutMapping("/goup")
  @PermissionRequired(KLASS_PUT)
  @Timed
  public void goup(@RequestParam @Valid Integer schoolId){
    klassService.goup(schoolId);
  }
}
