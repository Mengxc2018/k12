package cn.k12soft.servo.module.expense.repository;

import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.ExpenseIdentDiscount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2017/12/16.
 */
@org.springframework.stereotype.Repository
public interface ExpenseIdentDiscountRepository extends JpaRepository<ExpenseIdentDiscount, Integer> {

  List<ExpenseIdentDiscount> findAllByEntry(ExpenseEntry expenseEntry);

    void deleteByEntry(ExpenseEntry entry);
}
