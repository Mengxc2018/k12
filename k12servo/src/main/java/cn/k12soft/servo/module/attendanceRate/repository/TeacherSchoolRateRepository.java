package cn.k12soft.servo.module.attendanceRate.repository;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.attendanceRate.domain.TeacherSchoolRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TeacherSchoolRateRepository extends JpaSpecificationExecutor<TeacherSchoolRate>, JpaRepository<TeacherSchoolRate, Long>{
    Collection<TeacherSchoolRate> findBySchoolId(Integer schoolId);

    TeacherSchoolRate queryBySchool(School school);

    TeacherSchoolRate findByActorId(Integer actorId);
}
