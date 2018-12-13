package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.TEACHING_DELETE;
import static cn.k12soft.servo.domain.enumeration.Permission.TEACHING_GET;
import static cn.k12soft.servo.domain.enumeration.Permission.TEACHING_POST;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.TeachingService;
import cn.k12soft.servo.service.dto.TeachingDTO;
import cn.k12soft.servo.service.mapper.TeachingMapper;
import cn.k12soft.servo.web.form.TeachingForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2017/8/2.
 */
@RestController
@RequestMapping("/management/teachings")
public class TeachingManagement {

  private final TeachingService teachingService;
  private final TeachingMapper teachingMapper;

  @Autowired
  public TeachingManagement(TeachingService teachingService, TeachingMapper teachingMapper) {
    this.teachingService = teachingService;
    this.teachingMapper = teachingMapper;
  }

  @ApiOperation("获取任命列表")
  @GetMapping
  @PermissionRequired(TEACHING_GET)
  @Timed
  public Collection<TeachingDTO> getAll(@Active Actor actor) {
    return teachingMapper.toDTOs(teachingService.findAllBySchoolId(actor.getSchoolId()));
  }

  @ApiOperation("任命老师到指定班级")
  @PostMapping
  @PermissionRequired(TEACHING_POST)
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public TeachingDTO create(@RequestBody @Valid TeachingForm form) {
    return teachingMapper.toDTO(teachingService.create(form));
  }

  @ApiOperation("删除老师任命")
  @DeleteMapping("/{teachingId:\\d+}")
  @PermissionRequired(TEACHING_DELETE)
  @Timed
  public void delete(@PathVariable Integer teachingId) {
    teachingService.delete(teachingId);
  }
}
