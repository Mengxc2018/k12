package cn.k12soft.servo.module.warehouse.warehouse.management;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.warehouse.warehouse.domain.Warehouse;
import cn.k12soft.servo.module.warehouse.warehouse.repositopry.WarehouseRepository;
import cn.k12soft.servo.security.Active;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/watehouse/management")
@Transactional
public class WarehouseManagement {

    private final WarehouseRepository wareHouseRepository;

    @Autowired
    public WarehouseManagement(WarehouseRepository wareHouseRepository) {
        this.wareHouseRepository = wareHouseRepository;
    }

    /**
     *
     * @param actor
     * @param name
     * @param isbn
     * @return
     */
    @ApiOperation("匹配库查询")
    @GetMapping("/find")
    public Warehouse find(@Active Actor actor,
                          @RequestParam(required = false) @Valid String name,
                          @RequestParam(required = false) @Valid String isbn){
        return isbn == null ? wareHouseRepository.findByName(name) : wareHouseRepository.findByIsbn(isbn);
    }

    @ApiOperation("查询库所有商品")
    @GetMapping("/findAll")
    public List<Warehouse> findAll(@Active Actor actor){
        return wareHouseRepository.findAllBySchoolId(actor.getSchoolId());
    }
}
