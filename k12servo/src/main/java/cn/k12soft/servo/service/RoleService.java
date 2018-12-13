package cn.k12soft.servo.service;

import static com.google.common.base.Strings.isNullOrEmpty;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Role;
import cn.k12soft.servo.domain.enumeration.Permission;
import cn.k12soft.servo.repository.RoleRepository;
import cn.k12soft.servo.web.form.RoleForm;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {

  private final RoleRepository repository;

  @Autowired
  public RoleService(RoleRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public List<Role> getRoles() {
    return repository.findAll();
  }

  public Role createRole(Actor actor, RoleForm form) {
    Role role = new Role(actor.getSchoolId(), form.getName(), form.getDescription());
    Set<Permission> permissions = form.getPermissions();
    if (permissions != null && !permissions.isEmpty()) {
      role.getPermissions().addAll(permissions);
    }
    return repository.save(role);
  }

  public Role updateRole(Integer roleId, RoleForm form) {
    Role role = repository.findOne(roleId);
    if (!isNullOrEmpty(form.getName())) {
      role.setName(form.getName());
    }
    if (!isNullOrEmpty(form.getDescription())) {
      role.setDescription(form.getDescription());
    }
    Set<Permission> permissionIds = form.getPermissions();
    if (permissionIds != null && !permissionIds.isEmpty()) {
      role.getPermissions().addAll(permissionIds);
    }
    return role;
  }

  public void deleteRole(Integer roleId) {
    repository.delete(roleId);
  }

  public Role getRoleByName(Integer schoolId, String root) {
    return repository.findBySchoolIdAndName(schoolId, root);
  }

  public Role save(Role rootRole) {
    return repository.save(rootRole);
  }
}
