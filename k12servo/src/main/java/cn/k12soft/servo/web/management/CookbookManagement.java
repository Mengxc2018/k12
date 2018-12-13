package cn.k12soft.servo.web.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Cookbook;
import cn.k12soft.servo.domain.enumeration.Permission;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.CookbookService;
import cn.k12soft.servo.web.form.CookbookForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/management/cookbooks")
public class CookbookManagement {

  private final CookbookService cookbookService;

  @ApiOperation("创建食谱")
  @PostMapping
  @PermissionRequired(Permission.COOKBOOK_CREATE)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public Cookbook create(@Active Actor actor, @RequestBody @Valid CookbookForm form) {
    return cookbookService.create(actor, form);
  }

  public CookbookManagement(CookbookService cookbookService) {
    this.cookbookService = cookbookService;
  }

  @ApiOperation("获取食谱列表")
  @GetMapping(params = {"schoolId"})
  @PermissionRequired(Permission.COOKBOOK_VIEW)
  @Timed
  public List<Cookbook> getAll(@RequestParam("schoolId") Integer schoolId,
                               @RequestParam(value = "fromDate", required = false) Instant fromDate) {

    List<Cookbook> cookbooks;
    if (fromDate == null) {
      cookbooks = cookbookService.findAllBySchool(schoolId);
    } else {
      cookbooks = cookbookService.findAllBySchoolAndFrom(schoolId, fromDate);
    }

    return cookbooks;
  }

  @ApiOperation("更新食谱")
  @PutMapping("/{cookbookId:\\d+}")
  @PermissionRequired(Permission.COOKBOOK_PUT)
  @Timed
  public Cookbook update(@PathVariable Integer cookbookId,
                         @RequestBody @Valid CookbookForm form) {
    return cookbookService.update(cookbookId, form);
  }


  @ApiOperation("删除食谱")
  @DeleteMapping("/{cookbookId:\\d+}")
  @PermissionRequired(Permission.COOKBOOK_DELETE)
  @Timed
  public void delete(@PathVariable Integer cookbookId) {
    cookbookService.delete(cookbookId);
  }
}
