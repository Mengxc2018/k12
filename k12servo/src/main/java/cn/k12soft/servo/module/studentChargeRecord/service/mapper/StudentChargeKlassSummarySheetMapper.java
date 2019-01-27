package cn.k12soft.servo.module.studentChargeRecord.service.mapper;

import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeKlassTotal;
import cn.k12soft.servo.module.studentChargeRecord.domain.dto.StudentChargeKlassSummarySheetDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentChargeKlassSummarySheetMapper extends EntityMapper<StudentChargeKlassTotal, StudentChargeKlassSummarySheetDTO> {
    @Override
    protected StudentChargeKlassSummarySheetDTO convert(StudentChargeKlassTotal sckt) {
        return new StudentChargeKlassSummarySheetDTO(
                sckt.getKlassId(),
                sckt.getKlassName(),
                sckt.getListed(),
                sckt.getFeeTotal(),
                sckt.getFeeAllTotal(),
                sckt.getSchoolId(),
                sckt.getCreateAt(),
                sckt.getRemark()
        );
    }
}
