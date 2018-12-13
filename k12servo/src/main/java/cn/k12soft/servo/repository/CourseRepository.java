package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/2.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
