package cn.k12soft.servo.module.attendanceRate.repository;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.attendanceRate.domain.StudentSchoolRate;
import cn.k12soft.servo.module.zone.domain.Citys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface StudentSchoolRateRepository extends JpaSpecificationExecutor<StudentSchoolRate>, JpaRepository<StudentSchoolRate, Long>{
    StudentSchoolRate findBySchoolId(Integer schoolId);

    Collection<StudentSchoolRate> queryBySchool(School schoolId, LocalDate date);

    Collection<StudentSchoolRate> findByCityId(Integer cityId);
}
