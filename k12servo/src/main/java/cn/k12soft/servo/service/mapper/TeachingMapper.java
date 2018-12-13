package cn.k12soft.servo.service.mapper;

import cn.k12soft.servo.domain.Teaching;
import cn.k12soft.servo.repository.TeachingRepository;
import cn.k12soft.servo.service.dto.TeacherDTO;
import cn.k12soft.servo.service.dto.TeachingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeachingMapper extends EntityMapper<Teaching, TeachingDTO> {

  private final TeachingRepository repository;
  private final TeacherMapper teacherMapper;
  private static final Logger logger = LoggerFactory.getLogger(TeachingMapper.class);

  @Autowired
  public TeachingMapper(TeachingRepository repository,
                        TeacherMapper teacherMapper) {
    this.repository = repository;
    this.teacherMapper = teacherMapper;
  }

  @Override
  protected TeachingDTO convert(Teaching teaching) {
    TeacherDTO teacherDTO = teacherMapper.fromTeacherId(teaching.getTeacherId());
    logger.info(teaching.toString());
    return new TeachingDTO(teaching.getId(), teacherDTO, teaching.getKlass(), teaching.getCourse());
  }
}
