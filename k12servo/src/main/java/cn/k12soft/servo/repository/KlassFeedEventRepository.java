package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.KlassFeedEvent;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/6.
 */
@Repository
public interface KlassFeedEventRepository extends JpaRepository<KlassFeedEvent, Long> {

  List<KlassFeedEvent> findAllByKlassInAndIdGreaterThan(Set<Klass> klasses, Long id);

  Page<KlassFeedEvent> findAllByKlassInAndIdGreaterThan(Set<Klass> klasses, Long id, Pageable limit);

  Page<KlassFeedEvent> findAllByKlassInAndIdGreaterThanOrderByIdDesc(Set<Klass> klasses, Long id, Pageable limit);

  List<KlassFeedEvent> findAllByIdGreaterThan(long fromId);

  List<KlassFeedEvent> findAllBySchoolIdAndIdGreaterThan(Integer schoolId, Long id);

  Page<KlassFeedEvent> findAllBySchoolIdAndIdGreaterThan(Integer schoolId, Long id, Pageable limit);

  Page<KlassFeedEvent> findAllBySchoolIdAndIdGreaterThanOrderByIdDesc(Integer schoolId, Long id, Pageable limit);
}
