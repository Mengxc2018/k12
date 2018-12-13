package cn.k12soft.servo.module.rateGroup.repository;

import cn.k12soft.servo.module.rateGroup.domain.GroupStudentRate;
import cn.k12soft.servo.module.zone.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GroupStuRateRepositdry extends JpaRepository<GroupStudentRate, Long>, JpaSpecificationExecutor<GroupStudentRate>{
    GroupStudentRate queryByGroups(Groups group);

    Collection<GroupStudentRate> findByGroups(Groups groups);
}
