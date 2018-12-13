package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Teaching;
import cn.k12soft.servo.module.duty.domain.dto.DutyDTO;
import cn.k12soft.servo.module.duty.repositpry.DutyRepository;
import cn.k12soft.servo.module.duty.service.mapper.DutyMapper;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.employees.service.mapper.EmployeeMapper;
import cn.k12soft.servo.repository.TeachingRepository;
import cn.k12soft.servo.service.dto.ActorDTO;
import cn.k12soft.servo.service.dto.TeacherDTO;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
@Component
public class TeacherMapper extends EntityMapper<Actor, TeacherDTO> {

  private final TeachingRepository teachingRepository;
  private final ActorMapper actorMapper;
  private final KlassMapper klassMapper;

  @Autowired
  public TeacherMapper(TeachingRepository teachingRepository,
                       ActorMapper actorMapper,
                       KlassMapper klassMapper) {
    this.teachingRepository = teachingRepository;
    this.actorMapper = actorMapper;
    this.klassMapper = klassMapper;
  }

  @Override
  protected TeacherDTO convert(Actor actor) {
    Integer teacherId = actor.getId();
//    Employee employee = employeeRepository.findByActorId(actor.getId());
//    DutyDTO dutyDTO = new DutyDTO();
//      if (employee != null){
//          if (employee.getDuty() != null){
//              dutyDTO =  dutyMapper.toDTO(dutyRepository.findOne(Long.parseLong(employee.getDuty().getId().toString())));
//          }
//    }else {
//        dutyDTO = new DutyDTO();
//        employee = new Employee();
//    }

    return new TeacherDTO()
      .setId(teacherId)
      .setActor(actorMapper.toDTO(actor))
//      .setDutyDTO(dutyDTO)
//      .setEmployeeDTO(employeeMapper.toDTO(employee))
      .setRoles(actor.getRoles())
      .setKlass(klassMapper.toDTOs(teachingRepository.findAllByTeacherId(actor.getId())
        .stream()
        .map(Teaching::getKlass)
        .collect(Collectors.toList())));
  }

  public TeacherDTO fromTeacherId(Integer teacherId) {
    ActorDTO actorDTO = actorMapper.fromActorId(teacherId);
    return new TeacherDTO()
      .setId(teacherId)
      .setActor(actorDTO)
      .setKlass(klassMapper.toDTOs(teachingRepository.findAllByTeacherId(teacherId)
        .stream()
        .map(Teaching::getKlass)
        .collect(Collectors.toList())));
  }
}
