package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.InterestKlass;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestKlassRepository extends JpaRepository<InterestKlass, Integer> {

  List<InterestKlass> findAllBySchoolId(Integer schoolId);

  InterestKlass findByName(String iklassName);

    InterestKlass findByNameAndSchoolId(String iklassName, Integer schoolId);
}
