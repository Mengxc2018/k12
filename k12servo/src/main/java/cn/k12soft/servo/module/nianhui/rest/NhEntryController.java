package cn.k12soft.servo.module.nianhui.rest;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.form.ExpenseEntryForm;
import cn.k12soft.servo.module.expense.service.ExpenseEntryService;
import cn.k12soft.servo.module.weixin.pojo.SNSUserInfo;
import cn.k12soft.servo.module.weixin.pojo.WeixinOauth2Token;
import cn.k12soft.servo.module.weixin.util.AdvancedUtil;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.service.dto.ExpenseEntryDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by xfnjlove on 2018/1/6.
 */

@Controller
public class NhEntryController {

    private final ExpenseEntryService service;

    @Autowired
    public NhEntryController(ExpenseEntryService service) {
        this.service = service;
    }

    @ApiOperation("年会签到")
    @GetMapping("/nianhui/nianHuicheckIn")
    public String checkIn(@RequestParam ("code") String code,
                          Model model) throws ServletException, IOException {

        // 用户同意授权
        if (!"authdeny".equals(code)) {
            String APPID  = "APPID";
            String SECRET = "SECRET";
            APPID 	= "AppIDwx6d6dd21041a62d3f";
            SECRET 	= "67160e6d1f04a6f7f6a3dd4dafc16422";
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(APPID, SECRET, code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

            // 设置要传递的参数
            //request.setAttribute("snsUserInfo", snsUserInfo);

            model.addAttribute("hello",snsUserInfo.getNickname());
        }else{
            model.addAttribute("hello","no data");
        }

        return "/nianhui/nianHuicheckIn";
    }



    public String checkInTest(Map<String,Object> map){


        return "/nianhui/nianHuicheckIn";
    }

}
