package cn.k12soft.servo.web.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.IKStudent;
import cn.k12soft.servo.domain.InterestKlass;
import cn.k12soft.servo.module.revenue.domain.Income;
import cn.k12soft.servo.module.revenue.service.IncomeService;
import cn.k12soft.servo.repository.InterestKlassRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.InterestKlassService;
import cn.k12soft.servo.service.mapper.IKStudentService;
import cn.k12soft.servo.util.Times;
import cn.k12soft.servo.web.form.IkStuInfoForm;
import cn.k12soft.servo.web.form.IkStuInfoPojoForm;
import cn.k12soft.servo.web.form.InterestKlassForm;
import cn.k12soft.servo.web.view.TermIncomeInfo;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static cn.k12soft.servo.domain.enumeration.Permission.*;

/**
 * 兴趣班
 */
@RestController
@RequestMapping("/management/ikm")
public class InterestKlassManagement {

  private final InterestKlassService interestKlassService;
  private final IKStudentService ikStudentService;
  private final InterestKlassRepository repository;
  private final IncomeService incomeService;
  private final InterestKlassRepository interestKlassRepository;
  private final KlassRepository klassRepository;

  @Autowired
  public InterestKlassManagement(InterestKlassService interestKlassService, IKStudentService ikStudentService,
                                 InterestKlassRepository repository
          , IncomeService incomeService, InterestKlassRepository interestKlassRepository, KlassRepository klassRepository) {
    this.interestKlassService = interestKlassService;
    this.ikStudentService = ikStudentService;
    this.repository = repository;
    this.incomeService = incomeService;
    this.interestKlassRepository = interestKlassRepository;
    this.klassRepository = klassRepository;
  }

  @ApiOperation("获取兴趣班班级列表")
  @GetMapping
  @PermissionRequired(INTEREST_KLASS_GET)
  @Timed
  public List<InterestKlass> getKlass(@Active Actor actor) {
    return repository.findAllBySchoolId(actor.getSchoolId());
  }

  @ApiOperation("获取指定兴趣班班级信息")
  @GetMapping("/{klassId:\\d+}")
  @PermissionRequired(INTEREST_KLASS_GET)
  @Timed
  public InterestKlass fetch(@PathVariable Integer klassId) {
    return interestKlassService.get(klassId);
  }


  @ApiOperation("新增兴趣班班级信息")
  @PostMapping
  @PermissionRequired(INTEREST_KLASS_POST)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public InterestKlass create(@Active Actor actor, @RequestBody @Valid InterestKlassForm form) {
    return interestKlassService.create(actor.getSchoolId(), form);
  }

  @ApiOperation("批量：新增兴趣班班级信息")
  @PostMapping("/createMany")
  @PermissionRequired(INTEREST_KLASS_POST)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public List<InterestKlass> createMany(@Active Actor actor, @RequestBody @Valid List<InterestKlassForm> forms) {
    return interestKlassService.createMany(actor.getSchoolId(), forms);
  }

  @ApiOperation("更新班级信息")
  @PutMapping("/{klassId:\\d+}")
  @PermissionRequired(INTEREST_KLASS_PUT)
  @Timed
  public InterestKlass update(@PathVariable Integer klassId,
                              @RequestBody InterestKlassForm form) {
    return interestKlassService.update(klassId, form);
  }

  @ApiOperation("删除班级")
  @DeleteMapping("/{klassId:\\d+}")
  @PermissionRequired(INTEREST_KLASS_DELETE)
  @Timed
  public void delete(@PathVariable Integer klassId) {
    interestKlassService.delete(klassId);
    this.ikStudentService.deleteByIklassId(klassId);
  }

  @ApiOperation("添加学生")
  @PostMapping("/addStudent")
  @PermissionRequired(INTEREST_KLASS_DELETE)
  @Timed
  public void addStudent(@Active Actor actor, @RequestBody @Valid IkStuInfoForm form) throws JSONException {
    InterestKlass interestKlass = this.interestKlassService.get(form.getIklassId());
    if (interestKlass == null) {
      return;
    }
    for (String[] stuInfo : form.getStuInfoList()) {
      Integer studentId = Integer.valueOf(stuInfo[0]);
      String studentName = stuInfo[1];
      String note = stuInfo[2];
      IKStudent student = this.ikStudentService.findByIklassIdAndStudentId(form.getIklassId(), studentId);
      if (student != null) {
        continue;
      }
      student = new IKStudent(actor.getSchoolId());
      student.setIklassId(interestKlass.getId());
      student.setStudentId(studentId);
      student.setName(studentName);
      student.setRemainCount(interestKlass.getLessonCount());
      student.setNote(note==null?"":note);
      student.setCreateAt(Instant.now());
      this.ikStudentService.save(student);
    }
  }

  @ApiOperation("批量：添加学生")
  @PostMapping("/addStudentMany")
  @PermissionRequired(INTEREST_KLASS_DELETE)
  @Timed
  public void addStudentMany(@Active Actor actor, @RequestBody @Valid List<IkStuInfoPojoForm> forms) throws JSONException {
    Integer schoolId = actor.getSchoolId();
    InterestKlass iklass = new InterestKlass();
    for (IkStuInfoPojoForm form : forms){
      iklass = this.interestKlassService.findByNameAndSchoolId(form.getIklassName(), schoolId);
      if (iklass == null) {
        return;
      }
      for (String[] stuInfo : form.getStuInfoList()) {
        Integer studentId = Integer.valueOf(stuInfo[0]);
        String studentName = stuInfo[1];
        String note = stuInfo[2];
        IKStudent student = this.ikStudentService.findByIklassIdAndStudentId(iklass.getId(), studentId);
        if (student != null) {
          continue;
        }
        student = new IKStudent(actor.getSchoolId());
        student.setIklassId(iklass.getId());
        student.setStudentId(studentId);
        student.setName(studentName);
        student.setRemainCount(iklass.getLessonCount());
        student.setNote(note==null?"":note);
        student.setCreateAt(Instant.now());
        this.ikStudentService.save(student);
      }
    }
  }

