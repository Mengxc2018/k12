package cn.k12soft.servo.module.department.controller;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.department.domain.Dept;
import cn.k12soft.servo.module.department.domain.form.DeptForm;
import cn.k12soft.servo.module.department.service.DeptService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department/management")
public class DeptController {

    private final DeptService service;

    public DeptController(DeptService service) {
        this.service = service;
    }

    @ApiOperation("添加部门，可添加多个")
    @PostMapping("/create")
    public void voidcreate(@Active Actor actor,
                       @RequestBody @Valid List<DeptForm> forms){
        this.service.create(forms);
    }

    @ApiOperation("查询当前角色所在学校或地区的部门列表，角色为家长或者老师查不到")
    @GetMapping("/findBy")
    public List<Dept> findBy(@Active Actor actor){
        return service.findBy(actor);
    }

    @ApiOperation("查询所有部门，角色为家长或者老师查不到")
    @GetMapping("/findAll")
    public List<Dept> findAll(@Active Actor actor){
        return service.findByAll(actor);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/deleteBy")
    public void deleteBy(@Active Actor actor,
                         @RequestParam @Valid String ids){
        service.deleteBy(ids);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/updateBy")
    public void updateBy(@Active Actor actor,
                         @RequestParam @Valid Integer id,
                         @RequestBody @Valid DeptForm form){
        service.updateBy(id, form);
    }







}

