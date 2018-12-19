package cn.k12soft.servo.module.employees.repository;

import cn.k12soft.servo.module.employees.domain.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    Employee findByActorId(Integer actorId);

    Collection<Employee> findBySchoolId(Integer schoolId);

    @Query(value = "SELECT a.id AS actorId, u.username AS userName, IFNULL(d.id, '') AS dutyId, IFNULL(d.name, '') AS dutyName, u.mobile AS mobile"
            + " FROM actor a"
            + " JOIN user u ON a.USER_ID = u.ID "
            + " JOIN actor_types ay ON ay.ACTOR_ID = a.ID "
            + " LEFT JOIN employee e ON e.actor_id = a.id"
            + " LEFT JOIN duty d ON d.id = e.duty_id"
            + " WHERE ay.TYPES = 'TEACHER'"
            + " AND !ISNULL(e.id)"
            + " And a.school_Id = :schoolId", nativeQuery = true)
    List<Object[]> findAssigneds(@Param("schoolId") Integer schoolId);


    @Query(value = "SELECT a.id AS actorId, u.username AS userName, IFNULL(d.id, '') AS dutyId, IFNULL(d.name, '') AS dutyName, u.mobile AS mobile"
            + " FROM actor a"
            + " JOIN user u ON a.USER_ID = u.ID "
            + " JOIN actor_types ay ON ay.ACTOR_ID = a.ID "
            + " LEFT JOIN employee e ON e.actor_id = a.id"
            + " LEFT JOIN duty d ON d.id = e.duty_id"
            + " WHERE ay.TYPES = 'TEACHER'"
            + " AND ISNULL(e.id)"
            + " And a.school_Id = :schoolId", nativeQuery = true)
    List<Object[]> findUnAssigneds(@Param("schoolId") Integer schoolId);

    Collection<Employee> findBySchoolIdAndActorId(Integer schoolId, Integer actorId);

    @Query(value = "SELECT MAX(actor_num)"
            + " FROM employee"
            + " WHERE actor_num > :numMix"
            + "  AND actor_num < :numMax", nativeQuery = true)
    Long findMaxActorNumAndActorNumBetween(@Param("numMix") String numMix, @Param("numMax") String numMax);

    Employee queryBySchoolIdAndActorId(Integer schoolId, Integer actorId);

    Optional<Employee> queryByActorId(Integer teacherActor);

    Collection<Employee> findBySchoolIdAndIsLeaveAndLeaveAtBetween(Integer schoolId, boolean b, Instant first, Instant second, Sort lleaveAt);

    Collection<Employee> findBySchoolIdAndSalaryGreaterThan(Integer schoolId, float i);
}
