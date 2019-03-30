package cn.k12soft.servo.repository;

import cn.k12soft.servo.domain.User;

import java.util.Collection;
import java.util.Optional;

import cn.k12soft.servo.web.form.UserPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByMobile(String mobile);

    User queryByMobile(String mobileStr);

    @Query(value = "select "
            + " u.id as id,"
            + " u.username as username,"
            + " u.mobile as mobile,"
            + " u.user_state as userState,"
            + " s.name as sutName,"
            + " (select name from klass k where k.id = :klassId) as klassName"
            + " from user u"
            + " JOIN actor a ON a.user_id = u.id"
            + " JOIN actor_types att ON att.actor_id = a.id"
            + " JOIN guardian g ON g.patriarch_id = a.id"
            + " JOIN student s ON g.student_id = s.id"
            + " WHERE u.user_state = 'INACTIVE'"
            + " AND s.klass_id = :klassId"
            + " AND att.types = 'PATRIARCH'"
            + " AND a.school_id = :schoolId",nativeQuery = true)
    Collection<Object[]> findAllByUserStateByKlassId(@Param("klassId") Integer klassId,
                                                     @Param("schoolId") Integer schoolId);

    @Query(value = "select "
            + " u.id as id,"
            + " u.username as username,"
            + " u.mobile as mobile,"
            + " u.user_state as userState,"
            + " s.name as sutName,"
            + " (select name from klass k where k.id = s.klass_Id) as klassName"
            + " from user u"
            + " JOIN actor a ON a.user_id = u.id"
            + " JOIN actor_types att ON att.actor_id = a.id"
            + " JOIN guardian g ON g.patriarch_id = a.id"
            + " JOIN student s ON g.student_id = s.id"
            + " WHERE u.user_state = 'INACTIVE'"
            + " AND att.types = 'PATRIARCH'"
            + " AND a.school_id = :schoolId",nativeQuery = true)
    Collection<Object[]> findAllByUserState(@Param("schoolId") Integer schoolId);

}
