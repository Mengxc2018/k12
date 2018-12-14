package cn.k12soft.servo.module.activiti.activitisTypes.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.activiti.activitisTypes.domain.dto.ActivitiTypeDTO;
import cn.k12soft.servo.module.activiti.activitisTypes.domain.form.ActivitiTypeForm;
import cn.k12soft.servo.module.activiti.activitisTypes.service.ActivitiTypeService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static cn.k12soft.servo.domain.enumeration.Permission.SCHOOL_VIEW;

@RestController
@RequestMapping("/ProceaaType/management")
public class ActivitiTypeManagement {

    private final ActivitiTypeService activitiTypeService;

    @Autowired
    public ActivitiTypeManagement(ActivitiTypeService activitiTypeService) {
        this.activitiTypeService = activitiTypeService;
    }

    @ApiOperation("新增流程类型")
    @PostMapping
    public ActivitiTypeDTO create(@Active Actor actor,
                                  @RequestBody @Valid ActivitiTypeForm form){
        return activitiTypeService.create(form, actor);
    }

    @ApiOperation("删除流程类型")
    @DeleteMapping("/delete/{id:\\d+}")
    public void create(@PathVariable Long id){
        activitiTypeService.delete(id);
    }

    @ApiOperation("查询流程类型")
    @GetMapping
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    @PermissionRequired(SCHOOL_VIEW)
    public Collection<ActivitiTypeDTO> query(@Active Actor actor){
        return activitiTypeService.query(actor.getSchoolId());
    }

}
