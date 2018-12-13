package cn.k12soft.servo.module.feeCollect.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.feeCollect.domain.dto.FeeCollectDTO;
import cn.k12soft.servo.module.feeCollect.service.FeeCollectService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/feeCollect/management")
public class FeeCollectManagement {

    private final FeeCollectService feeCollectService;

    @Autowired
    public FeeCollectManagement(FeeCollectService feeCollectService) {
        this.feeCollectService = feeCollectService;
    }

    @ApiOperation("月查询")
    @GetMapping("/findByMonth")
    public Collection<FeeCollectDTO> findByMonth(@Active Actor actor,
                                                 @RequestParam @Valid LocalDate localDate){
        return this.feeCollectService.findByMonth(actor, localDate);
    }

}
