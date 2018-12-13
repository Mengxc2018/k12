package cn.k12soft.servo.module.vedioMonitor.repository;

import cn.k12soft.servo.module.vedioMonitor.domain.HKUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by liubing on 2018/3/31
 */
@Repository
public interface HKUserRepository extends JpaRepository<HKUser, Integer> {

    HKUser findByUserId(Integer userId);

    Collection<HKUser> findAllBySchoolId(Integer schoolId);

    HKUser findBySchoolIdAndUserId(Integer schoolId, Integer userId);

    Collection<HKUser> findAllBySchoolIdAndKlassId(Integer schoolId, Integer klassId, Sort state);
}
