package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.News;
import cn.k12soft.servo.domain.School;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

  List<News> findAllBySchoolId(Integer schoolId);

  List<News> findAllBySchoolIdAndUpdatedAtAfter(School school, Instant from);
}

