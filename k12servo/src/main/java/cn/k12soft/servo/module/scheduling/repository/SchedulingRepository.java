package cn.k12soft.servo.module.scheduling.repository;

import cn.k12soft.servo.module.scheduling.domain.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long>, JpaSpecificationExecutor<Scheduling> {

    Collection<Scheduling> findBySchoolId(Integer schoolId);

    Collection<Scheduling> findBySchedulingTypeAndSchoolId(String schedulingType, Integer schoolId);

}
