package cn.k12soft.servo.module.duty.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil.TRUE_FALSE;
import cn.k12soft.servo.module.duty.domain.dto.DutyDTO;
import cn.k12soft.servo.module.duty.domain.form.DutyForm;
import cn.k12soft.servo.module.duty.service.DutyService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("duty/management")
public class DutyManagement {

    private final DutyService dutyService;

    @Autowired
    public DutyManagement(DutyService dutyService) {
        this.dutyService = dutyService;
    }

    /**
     * 新增职务
     * @param forms
     * @return
     */
    @ApiOperation("新增职务(可批量新增)")
    @PostMapping
    public List<DutyDTO> create(@Active Actor actor,
                                @RequestBody List<DutyForm> forms){
        return dutyService.create(actor, forms);
    }

    /**
     * 修改职务
     * @param form
     * @param dutyId
     * @return
     */
    @ApiOperation("修改职务")
    @PutMapping
    public DutyDTO update(@Active Actor actor,
                          @RequestBody @Valid DutyForm form,
                          @RequestParam("dutyId") Integer dutyId){
        return dutyService.update(actor, form, dutyId);
    }

    @ApiOperation("查询,为TRUE，查询基层职务；为FALSE，查询非基层职务")
    @GetMapping
    public Collection<DutyDTO> substratumQuery(@Active Actor actor,
                                     @RequestParam(value = "isSubstratum", required = false) TRUE_FALSE isSubstratum){
        return dutyService.query(actor, isSubstratum);
    }

    @ApiOperation("查询所有职务")
    @GetMapping("/queryAll")
    public Collection<DutyDTO> query(@Active Actor actor){
        return dutyService.queryUnSunstratum(actor);

    }

    @ApiOperation("删除职务")
    @DeleteMapping("/{dutyId:\\d+}")
    public void deleteDuty(@Active Actor actor,
                           @PathVariable @Valid Integer dutyId){
        dutyService.delete(new Long(dutyId));
    }


}
