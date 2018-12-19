package cn.k12soft.servo.module.employees.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.employees.domain.dto.EmployeeDTO;
import cn.k12soft.servo.module.employees.domain.dto.EpleToSalDTO;
import cn.k12soft.servo.module.employees.domain.form.EmpCommitForm;
import cn.k12soft.servo.module.employees.domain.form.EmployeeForm;
import cn.k12soft.servo.module.employees.service.EmployeeService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee/management")
public class EmpolyeeManagement {

    private final EmployeeService employeeService;

    @Autowired
    public EmpolyeeManagement(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 员工分配，可批量
     * @param form
     * @return
     */
    @ApiOperation("新增员工职位分配，可批量分配，分配到当前登录的学校")
    @PostMapping
    public List<EmployeeDTO> create(@Active Actor actor,
                                    @RequestBody @Valid EmployeeForm form){
        return employeeService.create(actor, form);
    }


//    /**
//     * 分配上级
//     * 前端从teacher表里面取到的数据，teacher表的id为actorId，所以传到这个接口的id为actorid，通过actorId找到员工的数据，然后分配
//     * @param actor
//     * @param parentActorId
//     * @return
//     */
//    @ApiOperation("分配、更新上级")
//    @PutMapping("/{parentActorId:\\d+}")
//    public EmployeeDTO addParent(@Active Actor actor,
//                                 @RequestParam("actorId") Integer actorId,
//                                 @PathVariable @Valid Integer parentActorId){
//        return employeeService.addParend(actor, actorId, parentActorId);
//
//    }

    /**
     * 只删除员工上的职务，相当于把职务设置成null，但是该员工表的数据还在
     * @param actor
     * @param employeeId
     */
    @ApiOperation("删除")
    @DeleteMapping("/delete/{employeeId:\\d+}")
    public void delete(@Active Actor actor,
                       @PathVariable("employeeId") Integer employeeId){
        employeeService.deleteByActorId(employeeId);
    }

    @ApiOperation("修改职位")
    @PutMapping("/updateDuty")
    public EmployeeDTO update(@Active Actor actor,
                              @RequestBody @Valid EmployeeForm form){
        return employeeService.update(form);
    }

    @ApiOperation("条件查询员工，有actorId时，按照actorId查询，没有时，查询全部")
    @GetMapping
    public Collection<EmployeeDTO> query(@Active Actor actor,
                                         @RequestParam(value = "actorId", required = false) Integer actorId){
        return employeeService.query(actor, actorId);
    }

    @ApiOperation("查询某个员工信息")
    @GetMapping("/queryOne")
    public EmployeeDTO queryOne(@Active Actor actor){
        return employeeService.queryOne(actor);
    }

    /**
     * 查询已分配的员工
     * @param actor
     * @return
     */
    @ApiOperation("查询已分配的员工")
    @GetMapping("/assigned")
    public List<Map<String, Object>> queryAssigned(@Active Actor actor){
        return employeeService.findAssigned(actor);
    }

    /**
     * 查询未分配的员工
     * @param actor
     * @return
     */
    @ApiOperation("查询未分配的员工")
    @GetMapping("/unAssigned")
    public List<Map<String, Object>> queryUnAssigned(@Active Actor actor){
        return employeeService.findUnAssigned(actor);
    }

    @ApiOperation("当角色只有一个教师角色，返回自己的信息；当角色不包含教师角色，查询所有员工，包括分配的和未分配的")
    @GetMapping("/queryAll")
    public Collection<EmployeeDTO> queryAll(@Active Actor actor){
        return employeeService.queryAll(actor);
    }

    @ApiOperation("员工表查询所有员工薪资信息")
    @GetMapping("/querySocialSecurity")
    public Collection<EpleToSalDTO> employeeOfSalart(@Active Actor actor){
        return employeeService.findSalart(actor);
    }

    // 查询已离职的教师数据（条件：学校、时间、月查询）
    @ApiOperation("查询已离职的员工")
    @GetMapping("/findLeave")
    public Collection<EmployeeDTO> findLeave(@Active Actor actor, @RequestParam @Valid LocalDate localDate){
        return employeeService.findLeave(actor, localDate);
    }

    @ApiOperation("员工提交自己员工信息")
    @PostMapping("/empCommit")
    public void empCommit(@Active Actor actor,
                       @RequestBody @Valid EmpCommitForm form){
        employeeService.empCommit(actor, form);
    }

    @ApiOperation("管理员获取员工自己提交的信息列表")
    @GetMapping("/findEmpCommit")
    public Collection<EmployeeDTO> findEmpCommit(@Active Actor actor
//                       @RequestBody @Valid EmpCommitForm form
    ){
        return employeeService.findEmpCommit(actor);
    }

}
