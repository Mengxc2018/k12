package cn.k12soft.servo.service;

import static com.google.common.base.Strings.isNullOrEmpty;

import cn.k12soft.servo.domain.Grade;
import cn.k12soft.servo.domain.InterestKlass;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.enumeration.KlassType;
import cn.k12soft.servo.repository.InterestKlassRepository;
import cn.k12soft.servo.web.form.InterestKlassForm;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterestKlassService extends AbstractEntityService<InterestKlass, Integer> {

  private final GradeService gradeService;

  @Autowired
  public InterestKlassService(InterestKlassRepository interestKlassRepository,
                              GradeService gradeService) {
    super(interestKlassRepository);
    this.gradeService = gradeService;
  }

  @Override
  protected InterestKlassRepository getEntityRepository() {
    return (InterestKlassRepository) super.getEntityRepository();
  }

  public InterestKlass create(Integer schoolId, InterestKlassForm form) {
//    Grade grade = gradeService.get(FeeDetailsForm.getGradeId());
    KlassType klassType = KlassType.get(form.getType());
    InterestKlass klass = new InterestKlass(schoolId, form.getName(), klassType, form.getDescription());
    klass.setCreateAt(Instant.now());
    klass.setLessonCount(form.getLessonCount());
    klass.setStartLesAt(form.getStartLesAt());
    klass.setEndLesAt(form.getEndLesAt());
    klass.setLesPeriod(form.getLesPeriod());
    return getEntityRepository().save(klass);
  }

  public List<InterestKlass> createMany(Integer schoolId, List<InterestKlassForm> forms) {
    List<InterestKlass> list = new ArrayList<>();
    for (InterestKlassForm form : forms){
      list.add(create(schoolId, form));
    }
    return list;
  }

  public InterestKlass update(Integer id, InterestKlassForm form) {
    InterestKlass klass = get(id);
    if (!isNullOrEmpty(form.getName())) {
      klass.setName(form.getName());
    }
    if (!isNullOrEmpty(form.getDescription())) {
      klass.setDescription(form.getDescription());
    }
    return getEntityRepository().save(klass);
  }

  private Set<InterestKlass> concat(Set<InterestKlass> a, Set<InterestKlass> b) {
    a.addAll(b);
    return a;
  }

  public List<InterestKlass> findBySchoolId(Integer schoolId) {
    return getEntityRepository().findAllBySchoolId(schoolId);
  }


  public InterestKlass findByNameAndSchoolId(String iklassName, Integer schoolId) {
    return this.getEntityRepository().findByNameAndSchoolId(iklassName, schoolId);
  }
}