  @ApiOperation("删除学生")
  @DeleteMapping("/delStudent")
  @PermissionRequired(INTEREST_KLASS_DELETE)
  @Timed
  public void deleteStudent(@Active Actor actor, @RequestParam String ids) throws JSONException {
    JSONArray jsonArray = new JSONArray(ids);
    for (int i = 0; i < jsonArray.length(); i++) {
      int id = jsonArray.getInt(i);
      this.ikStudentService.findDelete(id);
    }
  }

  @ApiOperation("查询学生")
  @GetMapping("/findStudent")
  @PermissionRequired(INTEREST_KLASS_DELETE)
  @Timed
  public List<IKStudent> deleteStudent(@Active Actor actor, @RequestParam Integer klassId) {
    return this.ikStudentService.findByIklassId(klassId);
  }

  @ApiOperation("修改考勤")
  @PutMapping("/editStudent")
  @PermissionRequired(INTEREST_KLASS_DELETE)
  @Timed
  public void editStudent(@Active Actor actor, @RequestParam Integer id, @RequestParam Integer remainCount) throws JSONException {
    IKStudent student = this.ikStudentService.get(id);
    if (student == null) {
      return;
    }
    if (remainCount.equals(student.getRemainCount())) {
      return;
    }
    student.setRemainCount(remainCount);
    this.ikStudentService.save(student);
  }

  @ApiOperation("兴趣班学期收入对比")
  @GetMapping("/termIncome")
  @PermissionRequired(INTEREST_KLASS_POST)
  @Timed
  public List<TermIncomeInfo> termIncome(@Active Actor actor, @RequestParam(value = "firstTime", required = true) long firstTime,
                                         @RequestParam(value = "secondTime", required = true) long secondTime){
    List<TermIncomeInfo> list = new ArrayList<>();

    LocalDate localDateFirst = Times.long2LocalDate(firstTime);
    LocalDate localDateSecond = Times.long2LocalDate(secondTime);
    Pair<LocalDate,LocalDate> pairFirst = Times.getTermDatePair(localDateFirst);
    Pair<LocalDate, LocalDate> pairSecond = Times.getTermDatePair(localDateSecond);
    LocalDate termEndFirst =  pairFirst.getSecond();
    LocalDate termEndSecond = pairSecond.getSecond();

    List<InterestKlass> klassList = this.interestKlassService.findBySchoolId(actor.getSchoolId());

    List<TermIncomeInfo.InnerTermIncomeInfo> itiiFirstList = new ArrayList<>();
    List<TermIncomeInfo.InnerTermIncomeInfo> itiiSecondList = new ArrayList<>();
    TermIncomeInfo tiiFirst = new TermIncomeInfo(pairFirst.getFirst(), itiiFirstList);
    TermIncomeInfo tiiSecond = new TermIncomeInfo(pairSecond.getFirst(), itiiSecondList);
    for (InterestKlass klass : klassList) {
      String name = klass.getName();

      int stuCountFirst = 0;
      float incomeFirst = 0;
      int stuCountSecond = 0;
      float incomeSecond = 0;

      List<IKStudent> kstudentList = this.ikStudentService.findByIklassId(klass.getId());
      for (IKStudent kstu : kstudentList) {
        LocalDate createAtDate = kstu.getCreateAt().atZone(ZoneId.systemDefault()).toLocalDate();
        if(createAtDate.isBefore(termEndFirst)){
          stuCountFirst++;
        }
        if(createAtDate.isBefore(termEndSecond)){
          stuCountSecond++;
        }
      }

      int schoolId = klass.getSchoolId();
      int klassType = klass.getType().getId();
      int klassId = klass.getId();

      List<Income> incomeListFirst = this.incomeService.findAllBySchoolIdAndKlassIdAndCreateAtBetween(schoolId, klassType, klassId, pairFirst);
      for (Income income : incomeListFirst) {
        incomeFirst += income.getMoney();
      }

      List<Income> incomeListSecond = this.incomeService.findAllBySchoolIdAndKlassIdAndCreateAtBetween(schoolId, klassType, klassId, pairSecond);
      for (Income income : incomeListSecond) {
        incomeSecond += income.getMoney();
      }

      TermIncomeInfo.InnerTermIncomeInfo itiiFirst = new TermIncomeInfo.InnerTermIncomeInfo(klass.getId(), name, klass.getCreateAt(), stuCountFirst, incomeFirst);
      TermIncomeInfo.InnerTermIncomeInfo itiiSecond = new TermIncomeInfo.InnerTermIncomeInfo(klass.getId(), name, klass.getCreateAt(), stuCountSecond, incomeSecond);
      itiiFirstList.add(itiiFirst);
      itiiSecondList.add(itiiSecond);
    }
    list.add(tiiFirst);
    list.add(tiiSecond);
    return list;
  }
}
