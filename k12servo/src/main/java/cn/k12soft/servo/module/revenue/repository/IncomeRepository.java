package cn.k12soft.servo.module.revenue.repository;

import cn.k12soft.servo.module.revenue.domain.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Created by liubing on 2018/3/20
 */
@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
    Page<Income> findBySchoolIdAndCreateAtBetween(Integer schoolId, Instant from, Instant to, Pageable pageable);

    List<Income> findAllBySchoolIdAndCreateAtBetween(Integer schoolId, Instant from, Instant to);

    List<Income> findAllBySchoolIdAndKlassTypeAndKlassIdAndCreateAtBetween(int schoolId, int klassType, int klassId, Instant begin, Instant end);
}
