package cn.k12soft.servo.module.expense.repository;

import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import cn.k12soft.servo.module.expense.domain.PaybackRule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 */
@Repository
public interface PaybackRuleRepository extends JpaRepository<PaybackRule, Integer> {

  List<PaybackRule> findAllBySchoolId(Integer schoolId);

  List<PaybackRule> findAllByEntry(ExpenseEntry expenseEntry);

  void deleteByEntry(ExpenseEntry entry);
}
