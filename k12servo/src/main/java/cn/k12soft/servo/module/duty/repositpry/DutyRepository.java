package cn.k12soft.servo.module.duty.repositpry;

import cn.k12soft.servo.module.VacationTeacher.VacationTeacherUtil;
import cn.k12soft.servo.module.duty.domain.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DutyRepository extends JpaRepository<Duty, Long>, JpaSpecificationExecutor<Duty> {
    Collection<Duty> findBySchoolId(Integer schoolId);

    Collection<Duty> findBySchoolIdAndIsSubstratum(Integer schoolId, VacationTeacherUtil.TRUE_FALSE isSubstratum);

    Duty findByName(String name);
}
