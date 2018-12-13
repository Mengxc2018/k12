package cn.k12soft.servo.module.attendanceRate.repository;

import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.attendanceRate.domain.SchoolTeaRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SchoolTeaRateRepository extends JpaRepository<SchoolTeaRate, Long>, JpaSpecificationExecutor<SchoolTeaRate>{
    Collection<SchoolTeaRate> findByCityId(Integer cityId);

    SchoolTeaRate findBySchool(School school);
}
