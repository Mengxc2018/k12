package cn.k12soft.servo.module.web.klassName;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.module.web.klassName.KlassNameDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class KlassNameMapper extends EntityMapper<Klass, KlassNameDTO>{
    @Override
    protected KlassNameDTO convert(Klass klass) {
        return new KlassNameDTO(
                klass.getId(),
                klass.getName()
        );
    }
}
