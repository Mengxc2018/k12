package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.service.dto.SchoolDTO;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@Component
public class SchoolMapper extends EntityMapper<School, SchoolDTO> {

  private final SchoolRepository repository;

  @Autowired
  public SchoolMapper(SchoolRepository repository) {this.repository = repository;}

  @Override
  protected SchoolDTO convert(School school) {
    return new SchoolDTO()
      .setId(school.getId())
      .setName(school.getName())
      .setDesc(school.getDesc())
      .setFormDate(school.getFormDate())
      .setToDate(school.getToDate())
      .setAnnual(school.getAnnual())
      .setSick(school.getSick())
      .setBarth(school.getBarth())
      .setBarthWith(school.getBarthWith())
      .setMarry(school.getMarry())
      .setFuneral(school.getFuneral());
  }

  public Collection<SchoolDTO> fromSchoolIds(Collection<Integer> schoolIds) {
    List<School> schools = repository.findAll(schoolIds);
    return toDTOs(schools);
  }
}
