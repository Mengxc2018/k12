package cn.k12soft.servo.module.expense.repository;

import cn.k12soft.servo.module.expense.domain.ExpenseEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a>
 * Created on 2017/12/16.
 */
@org.springframework.stereotype.Repository
public interface ExpenseEntryRepository extends JpaRepository<ExpenseEntry, Integer> {

  List<ExpenseEntry> findAllBySchoolId(Integer schoolId);

    ExpenseEntry findAllByNameAndSchoolId(String expenseName, Integer schoolId);
}
