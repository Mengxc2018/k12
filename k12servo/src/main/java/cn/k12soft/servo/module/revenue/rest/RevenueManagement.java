package cn.k12soft.servo.module.revenue.rest;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.employees.service.EmployeeService;
import cn.k12soft.servo.module.revenue.domain.*;
import cn.k12soft.servo.module.revenue.form.PayDetailForm;
import cn.k12soft.servo.module.revenue.form.PayoutForm;
import cn.k12soft.servo.module.revenue.form.TeacherSocialSecurityForm;
import cn.k12soft.servo.module.revenue.service.*;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.util.Times;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

import static cn.k12soft.servo.domain.enumeration.Permission.CHARGE_PLAN_CREATE;
import static cn.k12soft.servo.domain.enumeration.Permission.CHARGE_PLAN_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.CHARGE_PLAN_GET;

@RestController
@Transactional
public class RevenueManagement {

    private PayoutService payoutService;
    private PayoutMainTypeService payoutMainTypeService;
    private PayoutSubTypeService payoutSubTypeService;
    private TeacherSocialSecurityService teacherSocialSecurityService;
    private IncomeService incomeService;
    private IncomeDetailService incomeDetailService;
    private final EmployeeRepository employeeRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public RevenueManagement(PayoutService payoutService,
                             PayoutMainTypeService payoutTypeService,
                             PayoutSubTypeService payoutSubTypeService,
                             TeacherSocialSecurityService teacherSocialSecurityService,
                             IncomeService incomeService,
                             IncomeDetailService incomeDetailService,
                             EmployeeRepository employeeRepository,
                             ActorRepository actorRepository){
        this.payoutService = payoutService;
        this.payoutMainTypeService = payoutTypeService;
        this.payoutSubTypeService = payoutSubTypeService;
        this.teacherSocialSecurityService = teacherSocialSecurityService;
        this.incomeService = incomeService;
        this.incomeDetailService = incomeDetailService;
        this.employeeRepository = employeeRepository;
        this.actorRepository = actorRepository;
    }

    // 个税起征点
    private static final Float TAX_INCOME = 5000f;

    @ApiOperation("添加主类别")
    @PostMapping(value = "/revenue/payoutType/create")
    @PermissionRequired(CHARGE_PLAN_CREATE)
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayoutType(@Active Actor actor, @RequestBody String name){

        if(StringUtils.isBlank(name)){
            return;
        }

        PayoutMainType payoutMainType = this.payoutMainTypeService.findByNameAndSchoolId(name, actor.getSchoolId());
        if(payoutMainType != null){
            return;
        }

        payoutMainType = new PayoutMainType(actor.getSchoolId());
        payoutMainType.setName(name);
        this.payoutMainTypeService.save(payoutMainType);
    }

    @ApiOperation("查询所有主类别")
    @GetMapping(value = "/revenue/payoutType/findAll")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    public List<PayoutMainType> get(@Active Actor actor){
        return this.payoutMainTypeService.findByShoolId(actor.getSchoolId());
    }

    @ApiOperation("添加子类别")
    @PostMapping(value = "/revenue/payoutSubType/create")
    @PermissionRequired(CHARGE_PLAN_CREATE)
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayoutDetail(@Active Actor actor, PayDetailForm form){
        if(StringUtils.isBlank(form.getName())){
            return;
        }
        if(form.getType()<=0){
            return;
        }

        PayoutMainType payoutMainType = this.payoutMainTypeService.get(form.getType());
        if(payoutMainType == null){
            return;
        }

        PayoutSubType payoutSubType = this.payoutSubTypeService.findByNameAndPayoutMainTypeAndSchoolId(form.getName(), payoutMainType, actor.getSchoolId());
        if(payoutSubType != null){
            return;
        }

        payoutSubType = new PayoutSubType(actor.getSchoolId());
        payoutSubType.setName(form.getName());
        payoutSubType.setPayoutMainType(payoutMainType);
        this.payoutSubTypeService.save(payoutSubType);
    }

    @ApiOperation("查询所有子类别")
    @GetMapping(value = "/revenue/payoutSubType/findAll")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    public List<PayoutSubType> getALLPayoutTypeDetail(@Active Actor actor){
        return this.payoutSubTypeService.findBySchoolId(actor.getSchoolId());
    }

