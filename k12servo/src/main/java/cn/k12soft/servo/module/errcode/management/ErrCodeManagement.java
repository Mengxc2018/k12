package cn.k12soft.servo.module.errcode.management;

import cn.k12soft.servo.module.errcode.domain.ErrCode;
import cn.k12soft.servo.module.errcode.domain.ErrCodeForm;
import cn.k12soft.servo.module.errcode.service.ErrCodeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/web")
public class ErrCodeManagement {

    private final ErrCodeService errCodeService;

    @Autowired
    public ErrCodeManagement(ErrCodeService errCodeService) {
        this.errCodeService = errCodeService;
    }

    @ApiOperation("查询错误码对应的错误信息")
    @GetMapping("/findAll")
    public ResponseEntity<List<ErrCode>> findAll(){
        return errCodeService.findBy();
    }

    @ApiOperation("添加")
    @PostMapping("/create")
    public ResponseEntity<List<ErrCode>> created(@RequestBody @Valid List<ErrCodeForm> form){
        return errCodeService.create(form);
    }

}
