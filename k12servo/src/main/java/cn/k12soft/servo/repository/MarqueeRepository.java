package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Marquee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xfnjlove on 2017/12/26.
 */
@Repository
public interface MarqueeRepository extends JpaRepository<Marquee, Integer> {

  List<Marquee> findAllBySchoolId(Integer schoolId);

}