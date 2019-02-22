package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import cn.k12soft.servo.domain.enumeration.ActorState;
import cn.k12soft.servo.domain.enumeration.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByMobile(String mobile);

    User queryByMobile(String mobileStr);

    @Query(value = "select * from user u"
            + " JOIN actor a ON a.user_id = u.id"
            + " WHERE u.user_state = :inactive"
            + " AND u.is_one_self = :b"
            + " AND a.school_id = :schoolId",nativeQuery = true)
    Collection<User> findAllByUserStateAndIsOneSelf(@Param("schoolId") Integer schoolId,
                                                    @Param("inactive") UserState inactive,
                                                    @Param("b") boolean b);
}
