package cn.k12soft.servo.module.studentChargeRecord.service.mapper;

import cn.k12soft.servo.module.studentChargeRecord.domain.StudentChargeKlassTotal;
import cn.k12soft.servo.module.studentChargeRecord.domain.dto.StudentChargeKlassTotalDTO;
import cn.k12soft.servo.service.mapper.EntityMapper;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class StudentChargeKlassTotalMapper extends EntityMapper<StudentChargeKlassTotal, StudentChargeKlassTotalDTO> {
    @Override
    protected StudentChargeKlassTotalDTO convert(StudentChargeKlassTotal sckt) {

        String feeOther = sckt.getFeeOther();
        JSONObject jsonObject = JSONObject.fromObject(feeOther);
        return new StudentChargeKlassTotalDTO(
                sckt.getId(),
                sckt.getKlassId(),
                sckt.getKlassName(),
                sckt.getFeeFood(),
                sckt.getFeeEducation(),
                jsonObject,
                sckt.getFeeTotal(),
                sckt.getFeeAllTotal(),
                sckt.getSchoolId(),
                sckt.getCreateAt()
        );
    }
}
