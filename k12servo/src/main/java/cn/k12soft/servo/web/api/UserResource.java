package cn.k12soft.servo.web.api;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.RelationType;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.SchoolService;
import cn.k12soft.servo.service.UserService;
import cn.k12soft.servo.service.dto.UserDistrictDTO;
import cn.k12soft.servo.web.form.*;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

import static cn.k12soft.servo.domain.enumeration.ActorType.MANAGER;

@RestController
@RequestMapping
public class UserResource {

  private final UserService userService;
  private final SchoolService schoolService;

  @Autowired
  public UserResource(UserService userService, SchoolService schoolService) {
    this.userService = userService;
    this.schoolService = schoolService;
  }

  @ApiOperation("用户注册")
  @PostMapping("/users")
  @Timed
  public ResponseEntity<?> register(@RequestBody @Valid UserForm form) {
    return userService.register(form);
  }

  @ApiOperation("手机号注册")
  @PutMapping("/tokens/registerUserByMobile")
  public ResponseEntity<UserDistrictDTO> registerUserByMobile(@RequestBody @Valid RegisterForm form){
    return this.userService.registerUserByMobile(form);
  }

  /**
   * 手机号验证码、判断手机号有没有被邀请
   * 没有被邀清需要返回一个识别码，前端发来一个学校id、班级id
   * @param form
   * @return
   */
  @ApiOperation("1、手机号注册，没有被邀请")
  @PutMapping("/tokens/registerUserByMobileNo")
  public Map<String, String>  registerUserByMobileNo(@RequestBody @Valid RegisterForm form){
    return this.userService.registerUserByMobileNo(form);
  }

  @ApiOperation("用户请求重置密码")
  @PostMapping("/users/{mobile}/password")
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public void resetPasswordApply(@PathVariable String mobile) {
    userService.resetPasswordApply(mobile);
  }

  @ApiOperation("用户重置密码")
  @PutMapping("/users/{mobile}/password")
  @Timed
  public void resetPassword(@PathVariable String mobile,
                            @RequestBody @Valid ResetPasswordForm form) {
    userService.resetPassword(mobile, form);
  }

  @ApiOperation("外部创建新建学校")
  @PostMapping("/schools")
  @Timed
  @ResponseStatus(HttpStatus.CREATED)
  public School create(@RequestBody @Valid SchoolForm form) {
    School school = schoolService.create(form);
    return school;
  }

  @ApiOperation("更改密码")
  @PutMapping("/api/users/u/password")
  @Timed
  public void update(@Active Actor actor,
                     @RequestBody @Valid ChangePasswordForm form) {
    userService.changePassword(actor.getUserId(), form);
  }

  @ApiOperation("更新当前用户信息")
  @PutMapping("/api/users")
  @Timed
  public ResponseEntity<User> update(@Active Actor actor,
                                     @RequestBody UserForm form) {
    return ResponseEntity.ok(userService.update(actor.getUserId(), form));
  }

  /**
   * 自己注册，非邀请
   * @param mobile
   * @param klassId
   * @param stuName
   * @param schoolId
   * @param relationType
   */
  @ApiOperation("扫码后提交手机号、班级id、学生姓名、学校id")
  @GetMapping("/tokens/CreateUser")
  public ResponseEntity CreateUser(@RequestParam @Valid String mobile,
                         @RequestParam @Valid Integer klassId,
                         @RequestParam @Valid String stuName,
                         @RequestParam @Valid Integer schoolId,
                         @RequestParam @Valid RelationType relationType){
    return this.userService.createUser(mobile, klassId, stuName, schoolId, relationType);
  }

  @ApiOperation("教师查询未激活的用户，有klassId时按照班级查，没有时查询全部")
  @GetMapping("/user/findUnActive")
  public Collection<UserPojo> findUnActive(@Active Actor actor,
                                           @RequestParam(required = false) @Valid Integer klassId){
    return this.userService.findUnActive(actor, klassId);
  }

  @ApiOperation("教师批量激活用户，将userid用英文逗号隔开")
  @PutMapping("/doActive")
  public void doActive(@Active Actor actor,
                       @RequestBody @Valid String ids){
    this.userService.doActive(ids);
  }

}
