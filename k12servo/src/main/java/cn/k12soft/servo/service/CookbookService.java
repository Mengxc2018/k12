package cn.k12soft.servo.service;

import static com.google.common.base.Strings.isNullOrEmpty;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Cookbook;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.repository.CookbookRepository;
import cn.k12soft.servo.web.form.CookbookForm;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@Service
@Transactional
public class CookbookService extends AbstractEntityService<Cookbook, Integer> {

  private final SchoolService schoolService;

  @Autowired
  public CookbookService(CookbookRepository cookbookRepository,
                         SchoolService schoolService) {
    super(cookbookRepository);
    this.schoolService = schoolService;
  }

  public Cookbook create(Actor createdBy, CookbookForm form) {
    School school = schoolService.get(form.getSchoolId());
    Cookbook cookbook = new Cookbook(school, form.getTitle(), form.getContent(), createdBy);
    return getEntityRepository().save(cookbook);
  }

  public Cookbook update(Integer planId,
                         CookbookForm form) {
    Cookbook cookbook = getEntityRepository().findOne(planId);
    if (!isNullOrEmpty(form.getTitle())) {
      cookbook.title(form.getTitle());

    }
    if (!isNullOrEmpty(form.getContent())) {
      cookbook.content(form.getContent());
    }
    return getEntityRepository().save(cookbook);
  }

  @Transactional(readOnly = true)
  public Cookbook getCookbook(Integer id) {
    return getEntityRepository().findOne(id);
  }

  @Transactional(readOnly = true)
  public List<Cookbook> findAllBySchool(Integer schoolId) {
    return getEntityRepository().findAllBySchoolId(schoolId);
  }

  @Transactional(readOnly = true)
  public List<Cookbook> findAllBySchoolAndFrom(Integer schoolId,
                                               Instant fromDate) {
    return getEntityRepository().findAllBySchoolIdAndUpdatedAtAfter(schoolId, fromDate);
  }

  public void delete(Integer planId) {
    getEntityRepository().delete(planId);
  }

  @Override
  protected CookbookRepository getEntityRepository() {
    return (CookbookRepository) super.getEntityRepository();
  }
}
