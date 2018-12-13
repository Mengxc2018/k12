package cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.service.mapper;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.enumeration.AllotType;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.WarehouseSchoolAllot;
import cn.k12soft.servo.module.warehouse.warehouseSchoolAllot.domain.dto.WarehouseSchoolAllotDTO;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WarehouseSchoolAllotMapper extends EntityMapper<WarehouseSchoolAllot, WarehouseSchoolAllotDTO>{
    private final KlassRepository klassRepository;

    @Autowired
    public WarehouseSchoolAllotMapper(KlassRepository klassRepository) {
        this.klassRepository = klassRepository;
    }

    @Override
    protected WarehouseSchoolAllotDTO convert(WarehouseSchoolAllot allot) {
        AllotType type = allot.getType();
        String idstr = allot.getApplyBy();
        Klass klass = null;
        switch (type){
            case DEPT:
                break;
            case KLASS:
                klass = klassRepository.findOne(Integer.valueOf(idstr));
                break;
        }
        return new WarehouseSchoolAllotDTO(
                allot.getId(),
                allot.getName(),
                allot.getIsbn(),
                allot.getSpec(),
                allot.getWareNum(),
                allot.getAllotNum(),
                allot.getType(),
                (Object) klass,
                allot.getPrice(),
                allot.getIsAllot(),
                allot.getCreatedBy(),
                allot.getAllotBy(),
                allot.getCreatedAt(),
                allot.getSchoolId()
        );
    }
}
