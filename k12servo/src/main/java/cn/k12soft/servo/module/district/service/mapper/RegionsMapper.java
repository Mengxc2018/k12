package cn.k12soft.servo.module.district.service.mapper;

import cn.k12soft.servo.module.district.form.dto.RegionsDTO;
import cn.k12soft.servo.module.zone.domain.Regions;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class RegionsMapper extends EntityMapper<Regions, RegionsDTO>{
    @Override
    protected RegionsDTO convert(Regions regions) {
        return new RegionsDTO(
                regions.getId(),
                regions.getName(),
                regions.getCode()
        );
    }
}
