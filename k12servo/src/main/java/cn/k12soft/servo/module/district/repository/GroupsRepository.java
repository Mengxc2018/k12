package cn.k12soft.servo.module.district.repository;

import cn.k12soft.servo.module.zone.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long>, JpaSpecificationExecutor<Groups>{
    Integer countByCode(String codeStr);

    Groups findById(Integer groupId);

    Collection<Groups> findByName(String groupsName);

    Groups findByCode(String code);

}
