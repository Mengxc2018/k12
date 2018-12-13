package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.KlassFeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface KlassFeedCommentRepository extends JpaRepository<KlassFeedComment, Long> {

}
