package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.ROLE_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.ROLE_GET;
import static cn.k12soft.servo.domain.enumeration.Permission.ROLE_POST;
import static cn.k12soft.servo.domain.enumeration.Permission.ROLE_PUT;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Role;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.RoleService;
import cn.k12soft.servo.web.form.RoleForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@RestController
@RequestMapping("/management/roles")
public class RoleManagement {

  private final RoleService roleService;

  @Autowired
  public RoleManagement(RoleService roleService) {
    this.roleService = roleService;
  }

  @ApiOperation("获取角色列表")
  @GetMapping
  @PermissionRequired(ROLE_GET)
  @Timed
  public List<Role> getRoles(@Active User user) {
    return roleService.getRoles();
  }

  @ApiOperation("创建新角色")
  @PostMapping
  @PermissionRequired(ROLE_POST)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public Role createRole(@Active Actor actor,
                         @RequestBody @Valid RoleForm form) {
    return roleService.createRole(actor, form);
  }

  @ApiOperation("更新角色")
  @PutMapping("/{roleId:\\d+}")
  @PermissionRequired(ROLE_PUT)
  @Timed
  public Role update(@PathVariable Integer roleId,
                     @RequestBody RoleForm form) {
    return roleService.updateRole(roleId, form);
  }

  @ApiOperation("删除角色")
  @DeleteMapping("/{roleId:\\d+}")
  @PermissionRequired(ROLE_DELETE)
  @Timed
  public void delete(@PathVariable Integer roleId) {
    roleService.deleteRole(roleId);
  }
}
