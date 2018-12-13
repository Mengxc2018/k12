package cn.k12soft.servo.module.applyFor.repository;

import cn.k12soft.servo.module.applyFor.domain.ApplyFor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Repository
public interface ApplyForRepository extends JpaRepository<ApplyFor, Long >, JpaSpecificationExecutor<ApplyFor> {

    List<ApplyFor> findAllByMasterIdAndAuditResultIsNull(Integer masterId);

    @Query(value = "SELECT * FROM apply_for"
            + " WHERE create_time = "
            + " (SELECT MAX(create_time) FROM  apply_for"
            + "   WHERE process_instance_id = :processInstanceId"
            + "   AND actor_id = :actorId"
            + "   AND process_type = :processType)"
            + " AND process_instance_id = :processInstanceId"
            + " AND actor_id = :actorId"
            + " AND process_type = :processType", nativeQuery = true)
    ApplyFor findMaxCreateTimeByProcessInstanceIdAndActorIdAndProcessType(@Param("processInstanceId") String getProcessInstanceId,
                                                                          @Param("actorId") Integer actorId,
                                                                          @Param("processType") Integer processType);

    @Query(value = "SELECT * FROM apply_for"
            + " WHERE create_time = "
            + " (SELECT MAX(create_time) FROM  apply_for"
            + "   WHERE process_instance_id = :processInstanceId)"
            + " AND process_instance_id = :processInstanceId" , nativeQuery = true)
    ApplyFor findMaxCreateTimeByProcessInstanceId(@Param("processInstanceId") String getProcessInstanceId);

    List<ApplyFor> findByProcessInstanceId(String processInstanceId);

    Collection<ApplyFor> findAllByMasterIdAndProcessTypeAndAuditResultIsNullAndCreateTimeBetween(Integer actorId, Integer processType, Instant first, Instant seconds, Sort sort);

    Collection<ApplyFor> findByMasterIdAndProcessTypeAndCreateTimeBetweenAndAuditResultIsNotNull(Integer actorId, Integer processType, Instant first, Instant second);

    Collection<ApplyFor> findAllByMasterIdAndProcessTypeAndAuditResultIsNotNull(Integer actorId, Integer processType, Sort sort);

    ApplyFor findByProcessInstanceIdAndActorIdAndMassageIdAndProcessTypeAndAuditResultIsNull(String processInstanceId, Integer actorId, Integer massageId, Integer processType);

    ApplyFor findByMassageIdAndProcessInstanceIdAndAuditResultIsNull(Integer massageId, String processInstanceId);

    Collection<ApplyFor> findByMassageIdAndProcessInstanceIdOrderByCreateTimeAsc(Integer massageId, String processInstanceId);

    List<ApplyFor> findByMassageId(Integer id);
}
