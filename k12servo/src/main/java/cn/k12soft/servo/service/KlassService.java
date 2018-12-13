package cn.k12soft.servo.service;

import static com.google.common.base.Strings.isNullOrEmpty;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Grade;
import cn.k12soft.servo.domain.Guardian;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.Teaching;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.repository.GradeRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.web.form.KlassForm;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KlassService extends AbstractEntityService<Klass, Integer> {

  private final GradeService gradeService;
  private final GuardianService guardianService;
  private final TeachingService teachingService;
  private final GradeRepository gradeRepository;

  @Autowired
  public KlassService(KlassRepository klassRepository,
                      GradeService gradeService,
                      GuardianService guardianService,
                      TeachingService teachingService,
                      GradeRepository gradeRepository) {
    super(klassRepository);
    this.gradeService = gradeService;
    this.guardianService = guardianService;
    this.teachingService = teachingService;
    this.gradeRepository = gradeRepository;
  }


  public static final String DA = "da";
  public static final String ZHONG = "zhong";
  public static final String XIAO = "xiao";
  public static final String TUO = "tuo";


  @Override
  protected KlassRepository getEntityRepository() {
    return (KlassRepository) super.getEntityRepository();
  }

  public Klass create(KlassForm form) {
    Grade grade = gradeService.get(form.getGradeId());
    Klass klass = new Klass(grade, form.getName(), form.getDescription());
    klass.setJoinOfYear(form.getJoinOfYear());
    return getEntityRepository().save(klass);
  }

  public Klass update(Integer id, KlassForm form) {
    Klass klass = get(id);
    if (!isNullOrEmpty(form.getName())) {
      klass.setName(form.getName());
    }
    if (!isNullOrEmpty(form.getDescription())) {
      klass.setDescription(form.getDescription());
    }
    if (!isNullOrEmpty(form.getGradeId().toString())) {
        Grade grade = gradeRepository.findOne(form.getGradeId());
//        if (!domain.getGradeId().equals(klass.getGrade().getId())){
//                klass.setGoupTime(Instant.now());
//        }
      klass.setGrade(grade);

    }
    if (!isNullOrEmpty(form.getJoinOfYear())){
      klass.setJoinOfYear(form.getJoinOfYear());
    }

    // TODO: 2018/9/4 此位置由移动版本已发布不好修改，做特殊修改,下版本需要修改回来
      if (klass.getGrade().getName().equals(DA)) {
        if (!form.getIsGraduate()){
              klass.setGraduate(true);
          }
      }

    return getEntityRepository().save(klass);
  }

  @Transactional(readOnly = true)
  public Set<Klass> getKlasses(Actor actor) {
    if (actor.getTypes().contains(ActorType.MANAGER)) {
      return new HashSet<>(getEntityRepository().findAll());
    }
    return actor.getTypes()
      .stream()
      .map(roleType -> klassesOf(roleType, actor))
      .reduce(this::concat)
      .orElse(Collections.emptySet());
  }

  @Transactional(readOnly = true)
  Set<Klass> klassesOf(ActorType actorType, Actor actor) {
    switch (actorType) {
      case PATRIARCH:
        return guardianService.getAllByPatriarchId(actor.getId()).stream()
          .map(Guardian::getStudent)
          .map(Student::getKlass)
          .collect(Collectors.toSet());
      case TEACHER:
        return teachingService.getAllByTeacherId(actor.getId())
          .stream()
          .map(Teaching::getKlass)
          .collect(Collectors.toSet());
    }
    throw new Error();
  }

  private Set<Klass> concat(Set<Klass> a, Set<Klass> b) {
    a.addAll(b);
    return a;
  }

  public List<Klass> getAllOfSchool(Integer schoolId) {
    return getEntityRepository().findAllBySchoolIdAndIsGraduate(schoolId, false);
  }
  public List<Klass> getGoupOfSchool(Integer schoolId) {
    return getEntityRepository().findAllBySchoolIdAndIsGraduate(schoolId, true);
  }

  public void goup(Integer schoolId) {

    Grade gradeTuo = gradeRepository.findByschoolIdAndDescription(schoolId, TUO);
    Grade gradeXiao = gradeRepository.findByschoolIdAndDescription(schoolId, XIAO);
    Grade gradeZhong = gradeRepository.findByschoolIdAndDescription(schoolId, ZHONG);
    Grade gradeDa = gradeRepository.findByschoolIdAndDescription(schoolId, DA);

    Instant instant = Instant.now();
    LocalDate localDate = LocalDate.now();

    // 大班先毕业，isGraduate为true
    List<Klass> klassList = getEntityRepository().findAllBySchoolIdAndGrade(schoolId, gradeDa);
    for (Klass k : klassList){
      k.setGraduate(true);
      k.setGraduateAt(instant);
      String yearStr = localDate.getYear()+"届";
      k.setGraduateOfYear(yearStr);
      getEntityRepository().save(k);
    }

    // 中班升大班
    List<Klass> zhongList = getEntityRepository().findAllBySchoolIdAndGrade(schoolId, gradeZhong);
    for (Klass zhkl : zhongList){
      zhkl.setGrade(gradeDa);
      zhkl.setGoupTime(instant);
      getEntityRepository().save(zhkl);
    }

    // 小班升中班
    List<Klass> xiaoList = getEntityRepository().findAllBySchoolIdAndGrade(schoolId, gradeXiao);
    for (Klass xkl : xiaoList){
      xkl.setGrade(gradeZhong);
      xkl.setGoupTime(instant);
      getEntityRepository().save(xkl);
    }

    // 托班升小班
    List<Klass> tuoList = getEntityRepository().findAllBySchoolIdAndGrade(schoolId, gradeTuo);
    for (Klass tkl : tuoList){
      tkl.setGrade(gradeXiao);
      tkl.setGoupTime(instant);
      getEntityRepository().save(tkl);
    }
  }

  public List<Klass> findKlassOfTeacher(Actor actor) {
    return getEntityRepository().findKlassByActorId(actor.getId(), false);
  }
}
