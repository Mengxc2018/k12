package cn.k12soft.servo.module.wxLogin.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.AttendPeriodStat;
import cn.k12soft.servo.domain.enumeration.ActiveSourceType;
import cn.k12soft.servo.domain.enumeration.WxActiveType;
import cn.k12soft.servo.module.wxLogin.domain.WxActiveDTO;
import cn.k12soft.servo.module.wxLogin.domain.WxMsgBoard;
import cn.k12soft.servo.module.wxLogin.domain.WxPushMsg;
import cn.k12soft.servo.module.wxLogin.domain.form.PushCodeForm;
import cn.k12soft.servo.module.wxLogin.domain.form.TemplateMessage;
import cn.k12soft.servo.module.wxLogin.domain.form.WxMsgBoardForm;
import cn.k12soft.servo.module.wxLogin.domain.form.WxUsersRegisterForm;
import cn.k12soft.servo.module.wxLogin.service.WxMsgBoardService;
import cn.k12soft.servo.module.wxLogin.service.WxService;
import cn.k12soft.servo.module.wxLogin.service.WxUserService;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.dto.UserDTO;
import cn.k12soft.servo.web.form.TokenForm;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wxLogin")
public class WxManagement {

    private Logger log = LoggerFactory.getLogger(WxManagement.class);
    private final WxUserService wxLoginService;
    private final WxService wxService;
    private final WxMsgBoardService wxMsgBoardService;

    @Autowired
    public WxManagement(WxUserService wxLoginService, WxService wxService, WxMsgBoardService wxMsgBoardService) {
        this.wxLoginService = wxLoginService;
        this.wxService = wxService;
        this.wxMsgBoardService = wxMsgBoardService;
    }

    public static final String URL = "https://api.weixin.qq.com/cgi-bin/wxopen/template/list?access_token=ACCESS_TOKEN";
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    @ApiOperation("测试：微信用户注册")
    @PostMapping("/web/wxUsersTest")
    @Timed
    public ResponseEntity<?> registerTest(@RequestParam @Valid String code,
                                          @RequestParam @Valid String mobile,
                                          @RequestParam @Valid String verificationCode) {
        return wxLoginService.registerTest(mobile, code, verificationCode);
    }

    @ApiOperation("微信用户注册")
    @PostMapping("/web/wxUsersRegr")
    @Timed
    public Map<String, Object> wxUsersRegister(@RequestBody @Valid WxUsersRegisterForm form) {
        return wxLoginService.wxUsersRegister(form);
    }

    @ApiOperation("微信用户用code登录")
    @PostMapping("/web/wxTokens")
    @Timed
    public ResponseEntity<UserDTO> userWxLogin(@RequestParam @Valid String code) throws JsonProcessingException {
        return wxLoginService.userWxLogin(code);
    }

    @ApiOperation("微信用户手机号密码code登录")
    @PostMapping("/web/wxTokensPWD")
    @Timed
    public ResponseEntity<UserDTO> userLogin(@RequestParam @Valid String code,
                                             @RequestBody @Valid TokenForm form) throws JsonProcessingException {
        return wxLoginService.userLogin(code, form);
    }

    @ApiOperation("获取帐号下已存在的模板列表")
    @GetMapping("/getTemplateList")
    public Map<String, Object> getTemplateList(@Active Actor actor) {
        return wxService.getTemplateList(actor);
    }

    @ApiOperation("发送模版消息")
    @PostMapping("/sendTemplateMessage")
    public Map<String, Object> sendTemplateMessage(@Active Actor actor,
                                              @RequestBody @Valid TemplateMessage form){
        return wxService.sendTemplateMessage(actor, form, null);
    }

    @ApiOperation("获取推送码、删除过期推送码")
    @PostMapping("/getPushCode")
    public void getPushCode(@Active Actor actor,
                            @RequestBody @Valid List<PushCodeForm> forms){
        wxService.getPushCode(actor, forms);
    }

    @ApiOperation("删除过期推送码")
    @GetMapping("/deleteTimeout")
    public void getPushCode(@Active Actor actor){
        wxService.deleteTimeout(actor);
    }

    @ApiOperation("查询推送消息，按天查询，按周查询。填date按天查询，不填按照周查询")
    @GetMapping("/findWxPushMassage")
    public Collection<WxPushMsg> findWxPushMassage(@Active Actor actor,
                                                   @RequestParam(required = false) @Valid LocalDate date){
        return wxService.findWxPushMassage(actor, date);
    }


    @ApiOperation("统计微信小程序活跃接口")
    @PostMapping("/countActive")
    public void countActive(@Active Actor actor,
                            @RequestParam @Valid WxActiveType wxActiveType,
                            @RequestParam @Valid ActiveSourceType source){
        wxService.countActive(actor, wxActiveType, source);
    }

    /**
     *
     * @param actor
     * @param type
     * @param localDate
     * @param actorId
     * @return
     */
    @ApiOperation("按周、月查询小程序登录活跃度，actorID为空，则查询所有人的，不为空，查询单个人")
    @GetMapping("/findActive")
    public Collection<WxActiveDTO> findWeekOrMonth(@Active Actor actor,
                                                   @RequestParam @Valid AttendPeriodStat.PeriodType type,
                                                   @RequestParam @Valid LocalDate localDate,
                                                   @RequestParam(required = false) @Valid Integer actorId){
        return wxService.findActive(actor, type, actorId, localDate);
    }

    @ApiOperation("查询所有留言")
    @GetMapping("/findAllMassageBoard")
    public Collection<WxMsgBoard> findAll(@Active Actor actor,
                                          @RequestParam @Valid LocalDate date){
        return wxMsgBoardService.findAllBySchoolId(actor, date);
    }

    @ApiOperation("新建留言")
    @PostMapping("/addMassage")
    public WxMsgBoard created(@Active Actor actor,
                              @RequestBody @Valid WxMsgBoardForm form){
        return wxMsgBoardService.created(actor, form);
    }

    @ApiOperation("短信验证码")
    @PostMapping("/web/sendMsmCode")
    public void sendMsmCode(@RequestParam @Valid String mobile){
        wxLoginService.sendMsmCode(mobile);
    }

}
