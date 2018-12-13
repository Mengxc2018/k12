package cn.k12soft.servo.module.schedulingPerson.repository;

import cn.k12soft.servo.module.schedulingPerson.domian.SchedulingPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SchedulingPersonRepository extends JpaRepository<SchedulingPerson, Long>, JpaSpecificationExecutor<SchedulingPerson> {

    SchedulingPerson findByActorId(Integer actor);

    Collection<SchedulingPerson> findBySchoolId(Integer schoolId);

    @Query(value = "UPDATE scheduling_person"
            + " SET annaul = :sAnnaul"
            + " WHERE id = :id", nativeQuery = true)
    void updateAnnualById(Integer sAnnaul, long id);

    @Query(value = "UPDATE scheduling_person"
            + " SET sick = :sSick"
            + " WHERE id = :id", nativeQuery = true)
    void updateSickById(@Param("sSick") Integer sSick,
                        @Param("id") long id);

    Collection<SchedulingPerson> findBySchoolIdAndActorId(Integer schoolId, Integer actorId);

    @Query(value = "SELECT COUNT(*) FROM scheduling_person where actor_id = :id", nativeQuery = true)
    Integer countByActorId(@Param("id") Integer id);
}
