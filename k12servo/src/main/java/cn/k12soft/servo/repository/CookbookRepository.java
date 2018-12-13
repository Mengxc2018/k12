package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Cookbook;
import cn.k12soft.servo.domain.School;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/7.
 */
@Repository
public interface CookbookRepository extends JpaRepository<Cookbook, Integer> {

  List<Cookbook> findAllBySchoolId(Integer schoolId);

  List<Cookbook> findAllBySchoolIdAndUpdatedAtAfter(Integer schoolId, Instant from);
}
