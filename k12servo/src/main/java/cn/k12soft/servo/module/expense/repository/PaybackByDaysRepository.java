package cn.k12soft.servo.module.expense.repository;

import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.PaybackByDays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liubing on 2018/4/11
 */
public interface PaybackByDaysRepository extends JpaRepository<PaybackByDays, Integer> {
    List<PaybackByDays> findAllByEntry(ExpenseEntry expenseEntry);

    void deleteByEntry(ExpenseEntry expenseEntry);
}
