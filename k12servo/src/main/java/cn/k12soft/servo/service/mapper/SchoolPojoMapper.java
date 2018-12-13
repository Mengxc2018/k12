package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.service.dto.SchoolPojoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@Component
public class SchoolPojoMapper extends EntityMapper<School, SchoolPojoDTO> {

  @Override
  protected SchoolPojoDTO convert(School school) {
    return new SchoolPojoDTO(
            school.getId(),
            school.getName(),
            school.getCode()
    );
  }
}
