package cn.k12soft.servo.module.supplier.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.supplier.domain.Supplier;
import cn.k12soft.servo.module.supplier.domain.form.SupplierForm;
import cn.k12soft.servo.module.supplier.service.SupplierService;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supplier/management")
public class SupplierManagement {

    private final SupplierService service;

    public SupplierManagement(SupplierService service) {
        this.service = service;
    }

    @ApiOperation("创建厂商")
    @PostMapping("/created")
    public void create(@Active Actor actor,
                       @RequestBody @Valid List<SupplierForm> forms){
        this.service.createMany(forms);
    }

    @ApiOperation("更新")
    @PutMapping("/put")
    public Supplier update(@Active Actor actor,
                           @RequestParam @Valid Integer id,
                           @RequestBody @Valid SupplierForm form){
        return this.service.update(id, form);
    }

    @ApiOperation("查询厂商，可条件查询")
    @GetMapping("/find")
    public Collection<Supplier> find(@Active Actor actor,
                                     @RequestParam(required = false) @Valid String name,
                                     @RequestParam(required = false) @Valid String contacts,
                                     @RequestParam(required = false) @Valid String mobile,
                                     @RequestParam(required = false) @Valid String telephone){
        return this.service.find(actor, name, contacts, mobile, telephone);
    }

    @ApiOperation("删除，可批量")
    @DeleteMapping("/deleteBy")
    public void deleteBy(@Active Actor actor,
                         @RequestParam @Valid String ids){
        this.service.deleteBy(ids);
    }

}
