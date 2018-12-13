package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Vacation;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/9/10.
 */
@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

    List<Vacation> findAllByKlassIdAndCreatedAtBetween(Integer klassId, Instant from, Instant to);

    Collection<Vacation> findAllByKlassIdAndStudentIdAndCreatedAtBetween(Integer klassId,
                                                                         Integer studentId,
                                                                         Instant startOfDay,
                                                                         Instant endOfDay);

    Collection<Vacation> findAllByStudentIdAndCreatedAtBetween(Integer studentId,
                                                               Instant startOfDay,
                                                               Instant endOfDay);

    Collection<Vacation> findAllByCreatedAtBetween(Instant startOfDay, Instant endOfDay);

    @Query(value = "select * FROM vacation v"
            + " WHERE v.klass_ID = :klassId"
            + " AND DATE(v.FROM_DATE) <= DATE(:instant)"
            + " AND DATE(v.TO_DATE) >= DATE(:instant)", nativeQuery = true)
    Collection<Vacation> findAllByKlassIdAndCreatedAtBetween(@Param("klassId") Integer klassId,
                                                             @Param("instant") Instant instant);

    List<Vacation> findAllBySchoolIdAndKlassIdAndStudentIdAndCreatedAtBetween(Integer schoolId, Integer klassId, Integer stuId, Instant first, Instant second);
}
