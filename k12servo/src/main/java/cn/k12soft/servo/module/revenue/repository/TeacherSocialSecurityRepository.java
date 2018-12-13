package cn.k12soft.servo.module.revenue.repository;

import cn.k12soft.servo.module.revenue.domain.PayoutMainType;
import cn.k12soft.servo.module.revenue.domain.PayoutSubType;
import cn.k12soft.servo.module.revenue.domain.TeacherSocialSecurity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherSocialSecurityRepository extends JpaRepository<TeacherSocialSecurity, Integer> {
    Page<TeacherSocialSecurity> findBySchoolId(Integer schoolId, Pageable pageable);

    Page<TeacherSocialSecurity> findByNameAndSchoolId(String name, Integer schoolId, Pageable pageable);

    TeacherSocialSecurity findByActorId(String actorId);

    List<TeacherSocialSecurity> findBySchoolId(Integer schoolId);
}
