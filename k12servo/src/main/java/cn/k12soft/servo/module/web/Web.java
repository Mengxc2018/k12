package cn.k12soft.servo.module.web;

import cn.k12soft.servo.module.web.klassName.KlassNameDTO;
import cn.k12soft.servo.module.web.klassName.KlassNameMapper;
import cn.k12soft.servo.service.KlassService;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/web")
public class Web {

    private final KlassService klassService;
    private final KlassNameMapper klassNameMapper;

    @Autowired
    public Web(KlassService klassService, KlassNameMapper klassNameMapper) {
        this.klassService = klassService;
        this.klassNameMapper = klassNameMapper;
    }

    @ApiOperation("获取班级列表，不带token，不包括已经毕业的班级")
    @GetMapping("/getKlassList")
    @Timed
    public Collection<KlassNameDTO> getKlassList(@RequestParam @Valid Integer schoolId) {
        return klassNameMapper.toDTOs(klassService.getAllOfSchool(schoolId));
    }




}
