package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.KlassFeed;
import cn.k12soft.servo.domain.KlassFeedLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wang@codepoch.com">fenghua.wang</a> Created on 2017/8/20.
 */
@Repository
public interface KlassFeedLikeRepository extends JpaRepository<KlassFeedLike, Long> {

  List<KlassFeedLike> findAllByFeed(KlassFeed klassFeed);
}
