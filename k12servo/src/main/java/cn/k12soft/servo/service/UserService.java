package cn.k12soft.servo.service;

import static cn.k12soft.servo.domain.enumeration.UserState.INACTIVE;
import static cn.k12soft.servo.util.HTTPHeaders.BEARER_PREFIX;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import cn.k12soft.servo.domain.*;
import cn.k12soft.servo.domain.enumeration.RelationType;
import cn.k12soft.servo.domain.enumeration.UserState;
import cn.k12soft.servo.module.wxLogin.domain.WxUsers;
import cn.k12soft.servo.module.wxLogin.repository.WxUserRepository;
import cn.k12soft.servo.repository.*;
import cn.k12soft.servo.security.AuthenticationException;
import cn.k12soft.servo.security.jwt.JWTProvider;
import cn.k12soft.servo.service.dto.UserDistrictDTO;
import cn.k12soft.servo.service.mapper.UserDistrictMapper;
import cn.k12soft.servo.third.aliyun.AliyunSMSService;
import cn.k12soft.servo.web.form.ChangePasswordForm;
import cn.k12soft.servo.web.form.RegisterForm;
import cn.k12soft.servo.web.form.ResetPasswordForm;
import cn.k12soft.servo.web.form.UserForm;
import java.net.URI;
import java.time.Instant;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService extends AbstractEntityService<User, Integer> {

  private static final HttpHeaders TEXT_PLAIN;

  static {
    TEXT_PLAIN = new HttpHeaders();
    TEXT_PLAIN.setContentType(MediaType.TEXT_PLAIN);
  }

  private final AliyunSMSService aliyunSMSService;
  private final InvitationRepository invitationRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTProvider jwtProvider;
  private final UserDistrictMapper userMapper;
  private final StudentRepository studentRepository;
  private final GuardianRepository guardianRepository;
  private final ActorRepository actorRepository;
  private final WxUserRepository wxUserRepository;

  @Autowired
  public UserService(UserRepository repository,
                     AliyunSMSService aliyunSMSService,
                     InvitationRepository invitationRepository,
                     PasswordEncoder passwordEncoder, JWTProvider jwtProvider, UserDistrictMapper userMapper, StudentRepository studentRepository, GuardianRepository guardianRepository, ActorRepository actorRepository, WxUserRepository wxUserRepository) {
    super(repository);
    this.aliyunSMSService = aliyunSMSService;
    this.invitationRepository = invitationRepository;
    this.passwordEncoder = passwordEncoder;
      this.jwtProvider = jwtProvider;
      this.userMapper = userMapper;
    this.studentRepository = studentRepository;
    this.guardianRepository = guardianRepository;
      this.actorRepository = actorRepository;
      this.wxUserRepository = wxUserRepository;
  }

  public User createUserNotActivated(String mobile, String username, boolean is) {
    User user = new User();
    user.mobile(mobile)
      .setUserState(UserState.INACTIVE)
      .username(username)
      .setOneSelf(is);
    return getEntityRepository().save(user);
  }

  public User createUser4OpManager(String mobile, String userName){
    User user = new User();
    user.mobile(mobile).setUserState(UserState.ACTIVE).username(userName).password("123456");
    return user;
  }

  public ResponseEntity<?> register(UserForm form) {
    return invitationRepository.findOneByMobile(form.getMobile())
//      .filter(invitation -> Objects.equals(invitation.getSecretCode(), FeeDetailsForm.getSecretCode()))
      .map(invitation -> {
        User user = this.getEntityRepository().queryByMobile(form.getMobile());
        activate(user.getId(), form);
//        invitationRepository.delete(invitation.getId());
        return ResponseEntity.created(URI.create("/api/users" + user.getId())).body(user);
      })
      .orElseGet(() ->
        new ResponseEntity("No invitation info found or not match", TEXT_PLAIN, BAD_REQUEST)
      );
  }

  private User activate(Integer userId, UserForm form) {
    User user = getEntityRepository().getOne(userId);
    user.username(form.getUsername())
      .gender(form.getGender())
      .password(passwordEncoder.encode(form.getPassword()))
      .setUserState(UserState.ACTIVE)
      .avatar(form.getAvatar())
      .portrait(form.getPortrait())
      .createdAt(Instant.now());
    return getEntityRepository().save(user);
  }

  public User update(Integer userId, UserForm form) {
    User user = getEntityRepository().getOne(userId);
    if (form.getAvatar() != null) {
      user.avatar(form.getAvatar());
    }
    if (form.getPortrait() != null) {
      user.portrait(form.getPortrait());
    }

    user.gender(form.getGender());
    user.username(form.getUsername());
    user.setUpdate(true);
    return getEntityRepository().save(user);
  }

  public Optional<User> findByMobile(String mobile) {
    return getEntityRepository().findByMobile(mobile);
  }

  public void changePassword(Integer userId,
                             ChangePasswordForm form) {
    User user = getEntityRepository().getOne(userId);
    if (!passwordEncoder.matches(form.getOld(), user.getPassword())) {
      throw new IllegalArgumentException("Password");
    }
    user.password(passwordEncoder.encode(form.getNow()));
    getEntityRepository().save(user);
  }

  public void resetPasswordApply(String mobile) {
    UserRepository userRepository = getEntityRepository();
    userRepository.findByMobile(mobile)
      .ifPresent(user -> {
        user = userRepository.save(user.resetCode(generateSecretCode()));
        aliyunSMSService.sendPasswordResetCode(mobile, user.getResetCode());
      });
  }


  public void resetPassword(String mobile, ResetPasswordForm form) {
    UserRepository userRepository = getEntityRepository();
    userRepository.findByMobile(mobile)
      .ifPresent(user -> {
        int resetCode = form.getCode();
        if (user.getResetCode() != null && user.getResetCode() == resetCode) {
          user.setPassword(passwordEncoder.encode(form.getPassword()));
          user.clearReset();
          userRepository.save(user);
        } else {
          throw new IllegalArgumentException("Reset code error");
        }
      });
  }

  private static int generateSecretCode() {
    return Integer.parseInt(RandomStringUtils.randomNumeric(6));
  }

  @Override
  protected UserRepository getEntityRepository() {
    return (UserRepository) super.getEntityRepository();
  }

  public ResponseEntity<UserDistrictDTO> registerUserByMobile(RegisterForm form) {
      Optional<Invitation> invitation = invitationRepository.findOneByMobile(form.getMobile());
      if (!invitation.isPresent()){
          return null;
      }

      if(!form.getCode().equals(String.valueOf(invitation.get().getSecretCode()))){
        throw new IllegalArgumentException("验证码不匹配！");
      }

      String token = "";
      Optional<User> userOptional = this.getEntityRepository().findByMobile(form.getMobile());

      // 如果存在，更新用户user权限，返回token
      if(userOptional.isPresent()){
        return updateUserState(userOptional);
      }else {
        // 如果不存在，返回信息
        Map<String, Object> map = new HashMap<>();
        map.put("errCode", "4003");
        map.put("mobile", form.getMobile());
        map.put("massage", "用户不存在");
        throw new IllegalArgumentException(map.toString());
      }
  }

  /**
   * 没有被邀清时，手机号先放到验证码invitation表里
   * 4001 用户没有被邀清
   * 4002 验证码不匹配
   * @param form
   * @return
   */
  public Map<String, String> registerUserByMobileNo(RegisterForm form) {

    Map<String, String> map = new HashMap<>();
    Optional<Invitation> invitation = invitationRepository.findOneByMobile(form.getMobile());
    if (!invitation.isPresent()){
      return null;
    }else {
      if(!form.getCode().equals(String.valueOf(invitation.get().getSecretCode()))){
        map.put("code", "4002");
        map.put("mobile", form.getMobile());
        map.put("massage", "验证码不匹配");
        return map;
      }

      Optional<User> userOptional = this.getEntityRepository().findByMobile(form.getMobile());
      if(!userOptional.isPresent()){
        map.put("code", "4003");
        map.put("mobile", form.getMobile());
        map.put("massage", "用户不存在");
        return map;
      }else{
        registerUserByMobile(form);
      }
    }
    return null;

  }

  public void createUser(String mobile, Integer klassId, String stuName, Integer schoolId, RelationType relationType) {
    // user actor 的创建
    String username = "此家伙很懒，还没改名字";
    List<Student> students = this.studentRepository.getByName(stuName);
    if (students.size() == 0){
      throw new IllegalArgumentException("没有找到该儿童，请与教师核对儿童姓名");
    }

    // 创建user、actor
    Actor assoc = createUserActor(mobile, username, schoolId);

    // 儿童与家长的绑定
    Integer actorId = assoc.getId();
    Student student = studentRepository.findOne(students.get(0).getId());
    Guardian guardian = new Guardian(actorId, student, relationType);
    guardianRepository.save(guardian);
  }

  /**
   * 创建用户的user actor
   * @param mobile
   * @param username
   * @param schoolId
   * @return
   */
  public Actor createUserActor(String mobile, String username, Integer schoolId){
    User user = findByMobile(mobile).orElseGet(() -> createUserNotActivated(mobile, username, true));
    return user.getActors().stream()
            .filter(actor -> Objects.equals(actor.getSchoolId(), schoolId))
            .findAny()
            .orElseGet(() -> {
              Actor actor = new Actor(schoolId, user.getId());
              actor = actorRepository.save(actor);
              user.getActors().add(actor);

              // 默认密码：123456
              user.setPassword(passwordEncoder.encode("123456"));
              user.setUsername(username);
              user.setUpdate(true);
              user.setOneSelf(false);
              getEntityRepository().save(user);

              // 如果有微信，更新微信
                WxUsers wxUsers = wxUserRepository.findByMobile(user.getMobile());
                if (wxUsers != null){
                    wxUsers.setActor((List<Actor>) user.getActors());
                    wxUsers.setUser(user);
                    wxUserRepository.save(wxUsers);
                }
              return actor;
            });
  }

  /**
   * 更新用户user状态，并返回token
   * @param userOptional
   * @return
   */
  public ResponseEntity<UserDistrictDTO> updateUserState(Optional<User> userOptional){
    String token = "";
    User user = userOptional.get();
    String mobile = user.getMobile();
    try {
      // 更新用户状态
      user.setUserState(UserState.ACTIVE);
      this.getEntityRepository().save(user);
      List<Invitation> one = invitationRepository.findByMobile(mobile);
      for (Invitation i : one){
        invitationRepository.delete(i);
      }

      token = jwtProvider.createToken(user, true);
      return ResponseEntity.ok()
              .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token)
              .body(userMapper.toDTO(userOptional.get(), token));

    } catch (org.springframework.security.core.AuthenticationException ex) {
      throw new AuthenticationException(ex.getMessage());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Collection<User> findUnActive(Actor actor) {
    Integer schoolId = actor.getSchoolId();
    return this.getEntityRepository().findAllByUserStateAndIsOneSelf(schoolId, INACTIVE, true);
  }

    public void doActive(String ids) {
        String[] ida = ids.split(",");
        for (String idx : ida){
            User user = this.getEntityRepository().findOne(Integer.valueOf(idx));
            user.setUserState(UserState.ACTIVE);
            this.getEntityRepository().save(user);
        }
    }
}