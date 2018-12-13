package cn.k12soft.servo.module.attendanceRate.service.mapper;

import cn.k12soft.servo.module.attendanceRate.domain.StudentKlassRate;
import cn.k12soft.servo.module.attendanceRate.domain.dto.StudentKlassRateDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentKlassRateMapper extends EntityMapper<StudentKlassRate, StudentKlassRateDTO>{
    @Override
    protected StudentKlassRateDTO convert(StudentKlassRate studentKlassRate) {
        return new StudentKlassRateDTO(
                studentKlassRate.getId(),
                studentKlassRate.getKlass(),
                studentKlassRate.getSchool(),
                studentKlassRate.getJanuary(),
                studentKlassRate.getFebruary(),
                studentKlassRate.getMarch(),
                studentKlassRate.getApril(),
                studentKlassRate.getMay(),
                studentKlassRate.getJune(),
                studentKlassRate.getJuly(),
                studentKlassRate.getAuguest(),
                studentKlassRate.getSeptember(),
                studentKlassRate.getOctober(),
                studentKlassRate.getNovember(),
                studentKlassRate.getDecember(),
                studentKlassRate.getCreatedAt()
        );
    }
}
