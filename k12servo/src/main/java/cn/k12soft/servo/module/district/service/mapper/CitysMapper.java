package cn.k12soft.servo.module.district.service.mapper;

import cn.k12soft.servo.module.district.form.dto.CitysDTO;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class CitysMapper extends EntityMapper<Citys, CitysDTO>{
    @Override
    protected CitysDTO convert(Citys citys) {
        return new CitysDTO(
                citys.getId(),
                citys.getName(),
                citys.getCode()
        );
    }
}
