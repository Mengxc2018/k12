package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.News;
import cn.k12soft.servo.domain.NewsEvent;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.enumeration.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NewsEventRepository extends JpaRepository<NewsEvent, Long> {

    List<NewsEvent> findAllBySchoolIdAndIdGreaterThan(Integer schoolId, Long fromEventId);

    List<NewsEvent> findAllByNewsIdInAndIdGreaterThan(Set<News> newsSet, Long fromEventId);
}
