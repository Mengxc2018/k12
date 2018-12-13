package cn.k12soft.servo.module.taxRateModel.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.taxRateModel.domain.TaxRateModel;
import cn.k12soft.servo.module.taxRateModel.domain.form.TaxRateModelForm;
import cn.k12soft.servo.module.taxRateModel.service.TaxRateModelService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/taxRateModel/Management")
public class TaxRateModelManagement {

    private final TaxRateModelService taxRateModelService;

    @Autowired
    public TaxRateModelManagement(TaxRateModelService taxRateModelService) {
        this.taxRateModelService = taxRateModelService;
    }

    @ApiOperation("创建模版")
    @PostMapping
    public TaxRateModel created(@Active Actor actor,
                                @RequestBody @Valid TaxRateModelForm form){
        return taxRateModelService.created(actor, form);
    }

    @ApiOperation("修改模版")
    @PutMapping
    public TaxRateModel update(@Active Actor actor,
                               @RequestParam @Valid Integer id,
                               @RequestBody @Valid TaxRateModelForm form){
        return taxRateModelService.update(actor, id, form);
    }

    @ApiOperation("查询所有模版")
    @GetMapping("/findAll")
    public Collection<TaxRateModel> find(@Active Actor actor){
        return taxRateModelService.findAll();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public void deleteBy(@RequestParam @Valid Integer id){

        taxRateModelService.deleteBy(id);

    }

}
