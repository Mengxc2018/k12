package cn.k12soft.servo.module.charge.service;

import cn.k12soft.servo.module.charge.domain.RecruitStudent;
import cn.k12soft.servo.module.charge.repository.RecruitStudentRepository;
import cn.k12soft.servo.service.AbstractEntityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecruitStudentService extends AbstractEntityService<RecruitStudent, Integer> {

  @Autowired
  public RecruitStudentService(JpaRepository<RecruitStudent, Integer> entityRepository) {
    super(entityRepository);
  }

  public RecruitStudent findByStudentId(Integer studentId) {
    return getEntityRepository().findByStudentId(studentId);
  }

  @Override
  protected RecruitStudentRepository getEntityRepository() {
    return (RecruitStudentRepository) super.getEntityRepository();
  }

  public Page<RecruitStudent> findAllSchoolId(Integer schoolId, Pageable pageable) {
    return getEntityRepository().findBySchoolId(schoolId, pageable);
  }

  public List<RecruitStudent> findByKlassId(Integer klassId) {
    return getEntityRepository().findByKlassId(klassId);
  }
}
