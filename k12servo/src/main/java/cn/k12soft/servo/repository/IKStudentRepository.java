package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.IKStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKStudentRepository extends JpaRepository<IKStudent, Integer> {
    IKStudent findByIklassIdAndStudentId(Integer iklassId, Integer studentId);

    void deleteByIklassId(Integer iKlassId);

    List<IKStudent> findByIklassId(Integer klassId);
}
