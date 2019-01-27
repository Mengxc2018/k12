package cn.k12soft.servo.module.account.service.mapper;

import cn.k12soft.servo.module.account.domain.StudentAccountChangeRecord;
import cn.k12soft.servo.module.account.domain.dto.StudentAccountChangeRecordDTO;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public abstract class StudentAccountChangeRecordMapper extends EntityMapper<StudentAccountChangeRecord, StudentAccountChangeRecordDTO> {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private KlassRepository klassRepository;

    @Override
    protected StudentAccountChangeRecordDTO convert(StudentAccountChangeRecord stu) {
        return new StudentAccountChangeRecordDTO(
//                stu.getId(),
//                stu.getKlassId(),
//                klassRepository.findOne(stu.getKlassId()).getName(),
//                stu.getStudentAccountId(),
//                stu.getStudentId(),
//                studentRepository.findOne(stu.getStudentId()).getName(),
//                stu.getChangeMoney(),
//                stu.getRemainMoney(),
//                stu.getCreatedBy(),
//                stu.getCreateAt(),
//                stu.getOpType()
        );
    }

    public abstract Page<StudentAccountChangeRecordDTO> toDTOs(Page<StudentAccountChangeRecord> byStudent);
}
