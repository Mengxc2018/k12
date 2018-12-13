package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.KlassPlan;
import cn.k12soft.servo.domain.enumeration.PlanType;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface KlassPlanRepository extends JpaRepository<KlassPlan, Integer> {

  List<KlassPlan> findAllByKlassAndPlanType(Klass klass, PlanType type);

  List<KlassPlan> findAllByKlassAndPlanTypeAndUpdatedAtAfter(Klass klass, PlanType type,
                                                             Instant from);
}
