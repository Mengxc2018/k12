package cn.k12soft.servo.module.employees.service.mapper;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Teaching;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.module.attendanceCount.domain.dto.EntityMapperToList;
import cn.k12soft.servo.module.department.domain.Dept;
import cn.k12soft.servo.module.department.repository.DeptRepository;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.domain.dto.EmployeeDTO;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.TeachingRepository;
import cn.k12soft.servo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class EmployeeMapper extends EntityMapperToList<Employee, EmployeeDTO> {

    private final UserRepository userRepository;
    private final ActorRepository actorRepository;
    private final TeachingRepository teachingRepository;
    private final DeptRepository deptRepository;

    @Autowired
    public EmployeeMapper(UserRepository userRepository,
                          ActorRepository actorRepository,
                          TeachingRepository teachingRepository, DeptRepository deptRepository) {
        this.userRepository = userRepository;
        this.actorRepository = actorRepository;
        this.teachingRepository = teachingRepository;
        this.deptRepository = deptRepository;
    }

    @Override
    protected EmployeeDTO convert(Employee employee) {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        if(employee.getId() != null){
            Actor actor = actorRepository.findOne(employee.getActorId());
            User user = userRepository.getOne(actor.getUserId());
            String userByMasterName = "";
            if (employee.getParentActorId() != null && employee.getParentActorId() != 0){
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

            Dept dept = employee.getDeptId() == null ? null : deptRepository.findOne(Long.parseLong(employee.getDeptId().toString()));
            if (employee.getDeptId() == null){
                dept = null;
            }else{
                dept = deptRepository.findOne(Long.parseLong(employee.getDeptId().toString()));
            }

            return new EmployeeDTO(
                    employee.getId(),
                    employee.getFileId(),
                    employee.getActorId(),
                    user.getUsername(),
                    employee.getActorNum(),
                    dept,
                    employee.getProbation(),
                    employee.getSalaryProbationer(),
                    employee.getSalary(),
                    employee.getDateJoinAt(),
                    employee.getDateOfficialAt(),
                    employee.getDateRegisterAt(),
                    employee.getIdCard(),
                    employee.getSex(),
                    employee.getMobile(),
                    employee.getEmergencyContact(),
                    employee.getEmergencyContactMobile(),
                    employee.getNativePlace(),
                    employee.getNation(),
                    employee.getPoliticsStatus(),
                    employee.getIsMarry(),
                    employee.getHjAddress(),
                    employee.getAddress(),
                    employee.getEducation(),
                    employee.getGraduateSchool(),
                    employee.getSpecialty(),
                    employee.getCgfns(),
                    employee.getUseForm(),
                    employee.getIsContract(),
                    employee.getContractDateStart(),
                    employee.getContractDateEnd(),
                    employee.getRenewRemind(),
                    employee.getIsRenew(),
                    employee.getRenewDateStart(),
                    employee.getRenewDateEnd(),
                    employee.getRemark(),
                    employee.getIsGraduate(),
                    employee.getIsHasDiploma(),
                    employee.getIsOfficial(),
                    employee.getIsLeave(),
                    employee.getLeaveAt(),
                    employee.getIsHasSocial(),
                    employee.getDuty(),
                    employee.getParentActorId(),
                    employee.getOvertime(),
                    employee.getRest(),
                    employee.getAnnual(),
                    employee.getSick(),
                    employee.getBarth(),
                    employee.getBarthWith(),
                    employee.getMarry(),
                    employee.getFuneral(),
                    employee.isShow(),
                    employee.getSchoolId(),
                    employee.getCreatedAt(),
                    userByMasterName,
                    klassIds,
                    klassNames,
                    courseNames,
                    courseIds
                    );
        }

        return employeeDTO;
    }
}
