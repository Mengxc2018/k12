package cn.k12soft.servo.web.api;

import static cn.k12soft.servo.util.HTTPHeaders.BEARER_PREFIX;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.UserState;
import cn.k12soft.servo.security.AuthenticationException;
import cn.k12soft.servo.security.UserPrincipal;
import cn.k12soft.servo.security.jwt.JWTFilter;
import cn.k12soft.servo.security.jwt.JWTProvider;
import cn.k12soft.servo.service.UserService;
import cn.k12soft.servo.service.dto.RegionUserDTO;
import cn.k12soft.servo.service.dto.UserDTO;
import cn.k12soft.servo.service.dto.UserDistrictDTO;
import cn.k12soft.servo.service.mapper.*;
import cn.k12soft.servo.web.form.TokenForm;
import cn.k12soft.servo.web.view.SchoolResult;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Transactional(readOnly = true)
public class TokenResource {

  private final AuthenticationManager authenticationManager;
  private final JWTProvider jwtProvider;
  private final UserService userService;
  private final UserMapper userMapper;
  private final UserDistrictMapper userDistrictMapper;
  private final JWTFilter jwtFilter;
  private final PatriarchMapper patriarchMapper;
  private final TeacherMapper teacherMapper;
  private final ManagerMapper managerMapper;

  @Autowired
  public TokenResource(AuthenticationManager authenticationManager,
                       JWTProvider jwtProvider,
                       UserService userService,
                       UserMapper userMapper,
                       UserDistrictMapper userDistrictMapper,
                       JWTFilter jwtFilter,
                       PatriarchMapper patriarchMapper,
                       TeacherMapper teacherMapper,
                       ManagerMapper managerMapper) {
    this.authenticationManager = authenticationManager;
    this.jwtProvider = jwtProvider;
    this.userService = userService;
    this.userMapper = userMapper;
      this.userDistrictMapper = userDistrictMapper;
      this.jwtFilter = jwtFilter;
    this.patriarchMapper = patriarchMapper;
    this.teacherMapper = teacherMapper;
    this.managerMapper = managerMapper;
  }

  @ApiOperation("用户认证")
  @PostMapping("/tokens")
  @Timed
  public ResponseEntity<UserDTO> authorize(@RequestBody @Valid TokenForm form)
    throws JsonProcessingException {
    UsernamePasswordAuthenticationToken authToken =
      new UsernamePasswordAuthenticationToken(form.getMobile(), form.getPassword());
    try {
      Authentication authentication = this.authenticationManager.authenticate(authToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      User user = userPrincipal.getUser();
      if (user.getUserState().compareTo(UserState.INACTIVE) == 0){
        throw new IllegalArgumentException("帐号未激活，请先激活！");
      }
      String token = jwtProvider.createToken(user, true);
        return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token)
        .body(userMapper.toDTO(user, token));
    } catch (org.springframework.security.core.AuthenticationException ex) {
      throw new AuthenticationException(ex.getMessage());
    }
  }

  @ApiOperation("用户登录到具体学校")
  @PostMapping("/tokens/school/{schoolId:\\d+}")
  @Timed
  public ResponseEntity<?> authorize(HttpServletRequest request,
                                     @PathVariable Integer schoolId) throws IOException {
    String jwt = jwtFilter.resolveToken(request)
      .orElseThrow(
        () -> new IllegalArgumentException("No permissions to visitSchool school:" + schoolId));
    Jws<Claims> jws = jwtProvider.parse(jwt);
    Integer userId = Integer.parseInt(jws.getBody().getSubject());
    User user = userService.get(userId);
    Actor actor = user.getActors().stream()
      .filter(a -> Objects.equals(a.getSchoolId(), schoolId))
      .findAny()
      .orElseThrow(
        () -> new IllegalArgumentException("No permissions to visitSchool school:" + schoolId));
    SchoolResult schoolResult = new SchoolResult();
    actor.getTypes().forEach(actorType -> {
      switch (actorType) {
        case PATRIARCH:
          schoolResult.setPatriarch(patriarchMapper.toDTO(actor));
          break;
        case TEACHER:
          schoolResult.setTeacher(teacherMapper.toDTO(actor));
          break;
        case MANAGER:
          schoolResult.setManager(managerMapper.toDTO(actor));
        default:
          //do nothing
      }
    });
    String actorToken = jwtProvider.createToken(actor, true);
    schoolResult.setToken(actorToken);
    return ResponseEntity.ok()
      .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + actorToken)
      .body(schoolResult);
  }

  @ApiOperation("区域用户登录")
  @PostMapping("/tokens/region")
  @Timed
  public ResponseEntity<?> authorize(HttpServletRequest request) throws IOException{
    String jwt = jwtFilter.resolveToken(request)
            .orElseThrow(
                    () -> new IllegalArgumentException("No permissions to visitSchool:"));
    Jws<Claims> jws = jwtProvider.parse(jwt);
    Integer userId = Integer.parseInt(jws.getBody().getSubject());
    User user = userService.get(userId);
    Actor actor = user.getActors().stream()
            .filter(a -> Objects.equals(a.getSchoolId(), 0))
            .findAny()
            .orElseThrow(
                    () -> new IllegalArgumentException("No permissions to visitSchool"));
    SchoolResult schoolResult = new SchoolResult();
    actor.getTypes().forEach(actorType -> {
      switch (actorType) {
        case CITY:
        case PROVINCE:
        case REGION:
        case GROUP:
          schoolResult.setRegionUser(RegionUserDTO.create(actor, actorType));
          break;
        default:
          //do nothing
      }
    });
    String actorToken = jwtProvider.createToken(actor, true);
    schoolResult.setToken(actorToken);
    return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + actorToken)
            .body(schoolResult);
  }


  @ApiOperation("用户手机号登录返回actorToken")
  @PostMapping("/tokens/district")
  public ResponseEntity<UserDistrictDTO> authorizeUser(@RequestBody @Valid TokenForm form)
          throws IOException {
      // 用户手机号密码登录
    UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(form.getMobile(), form.getPassword());
      Authentication authentication = this.authenticationManager.authenticate(authToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
      User user = userPrincipal.getUser();
      Actor actor = user.getActors().stream()
//      .filter(a -> Objects.equals(a.getTypes(), 0))
              .findAny()
              .orElseThrow(
                      () -> new IllegalArgumentException("No permissions to visitSchool"));
      SchoolResult schoolResult = new SchoolResult();

      actor.getTypes().forEach(actorType -> {
          switch (actorType) {
              case CITY:
                  schoolResult.setRegionUser(RegionUserDTO.create(actor, actorType));
                  break;
              case PROVINCE:
                  schoolResult.setRegionUser(RegionUserDTO.create(actor, actorType));
                  break;
              case REGION:
                  schoolResult.setRegionUser(RegionUserDTO.create(actor, actorType));
                  break;
              case GROUP:
                  schoolResult.setRegionUser(RegionUserDTO.create(actor, actorType));
                  break;
              default:
                  //do nothing
          }
      });
      String actorToken = jwtProvider.createToken(actor, true);
      schoolResult.setToken(actorToken);
      return ResponseEntity.ok()
              .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + actorToken)
              .body(userDistrictMapper.toDTO(user, actorToken));
  }
}
