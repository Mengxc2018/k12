package cn.k12soft.servo.module.revenue.repository;

import cn.k12soft.servo.module.revenue.domain.IncomeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liubing on 2018/8/23
 */
@Repository
public interface IncomeDetailRepository extends JpaRepository<IncomeDetail, Integer> {
    List<IncomeDetail> findByExpenseIdAndTheYearMonth(Integer id, Integer theYearMonth);
}
