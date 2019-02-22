package cn.k12soft.servo.module.wxLogin.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Invitation;
import cn.k12soft.servo.domain.User;
import cn.k12soft.servo.domain.enumeration.UserState;
import cn.k12soft.servo.module.wxApplication.domain.WxApplication;
import cn.k12soft.servo.module.wxApplication.repository.WxAppRepository;
import cn.k12soft.servo.module.wxLogin.domain.WxUsers;
import cn.k12soft.servo.module.wxLogin.domain.form.WxUsersRegisterForm;
import cn.k12soft.servo.module.wxLogin.domain.pojo.WxErrPojo;
import cn.k12soft.servo.module.wxLogin.domain.pojo.WxPojo;
import cn.k12soft.servo.module.wxLogin.repository.WxUserRepository;
import cn.k12soft.servo.repository.ActorRepository;
import cn.k12soft.servo.repository.InvitationRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.security.AuthenticationException;
import cn.k12soft.servo.security.UserPrincipal;
import cn.k12soft.servo.security.jwt.JWTProvider;
import cn.k12soft.servo.service.AbstractRepositoryService;
import cn.k12soft.servo.service.InvitationService;
import cn.k12soft.servo.service.UserService;
import cn.k12soft.servo.service.dto.UserDTO;
import cn.k12soft.servo.service.mapper.UserMapper;
import cn.k12soft.servo.third.aliyun.AliyunSMSService;
import cn.k12soft.servo.web.form.TokenForm;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static cn.k12soft.servo.util.HTTPHeaders.BEARER_PREFIX;

@Service
@Transactional
public class WxUserService extends AbstractRepositoryService<WxUsers, Long, WxUserRepository> {
    private static final HttpHeaders TEXT_PLAIN;

    static {
        TEXT_PLAIN = new HttpHeaders();
        TEXT_PLAIN.setContentType(MediaType.TEXT_PLAIN);
    }
    private final InvitationRepository invitationRepository;
    private final ActorRepository actorRepository;
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final UserMapper userMapper;
    private final WxAppRepository wxAppRepository;
    private final AuthenticationManager authenticationManager;
    private final AliyunSMSService aliyunSMSService;
    private final UserService userService;

    @Autowired
    protected WxUserService(WxUserRepository repository,
                            InvitationRepository invitationRepository,
                            ActorRepository actorRepository,
                            UserRepository userRepository,
                            JWTProvider jwtProvider,
                            UserMapper userMapper,
                            WxAppRepository wxAppRepository,
                            AuthenticationManager authenticationManager,
                            AliyunSMSService aliyunSMSService,
                            UserService userService) {
        super(repository);
        this.invitationRepository = invitationRepository;
        this.actorRepository = actorRepository;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.userMapper = userMapper;
        this.wxAppRepository = wxAppRepository;
        this.authenticationManager = authenticationManager;
        this.aliyunSMSService = aliyunSMSService;
        this.userService = userService;
    }

    /**
     * 获取openid
     * @param code
     * @return
     */
    public WxPojo getWxOpenid(String code){
        List<WxApplication> all = wxAppRepository.findAll();
        WxApplication wxApp = all.get(0);
        String appid = "&appid=" + wxApp.getAppid();        // appid:wx6df6913b21e771ed
        String secret = "&SECRET=" + wxApp.getSecret();     // secret:03dfc8a010ca9db45e60cb020dfc3050
        String grantType = "&grant_type=" + wxApp.getGrantType();
        String jscode = "&js_code=" + code;
        String uri = "https://api.weixin.qq.com/sns/jscode2session?" + appid + secret + jscode + grantType;

        // 发送请求获取微信用户 openid 跟 session_key
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String strbody = restTemplate.exchange(uri, HttpMethod.GET, entity,String.class).getBody();
        WxErrPojo wxErrPojo = JSONObject.parseObject(strbody, WxErrPojo.class);
        if (wxErrPojo.getErrcode() != null || wxErrPojo.getErrmsg() != null){
            throw new IllegalArgumentException(wxErrPojo.toString());
        }
        WxPojo wxPojo = JSONObject.parseObject(strbody, WxPojo.class);

        return wxPojo;
    }

    /**
     * 保存更新微信openid等信息
     * @param wxPojo
     * @param mobileStr
     */
    public void wxUserUpdate(WxPojo wxPojo, String mobileStr){

        // 通过微信openid查询是否有此用户，如果没有用户，则注册，并且绑定手机号
        WxUsers wxUsers = getRepository().findByOpenid(wxPojo.getOpenid());

        if (wxUsers == null){
            // 获取用户信息
//            User user = userRepository.queryByMobile(mobileStr);
//            List<Actor> actors = actorRepository.findByUserId(user.getId());
            wxUsers = new WxUsers(
                    mobileStr,
                    wxPojo.getOpenid(),
                    wxPojo.getSession_key(),
                    null,
                    null
            );
            getRepository().save(wxUsers);
        }
    }

