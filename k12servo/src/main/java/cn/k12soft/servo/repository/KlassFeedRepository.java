package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.KlassFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

@org.springframework.stereotype.Repository
public interface KlassFeedRepository extends JpaRepository<KlassFeed, Long> {

  int countAllByKlass_IdAndIdGreaterThan(Integer klassId, Long eventId);

    List<KlassFeed> findAllBySchoolIdAndKlassAndCreatedAtBetween(Integer schoolId, Klass klass, Instant first, Instant second);

    @Query(value = "SELECT * FROM klass_feed_pictures"
            + " WHERE KLASS_FEED_ID = :klassFeedId", nativeQuery = true)
  List<String> findAllByPictures(@Param("klassFeedId") Long klassFeedId);
}
