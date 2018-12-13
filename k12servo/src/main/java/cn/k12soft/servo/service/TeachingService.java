package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Course;
import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Teaching;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.TeachingRepository;
import cn.k12soft.servo.web.form.TeachingForm;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeachingService {

  private final TeachingRepository repository;
  private final ActorRepository actorRepository;
  private final KlassService klassService;
  private final CourseService courseService;

  @Autowired
  public TeachingService(TeachingRepository repository,
                         ActorRepository actorRepository,
                         @Lazy KlassService klassService,
                         @Lazy CourseService courseService) {
    this.repository = repository;
    this.actorRepository = actorRepository;
    this.klassService = klassService;
    this.courseService = courseService;
  }

  public Teaching create(TeachingForm form) {
    Actor teacher = actorRepository.findOne(form.getTeacherId());
    Preconditions.checkState(teacher.getTypes().contains(ActorType.TEACHER), "Not a teacher");
    Klass klass = klassService.get(form.getKlassId());
    Course course = courseService.get(form.getCourseId());
    Teaching teaching = new Teaching(teacher.getId(), klass, course);
    return repository.save(teaching);
  }

  public void delete(Integer id) {
    repository.delete(id);
  }

  @Transactional(readOnly = true)
  public List<Teaching> getAll() {
    return repository.findAll();
  }

  public boolean isUserTeaching(Actor user, Klass klass) {
    return repository.findByTeacherIdAndKlass(user.getId(), klass).isPresent();
  }

  @Transactional(readOnly = true)
  public List<Teaching> getAllByTeacherId(Integer teacherId) {
    return repository.findAllByTeacherId(teacherId);
  }

  public Collection<Teaching> findAllBySchoolId(Integer schoolId) {
    return repository.findAllBySchoolId(schoolId);
  }
}
