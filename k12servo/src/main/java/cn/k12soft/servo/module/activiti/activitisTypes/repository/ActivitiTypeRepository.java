package cn.k12soft.servo.module.activiti.activitisTypes.repository;

import cn.k12soft.servo.module.activiti.activitisTypes.domain.ActivitisTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ActivitiTypeRepository extends JpaRepository<ActivitisTypes, Long>, JpaSpecificationExecutor<ActivitisTypes>{

    @Query(value = "SELECT MAX(type_no) FROM activitis_types", nativeQuery = true)
    Long findMaxByTypeNo();

    Collection<ActivitisTypes> findBySchoolId(Integer schoolId);
}
