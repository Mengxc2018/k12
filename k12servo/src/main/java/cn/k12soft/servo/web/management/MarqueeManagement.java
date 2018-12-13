package cn.k12soft.servo.web.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.Marquee;
import cn.k12soft.servo.domain.enumeration.Permission;
import cn.k12soft.servo.security.Active;
import cn.k12soft.servo.security.permission.PermissionRequired;
import cn.k12soft.servo.service.MarqueeService;
import cn.k12soft.servo.web.form.MarqueeForm;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

/**
 * Created by xfnjlove on 2017/12/26.
 */
@RestController
@RequestMapping("/management/marquees")
public class MarqueeManagement {

    private final MarqueeService marqueeService;

    @ApiOperation("创建园所图片")
    @PostMapping
    @PermissionRequired(Permission.MARQUEE_CREATE)
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public Marquee create(@Active Actor actor, @RequestBody @Valid MarqueeForm form) {
        return marqueeService.create(actor, form);
    }

    public MarqueeManagement(MarqueeService marqueeService) {
        this.marqueeService = marqueeService;
    }

    @ApiOperation("获取园所图片列表")
    @GetMapping(params = {"schoolId"})
    @PermissionRequired(Permission.MARQUEE_VIEW)
    @Timed
    public List<Marquee> getAll(@RequestParam("schoolId") Integer schoolId) {

        List<Marquee> marquees;
        marquees = marqueeService.findAllBySchool(schoolId);
        return marquees;
    }

    @ApiOperation("更新园所图片")
    @PutMapping("/{marqueeId:\\d+}")
    @PermissionRequired(Permission.MARQUEE_PUT)
    @Timed
    public Marquee update(@PathVariable Integer marqueeId,
                           @RequestBody @Valid MarqueeForm form) {
        return marqueeService.update(marqueeId, form);
    }

    @ApiOperation("删除园所图片")
    @DeleteMapping("/{marqueeId:\\d+}")
    @PermissionRequired(Permission.MARQUEE_DELETE)
    @Timed
    public void delete(@PathVariable Integer marqueeId) {
        marqueeService.delete(marqueeId);
    }
}

