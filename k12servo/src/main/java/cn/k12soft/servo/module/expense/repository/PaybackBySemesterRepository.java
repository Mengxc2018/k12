package cn.k12soft.servo.module.expense.repository;

import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.PaybackBySemester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liubing on 2018/4/11
 */
public interface PaybackBySemesterRepository extends JpaRepository<PaybackBySemester, Integer>{
    List<PaybackBySemester> findAllByEntry(ExpenseEntry expenseEntry);

    void deleteByEntry(ExpenseEntry expenseEntry);
}
