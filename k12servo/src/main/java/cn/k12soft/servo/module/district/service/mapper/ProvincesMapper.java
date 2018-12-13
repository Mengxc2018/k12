package cn.k12soft.servo.module.district.service.mapper;

import cn.k12soft.servo.module.district.form.dto.ProvincesDTO;
import cn.k12soft.servo.module.zone.domain.Provinces;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ProvincesMapper extends EntityMapper<Provinces, ProvincesDTO>{
    @Override
    protected ProvincesDTO convert(Provinces provinces) {
        return new ProvincesDTO(
                provinces.getId(),
                provinces.getName(),
                provinces.getCode()
        );
    }
}
