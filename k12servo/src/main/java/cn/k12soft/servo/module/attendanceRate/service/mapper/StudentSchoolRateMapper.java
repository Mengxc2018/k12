package cn.k12soft.servo.module.attendanceRate.service.mapper;

import cn.k12soft.servo.module.attendanceRate.domain.StudentSchoolRate;
import cn.k12soft.servo.module.attendanceRate.domain.dto.StudentSchoolRateDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentSchoolRateMapper extends EntityMapper<StudentSchoolRate, StudentSchoolRateDTO>{
    @Override
    protected StudentSchoolRateDTO convert(StudentSchoolRate studentSchoolRate) {
        return new StudentSchoolRateDTO(
                studentSchoolRate.getId(),
                studentSchoolRate.getSchool(),
                studentSchoolRate.getJanuary(),
                studentSchoolRate.getFebruary(),
                studentSchoolRate.getMarch(),
                studentSchoolRate.getApril(),
                studentSchoolRate.getMay(),
                studentSchoolRate.getJune(),
                studentSchoolRate.getJuly(),
                studentSchoolRate.getAuguest(),
                studentSchoolRate.getSeptember(),
                studentSchoolRate.getOctober(),
                studentSchoolRate.getNovember(),
                studentSchoolRate.getDecember(),
                studentSchoolRate.getCreatedAt()
        );
    }
}
