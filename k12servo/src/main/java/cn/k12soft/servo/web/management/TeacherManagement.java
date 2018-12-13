package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.TEACHER_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.TEACHER_GET;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.TeachingRepository;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.dto.TeacherDTO;
import cn.k12soft.servo.service.mapper.TeacherMapper;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/12.
 */
@RestController
@RequestMapping("/management/teachers")
public class TeacherManagement {

  private final ActorRepository actorRepository;
  private final TeachingRepository teachingRepository;
  private final TeacherMapper mapper;
  private static final Logger logger = LoggerFactory.getLogger(TeacherManagement.class);
  private final ObjectMapper objectMapper;

  @Autowired
  public TeacherManagement(ActorRepository actorRepository,
                           TeachingRepository teachingRepository,
                           TeacherMapper mapper, ObjectMapper objectMapper) {
    this.actorRepository = actorRepository;
    this.teachingRepository = teachingRepository;
    this.mapper = mapper;
    this.objectMapper = objectMapper;
  }

  @ApiOperation("获取所有老师列表")
  @GetMapping(params = "schoolId")
  @PermissionRequired(TEACHER_GET)
  @Timed
  public Collection<TeacherDTO> getTeachers(@RequestParam("schoolId") Integer schoolId) throws JsonProcessingException {
    List<Actor> teacherActors = actorRepository.findAllBySchoolIdAndTypesContains(schoolId, ActorType.TEACHER);
    Collection<TeacherDTO> teacherDTOS = mapper.toDTOs(teacherActors);
    logger.info(objectMapper.writeValueAsString(teacherDTOS));
    return teacherDTOS;
  }

  @ApiOperation("删除指定老师")
  @DeleteMapping("/{teacherId:\\d+}")
  @PermissionRequired(TEACHER_DELETE)
  @Timed
  @Transactional
  public void delete(@PathVariable Integer teacherId) {
    Actor actor = actorRepository.findOne(teacherId);
    if (actor.getTypes().remove(ActorType.TEACHER)) {
      actorRepository.save(actor);
      teachingRepository.delete(teachingRepository.findAllByTeacherId(actor.getId()));
    }
  }
}

