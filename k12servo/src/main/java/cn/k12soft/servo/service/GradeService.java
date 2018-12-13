package cn.k12soft.servo.service;

import static com.google.common.base.Strings.isNullOrEmpty;

import cn.k12soft.servo.domain.Grade;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.repository.GradeRepository;
import cn.k12soft.servo.web.form.GradeForm;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GradeService extends AbstractEntityService<Grade, Integer> {

  @Autowired
  public GradeService(GradeRepository repository) {
    super(repository);
  }

  public Grade create(GradeForm form) {
    Grade grade = new Grade(form.getSchoolId(), form.getName(), form.getDescription());
    return getEntityRepository().save(grade);
  }

  public Grade update(Integer gradeId, GradeForm form) {
    Grade grade = get(gradeId);
    if (!isNullOrEmpty(form.getName())) {
      grade.setName(form.getName());
    }
    if (!isNullOrEmpty(form.getDescription())) {
      grade.setDescription(form.getDescription());
    }
    return getEntityRepository().save(grade);
  }

  public List<Grade> findAllBySchool(Integer schoolId) {
    return getEntityRepository().findAllBySchoolId(schoolId);
  }

  @Override
  protected GradeRepository getEntityRepository() {
    return (GradeRepository) super.getEntityRepository();
  }
}
