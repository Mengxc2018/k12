package cn.k12soft.servo.web.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Guardian;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.domain.enumeration.Permission;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.GuardianService;
import cn.k12soft.servo.service.dto.GuardianDTO;
import cn.k12soft.servo.service.mapper.GuardianMapper;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@RestController
@RequestMapping("/management/guardians")
public class GuardianManagement {

  private static final Logger logger = LoggerFactory.getLogger(GuardianManagement.class);
  private final GuardianService guardianService;
  private final GuardianMapper guardianMapper;
  private final ObjectMapper objectMapper;


  @Autowired
  public GuardianManagement(GuardianService guardianService, GuardianMapper guardianMapper,
                            ObjectMapper objectMapper) {
    this.guardianService = guardianService;
    this.guardianMapper = guardianMapper;
    this.objectMapper = objectMapper;
  }

  @ApiOperation("获取监护人列表")
  @GetMapping(params = {"schoolId", "studentId"})
  @PermissionRequired(Permission.GUARDIAN_VIEW)
  @Timed
  public Collection<GuardianDTO> getAll(@RequestParam("schoolId") Integer schoolId,
                                        @RequestParam("studentId") Integer studentId) throws JsonProcessingException {
    Collection<GuardianDTO> guardianDTOS = guardianMapper.toDTOs(guardianService.getAllByStudent(studentId));
    logger.info(objectMapper.writeValueAsString(guardianDTOS));
    return guardianDTOS;
  }

  @ApiOperation("获取班级内所有学生的监护人")
  @GetMapping(params = {"schoolId", "klassId"}, value = "/getAllByKlass")
  @PermissionRequired(Permission.GUARDIAN_VIEW)
  @Timed
  public Collection<GuardianDTO> getAllByKlass(@RequestParam("schoolId") Integer schoolId,
                                               @RequestParam("klassId") Integer klassId) throws JsonProcessingException {
    Collection<GuardianDTO> guardianDTOS = guardianMapper.toDTOs(guardianService.getAllBySchoolAndKlass(klassId, schoolId));
    logger.info(objectMapper.writeValueAsString(guardianDTOS));
    return guardianDTOS;
  }

  @ApiOperation("获取班级内信息有修改的监护人")
  @GetMapping(params = {"schoolId", "klassId"}, value = "/getAllByKlassUpdate")
  @PermissionRequired(Permission.GUARDIAN_VIEW)
  @Timed
  public Collection<GuardianDTO> getAllByKlassUpdate(@RequestParam("schoolId") Integer schoolId,
                                               @RequestParam("klassId") Integer klassId) throws JsonProcessingException {
    Collection<GuardianDTO> guardianDTOS = guardianMapper.toDTOs(guardianService.getAllByKlassUpdate(klassId, schoolId));
    logger.info(objectMapper.writeValueAsString(guardianDTOS));
    return guardianDTOS;
  }

  @ApiOperation("删除监护人")
  @DeleteMapping("/{guardianId:\\d+}")
  @PermissionRequired(Permission.GUARDIAN_DELETE)
  @Timed
  public void delete(@PathVariable Integer guardianId) {
    guardianService.delete(guardianId);
  }

  @ApiOperation("/查询监护人的孩子")
  @GetMapping("/findChildren")
  public Set<Guardian> findChildren(@Active Actor actor){
    return guardianService.findChildren(actor);
  }
}
