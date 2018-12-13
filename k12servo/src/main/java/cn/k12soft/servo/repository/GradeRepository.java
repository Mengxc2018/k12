package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Grade;
import cn.k12soft.servo.domain.School;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

  List<Grade> findAllBySchoolId(Integer schoolId);

  Grade findByschoolIdAndDescription(Integer schoolId, String tuo);
}
