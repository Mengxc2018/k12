package cn.k12soft.servo.web.management;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.SchoolService;
import cn.k12soft.servo.web.form.SchoolForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static cn.k12soft.servo.domain.enumeration.Permission.*;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */

@RestController
@RequestMapping("/management/schools")
public class SchoolManagement {

  private final SchoolService service;

  @Autowired
  public SchoolManagement(SchoolService service) {
    this.service = service;
  }

  @ApiOperation("获取学校列表")
  @GetMapping
  @PermissionRequired(SCHOOL_VIEW)
  @Timed
  public List<School> getAll() {
    return service.getAll();
  }

  @ApiOperation("新建学校")
  @PostMapping
  @PermissionRequired(SCHOOL_CREATE)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public School create(@RequestBody @Valid SchoolForm form) {
    return service.create(form);
  }

  @ApiOperation("更新学校信息")
  @PutMapping("/{schoolId:\\d+}")
  @PermissionRequired(SCHOOL_UPDATE)
  @Timed
  public School update(@PathVariable Integer schoolId,
                       @RequestBody SchoolForm form) {
    return service.update(schoolId, form);
  }

  @ApiOperation("删除学校")
  @DeleteMapping("/{id:\\d+}")
  @PermissionRequired(SCHOOL_DELETE)
  @Timed
  public void delete(@PathVariable Integer id){

      service.delete(id);

  }
}

