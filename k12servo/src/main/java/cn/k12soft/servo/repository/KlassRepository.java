package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Grade;
import cn.k12soft.servo.domain.Klass;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@org.springframework.stereotype.Repository
public interface KlassRepository extends JpaRepository<Klass, Integer> {

  List<Klass> findAllBySchoolId(Integer schoolId);

    List<Klass> findAllBySchoolIdAndGrade(Integer schoolId, Grade grade);

  List findAllBySchoolIdAndIsGraduate(Integer schoolId, boolean b);

  @Query(value = "select * from klass k"
          + " JOIN teaching t ON t.klass_id = k.ID"
          + " WHERE k.is_graduate = :b"
          + " AND t.TEACHER_ID = :id", nativeQuery = true)
    List<Klass> findKlassByActorId(@Param("id") Integer id,
                                   @Param("b") boolean b);

  Klass findByName(String klassName);

    Klass findByNameAndSchoolId(String iklassName, Integer schoolId);
}
