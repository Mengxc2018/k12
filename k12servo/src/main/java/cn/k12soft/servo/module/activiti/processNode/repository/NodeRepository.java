package cn.k12soft.servo.module.activiti.processNode.repository;

import cn.k12soft.servo.module.activiti.processNode.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long>, JpaSpecificationExecutor<Node>{
    Collection<Node> findBySchoolIdAndProcessType(Integer schoolId, Integer processType);

    @Query(value = "SELECT COUNT(processkey) FROM node", nativeQuery = true)
    Integer findMaxByProcessKey();

    Node findByDiscernDutyId(Integer id);

    List<Node> findByProcessType(Integer type);
}
