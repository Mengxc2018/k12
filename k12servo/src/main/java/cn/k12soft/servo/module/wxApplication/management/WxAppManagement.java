package cn.k12soft.servo.module.wxApplication.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.wxApplication.domain.WxAppForm;
import cn.k12soft.servo.module.wxApplication.domain.WxApplication;
import cn.k12soft.servo.module.wxApplication.service.WxAppService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/wxApplication")
public class WxAppManagement {

    private final WxAppService wxAppService;

    @Autowired
    public WxAppManagement(WxAppService wxAppService) {
        this.wxAppService = wxAppService;
    }

    @ApiOperation("新增小程序信息")
    @PostMapping
    public WxApplication create(@Active Actor actor,
                                @RequestBody @Valid WxAppForm form){
        return wxAppService.create(actor, form);
    }

    @ApiOperation("编辑小程序信息")
    @PutMapping
    public WxApplication update(@Active Actor actor,
                                @RequestParam @Valid Integer id,
                                @RequestBody @Valid WxAppForm form){
        return wxAppService.update(actor, id, form);
    }

    @ApiOperation("查询小程序信息")
    @GetMapping
    public List<WxApplication> find(@Active Actor actor){
        return wxAppService.findApp(actor);
    }

    @ApiOperation("删除小程序信息")
    @DeleteMapping("/{id:\\d+}")
    public void deleteApp(@Active Actor actor,
                          @RequestParam @Valid Integer id){
        wxAppService.deleteApp(actor, id);
    }
}