    public ResponseEntity<UserDTO> userWxLogin(String code)
        throws JsonProcessingException {
        WxPojo wxPojo = getWxOpenid(code);
        WxUsers wxUsers = getRepository().findByOpenid(wxPojo.getOpenid());
        if (wxUsers == null){
            throw new IllegalArgumentException("该用户未注册，请先注册");
        }else if (wxUsers.getMobile() == null || wxUsers.getOpenid() == null){
            throw new IllegalArgumentException("该用户未注册，请先注册");
        }
        try {
            User user = wxUsers.getUser();
            String token = jwtProvider.createToken(user, true);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token)
                    .body(userMapper.toDTO(user, token));
        } catch (org.springframework.security.core.AuthenticationException ex) {
            throw new AuthenticationException(ex.getMessage());
        }
    }

    public ResponseEntity<UserDTO> userLogin(String code, TokenForm form) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(form.getMobile(), form.getPassword());
        try {
            // 验证帐号密码
            Authentication authentication = this.authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            if (userPrincipal.getUser().getUserState().compareTo(UserState.INACTIVE) == 0){
                throw new IllegalArgumentException("帐号未激活，请先激活！");
            }

            // 通过微信code获取openid
            WxPojo wxPojo = getWxOpenid(code);
            // 保存微信信息
            wxUserUpdate(wxPojo, form.getMobile());

            User user = userPrincipal.getUser();
            String token = jwtProvider.createToken(user, true);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token)
                    .body(userMapper.toDTO(user, token));

        } catch (org.springframework.security.core.AuthenticationException ex) {
            throw new AuthenticationException(ex.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<?> registerTest(String mobile, String code, String verificationCode) {
//        UsernamePasswordAuthenticationToken authToken =
//                new UsernamePasswordAuthenticationToken(FeeDetailsForm.getMobile(), FeeDetailsForm.getPassword());
        try {
            // 验证帐号密码
//            Authentication authentication = this.authenticationManager.authenticate(authToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            // 通过微信code获取openid
            WxPojo wxPojo = getWxOpenid(code);
            // 保存微信信息
            wxUserUpdate(wxPojo, mobile);

            User user = this.userRepository.queryByMobile(mobile);
            if (user == null){
                throw new IllegalArgumentException("4000");
            }

            // 更新用户状态
            user.setUserState(UserState.ACTIVE);
            userRepository.save(user);
            List<Invitation> one = invitationRepository.findByMobile(mobile);
            for (Invitation i : one){
                invitationRepository.delete(i);
            }

            String token = jwtProvider.createToken(user, true);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token)
                    .body(userMapper.toDTO(user, token));

        } catch (org.springframework.security.core.AuthenticationException ex) {
            throw new AuthenticationException(ex.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<String, Object> wxUsersRegister(WxUsersRegisterForm form){
        String code = form.getCode();
        String mobile = form.getMobile();
        String verification = form.getVerificationCode();
        Map<String, Object> map = new LinkedHashMap<>();

        CompletableFuture completableFuture = CompletableFuture.supplyAsync(()->{
            // 通过微信code获取openid
            WxPojo wxPojo = getWxOpenid(code);
            // 保存微信信息
            wxUserUpdate(wxPojo, mobile);
            return null;
        });

        // 验证手机号是否重复
        Optional<User> userOptional = userRepository.findByMobile(mobile);
        System.out.println(userOptional.isPresent());
        if (userOptional.isPresent()){
            map.put("errCode", "4004");
            map.put("errMsg", "手机号已存在");
            throw new IllegalArgumentException(map.toString());
        }

        // 验证码效验
        boolean isInvitation = false;
        List<Invitation> invitations = invitationRepository.findByMobile(mobile);
        for (Invitation invitation : invitations){
            String secretCode = invitation.getSecretCode().toString();
            if (verification.equals(secretCode)){
                isInvitation = true;
            }
        }
        if (!isInvitation){
            map.put("errCode", "4002");
            map.put("errMsg", "验证码不匹配");
            throw new IllegalArgumentException(map.toString());
        }

        if (!userOptional.isPresent()){
            try {
                map.put("errCode", "4003");
                map.put("errMsg", "用户不存在");
                throw new IllegalAccessException(map.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else{
            // 如果用户存在，则激活
            userService.updateUserState(userOptional);
        }

        return map;
    }

    public void sendMsmCode(String mobile) {
        Integer code = Integer.valueOf(RandomStringUtils.randomNumeric(6));
        Collection<Invitation> invitations = this.invitationRepository.findByMobile(mobile);
        for (Invitation invitation : invitations){
            this.invitationRepository.delete(invitation);
        }
        User user = userRepository.queryByMobile(mobile);
        Invitation invitation = new Invitation();
        if (user != null) {
            Actor actor = actorRepository.findByUserIdAndMaxCreatedAt(user.getId());
            invitation.setActor(actor);
        }
        invitation.setMobile(mobile);
        invitation.setSecretCode(code);
        invitationRepository.save(invitation);

        aliyunSMSService.sendVerificationCode(mobile, code);
    }
}
