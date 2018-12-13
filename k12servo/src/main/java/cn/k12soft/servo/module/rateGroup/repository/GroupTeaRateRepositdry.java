package cn.k12soft.servo.module.rateGroup.repository;

import cn.k12soft.servo.module.rateGroup.domain.GroupTeacherRate;
import cn.k12soft.servo.module.zone.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GroupTeaRateRepositdry extends JpaRepository<GroupTeacherRate, Long>, JpaSpecificationExecutor<GroupTeacherRate>{
    GroupTeacherRate queryByGroups(Groups group);

    Collection<GroupTeacherRate> findByGroups(Groups groups);
}
