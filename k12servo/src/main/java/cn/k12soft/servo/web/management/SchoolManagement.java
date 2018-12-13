package cn.k12soft.servo.web.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.SchoolService;
import cn.k12soft.servo.service.dto.SchoolDTO;
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


  @ApiOperation("按照条件查询学校，需要什么条件加什么条件，不需要的条件可以不加，条件不能为空，最少一个字段，可以将这个字段值写为null")
  @PatchMapping("/findBy")
  public List<School> findBy(@Active Actor actor,
                             @RequestBody(required = false) @Valid School school){
    return this.service.findBY(school);
  }

  @ApiOperation("学校添加部门")
  @PutMapping("/addDept")
  public School addDept(@Active Actor actor,
                           @RequestParam @Valid Integer id,
                           @RequestParam @Valid String deptIds){
    return this.service.addDept(actor, id, deptIds);
  }

  @ApiOperation("学校移除部门")
  @DeleteMapping("/deleteBy")
  public void deleteBy(@Active Actor actor,
                       @RequestParam @Valid String deptIds){
    this.service.deleteBy(actor, deptIds);
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

