package cn.k12soft.servo.module.charge.repository;

import cn.k12soft.servo.module.charge.domain.RecruitStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitStudentRepository extends JpaRepository<RecruitStudent, Integer> {
    RecruitStudent findByStudentId(Integer studentId);

    Page<RecruitStudent> findBySchoolId(Integer schoolId, Pageable pageable);

    List<RecruitStudent> findByKlassId(Integer klassId);
}
