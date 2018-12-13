package cn.k12soft.servo.web.management;

import static cn.k12soft.servo.domain.enumeration.Permission.GUARDIAN_POST;
import static cn.k12soft.servo.domain.enumeration.Permission.MANAGER_POST;
import static cn.k12soft.servo.domain.enumeration.Permission.TEACHER_POST;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.InvitationService;
import cn.k12soft.servo.web.form.*;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2017/8/2.
 */
@RestController
@RequestMapping("/management/invitations")
public class InvitationManagement {

  private final InvitationService invitationService;

  public InvitationManagement(InvitationService invitationService) {
    this.invitationService = invitationService;
  }

  @ApiOperation("邀请家长")
  @PostMapping("/guardian")
  @PermissionRequired(GUARDIAN_POST)
  @Timed
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void createGuardian(@Active Actor actor,
                             @RequestBody @Valid GuardianForm form) {
    invitationService.inviteGuardian(actor, form);
  }

  @ApiOperation("邀请老师")
  @PostMapping("/teacher")
  @PermissionRequired(TEACHER_POST)
  @Timed
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void createTeacher(@Active Actor actor,
                            @RequestBody @Valid TeacherForm form) {
    invitationService.inviteTeacher(actor, form);
  }

  @ApiOperation("邀请老师测试")
  @PostMapping("/teacher/test")
  @PermissionRequired(TEACHER_POST)
  @Timed
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void createTeacherTest(@Active Actor actor,
                            @RequestParam("dutyId") Integer dutyId,
                            @RequestBody @Valid TeacherTestForm form) {
    invitationService.inviteTeacherTest(actor, dutyId, form);
  }



  @ApiOperation("邀请管理员")
  @PostMapping("/manager")
  @PermissionRequired(MANAGER_POST)
  @Timed
  @ResponseStatus(HttpStatus.ACCEPTED)
  @Transactional
  public void createManager(@Active Actor actor,
                            @RequestBody @Valid ManagerForm form) {
    invitationService.inviteManager(actor.getSchoolId(), form);
  }

  @ApiOperation("创建集团账号")
  @PostMapping("/groupManager")
  @Timed
  @ResponseStatus(HttpStatus.ACCEPTED)
  @Transactional
  public void createGroupManager(@Active Actor actor, @RequestBody @Valid ActorForm form){
    if(!actor.getTypes().contains(ActorType.MANAGER)){
      return;
    }
    invitationService.createGroupManager(actor, form);
  }

  @ApiOperation("创建区域账号")
  @PostMapping("/opManager")
  @Timed
  @ResponseStatus(HttpStatus.ACCEPTED)
  @Transactional
  public void createOpManager(@Active Actor actor, @RequestBody @Valid ActorForm form){
    invitationService.createOpManager(actor, form);
  }

  @ApiOperation("获取下级用户")
  @GetMapping("/users/findSub")
  @Timed
  public List<User> findByParentId(@Active Actor actor){
//    if(parentId == null || parentId ==0){
//      parentId = actor.getUserId();
//    }
    return invitationService.findByPid(actor.getId());
  }

  @ApiOperation("删除下级用户")
  @DeleteMapping("/users/delSub")
  @Timed
  public void delSub(@Active Actor actor, @RequestParam(value = "userId") Integer userId){
    invitationService.delSub(actor, userId);
  }

}



