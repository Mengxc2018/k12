package cn.k12soft.servo.module.employees.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Teaching;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.attendanceCount.domain.dto.EntityMapperToList;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.domain.dto.EmployeeDTO;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.TeachingRepository;
import cn.k12soft.servo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class EmployeeMapper extends EntityMapperToList<Employee, EmployeeDTO> {

    private final UserRepository userRepository;
    private final ActorRepository actorRepository;
    private final TeachingRepository teachingRepository;

    @Autowired
    public EmployeeMapper(UserRepository userRepository,
                          ActorRepository actorRepository,
                          TeachingRepository teachingRepository) {
        this.userRepository = userRepository;
        this.actorRepository = actorRepository;
        this.teachingRepository = teachingRepository;
    }

    @Override
    protected EmployeeDTO convert(Employee employee) {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        if(employee.getId() != null){
            Actor actor = actorRepository.findOne(employee.getActorId());
            User user = userRepository.getOne(actor.getUserId());
            String userByMasterName = "";
            if (employee.getParentActorId() != null){
                Actor actorByMaster = actorRepository.findOne(employee.getParentActorId());
                User userByMaster = userRepository.findOne(actorByMaster.getUserId());
                userByMasterName = userByMaster.getUsername();
            }

            // 教师管理的班级数据
            String klassNames = "";
            String klassIds = "";
            String courseNames = "";
            String courseIds = "";
            Collection<Teaching> teachings = teachingRepository.findAllByTeacherId(actor.getId());
            for (Teaching teaching : teachings){
                klassNames = klassNames + teaching.getKlass().getName() + " ";
                klassIds = klassIds + teaching.getKlass().getId() + " ";
                courseNames = courseNames + teaching.getCourse().getName() + " ";
                courseIds = courseIds + teaching.getCourse().getId() + " ";
            }

            return new EmployeeDTO(
                    employee.getId(),
                    employee.getDuty(),
                    employee.getActorId(),
                    user.getUsername(),
                    employee.getActorNum(),
                    user.getMobile(),
                    employee.getParentActorId(),
                    userByMasterName,
                    employee.getSchoolId(),
                    employee.getEmployeeBasic(),
                    klassNames,
                    klassIds,
                    courseNames,
                    courseIds,
                    employee.isShow(),
                    employee.getOvertime(),
                    employee.getRest(),
                    employee.getAnnual(),
                    employee.getSick(),
                    employee.getBarth(),
                    employee.getBarthWith(),
                    employee.getMarry(),
                    employee.getFuneral()
            );

        }

        return employeeDTO;
    }
}
