package cn.k12soft.servo.module.expense.rest;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.charge.service.ChargePlanService;
import cn.k12soft.servo.module.charge.service.StudentChargePlanService;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.form.ExpenseEntryForm;
import cn.k12soft.servo.module.expense.service.ExpenseEntryService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.dto.ExpenseEntryDTO;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2017/12/16.
 */
@RestController
@RequestMapping("/expense/entry")
public class ExpenseEntryController {

  private final ExpenseEntryService service;
  private ChargePlanService chargePlanService;
  private StudentChargePlanService studentChargePlanService;

  @Autowired
  public ExpenseEntryController(ExpenseEntryService service,
                                ChargePlanService chargePlanService,
                                StudentChargePlanService studentChargePlanService) {
    this.service = service;
    this.chargePlanService = chargePlanService;
    this.studentChargePlanService = studentChargePlanService;
  }

  @ApiOperation("获取费种")
  @GetMapping
  public List<ExpenseEntryDTO> list(@Active Actor actor) {
    return this.service.findBySchoolId(actor.getSchoolId());
  }

  @ApiOperation("创建费种")
  @PostMapping
  public ExpenseEntry create(@Active Actor actor, @Valid @RequestBody ExpenseEntryForm form) {
    return this.service.create(actor.getSchoolId(), form);
  }

  @ApiOperation("批量：创建费种")
  @PostMapping("/createMany")
  public List<ExpenseEntry> createMany(@Active Actor actor, @Valid @RequestBody List<ExpenseEntryForm> form) {
    return this.service.createMany(actor.getSchoolId(), form);
  }

  @ApiOperation("修改费种")
  @PutMapping("/update/{id:\\d+}")
  public ExpenseEntry update(@Active Actor actor, @PathVariable Integer id, @Valid @RequestBody ExpenseEntryForm form){
    ExpenseEntry expenseEntry = this.service.get(id);
    if(expenseEntry == null){
      return null;
    }
    return this.service.update(expenseEntry, form);
  }

  @ApiOperation("删除费种")
  @DeleteMapping("/{id:\\d+}")
  public void delete(@PathVariable Integer id) {
      ExpenseEntry entry = new ExpenseEntry(id);
      this.chargePlanService.deleteByExpenseEntry(entry);
      this.studentChargePlanService.deleteByExpenseEntry(entry);
    this.service.delete(id);
  }

  @ApiOperation("删除身份周期折扣")
  @DeleteMapping("/deleteIdentDiscounts/{id:\\d+}")
  public void deleteIdentDiscounts(@PathVariable Integer id){
    this.service.deleteIdentDiscounts(id);
  }

  @ApiOperation("删除时间周期折扣")
  @DeleteMapping("/deletePeriodDiscounts/{id:\\d+}")
  public void deletePeriodDiscounts(@PathVariable Integer id){
    this.service.deletePeriodDiscounts(id);
  }

  @ApiOperation("删除按天退费规则")
  @DeleteMapping("/deletePaybackByDays/{id:\\d+}")
  public void deletePaybackByDays(@PathVariable Integer id){
    this.service.deletePaybackByDasy(id);
  }

  @ApiOperation("删除按学期退费规则")
  @DeleteMapping("/deletePaybackBySemester/{id:\\d+}")
  public void deletePaybackBySemester(@PathVariable Integer id){
    this.service.deletePaybackBySemester(id);
  }
}
