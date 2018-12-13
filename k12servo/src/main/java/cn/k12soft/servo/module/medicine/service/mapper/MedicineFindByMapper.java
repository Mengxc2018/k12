package cn.k12soft.servo.module.medicine.service.mapper;

import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.module.medicine.domain.Medicine;
import cn.k12soft.servo.module.medicine.domain.dto.MedicineFindByDTO;
import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicineFindByMapper extends EntityMapper<Medicine, MedicineFindByDTO>{
    private final StudentRepository studentRepository;

    @Autowired
    public MedicineFindByMapper(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    protected MedicineFindByDTO convert(Medicine medicine) {
        Student student = studentRepository.findOne(medicine.getStuId());
        return new MedicineFindByDTO(
                medicine.getId(),
                medicine.getStuName(),
                student.getAvatar(),
                medicine.getExecuteTime()
        );
    }
}
