package cn.k12soft.servo.module.district.service.mapper;

import cn.k12soft.servo.module.district.form.dto.CitysDTO;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.service.dto.SchoolPojoDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import cn.k12soft.servo.service.mapper.SchoolPojoMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CitysMapper extends EntityMapper<Citys, CitysDTO>{
    private final SchoolRepository schoolRepository;
    private final SchoolPojoMapper schoolPojoMapper;

    public CitysMapper(SchoolRepository schoolRepository, SchoolPojoMapper schoolPojoMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolPojoMapper = schoolPojoMapper;
    }

    @Override
    protected CitysDTO convert(Citys citys) {
        Collection<SchoolPojoDTO> schools = schoolPojoMapper.toDTOs(schoolRepository.findByCityId(citys.getId()));
        return new CitysDTO(
                citys.getId(),
                citys.getName(),
                citys.getCode(),
                citys.getDepartment(),
                schools
                );
    }
}
