package cn.k12soft.servo.module.VacationTeacher.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.AttendanceTeacher.domain.AttendPeriodTeac.PeriodTeacType;
import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.VacationTeacher.domain.dto.VacationTeacherDTO;
import cn.k12soft.servo.module.VacationTeacher.form.VacationTeacherForm;
import cn.k12soft.servo.module.VacationTeacher.mapper.VacationTeacherMapper;
import cn.k12soft.servo.module.VacationTeacher.service.VacationTeacherService;
import cn.k12soft.servo.module.applyFor.domain.dto.ApplyForDTO;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

/**
 * actor:mxc
 */
@RestController
@RequestMapping("/api/vacationTeacher")
public class VacationTeacherManagement {

    private final VacationTeacherService vacationTeacherService;
    private final VacationTeacherMapper vacationTeacherMapper;

    @Autowired
    public VacationTeacherManagement(VacationTeacherService vacationTeacherService, VacationTeacherMapper vacationTeacherMapper) {
        this.vacationTeacherService = vacationTeacherService;
        this.vacationTeacherMapper = vacationTeacherMapper;
    }

    /**
     * 用户id为actorId
     * @param form
     * @return
     */
    @ApiOperation("教师请假")
    @PostMapping
    public VacationTeacherDTO create(@Active Actor actor,
                                     @RequestBody @Valid VacationTeacherForm form,
                                     @RequestParam @Valid Integer type){
        // 判断开始日期是否大于结束日期
        if (form.getFromDate().compareTo(form.getToDate()) >= 0){
            throw new IllegalArgumentException("From must be less than to!");
        }
        return vacationTeacherService.create(form, actor, type);
    }

    /**
     * 周查询、月查询
     * @param type
     * @param date
     * @return
     */
    @ApiOperation("按条件查询指定教师的请假记录")
    @GetMapping("/teacher")
    public Collection<VacationTeacherDTO> query(@Active Actor actor,
                                            @RequestParam(name = "activitisTypes") PeriodTeacType type,
                                            @RequestParam(name = "isGone") VacationTeacherUtil.ISGONE isGone,
                                            @RequestParam(name = "date")LocalDate date){

        return vacationTeacherService.queryByType(actor, date ,isGone, type);
    }

    @ApiOperation("请假查询某员工所有")
    @GetMapping
    public Collection<VacationTeacherDTO> query(@Active Actor actor,
                                                @RequestParam(name = "month",required = false) Integer month,
                                                @RequestParam(name = "year") Integer year){
        return vacationTeacherService.queryAll(actor,month,year);
    }

    @ApiOperation("请假查询某员工单条记录")
    @GetMapping("/queryOne")
    public VacationTeacherDTO query(@Active Actor actor,
                                    @RequestParam(name = "id",required = false) Integer id){
        return vacationTeacherMapper.toDTO(vacationTeacherService.get(Long.parseLong(id.toString())));
    }

    @ApiOperation("查询员工的申请记录")
    @GetMapping("/queryApply")
    public Collection<VacationTeacherDTO> queryApply(@Active Actor actor, @RequestParam LocalDate specialDate){
        return vacationTeacherService.findBySchoolIdAndActorIdAndCreatedAtBetween(actor, specialDate);
    }



}
