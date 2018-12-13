package cn.k12soft.servo.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Guardian;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.GuardianRepository;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GuardianService extends AbstractEntityService<Guardian, Integer> {

  private final ActorRepository actorRepository;
  private final UserRepository userRepository;

  @Autowired
  public GuardianService(GuardianRepository repository, ActorRepository actorRepository, UserRepository userRepository) {
    super(repository);
    this.actorRepository = actorRepository;
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public Set<Guardian> getAllByStudent(Integer studentId) {
    return getEntityRepository().findAllByStudent_Id(studentId);
  }

  @Transactional(readOnly = true)
  public Guardian getGuardian(Integer studentId, Integer patriarchId) {
    return getEntityRepository().findByPatriarchIdAndStudent_Id(patriarchId, studentId);
  }

  @Override
  protected GuardianRepository getEntityRepository() {
    return (GuardianRepository) super.getEntityRepository();
  }

  @Transactional(readOnly = true)
  public Set<Guardian> getAllByPatriarchId(Integer patriarchId) {
    return getEntityRepository().findAllByPatriarchId(patriarchId);
  }

  public Set<Guardian> findChildren(Actor actor) {
    // 过滤毕业的
    Set<Guardian> guardians = getEntityRepository().findAllByPatriarchId(actor.getId());
    return guardians.stream()
            .filter( guardian -> guardian.getStudent().getKlass().isGraduate() == false)
            .collect(Collectors.toSet());
  }

  public Collection<Guardian> getAllBySchoolAndKlass(Integer klassId, Integer schoolId) {
    return this.getEntityRepository().findAllBySchoolIdAndKlassId(schoolId, klassId);
  }

  public Collection<Guardian> getAllByKlassUpdate(Integer klassId, Integer schoolId) {
    Collection<Guardian> guardians = this.getEntityRepository().findAllBySchoolIdAndKlassIdAndIsUpdate(schoolId, klassId, true);
    CompletableFuture completableFuture = CompletableFuture.supplyAsync(()->{
      for (Guardian g : guardians){
        Actor actor = this.actorRepository.findOne(g.getPatriarchId());
        User user = this.userRepository.findOne(actor.getUserId());
        System.out.println(user.toString());
        user.setUpdate(false);
        this.userRepository.save(user);
      }
      return null;
    });
    return guardians;
  }
}