    @ApiOperation("修改子类别")
    @PutMapping(value = "/revenue/payoutSubType/update")
    @PermissionRequired(CHARGE_PLAN_CREATE)
    @Timed
    public void updateSubType(@Active Actor actor, @RequestParam(value = "id", required = true) Integer id,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "type") Integer payoutMainTypeId){
        PayoutSubType payoutSubType = this.payoutSubTypeService.get(id);
        if(payoutSubType == null){
            return;
        }
        boolean update = false;
        if(StringUtils.isNotBlank(name)){
            payoutSubType.setName(name);
            update = true;
        }
        if(payoutMainTypeId != null && payoutMainTypeId>0){
            if(payoutSubType.getPayoutMainType().getId() != payoutMainTypeId){
                PayoutMainType payoutMainType = this.payoutMainTypeService.get(payoutMainTypeId);
                if(payoutMainType != null){
                    payoutSubType.setPayoutMainType(payoutMainType);
                    update = true;
                }
            }
        }
        if(update){
            this.payoutSubTypeService.save(payoutSubType);
        }
    }

    @ApiOperation("删除子类别")
    @DeleteMapping(value = "/revenue/payoutSubType/delete")
    @PermissionRequired(CHARGE_PLAN_CREATE)
    @Timed
    public void deleteSubType(@Active Actor actor, @RequestParam(value = "id", required = true) Integer id){
        this.payoutSubTypeService.delete(id);
    }

    @ApiOperation("删除主类别")
    @DeleteMapping(value = "/revenue/payoutMainType/delete")
    @PermissionRequired(CHARGE_PLAN_CREATE)
    @Timed
    public void deleteMainType(@Active Actor actor, @RequestParam(value = "id", required = true) Integer id){
        PayoutMainType payoutMainType = this.payoutMainTypeService.get(id);
        if(payoutMainType == null){
            return;
        }
        this.payoutSubTypeService.deleteByPayoutMainType(payoutMainType); //类别
        this.payoutMainTypeService.delete(id);
    }

    @ApiOperation("添加支出")
    @PostMapping(value = "/revenue/payout/create")
    @PermissionRequired(CHARGE_PLAN_CREATE)
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayout(@Active Actor actor, @RequestBody PayoutForm form){
        PayoutSubType payoutSubType = this.payoutSubTypeService.get(form.getPayoutSubTypeId());
        if(payoutSubType == null){
            return;
        }
        Payout payout = new Payout(actor.getSchoolId());
        payout.setCreatedBy(actor);
        payout.setPayoutSubType(payoutSubType);
        payout.setMoney(form.getMoney());
        payout.setCreateAt(Instant.now());
        payout.setTheYearMonth(Times.time2yyyyMM(System.currentTimeMillis()));
        this.payoutService.save(payout);
    }

    @ApiOperation("支出明细")
    @GetMapping(value = "/revenue/payout/findAll")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    public List<Payout> getAllPayout(@Active Actor actor){
        return this.payoutService.findBySchoolId(actor.getSchoolId());
    }

    @ApiOperation("按主类别查询支出")
    @GetMapping(value = "/revenue/payout/findAllByMainType")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    public List<Payout> getAllPayoutByMainType(@Active Actor actor, @RequestParam(value = "id", required = false) Integer id){
        PayoutMainType payoutMainType = null;
        List<PayoutMainType> mainTypeList = new LinkedList<>();
        if(id != null && id>0){
            payoutMainType = this.payoutMainTypeService.get(id);
            if(payoutMainType == null){
                return null;
            }
            mainTypeList.add(payoutMainType);
        }

        List<PayoutSubType> subTypeList = null;
        if(mainTypeList.size()<=0){
            mainTypeList = this.payoutMainTypeService.findByShoolId(actor.getSchoolId());
            if(mainTypeList != null && mainTypeList.size()>0){
                subTypeList = new LinkedList<>();
                for (PayoutMainType mainType : mainTypeList) {
                    List<PayoutSubType> tmpSubList = this.payoutSubTypeService.findByPayoutMainTypeAndSchoolId(mainType, actor.getSchoolId());
                    subTypeList.addAll(tmpSubList);
                }
            }
        }else{
            subTypeList = this.payoutSubTypeService.findByPayoutMainTypeAndSchoolId(payoutMainType, actor.getSchoolId());
        }

        if(subTypeList == null || subTypeList.size()<=0){
            return null;
        }

        List<Payout> payoutList = this.payoutService.findBySchoolId(actor.getSchoolId());
        if(payoutList == null || payoutList.size()<=0){
            return null;
        }

        Set<Integer> subTypeIdSet = new HashSet<>();
        for (PayoutSubType subType : subTypeList) {
            subTypeIdSet.add(subType.getId());
        }

        List<Payout> returnList = new LinkedList<>();
        for (Payout payout : payoutList) {
            if(subTypeIdSet.contains(payout.getPayoutSubType().getId())){
                returnList.add(payout);
            }
        }
        return returnList;
    }

    @ApiOperation("添加老师社保信息")
    @PostMapping(value = "/revenue/tsss/create")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    public TeacherSocialSecurity createTeacherSocialSecurity(@Active Actor actor, @RequestBody TeacherSocialSecurityForm teacherSocialSecurityForm){
        return this.teacherSocialSecurityService.createTeacherSocialSecurity(actor, teacherSocialSecurityForm);
    }

    @ApiOperation("职工工资即时计算")
    @PostMapping(value = "/revenue/tsss/countTsss")
    @Timed
    public TeacherSocialSecurity countTsss(@Active Actor actor, @RequestBody TeacherSocialSecurityForm teacherSocialSecurityForm){
        return this.teacherSocialSecurityService.countTsss(actor, teacherSocialSecurityForm);
    }

    @ApiOperation("修改老师社保信息")
    @PutMapping(value = "/revenue/tsss/update")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    public void updateTeacherSocialSecurity(@Active Actor actor,
                                            @RequestBody TeacherSocialSecurityForm teacherSocialSecurityForm){
        this.teacherSocialSecurityService.updateTeacherSocialSecurity(actor, teacherSocialSecurityForm);
    }


    @ApiOperation("删除老师社保信息")
    @DeleteMapping(value = "/revenue/tsss/delete/{id:\\d+}")
    @PermissionRequired(CHARGE_PLAN_DELETE)
    @Timed
    public void deleteTeacherSocialSecurity(@Active Actor actor, @PathVariable Integer id){
        this.teacherSocialSecurityService.delete(id);
    }

    @ApiOperation("查询老师社保信息")
    @GetMapping(value = "/revenue/tsss/findAll")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    Page<TeacherSocialSecurity> getAllTeacherSocialSecurity(@Active Actor actor,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "page", required = true) int page,
                                     @RequestParam(value = "size", required = false) Integer size) {
        return this.teacherSocialSecurityService.getAllTeacherSocialSecurity(actor, name, page, size);
    }

    @ApiOperation("查询所有收入")
    @GetMapping(value = "/revenue/income/findAll")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    Page<Income> getAllIncome(@Active Actor actor,
                                             @RequestParam(value = "page", required = true) int page,
                                             @RequestParam(value = "startTime", required = true) long startTime,
                                             @RequestParam(value = "endTime", required = true) long endTime,
                                             @RequestParam(value = "size", required = false) Integer size) {
        int pageSize = 10;
        if (size != null) {
            pageSize = size;
        }
        page = Math.max(0, page - 1);
        Sort sort = new Sort(Sort.Direction.DESC, "names");
        Pageable pageable = new PageRequest(page, pageSize, sort);

        long fromTime = 0;
        long toTime = System.currentTimeMillis();
        if(startTime>0){
            fromTime = Times.monthStartTime(startTime);
        }
        if(endTime>0){
            toTime = Times.monthEndTime(endTime);
        }

        return this.incomeService.findAll(actor.getSchoolId(), fromTime, toTime, pageable);
    }

    @ApiOperation("查询收入详细")
    @GetMapping(value = "/revenue/income/findDetail")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    public List<IncomeDetail> getIncomeDetail(@Active Actor actor, @RequestParam(value="id", required = true) Integer id){
        Income income = this.incomeService.get(id);
        if(income == null){
            return null;
        }
        return this.incomeDetailService.findIncomeDetail(income.getExpenseId(), income.getTheYearMonth());
    }

    @ApiOperation("营收汇总")
    @GetMapping(value = "/revenue/income/findTotal")
    @PermissionRequired(CHARGE_PLAN_GET)
    @Timed
    public List<RevenueSummary> getTotalIncome(@Active Actor actor, @RequestParam(value = "startTime", required = true) long startTime){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(startTime);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        long startTimeOfYear = c.getTimeInMillis();
        startTimeOfYear = Times.monthStartTime(startTimeOfYear);//the first day of year

        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(startTime);
        end.set(Calendar.MONTH, Calendar.DECEMBER);
        long endTimeOfYear = end.getTimeInMillis();
        endTimeOfYear = Times.monthEndTime(endTimeOfYear);

        List<Income> incomeList = this.incomeService.findAllBySchoolIdAndCreateAtBetween(actor.getSchoolId(), startTimeOfYear, endTimeOfYear);
        List<Payout> payoutList = this.payoutService.findAllBySchoolIdAndCreateAtBetween(actor.getSchoolId(), startTimeOfYear, endTimeOfYear);

        Map<Integer, RevenueSummary> rsMap = new HashMap<>();
        for (Income income : incomeList) {
            RevenueSummary revenueSummary = rsMap.get(income.getTheYearMonth());
            if(revenueSummary == null){
                revenueSummary = new RevenueSummary(income.getTheYearMonth());
                revenueSummary.setRevenueIn(0f);
                revenueSummary.setRevenueOut(0f);
                rsMap.put(income.getTheYearMonth(), revenueSummary);
            }
            revenueSummary.setRevenueIn(income.getMoney() + revenueSummary.getRevenueIn());
        }

        for (Payout payout : payoutList) {
            RevenueSummary revenueSummary = rsMap.get(payout.getTheYearMonth());
            if(revenueSummary == null){
                revenueSummary = new RevenueSummary(payout.getTheYearMonth());
                revenueSummary.setRevenueIn(0f);
                revenueSummary.setRevenueOut(0f);
                rsMap.put(payout.getTheYearMonth(), revenueSummary);
            }
            revenueSummary.setRevenueOut(payout.getMoney() + revenueSummary.getRevenueOut());
        }
        return new ArrayList<>(rsMap.values());

    }

}









