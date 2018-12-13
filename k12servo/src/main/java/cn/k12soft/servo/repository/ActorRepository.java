package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.enumeration.ActorType;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:fenghua.wng@live.com">fenghua.wang</a> Created on 2017/8/13.
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

  List<Actor> findAllBySchoolIdAndTypesContains(Integer schoolId, ActorType type);

    @Query(value = "SELECT * FROM actor a"
            + " JOIN user u ON u.id = a.user_id"
            + " JOIN actor_types att ON att.actor_id = a.id"
            + " WHERE a.school_id = :schoolId"
            + " AND att.TYPES != 'PATRIARCH'", nativeQuery = true)
    Collection<Actor> findByNoPATRIARCH(@Param("schoolId") Integer schoolId);

  void deleteByUserId(Integer userId);

    List<Actor> findByParentId(Integer parentId);

    List<Actor> findByUserId(Integer id);

    @Query(value = "SELECT * from actor"
            + " WHERE JOINED_AT = ("
            + "  SELECT MAX(a.JOINED_AT) from actor a"
            + "   WHERE user_id = :userId)"
            + " AND user_id = :userId", nativeQuery = true)
    Actor findByUserIdAndMaxCreatedAt(@Param("userId") Integer userIid);
}
