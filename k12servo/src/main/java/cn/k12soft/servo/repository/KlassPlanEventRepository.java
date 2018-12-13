package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.KlassPlanEvent;
import cn.k12soft.servo.domain.enumeration.PlanEventType;
import cn.k12soft.servo.domain.enumeration.PlanType;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/6.
 */
@Repository
public interface KlassPlanEventRepository extends JpaRepository<KlassPlanEvent, Integer> {

  List<KlassPlanEvent> findAllByKlassInAndIdGreaterThan(Set<Klass> klasses, Long id);

  List<KlassPlanEvent> findAllByIdGreaterThan(Long fromId);

  List<KlassPlanEvent> findAllBySchoolIdAndIdGreaterThan(Integer schoolId, Long id);

  List<KlassPlanEvent> findAllByKlass_IdAndPlanTypeAndIdGreaterThan(Integer klassId,
                                                                    PlanType planType,
                                                                    Long eventId);

  List<KlassPlanEvent> findAllByKlass_IdAndTypeAndIdGreaterThan(Integer klassId, PlanEventType type, Long fromId);

  int countAllByKlass_IdAndTypeAndPlanTypeAndIdGreaterThan(Integer klassId, PlanEventType eventType, PlanType planType, Long fromId);
}
