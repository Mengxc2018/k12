package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Teaching;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface TeachingRepository extends JpaRepository<Teaching, Integer> {

  List<Teaching> findAllByTeacherId(Integer teacherId);

  Optional<Teaching> findByTeacherIdAndKlass(Integer teacherId, Klass klass);

  List<Teaching> findAllBySchoolId(Integer schoolId);
}
