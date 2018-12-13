package cn.k12soft.servo.module.schedulingPerson.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.schedulingPerson.domian.SchedulingPerson;
import cn.k12soft.servo.module.schedulingPerson.domian.dto.SchedulingPersonDTO;
import cn.k12soft.servo.module.schedulingPerson.domian.form.SchedulingPersonForm;
import cn.k12soft.servo.module.schedulingPerson.mapper.SchedulingPersonMapper;
import cn.k12soft.servo.module.schedulingPerson.repository.SchedulingPersonRepository;
import cn.k12soft.servo.module.schedulingPerson.service.SchedulingPersonService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.AbstractRepositoryService;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/scheduilingPerson/management")
public class SchedulingPersonManagement extends AbstractRepositoryService<SchedulingPerson, Long, SchedulingPersonRepository> {
    private final SchedulingPersonService schedulingPersonService;

    public SchedulingPersonManagement(SchedulingPersonRepository Repository,
                                      SchedulingPersonService schedulingPersonService) {
        super(Repository);
        this.schedulingPersonService = schedulingPersonService;
    }

    /**
     *
     * @param actor
     * @return
     */
    @GetMapping
    @ApiOperation("查询排班")
    public Collection<SchedulingPersonDTO> query(@Active Actor actor){
        return schedulingPersonService.getBySchoolId(actor);
    }


    @ApiOperation("查询个人排班时间")
    @GetMapping("/queryOne")
    @Timed
    public Collection<SchedulingPersonDTO> queryOne(@Active Actor actor){
        Collection<SchedulingPersonDTO> list = schedulingPersonService.getAllByActorIdAndSchoolId(actor);
        return list;
    }

    /**
     * 可批量上传用户 id ，绑定同一个scheduling
     * 批量上传格式； "id1,id2,id3...idn"
     * @param form
     * @return
     */
    @PostMapping
    @ApiOperation("人员排班分配,schedulingId只传入一个id；userId可批量传入，格式； 'id1,id2,id3...idn'")
    public void create(@Active Actor actor,
                       @RequestBody @Valid SchedulingPersonForm form){
        schedulingPersonService.create(actor, form);
    }

    /**
     * @param schedulingPersonId
     * @param form
     * @return
     */
    @ApiOperation("人员排班更改")
    @PutMapping("/{schedulingPersonId:\\d+}")
    @Timed
    public List<SchedulingPersonDTO> update(@Active Actor actor,
                                            @PathVariable("schedulingPersonId") Integer schedulingPersonId,
                                            @RequestBody @Valid SchedulingPersonForm form){
        return schedulingPersonService.updated(form, schedulingPersonId);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{schedulingPersonId:\\d+}")
    @Timed
    public void delete(@Active Actor actor,
                       @PathVariable("schedulingPersonId") Integer schedulingPersonId){
        getRepository().delete((long)schedulingPersonId);
    }

}
