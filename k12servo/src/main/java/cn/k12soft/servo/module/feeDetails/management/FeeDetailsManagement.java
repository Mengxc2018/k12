package cn.k12soft.servo.module.feeDetails.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.feeDetails.domain.FeeDetails;
import cn.k12soft.servo.module.feeDetails.domain.FeeDetailsLog;
import cn.k12soft.servo.module.feeDetails.domain.form.FeeDetailsForm;
import cn.k12soft.servo.module.feeDetails.service.FeeDetailsService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/feeDetails/management")
public class FeeDetailsManagement {

    private final FeeDetailsService feeDetailsService;

    @Autowired
    public FeeDetailsManagement(FeeDetailsService feeDetailsService) {
        this.feeDetailsService = feeDetailsService;
    }

    @ApiOperation("新建一个缴费，如果没有海康账户，就创建一个，并且授权；有就只授权")
    @PostMapping("/create")
    public FeeDetails create(@Active Actor actor,
                             @RequestBody @Valid FeeDetailsForm form){
        return this.feeDetailsService.create(actor, form);
    }

    @ApiOperation("续费，金额相加，并重新授权")
    @PutMapping("/put")
    public FeeDetails putBy(@Active Actor actor,
                            @RequestParam @Valid Integer id,
                            @RequestBody @Valid FeeDetailsForm form){
        return this.feeDetailsService.putBy(actor, id, form);
    }

    @ApiOperation("删除缴费")
    @DeleteMapping("/deleteBy")
    public void deleteBy(@Active Actor actor,
                         @RequestParam @Valid String ids){
        this.feeDetailsService.deleteBy(actor, ids);
    }

    @ApiOperation("月查询缴费,actorId为空，查询所有缴费记录；不为空查询个人所有缴费记录")
    @GetMapping("/findBy")
    public List<FeeDetails> findBy(@Active Actor actor,
                                   @RequestParam(required = false) @Valid Integer actorId,
                                   @RequestParam @Valid LocalDate localDate){
        return this.feeDetailsService.findBy(actor, actorId, localDate);
    }

    @ApiOperation("月查询缴费记录,actorId为空，查询所有缴费记录；不为空查询个人所有缴费记录")
    @GetMapping("/findByLog")
    public List<FeeDetailsLog> findByLog(@Active Actor actor,
                                         @RequestParam(required = false) @Valid Integer actorId,
                                         @RequestParam @Valid LocalDate localDate){
        return this.feeDetailsService.findByLog(actor, actorId, localDate);
    }

    @ApiOperation("查询个人缴费情况")
    @GetMapping("/findByOne")
    public FeeDetails findByOne(@Active Actor actor){
        return this.feeDetailsService.findByOne(actor);
    }

    @ApiOperation("查询是否有权限")
    @GetMapping("/isLimit")
    public boolean islimit(@Active Actor actor){
        return this.feeDetailsService.isLimit(actor);
    }

}
