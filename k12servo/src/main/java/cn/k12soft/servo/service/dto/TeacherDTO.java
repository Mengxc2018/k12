package cn.k12soft.servo.service.dto;

import cn.k12soft.servo.domain.Role;
import cn.k12soft.servo.module.duty.domain.Duty;
import cn.k12soft.servo.module.duty.domain.dto.DutyDTO;
import cn.k12soft.servo.module.employees.domain.dto.EmployeeDTO;

import java.util.Collection;
import java.util.Set;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
public class TeacherDTO {

  private Integer id;
  private ActorDTO actor;
//  private DutyDTO dutyDTO;
//  private EmployeeDTO employeeDTO;
  private Set<Role> roles;
  private Collection<KlassDTO> klass;

  public Integer getId() {
    return id;
  }

  public TeacherDTO setId(Integer id) {
    this.id = id;
    return this;
  }

  public ActorDTO getActor() {
    return actor;
  }

  public TeacherDTO setActor(ActorDTO actor) {
    this.actor = actor;
    return this;
  }

//  public DutyDTO getDutyDTO() {
//    return dutyDTO;
//  }
//
//  public TeacherDTO setDutyDTO(DutyDTO dutyDTO) {
//    this.dutyDTO = dutyDTO;
//    return this;
//  }

  public Set<Role> getRoles() {
    return roles;
  }

  public TeacherDTO setRoles(Set<Role> roles) {
    this.roles = roles;
    return this;
  }

  public Collection<KlassDTO> getKlass() {
    return klass;
  }

  public TeacherDTO setKlass(Collection<KlassDTO> klass) {
    this.klass = klass;
    return this;
  }

//  public EmployeeDTO getEmployeeDTO() {
//    return employeeDTO;
//  }
//
//  public TeacherDTO setEmployeeDTO(EmployeeDTO employeeDTO) {
//    this.employeeDTO = employeeDTO;
//    return this;
//  }

  @Override
  public String toString() {
    return "TeacherDTO{" +
            "id=" + id +
            ", actor=" + actor +
            ", roles=" + roles +
            ", klass=" + klass +
            '}';
  }
}
